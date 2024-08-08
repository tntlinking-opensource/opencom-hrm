package com.geeke.admin.common.controller;



import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.admin.service.UserService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;



@Api(value ="/person",tags = {"操作用户信息Controller"})
@RestController()
@RequestMapping("/person")
public class PersonController extends BaseController {

    @Autowired
    private UserService userService;
    
    @ApiOperation(value = "获取用户信息", notes = "直接获取登录人的用户信息", httpMethod = "GET",response=User.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = User.class)
    })
    @GetMapping("/me")
    public ResponseEntity<JSONObject> getCurrentUser() {
    	User user = SessionUtils.getUser();
    	String currentUserId = user.getId();
        User dto = this.userService.get(currentUserId);
        dto.setLoginPassword("");
        return ResponseEntity.ok(ResultUtil.successJson(dto));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "User", name = "userDetail", value = "", required = true)
    })
    @ApiOperation(value = "更新用户信息", notes = "更新当前登录人的用户信息", httpMethod = "PUT")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class  )
    })
    @PutMapping("/me")
    public ResponseEntity<JSONObject> updateCurrentUser(@RequestBody User userDetail) {
    	User user = SessionUtils.getUser();
        String currentUserId = user.getId();
        User dto = this.userService.get(currentUserId);
        dto.setName(userDetail.getName());
        dto.setLoginName(userDetail.getLoginName());
        dto.setLoginPassword(userDetail.getLoginPassword());
        dto.setLoginPasswordUpdate(userDetail.getLoginPasswordUpdate());
        String id = this.userService.save(dto).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

}
