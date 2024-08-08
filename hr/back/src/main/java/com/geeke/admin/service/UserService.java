package com.geeke.admin.service;

import cn.hutool.core.util.ObjUtil;
import com.geeke.admin.dao.UserDao;
import com.geeke.admin.dao.UserRoleDao;
import com.geeke.admin.entity.User;
import com.geeke.admin.entity.UserRole;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Service
 * @author
 * @version
 */

@Service("userService")
@Transactional(readOnly = true)
public class UserService extends CrudService<UserDao, User> {

    @Autowired
    private UserRoleDao userRoleDao;

    public long getCount(String companyId) {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("company_id", "=", null));
        PageRequest pageRequest = new PageRequest(0, 1, parameters, null);
        return dao.count(pageRequest);
    }

    @Override
    public User get(String id) {
        User user = super.get(id);
        if (ObjUtil.isNull(user)) {
            return null;
        }

        List<Parameter> params = null;
        PageRequest pageRequest;

        /*获取子表列表   用户角色*/
        params = Lists.newArrayList();

        params.add(new Parameter("user_id", "=", user.getId()));
        pageRequest = new PageRequest(params);
        user.setUserRoleList(userRoleDao.listAll(pageRequest));

        return user;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public User save(User user) {
        Map<String, String> colMaps = Maps.newHashMap();

        // 用户登录名唯一检查

        colMaps.clear();

        colMaps.put("login_name", "loginName");

        if (exists(dao, user, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "已经存在一个相同的登录名。"));
        }

        // 手机号唯一检查

        colMaps.clear();

        colMaps.put("phone", "phone");

        if (exists(dao, user, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "手机号重复！"));
        }

        User userTemp = super.save(user);

        // 加密字段处理
        if (StringUtils.isNoneBlank(userTemp.getId())) {
            // 设置加密字段  密码
            if (user.getLoginPasswordUpdate()) {
                Md5Hash md5 = new Md5Hash(user.getLoginPassword(), user.getId(), 6);
                String md5Password = md5.toHex();
                dao.updateLoginPassword(user.getId(), md5Password);
            }
        }

        /* 保存子表数据     用户角色 */
        saveUserRoleList(userTemp);

        return userTemp;
    }

    /**
     * 删除
     * @param entity
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int delete(User user) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     用户角色 */
        params = Lists.newArrayList();

        params.add(new Parameter("user_id", "=", user.getId()));
        pageRequest = new PageRequest(params);
        user.setUserRoleList(userRoleDao.listAll(pageRequest));

        if (user.getUserRoleList() != null && user.getUserRoleList().size() > 0) {
            userRoleDao.bulkDelete(preUpdateList((List) user.getUserRoleList()));
        }

        int rows = super.delete(user);
        return rows;
    }

    /**
     * 批量删除
     * @param entitys
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<User> userList) {
        for (User user : userList) {
            delete(user);
        }

        return userList.size();
    }

    // 修改加密字段

    /**
     * 修改 密码
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int changeLoginPassword(String id, String pass) {
        // Md5密码
        Md5Hash md5 = new Md5Hash(pass, id, 6);
        String md5Password = md5.toHex();

        int rows = dao.updateLoginPassword(id, md5Password);
        return rows;
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, User entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (UserRole child : entity.getUserRoleList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     用户角色 */
    private void saveUserRoleList(User user) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("user_id", "=", user.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<UserRole> list_UserRole = userRoleDao.listAll(pageRequest);

        List<UserRole> deletes = Lists.newArrayList(); // 删除列表
        List<UserRole> inserts = Lists.newArrayList(); // 添加列表
        List<UserRole> updates = Lists.newArrayList(); // 更新列表
        for (UserRole userRoleSaved : list_UserRole) {
            boolean found = false;
            for (UserRole userRole : user.getUserRoleList()) {
                if (userRoleSaved.getId().equals(userRole.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(userRoleSaved);
            }
        }
        if (deletes.size() > 0) {
            userRoleDao.bulkDelete(deletes);
        }
        for (UserRole userRole : user.getUserRoleList()) {
            if (StringUtils.isBlank(userRole.getId())) {
                userRole.setUser(user);

                userRole.preInsert();
                inserts.add(userRole);
            } else {
                userRole.preUpdate();
                updates.add(userRole);
            }
        }
        if (updates.size() > 0) {
            userRoleDao.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            userRoleDao.bulkInsert(inserts);
        }
    }
}
