package com.geeke.config.shiro;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.admin.service.UserService;
import com.geeke.common.data.Parameter;
import com.geeke.org.entity.Company;
import com.geeke.org.service.CompanyService;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.newtouch.cloud.common.dependency.remote.rest.DynamicRestTemplate;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: lys
 * @description: 自定义Realm
 * @date: 2019/10/24 10:06
 */
public class UserRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private DynamicRestTemplate dynamicRestTemplate;

    @Autowired
    private CompanyService companyService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //查询用户的权限
    	List<String> permissions = SessionUtils.getUserPermission();
        logger.info("permission的值为:" + permissions);
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);        
        
        return authorizationInfo;
    }

    /**
     * 验证当前登录的Subject
     * LoginController.login()方法中执行Subject.login()时 执行此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String loginName = (String) authcToken.getPrincipal();
        // 获取用户密码
        String password = new String((char[]) authcToken.getCredentials());
       

        // 通过组织架构微服务验证账户
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("username", loginName);
        formData.add("password", password);
        JSONObject objRespose = dynamicRestTemplate.postFORM("cloud-geeke-organisation", "/auth/authLogin", JSONObject.class, formData);
 		
		// 验证失败处理
        String code = objRespose.getString("code");
        if(ErrorEnum.E_10010.getErrorCode().equals(code)) {
            throw new UnknownAccountException();
        } else if(ErrorEnum.E_10011.getErrorCode().equals(code)) {
            throw new DisabledAccountException();
        } else if(!"100".equals(code)) {
            throw new AuthenticationException();
        }
        
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            loginName,
            password,
            getName()
        );

        //这里为了人事app网址，做了appUrl查本地，进行set但是要保证组织架构公司id与本地系统公司id相同，方可生效
        AtomicReference<JSONObject> userJson = new AtomicReference<>();
        Optional.ofNullable(JSONUtil.toBean(objRespose.getJSONObject("data").toJSONString(), User.class))
            .ifPresent(user -> {
                String companyId = user.getCompanyId();
                Company company = companyService.get(companyId);
                if(!StringUtils.isNullOrEmpty(company)) {
                    user.getCompany().setAppUrl(company.getAppUrl());
                    userJson.set((JSONObject) JSONObject.toJSON(user));
                }
            });

        //将用户信息放入session中
        SessionUtils.setUserJson(
            Optional.ofNullable(userJson.get()).orElse(objRespose.getJSONObject("data"))
        );

        return authenticationInfo;
    }
    
    private User getUserByLoginName(String loginName) {   	
    	List<Parameter> parms = Lists.newArrayList();
    	parms.add(new Parameter("login_name", "=", loginName));
        List<User> list = userService.listAll(parms, null);
        if (list == null || list.size() < 1) {
            logger.info("{} 用户账号不存在", loginName);
            return null;
        }
        return list.get(0);
    }
}
