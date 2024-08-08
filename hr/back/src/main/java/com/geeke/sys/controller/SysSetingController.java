package com.geeke.sys.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.feign.FileService;
import com.geeke.sys.controller.BaseController;
import com.geeke.sys.entity.SysFile;
import com.geeke.sys.entity.SysFileContent;
import com.geeke.sys.entity.SysSeting;
import com.geeke.sys.service.SysFileContentService;
import com.geeke.sys.service.SysFileService;
import com.geeke.sys.service.SysSetingService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统设置Controller
 * @author
 * @version
 */
@Api(value = "/sys/sysSeting", tags = {"系统设置Controller"})
@RestController
@RequestMapping(value = "/sys/sysSeting")
public class SysSetingController extends BaseController {

    @Autowired
    private SysSetingService sysSetingService;

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private SysFileContentService sysFileContentService;

    @Autowired
    private FileService fileService;
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取系统设置", notes = "通过id获取系统设置", httpMethod = "GET",response=SysSeting.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        SysSeting entity = sysSetingService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取系统设置（分页）", notes = "通过搜索条件获取系统设置（分页）", httpMethod = "POST",response=SysSeting.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<SysSeting> result = sysSetingService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取系统设置", notes = "通过搜索条件获取系统设置", httpMethod = "POST",response=SysSeting.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<SysSeting> result = sysSetingService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysSeting", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除系统设置", notes = "删除系统设置", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody SysSeting entity) {
        int rows = sysSetingService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加系统设置", notes = "批量添加系统设置，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<SysSeting> entitys) {
        List<String> ids = sysSetingService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新系统设置", notes = "批量更新系统设置，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<SysSeting> entitys) {
        List<String> ids = sysSetingService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除系统设置", notes = "批量删除系统设置，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<SysSeting> entitys) {
        int rows = sysSetingService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "sysSeting", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "files", value = "")
    })
    @ApiOperation(value = "保存系统设置", notes = "保存系统设置，并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(
        @RequestParam("sysSeting") String sysSeting,
        @RequestParam(value = "attachments", required = false) MultipartFile[] files
    ) {
        SysSeting entity = JSONObject.parseObject(sysSeting, SysSeting.class);
        String id = sysSetingService.save(entity, files).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    /**
     * 系统设置回显图片
     *
     * @param id
     * @param response
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "系统设置回显图片", notes = "系统设置回显图片", httpMethod = "GET")
    @GetMapping("/getFile/{id}")
    public void getById(@PathVariable("id") String id, HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        if (id.indexOf(".") >= 0) {
            id = id.substring(0, id.lastIndexOf("."));
        }
        String modifiedSice = request.getHeader("if-modified-since");
        //日期格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        if (StringUtils.isNotBlank(modifiedSice)) {
            //Thu, 04 Feb 2021 07:02:25 GMT   转化日期
            //            Date modifiedSiceDate = simpleDateFormat.parse(modifiedSice);
            SysFile sysFile = sysFileService.get(id);
            if (sysFile != null) {
                //                Date updateDate = sysFile.getUpdateDate();
                if (modifiedSice.equals(sysFile.getUpdateDate())) {
                    response.setStatus(304);
                    return;
                }
            }
        }
        SysFileContent sysFileContent = sysFileContentService.get(id);
        if (sysFileContent.getDelFlag().equals("2")){
            cn.hutool.json.JSONObject viewBase64 = fileService.getViewBase64(sysFileContent.getRemarks());
            if (viewBase64.get("code").equals(500)){
                throw new RuntimeException("上传到文件服务失败！");
            }
            String data = viewBase64.get("data").toString();
            byte[] decode= DatatypeConverter.parseBase64Binary(data.split(",")[data.split(",").length-1]);
            sysFileContent.setFileByte(decode);
        }
        response.setHeader("Content-Type", sysFileContent.getFileType());
        //        Date updateDate = sysFileContent.getUpdateDate();
        //        String format = simpleDateFormat.format(updateDate);
        String format = sysFileContent.getUpdateDate();
        response.addHeader("Last-Modified", format);
        try {
            OutputStream os = response.getOutputStream();

            os.write(sysFileContent.getFileByte());

            os.flush();

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "系统设置回显图片", notes = "系统设置回显图片", httpMethod = "GET")
    @GetMapping("/getSById/{id}")
    public void getSById(@PathVariable("id") String id, HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        if (id.indexOf(".") >= 0) {
            id = id.substring(0, id.lastIndexOf("."));
        }
        SysFileContent sysFileContent = new SysFileContent();
        cn.hutool.json.JSONObject viewBase64 = fileService.getViewBase64(id);
        if (viewBase64.get("code").equals(500)){
            throw new RuntimeException("上传到文件服务失败！");
        }
        String data = viewBase64.get("data").toString();
        byte[] decode= DatatypeConverter.parseBase64Binary(data.split(",")[data.split(",").length-1]);
        sysFileContent.setFileByte(decode);
        response.setHeader("Content-Type", sysFileContent.getFileType());
        //        Date updateDate = sysFileContent.getUpdateDate();
        //        String format = simpleDateFormat.format(updateDate);
        String format = sysFileContent.getUpdateDate();
        response.addHeader("Last-Modified", format);
        try {
            OutputStream os = response.getOutputStream();

            os.write(sysFileContent.getFileByte());

            os.flush();

            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "fileId", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletResponse", name = "response", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "HttpServletRequest", name = "request", value = "", required = true)
    })
    @ApiOperation(value = "文件下载", notes = "文件下载", httpMethod = "GET")
    @GetMapping("/filedownload")
    public void filedownload(@RequestParam("fileId") String fileId, HttpServletResponse response, HttpServletRequest request)
        throws Exception {
        if (fileId.indexOf(".") >= 0) {
            fileId = fileId.substring(0, fileId.lastIndexOf("."));
        }
        logger.info("fileId = {}", fileId);
        String modifiedSice = request.getHeader("if-modified-since");
        //日期格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        if (StringUtils.isNotBlank(modifiedSice)) {
            //Thu, 04 Feb 2021 07:02:25 GMT   转化日期
            //            Date modifiedSiceDate = simpleDateFormat.parse(modifiedSice);
            SysFile sysFile = sysFileService.get(fileId);
            if (sysFile != null) {
                //                Date updateDate = sysFile.getUpdateDate();
                if (modifiedSice.equals(sysFile.getUpdateDate())) {
                    response.setStatus(304);
                    return;
                }
            }
        }
        SysFileContent sysFileContent = sysFileContentService.get(fileId);
        response.setHeader("Content-Type", sysFileContent.getFileType());
        //        Date updateDate = sysFileContent.getUpdateDate();
        //        String format = simpleDateFormat.format(updateDate);
        String format = sysFileContent.getUpdateDate();
        response.addHeader("Last-Modified", format);
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
