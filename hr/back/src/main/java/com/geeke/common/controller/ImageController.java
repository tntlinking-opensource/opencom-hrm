package com.geeke.common.controller;

import com.geeke.induction.utils.QRCodeGenerator;
import com.geeke.sys.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * inductionController
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/common")
public class ImageController extends BaseController {

    @GetMapping(value = "/image")
    public ResponseEntity<String> getImage() throws Exception {
        // 从本地文件系统读取图片
        String imageStream = QRCodeGenerator.generateQRCode("http://172.17.10.59:5000",300,300,0,"png");
        return ResponseEntity.ok(imageStream);
    }

//    @GetMapping(value = "/image")
//    public ResponseEntity<String> getImage() throws Exception {
//        // 从本地文件系统读取图片
//        String imageStream = QRCodeGenerator.generateQRCode("http://172.17.10.59:5000",300,300,0,"png");
//        return ResponseEntity.ok(imageStream);
//    }
}
