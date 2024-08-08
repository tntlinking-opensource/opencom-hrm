package com.geeke.feign.fallback;


import cn.hutool.json.JSONObject;
import com.geeke.feign.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 服务降级
 */
@Slf4j
@Component
public class FileServiceFallBack implements FileService {

    @Override
    public JSONObject uploadSingle(MultipartFile file, String sysFile) {
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","上传失败");
        return obj;
    }

    @Override
    public JSONObject uploadMany(MultipartFile[] files, String sysFile) {
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","上传失败");
        return obj;
    }

    @Override
    public void downloadById( String Id) {

    }

    @Override
    public JSONObject fdeleteById(String id) {
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","删除失败！");
        return obj;
    }

    @Override
    public JSONObject getViewBase64( String id) {
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","获取失败！");
        return obj;
    }

    @Override
    public JSONObject findFileDataByObjectId(String objectId) {
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","获取失败！");
        return obj;
    }

    @Override
    public JSONObject findFileDataById(String id) {
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","获取失败！");
        return obj;
    }
}
