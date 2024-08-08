package com.geeke.admin.common.controller;

import java.util.List;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.common.entity.DataPermission;
import com.geeke.admin.common.entity.Permission;
import com.geeke.admin.common.service.PermissionService;
import com.geeke.admin.entity.Resource;
import com.geeke.admin.entity.Router;
import com.geeke.admin.entity.User;
import com.geeke.admin.service.ResourceService;
import com.geeke.admin.service.RouterService;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;

/**
 * 资源控制器
 * 
 * @author Lining
 * @date 2018/2/11
 */
@Api(value = "/permission", tags = {"资源控制器"})
@RestController("PermissionController")
@RequestMapping("/permission")
public class PermissionController extends BaseController {
    
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RouterService routerService;
    
    @Autowired
    private ResourceService resourceService;    
    
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "routerId", value = "")
    })
    @ApiOperation(value = "通过id获取资源控制器", notes = "通过id获取资源控制器", httpMethod = "GET",response =Resource.class ,responseContainer = "List")
    @GetMapping("/resources")
    public ResponseEntity<JSONObject> listResourcePermission(@RequestParam(required = false) String routerId) {
    	User user = SessionUtils.getUser();
    	
    	List<Resource> list = this.permissionService.listResourcePermission(user.getId(), routerId);
        return ResponseEntity.ok(ResultUtil.successJson(list));
    }
    
    @ApiOperation(value = "获取启用的路由表", notes = "获取启用的路由表", httpMethod = "GET",response =Permission.class,responseContainer = "List")
    @GetMapping("/permissions")
    public ResponseEntity<JSONObject> treePermission() {
    	List<Router> listRouter = this.listUnlockedRouter();
    	List<Resource> listResource = this.listUnlockedResource();
    	
    	List<Permission> listPermission = Lists.newArrayList();
    	for(Router router: listRouter ){
    		Permission permission = new Permission();
    		permission.setId(router.getId());
    		permission.setType(Permission.ROUTER_TYPE);
            String type = "";
            switch (router.getSystemCode())
            {
                case "0": type = "(后台)";break;
                case "1": type = "(企微)";break;

            }
    		permission.setName(type+router.getName());
    		permission.setCode(router.getCode());
    		permission.setParentId(router.getParent() == null? "": router.getParent().getId());
    		listPermission.add(permission);
    	}
    	
    	for(Resource resource: listResource) {
    		if(resource.getRouter() != null) {
    			Permission permission = new Permission();
    			permission.setId(resource.getId());
    			permission.setType(Permission.RESOURCE_TYPE);
    			permission.setName(resource.getName());
    			permission.setParentId(resource.getRouter().getId());
    			listPermission.add(permission);
    		}   		    		
    	}
    			
    	List<Permission> trees = this.bulid(listPermission);
    	return ResponseEntity.ok(ResultUtil.successJson(trees));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "roleId", value = "", required = true)
    })
    @ApiOperation(value = "得到资源权限列表", notes = "得到资源权限列表", httpMethod = "GET")
    @GetMapping("/{roleId}/resourcePermissions")
    public ResponseEntity<JSONObject> listResourcePermissionByRoleId(@PathVariable("roleId") String roleId) {
    	List<String> list = this.permissionService.listResourcePermission(roleId);
    	return ResponseEntity.ok(ResultUtil.successJson(list));
    }

    @GetMapping("/{roleId}/routerPermissions")
    public ResponseEntity<JSONObject> listRouterPermissionByRoleId(@PathVariable("roleId") String roleId) {
    	List<String> list = this.permissionService.listRouterPermissionByRoleId(roleId);
    	return ResponseEntity.ok(ResultUtil.successJson(list));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "roleId", value = "", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "Permission", name = "permission", value = "", required = true)
    })
    @ApiOperation(value = "保存角色权限", notes = "保存角色权限，并且返回该表条数", httpMethod = "POST")
    @PostMapping("/{roleId}/savePermission")
    public ResponseEntity<JSONObject> savePermission(@PathVariable("roleId") String roleId, @RequestBody Permission permission) {
        int rows = this.permissionService.savePermission(roleId, permission);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "roleId", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET",response=DataPermission.class,responseContainer = "List")
    @GetMapping("/{roleId}/dataPermissions")
    public ResponseEntity<JSONObject> listDataPermissionByRoleId(@PathVariable("roleId") String roleId) {
    	List<DataPermission> list = this.permissionService.listDataPermissionByRoleId(roleId);
    	return ResponseEntity.ok(ResultUtil.successJson(list));
    }
    
    
    
    /**
     * 两层循环实现建树
     * @param list 传入的树节点列表
     * 把list数据通过 id，和parentId够造成一个树
     * @return
     */
    private List<Permission> bulid(List<Permission> list) {
        List<Permission> trees = Lists.newArrayList();
        for (Permission entity : list) {
        	//增加了调试代码
        	if(Permission.ROUTER_TYPE.equals(entity.getType()) && (StringUtils.isBlank(entity.getParentId()) ||
        			"0".equals(entity.getParentId()))) {
                trees.add(entity);
            }

            for (Permission it : list) {
                if (entity.getId().equals(it.getParentId())) {
                    if (entity.getChildren() == null) {
                    	entity.setChildren(Lists.newArrayList());
                    }                	
                	entity.getChildren().add(it);
                }
            }
        }
        return trees;
    }
    
    /**
     * 获取未禁用的路由
     * @return
     */
    private List<Router> listUnlockedRouter() { 
        List<Parameter> parms = Lists.newArrayList();
        parms.add(new Parameter("is_locked", "=", "0"));

        
        // 系统级功能路由不能被分配： 3006  代码管理;	3007  路由管理;   3003   资源管理 ;  3011  字典数据;  3009    操作日志; 3024  系统编号规则; 3026 系统主题;
        // parms.add(new Parameter("id", "not in", "(3006, 3007, 3003, 3011, 3009, 3024, 3026)", true));
        
        parms.add(new Parameter("can_permission", "=", "1"));
        
        return this.routerService.listAll(parms, null);
    }    
    
    
    /**
     * 获取未禁用的资源
     * @return
     */
    private List<Resource> listUnlockedResource() {       
        List<Parameter> parms = Lists.newArrayList();
        parms.add(new Parameter("is_locked", "=", "0"));            
        parms.add(new Parameter("is_default", "=", "0"));		// 默认的不用分配
        /* 不参与分配的资源
         * 公司管理：41105517082353672 添加公司 、54172422505193473 删除公司 
         */
        // parms.add(new Parameter("id", "not in", "(41105517082353672, 54172422505193473)", true));
        
        parms.add(new Parameter("can_permission", "=", "1"));
        return this.resourceService.listAll(parms, null);
    }
}
