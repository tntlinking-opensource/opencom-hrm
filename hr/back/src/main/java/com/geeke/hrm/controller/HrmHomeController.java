package com.geeke.hrm.controller;

import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.admin.service.UserService;
import com.geeke.hrm.response.HrmHomeContractResponse;
import com.geeke.hrm.response.HrmHomeDataResponse;
import com.geeke.hrm.service.HrmHomeService;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.service.EmployeeInfoService;
import com.geeke.utils.QueryUtils;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yi.luo
 * @date 2023-05-31 11:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/hrm/hrmHome")
public class HrmHomeController {
    private final EmployeeInfoService employeeInfoService;
    private final HrmHomeService hrmHomeService;
    private final UserService userService;


    @GetMapping("/{employeeNo}")
    public ResponseEntity<JSONObject> getInfo(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info = employeeInfoService.findInductionInfos(employeeNo);
        List<User> empolynoList = userService.listAll(QueryUtils.queryAndList("empolyno", employeeNo), null);

        if (ObjUtil.isNull(info)) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001, "查无此人"));
        }
        if (empolynoList.size() > 1) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001, "工号重复"));
        }

        String companyId = empolynoList.get(0).getCompanyId();
        String inductionId = info.getInductionId();
        HrmHomeDataResponse hrmHomeDataResponse = hrmHomeService.getInfo(companyId, inductionId);
        return ResponseEntity.ok(ResultUtil.successJson(hrmHomeDataResponse));
    }

    @GetMapping("/contractReminder")
    public ResponseEntity<JSONObject> contractReminder() {
        HrmHomeContractResponse hrmHomeContractResponse = hrmHomeService.contractReminder();
        return ResponseEntity.ok(ResultUtil.successJson(hrmHomeContractResponse));
    }




}
