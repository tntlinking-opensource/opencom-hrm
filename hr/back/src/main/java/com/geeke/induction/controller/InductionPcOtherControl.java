package com.geeke.induction.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.induction.service.EmployeeInfoService;
import com.geeke.induction.service.InductionPcOtherService;
import com.geeke.induction.service.TsmInductionBasicService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * inductionController
 *
 * @author
 */
@RestController
@RequestMapping(value = "/induction/pc/other/")
@RequiredArgsConstructor
public class InductionPcOtherControl extends BaseController {
    private final InductionPcOtherService inductionPcOtherService;
    private final TsmInductionBasicService tsmInductionBasicService;
    private final EmployeeInfoService employeeInfoService;

    /**
     * 简历下载
     */
    @GetMapping(value = "/resume/{resumeType}/{employeeNo}")
    public void resumeTranslation(@PathVariable("employeeNo") String employeeNo,
                                                      @PathVariable("resumeType") String resumeType,
                                                      HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置要下载的文件名
        TsmInductionInfo info =employeeInfoService.findInductionInfos(employeeNo);
        TsmInductionBasic basic =tsmInductionBasicService.get(info.getInductionId());
        String fileName = basic.getName()+"example.docx";

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");

        // 写入下载流
        try {
            OutputStream os = response.getOutputStream();
            os.write(inductionPcOtherService.resumeTranslation(basic,resumeType));
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 归档
     */
    @GetMapping(value = "/archive/{inductionId}")
    public ResponseEntity<JSONObject> archive(@PathVariable("inductionId") String inductionId) {
        User user = inductionPcOtherService.archive(inductionId);
        return ResponseEntity.ok(ResultUtil.successJson(user));
    }

    /**
     * 面试结果
     */
    @PostMapping(value = "/saveInterviewResult/{result}")
    public ResponseEntity<JSONObject> saveInterviewResult(@PathVariable("result") String result,
                                                          @RequestBody TsmInductionBasic entity) {
        AtomicReference<TsmPost> tsmPostResult = inductionPcOtherService.saveInterviewResult(result, entity);
        return ResponseEntity.ok(ResultUtil.successJson(tsmPostResult.get()));
    }



    /**
     * 放弃报道
     */
    @GetMapping(value = "/saveReportingType/{inductionId}")
    public ResponseEntity<JSONObject> saveReportingType(@PathVariable("inductionId") String inductionId) {
        AtomicReference<TsmPost> tsmPostResult = inductionPcOtherService.getTsmPostAtomicReference(inductionId);
        return ResponseEntity.ok(ResultUtil.successJson(tsmPostResult.get()));
    }

    /**
     * 资料验收通过
     */
    @PostMapping(value = "/saveDataValidation/{inductionId}/{result}")
    public ResponseEntity<JSONObject> saveDataValidation(@PathVariable("result") String result,
                                                         @PathVariable("inductionId") String inductionId) {
        inductionPcOtherService.saveDataValidation(result, inductionId);
        return ResponseEntity.ok(ResultUtil.successJson());
    }



    /**
     * 身份证验证
     */
    @GetMapping(value = "/checkIdCardLast/{inductionId}/{idCardLast}")
    public ResponseEntity<JSONObject> checkIdCardLast(@PathVariable("inductionId") String inductionId,
                                                         @PathVariable("idCardLast") String idCardLast) {

        if (inductionPcOtherService.checkIdCardLast(inductionId, idCardLast)) {
            return ResponseEntity.ok(ResultUtil.successJson(true));
        } else {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_502));
        }
    }




}
