package com.geeke.ten.service;

import com.geeke.admin.dao.UserDao;
import com.geeke.admin.entity.Role;
import com.geeke.admin.entity.User;
import com.geeke.admin.entity.UserRole;
import com.geeke.admin.service.UserRoleService;
import com.geeke.admin.service.UserService;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.org.entity.Company;
import com.geeke.org.entity.Department;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.ten.dao.TenantAdminDao;
import com.geeke.ten.dao.TenantDao;
import com.geeke.ten.entity.Tenant;
import com.geeke.ten.entity.TenantAdmin;
import com.geeke.utils.Reflections;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 租户管理Service
 * @author
 * @version
 */

@Service("tenantService")
@Transactional(readOnly = true)
public class TenantService extends CrudService<TenantDao, Tenant> {

    private static final String TENANT_ADMIN_ID = "2021";

    @Autowired
    private TenantAdminDao tenantAdminDao;

    @Autowired
    private TenantAdminService tenantAdminService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Override
    public Tenant get(String id) {
        Tenant tenant = super.get(id);

        List<Parameter> params = null;
        PageRequest pageRequest;

        /*获取子表列表   租户管理员*/
        params = Lists.newArrayList();

        params.add(new Parameter("tenant_id", "=", tenant.getId()));
        pageRequest = new PageRequest(params);
        tenant.setTenantAdminList(tenantAdminDao.listAll(pageRequest));

        return tenant;
    }

    @Transactional(readOnly = false)
    @Override
    public Tenant save(Tenant tenant) {
        Tenant tenantTemp = super.save(tenant);

        /* 保存子表数据     租户管理员 */
        saveTenantAdminList(tenantTemp);

        return tenantTemp;
    }

    /**
     * 删除
     * @param tenant
     */
    @Override
    @Transactional(readOnly = false)
    public int delete(Tenant tenant) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     租户管理员 */
        params = Lists.newArrayList();

        params.add(new Parameter("tenant_id", "=", tenant.getId()));
        pageRequest = new PageRequest(params);
        tenant.setTenantAdminList(tenantAdminDao.listAll(pageRequest));

        if (tenant.getTenantAdminList() != null && tenant.getTenantAdminList().size() > 0) {
            tenantAdminService.bulkDelete(tenant.getTenantAdminList());
        }

        /* 处理子表     用户 */
        params = Lists.newArrayList();

        params.add(new Parameter("tenant_id", "=", tenant.getId()));
        pageRequest = new PageRequest(params);
        List<User> users = userDao.listAll(pageRequest);

        if (users != null && users.size() > 0) {
            userService.bulkDelete(users);
        }

        int rows = super.delete(tenant);
        return rows;
    }

    /**
     * 批量删除
     * @param tenantList
     */
    @Override
    @Transactional(readOnly = false)
    public int bulkDelete(List<Tenant> tenantList) {
        for (Tenant tenant : tenantList) {
            delete(tenant);
        }

        return tenantList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, Tenant entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (TenantAdmin child : entity.getTenantAdminList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     租户管理员 */
    private void saveTenantAdminList(Tenant tenant) {
        //先将新增的用户获取到id 回写回来

        addUserToTenantAdmin(tenant);
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("tenant_id", "=", tenant.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TenantAdmin> list_TenantAdmin = tenantAdminDao.listAll(pageRequest);

        List<TenantAdmin> deletes = Lists.newArrayList(); // 删除列表
        List<TenantAdmin> inserts = Lists.newArrayList(); // 添加列表
        List<TenantAdmin> updates = Lists.newArrayList(); // 更新列表
        for (TenantAdmin tenantAdminSaved : list_TenantAdmin) {
            boolean found = false;
            for (TenantAdmin tenantAdmin : tenant.getTenantAdminList()) {
                if (tenantAdminSaved.getId().equals(tenantAdmin.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tenantAdminSaved);
            }
        }
        if (deletes.size() > 0) {
            tenantAdminService.bulkDelete(deletes);
        }
        for (TenantAdmin tenantAdmin : tenant.getTenantAdminList()) {
            if (StringUtils.isBlank(tenantAdmin.getId())) {
                tenantAdmin.setTenant(tenant);

                inserts.add(tenantAdmin);
            } else {
                updates.add(tenantAdmin);
            }
            // 设置加密字段  备注信息
            if (tenantAdmin.getUser() != null && tenantAdmin.getUser().getLoginPassword() != null) {
                Md5Hash md5 = new Md5Hash(tenantAdmin.getUser().getLoginPassword(), tenantAdmin.getUser().getId(), 6);
                String md5Password = md5.toHex();
                tenantAdminDao.updateLoginPassword(tenantAdmin.getUser().getId(), md5Password);
            }
        }
        if (updates.size() > 0) {
            tenantAdminService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tenantAdminService.bulkInsert(inserts);
        }
        authorizedTenantAdmin(list_TenantAdmin,inserts,updates,deletes);
    }

    private void addUserToTenantAdmin(Tenant tenant) {
        //获取到没有id的User
        List<TenantAdmin> tenantAdminList = tenant.getTenantAdminList();
        if(CollectionUtils.isNotEmpty(tenantAdminList)){
            for (TenantAdmin admin : tenantAdminList) {
                User user = admin.getUser();
                if (Objects.nonNull(user) && StringUtils.isEmpty(user.getId())) {
                    //设置初始化值
                    setInitValue(user);
                    //设置租户
                    User resultUser = userService.save(user);
                    //保存返回有id的User
                    admin.setUser(resultUser);
                }
            }
        }
    }

    /**
     * 这个地方的逻辑 因为更新的时候 不知道更新了哪些数据 无法通过id去定位准确的用户和角色中间表
     * 然后 不管修改还是新增 都是修改后的数据 在授权的时候先到sys_user_role 删除所有数据 然后重新授权
     * @param oldTenantAdmins 原本数据中的管理员
     * @param inserts 新插入的管理员
     * @param updates 被更改的管理员
     * @param deletes 被删除的管理员
     */
    private void authorizedTenantAdmin(List<TenantAdmin> oldTenantAdmins,List<TenantAdmin> inserts,List<TenantAdmin> updates,List<TenantAdmin> deletes){
        //如果只有deletes不为空那就先删除一波 防止只有deletes被删数据的时候造成数据的不准确
        if(deletes.size() > 0){
            deleteUserRole(deletes);
        }
        //然后通过原本的数据 先删除掉所有的 然后再添加新增和修改的
        if(oldTenantAdmins.size() > 0){
            deleteUserRole(oldTenantAdmins);
        }
        //然后将修改的和新增的授权
        List<TenantAdmin> authorized = Lists.newArrayList();
        authorized.addAll(inserts);
        authorized.addAll(updates);
        //如果没有新增和修改的 那就是单删除了 这时候判断是否为空
        if(CollectionUtils.isNotEmpty(authorized)){
            insertUserRole(authorized);
        }
    }

    private void insertUserRole(List<TenantAdmin> tenantAdmins){
        //准备数据
        List<UserRole> userRoles = Lists.newArrayList();
        Role role = new Role();
        //租户管理员
        role.setId(TENANT_ADMIN_ID);
        //插入成功以后 给用户分配租户管理员角色
        List<User> users = tenantAdmins.stream().map(TenantAdmin::getUser).collect(Collectors.toList());
        for (User user : users) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoles.add(userRole);
        }
        userRoleService.bulkInsert(userRoles);
    }

    private void deleteUserRole(List<TenantAdmin> tenantAdmins){
        List<String> userIds = tenantAdmins.stream().map(TenantAdmin::getUser).map(User::getId).collect(Collectors.toList());
    }


    private void setInitValue(User user){
        if(Objects.nonNull(user)){
            Company company = new Company();
            company.setId("0");
            user.setCompany(company);
            Department department = new Department();
            department.setId("0");
            user.setDepartment(department);
            user.setIsLocked("0");
        }
    }
}
