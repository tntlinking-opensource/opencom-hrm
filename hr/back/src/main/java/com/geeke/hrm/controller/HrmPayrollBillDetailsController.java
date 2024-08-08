package com.geeke.hrm.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.admin.service.UserService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.hrm.entity.HrmPayrollBillDetails;
import com.geeke.hrm.service.HrmPayrollBillDetailsService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import java.util.List;
import java.util.Objects;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 薪资查看Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/hrm/hrmPayrollBillDetails")
public class HrmPayrollBillDetailsController extends BaseController {

    @Autowired
    private HrmPayrollBillDetailsService hrmPayrollBillDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        HrmPayrollBillDetails entity = hrmPayrollBillDetailsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<HrmPayrollBillDetails> result = hrmPayrollBillDetailsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<HrmPayrollBillDetails> result = hrmPayrollBillDetailsService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody HrmPayrollBillDetails entity) {
        String id = hrmPayrollBillDetailsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody HrmPayrollBillDetails entity) {
        int rows = hrmPayrollBillDetailsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<HrmPayrollBillDetails> entitys) {
        List<String> ids = hrmPayrollBillDetailsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<HrmPayrollBillDetails> entitys) {
        List<String> ids = hrmPayrollBillDetailsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<HrmPayrollBillDetails> entitys) {
        int rows = hrmPayrollBillDetailsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping("/checkPassword/{userId}/{password}")
    public ResponseEntity<JSONObject> checkUserPassword(@PathVariable("userId") String userId,@PathVariable("password") String password){
        User user = userService.get(userId);
        Md5Hash md5 = new Md5Hash(password, user.getId(), 6);
        String md5Password = md5.toHex();
        return ResponseEntity.ok(ResultUtil.successJson(Objects.equals(md5Password,user.getLoginPassword())));
    }
}
