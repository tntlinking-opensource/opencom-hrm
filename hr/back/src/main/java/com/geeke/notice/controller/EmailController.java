package com.geeke.notice.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.basic.entity.UserPropDetails;
import com.geeke.basic.service.UserPropDetailsService;
import com.geeke.notice.entity.Email;
import com.geeke.noticesend.utils.SendMail;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询列Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/notice/mail")
public class EmailController extends BaseController {
    @Autowired
    private UserPropDetailsService userPropDetailsService;

    @Autowired
    private SendMail sendMail;

        @PostMapping("/sendEmail")
        public ResponseEntity<JSONObject> getById(@RequestBody Email email) {
            UserPropDetails userPropDetails = userPropDetailsService.get(email.getSenderId());
            email.setSenderInfo(userPropDetails);
            sendMail.sentEmail(email, true);
        return ResponseEntity.ok(ResultUtil.successJson());
    }

}
