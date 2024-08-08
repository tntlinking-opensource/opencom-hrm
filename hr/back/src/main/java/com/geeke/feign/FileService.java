package com.geeke.feign;



import cn.hutool.json.JSONObject;
import com.geeke.feign.fallback.FileServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(name = "eb-oec-file-system-cloud", path = "/file",fallback = FileServiceFallBack.class)
public interface FileService {
    @PostMapping(path = "/upload/single",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    JSONObject uploadSingle(@RequestPart("file") MultipartFile file,
                               @RequestParam("param")  String sysFile);
    @PostMapping(path = "/upload/many",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    JSONObject uploadMany(@RequestPart("files") MultipartFile[] files,
                             @RequestParam("param") String sysFile);
    @GetMapping("/downloadById")
    void downloadById( String Id);
    @DeleteMapping("/fdeleteByIds/{id}")
    JSONObject fdeleteById(@PathVariable("id")String id);
    @RequestMapping("/getViewBase64/{id}")
    JSONObject getViewBase64(@PathVariable("id")String id);
    @GetMapping("/find/object/{id}")
    JSONObject findFileDataByObjectId(@PathVariable("id") String objectId);
    @GetMapping("/find/{id}")
    JSONObject findFileDataById(@PathVariable("id") String id);
}
