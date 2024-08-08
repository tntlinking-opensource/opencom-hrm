package com.geeke.databus;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.geeke.admin.dao.UserRoleDao;
import com.geeke.admin.entity.Role;
import com.geeke.admin.entity.User;
import com.geeke.admin.entity.UserRole;
import com.geeke.common.persistence.DataEntity;
import com.geeke.common.service.CrudService;
import com.geeke.feign.DataBusService;
import com.geeke.feign.TenantGrantService;
import com.geeke.utils.IdGen;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: huihai.tang
 * @date: 2023/3/23
 */


@RestController
@RequiredArgsConstructor
public class ReceivedDataController {
    private final DataBusService dataBusService;

    public static final String MASTER_DATA_NAME = "data_type_code";
    public static final String MASTER_DATA = "data";
    public static final String RESULT_DATA = MASTER_DATA;

    private final TenantGrantService tenantGrantService;

    private final UserRoleDao userRoleDao;

    /**
     * 自定义
     * 配置需要转换实体和对应主数据类型名称(大小写不敏感)
     * 类名与name与主数据不匹配的情况
     */
    //todo 若主数据name不对应，则需要 手动构建 key-class对应关系
    //static {
    //    DataSycProcess.setEntityMap("Company", CompanyOne.class);
    //}

    //数据体模板
    //{
    //	"tenant_id" : "111222333",
    //	"app_id" : "123456",
    //	"data_type_code" : "Company",
    //	"data_type_name" : "公司",
    //	"data" :
    //	[
    //		{
    //			"id" : "12345",
    //			"code" : "comp001",
    //			"name" : "公司001",
    //			"status" : 1,
    //			……
    //},
    //{
    //			"id" : "12346",
    //			"code" : "comp002",
    //			"name" : "公司002",
    //			"status" : 1,
    //			……
    //},
    //……
    //]
    //}

    @PostMapping("/synchronizeData")
    public ResponseEntity<JSONObject> received(@RequestBody List<String> params) {
        params.stream().map(JSONObject::parseObject)
                .forEach(this::extracted);
        //解析数据 存入数据库
        //修改租户管理员权限
        setTenantAdmin(params);
        return ResponseEntity.ok(ResultUtil.successJson(params));
    }

    @PostMapping("/activeSynchronizeData")
    public ResponseEntity<JSONObject> activeSynchronize(@RequestBody JSONObject jsonObject) {
        JSONObject body = dataBusService.initdata(jsonObject).getBody();
        if (ObjUtil.isNull(body)) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_500,"查询数据总线返回数据返回为空"));
        }
        if (ObjUtil.isNull(body.get(RESULT_DATA))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_500,body.toJSONString()));
        }

        body.getJSONArray(RESULT_DATA).stream()
                .map(obj -> (JSONObject)JSONObject.toJSON(obj))
                .forEach(this::extracted);
        return ResponseEntity.ok(ResultUtil.successJson(body));
    }

    @PostMapping("/synchronizeToDataBus")
    public ResponseEntity<JSONObject> synchronizeToDataBus(@RequestHeader String tenantId ,
                                                           @RequestHeader String sourceAppId ,
                                                           @RequestBody JSONObject jsonObject) {
        ResponseEntity<JSONObject> receive = dataBusService.receive(tenantId, sourceAppId, jsonObject);
        return ResponseEntity.ok(ResultUtil.successJson(receive));
    }

    private void extracted(JSONObject jsonObject) {
        JSONArray dataArray = jsonObject.getJSONArray(MASTER_DATA);
        String entityName = jsonObject.getString(MASTER_DATA_NAME);
        Class<DataEntity> dataClass = DataSycProcess.getDataEntity(entityName);
        dataArray.stream()
                .map(JSONObject::toJSONString)
                .map(entity -> {
                    DataEntity<Object> dataEntity = JSONObject.parseObject(entity, dataClass);
                    CrudService service = DataSycProcess.getService(dataClass);
                    DataEntity dbDate = service.get(dataEntity.getId());

                    if (Objects.isNull(dbDate)) {
                        service.bulkInsert(ListUtil.of(dataEntity));
                    } else {
                        dataEntity.setUpdateDate(dbDate.getUpdateDate());
                        service.bulkUpdate(ListUtil.of(dataEntity));
                    }

                    return dataEntity;
                }).collect(Collectors.toList());
    }

    private void setTenantAdmin(List<String> params){
        if(Objects.isNull(params) || CollectionUtils.isEmpty(params)){
            return;
        }
        JSONObject user =JSONObject.parseObject(params.get(0));
        String dataTypeCode = user.getString(MASTER_DATA_NAME);
        if(!Objects.equals(dataTypeCode,"user")){
            return;
        }
        String tenantId = user.getString("tenant_id");
        JSONObject jsonObject = tenantGrantService.tenantFindAdmin(tenantId);
        if(Objects.nonNull(jsonObject) && Objects.equals(jsonObject.getString("code"),"100")){
            JSONArray data = jsonObject.getJSONArray("data");
            List<String> ids = data.toJavaList(String.class);
            List<UserRole> userRoles = new ArrayList<>();
            for (String id : ids) {
                UserRole userRole = new UserRole();
                User u = new User();
                u.setId(id);
                userRole.setUser(u);
                Role role = new Role();
                role.setId("2001");
                userRole.setId(IdGen.uuid());
                userRole.setRole(role);
                userRoles.add(userRole);
                userRole.setCreateBy("DataBus");
                userRole.setUpdateBy("DateBus");
                userRole.setCreateDate(DateUtil.now());
                userRole.setUpdateDate(DateUtil.now());
                userRole.setDelFlag("0");
            }
            DynamicDataSourceContextHolder.clear();
            //切换到对应poolName的数据源
            DynamicDataSourceContextHolder.push(tenantId);
            userRoleDao.bulkInsert(userRoles);
        }
    }
}