package com.geeke.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.common.entity.DataPermission;
import com.geeke.admin.common.service.PermissionService;
import com.geeke.admin.entity.Router;
import com.geeke.admin.entity.User;
import com.geeke.common.data.Parameter;
import com.geeke.sys.entity.PersonalTheme;
import com.geeke.sys.entity.Theme;
import com.geeke.sys.service.LoginService;
import com.geeke.sys.service.PersonalThemeService;
import com.geeke.sys.service.ThemeService;
import com.geeke.utils.JwtUtils;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value ="/auth", tags = {"登录Controller"})
@RestController
@RequestMapping("/auth")
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private PersonalThemeService personalThemeService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "loginName", value = "用户名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "password", value = "用户密码", required = true)
    })
    @ApiOperation(value = "登录", notes = "", httpMethod = "POST",responseContainer = "Map")
    @PostMapping("/token")
    public ResponseEntity<?> getToken(HttpServletRequest request,String loginName, String password) {
         //登陆
         HttpStatus httpStatus = loginService.authLogin(loginName, password);

         if(HttpStatus.UNAUTHORIZED.equals(httpStatus))	{		//账号密码错误
             return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_10010));
         } else if(HttpStatus.FORBIDDEN.equals(httpStatus)) {			//账号禁用
             return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_10011));
         } else if(HttpStatus.SERVICE_UNAVAILABLE.equals(httpStatus)) {  //验证错误
             return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_10012));
         }

        User user = SessionUtils.getUser();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("userId", user.getId().toString());
        response.put("username", user.getName());
        response.put("loginname", user.getLoginName());
        response.put("tenant", user.getTenant());
        response.put("company", user.getCompany());
        response.put("department", user.getDepartment());
        JSONObject json = new JSONObject();
        json.put("host", request.getRemoteHost());
        json.put("userId", user.getId());
        json.put("tenant_id", user.getTenant().getString("id"));
        json.put("sessionId", SecurityUtils.getSubject().getSession().getId().toString());
        String token = jwtUtils.createJWT(json.toJSONString());
        response.put("token", token);
        logger.info("User's token = {}", token);

        return ResponseEntity.ok(ResultUtil.successJson(response));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "获取token", notes = "", httpMethod = "POST",responseContainer = "Map")
    @PostMapping("/loginedtocken")
    public ResponseEntity<?> getLoginedToken(HttpServletRequest request) {
        // 是否登录过
        HttpStatus httpStatus = loginService.authLogin();

        if(!HttpStatus.OK.equals(httpStatus)) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_20011));
        }
        Map<String, Object> response = getLoginUserInfo();
        return ResponseEntity.ok(ResultUtil.successJson(response));
    }


    /**
     * 登出
     *
     * @return
     */
    @ApiOperation(value = "登出", notes = "登出", httpMethod = "POST")
    @PostMapping("/logout")
    public ResponseEntity<JSONObject> logout() {
        loginService.logout();
        return ResponseEntity.ok(ResultUtil.successJson("退出成功"));
    }


    private Map<String, Object> getLoginUserInfo() {

        // 得到可访问的router
        User user = SessionUtils.getUser();

        // 用户访问资源权限
        List<String> permissionList = permissionService.listResourcePermissionByUser(user.getId());
        SessionUtils.setUserPermission(permissionList);

        // 用户路由权限
        List<Router> listRouter = permissionService.listRouterPermission(user.getId());

        // 用户数据权限
        List<DataPermission> listDataPermission = permissionService.listDataPermissionByUserId(user.getId());

        // 获取主题
        PersonalTheme personalTheme = getPersonalTheme(user.getId());

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("routers", listRouter);
        response.put("dataPermisions", listDataPermission);
        response.put("personalTheme", personalTheme);
        return response;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "userId", value = "", required = true)
    })
    @ApiOperation(value = "获取主题信息", notes = "")
    private PersonalTheme getPersonalTheme(String userId) {
    	// 获取个性化主题
    	List<Parameter> parms = Lists.newArrayList();
        parms.add(new Parameter("user_id", "=", userId));
        List<PersonalTheme> list = personalThemeService.listAll(parms, null);
        if(list != null && list.size() > 0 ) {
        	return list.get(0);
        }

        // 系统默认主题
        PersonalTheme defaultTheme = new PersonalTheme();
        defaultTheme.setUserId(Long.parseUnsignedLong(userId));
        List<Theme> themes = themeService.listAll(Lists.newArrayList(), null);
        if(themes != null && themes.size() > 0) {
        	for(Theme t : themes) {
        		if("1".equals(t.getIsDefault())) {
        			defaultTheme.setTheme(t.getTheme());
        			return defaultTheme;
        		}
        	}
        	// 未设置默认主题时，第一个作为默认主题
        	defaultTheme.setTheme(themes.get(0).getTheme());
        }
        return defaultTheme;
    }
}
