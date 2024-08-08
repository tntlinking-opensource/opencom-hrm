package com.geeke.sys.controller;

import cn.hutool.json.JSONObject;
import com.geeke.feign.FileService;
import com.geeke.sys.entity.SysFileContent;
import com.geeke.sys.service.SysFileContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 系统附件字节Controller
 * @author szy
 * @version 2021-08-26
 */
@Api(value = "/sys/fileContent", tags = {"系统附件字节Controller"})
@RestController
@RequestMapping(value = "/sys/fileContent")
public class SysFileContentController extends BaseController {

	@Autowired
	private SysFileContentService sysFileContentService;
    @Autowired
    private FileService fileService;
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取系统附件字节", notes = "通过id获取系统附件字节", httpMethod = "GET")
    @GetMapping("/{id}")
    public void getById(@PathVariable("id") String id, HttpServletResponse response){
        SysFileContent sysFileContent = sysFileContentService.get(id);
        if (sysFileContent.getDelFlag().equals("2")){
            JSONObject viewBase64 = fileService.getViewBase64(sysFileContent.getId());
            if (viewBase64.get("code").equals(500)){
                throw new RuntimeException("文件服务下载文件失败！");
            }
            String data = viewBase64.get("data").toString();
            byte[] decode= DatatypeConverter.parseBase64Binary(data);
            sysFileContent.setFileByte(decode);
        }
        response.setHeader("Content-Type",sysFileContent.getFileType());
        try {
            OutputStream os = response.getOutputStream();

            os.write(sysFileContent.getFileByte());

            os.flush();

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/export/{id}")
    public void downloadById(HttpServletResponse response,
                             @PathVariable("id") String Id) {
        SysFileContent sysFileContent = new SysFileContent();
//        if (sysFileContent.getDelFlag().equals("2")){
            JSONObject viewBase64 = fileService.getViewBase64(Id);
            if (viewBase64.get("code").equals(500)){
                throw new RuntimeException("上传到文件服务失败！");
            }
            String data = viewBase64.get("data").toString();
            byte[] decode= DatatypeConverter.parseBase64Binary(data.split(",")[data.split(",").length-1]);
            sysFileContent.setFileByte(decode);
//        }
        response.setHeader("Content-Type",sysFileContent.getFileType());
        try {
            OutputStream os = response.getOutputStream();

            os.write(sysFileContent.getFileByte());

            os.flush();

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    


}