package com.geeke.sys.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.feign.FileService;
import com.geeke.sys.dao.SysFileContentDao;
import com.geeke.sys.dao.SysFileDao;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.sys.entity.SysFile;
import com.geeke.sys.entity.SysFileContent;
import com.geeke.sys.entity.SysFileMinIO;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.Reflections;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 系统附件Service
 * @author szy
 * @version 2021-09-22
 */
 
@Service("sysFileService")
@Transactional(readOnly = true)
public class SysFileService extends CrudService<SysFileDao, SysFile>{

    @Autowired
    private SysFileContentDao sysFileContentDao;
    @Autowired
    private FileService fileService;
    @Override
    public SysFile get(String id) {
//        SysFile sysFile = super.get(id);
        JSONObject fileDataById = fileService.findFileDataById(id);
        SysFile sysFile = fileDataById.get("data", SysFile.class);
        List<Parameter> params = null;
        PageRequest pageRequest;
        /*获取子表列表   附件字节表*/
        params = Lists.newArrayList();
        params.add(new Parameter("id", "=", sysFile.getId()));
        pageRequest = new PageRequest(params);
        sysFile.setSysFileContentList(sysFileContentDao.listAll(pageRequest));
        JSONObject viewBase64 = fileService.getViewBase64(sysFile.getId());
        List<SysFileContent> sysFileContents = new ArrayList<>();
        SysFileContent sysFileContent = new SysFileContent();
        sysFileContents.add(sysFileContent);
        sysFile.setSysFileContentList(sysFileContents);
        if (viewBase64.get("code").equals(500)){
            throw new RuntimeException("上传到文件服务失败！");
        }
        String data = viewBase64.get("data").toString();
        byte[] decode= DatatypeConverter.parseBase64Binary(data);
        sysFile.getSysFileContentList().get(0).setFileByte(decode);

        return sysFile;
    }

    public List<SysFile> getByObjectId(String objectId) {
//        SysFile sysFile = super.get(id);
        JSONObject fileDataById = fileService.findFileDataByObjectId(objectId);
        JSONArray sysFiles = fileDataById.getJSONArray("data");

        List<SysFile> newSysFiles = new ArrayList<>();
        for (Object sysFileTemp : sysFiles) {
            SysFile sysFile = JSONUtil.parseObj(sysFileTemp).toBean(SysFile.class);
            List<Parameter> params = null;
            PageRequest pageRequest;
            /*获取子表列表   附件字节表*/
            params = Lists.newArrayList();
            params.add(new Parameter("id", "=", sysFile.getId()));
            pageRequest = new PageRequest(params);
            sysFile.setSysFileContentList(sysFileContentDao.listAll(pageRequest));
            JSONObject viewBase64 = fileService.getViewBase64(sysFile.getId());
            List<SysFileContent> sysFileContents = new ArrayList<>();
            SysFileContent sysFileContent = new SysFileContent();
            sysFileContents.add(sysFileContent);
            sysFile.setSysFileContentList(sysFileContents);
            if (viewBase64.get("code").equals(500)){
                throw new RuntimeException("上传到文件服务失败！");
            }
            String data = viewBase64.get("data").toString();
            byte[] decode= DatatypeConverter.parseBase64Binary(data);
            sysFile.getSysFileContentList().get(0).setFileByte(decode);
            newSysFiles.add(sysFile);
        }

        return newSysFiles;
    }

    @Transactional(readOnly = false)
    public List<String> saves(List<SysFile> entitys){
        ArrayList<String> objects = new ArrayList<>();
        for (int i = 0; i < entitys.size(); i++) {
            String id = save(entitys.get(i)).getId();
            objects.add(id);
        }
        return objects;
    }
    @Override
    @Transactional(readOnly = false)
    public SysFile save(SysFile sysFile) {

	    //2023年2月14日 17:09:03 头脑一热突然明白，撤销全部，重新编写
        com.alibaba.fastjson.JSONObject userObj = SessionUtils.getUserJson();
        String createBy = ObjUtil.isNotNull(userObj) ?
            userObj.getString("name") + "(" + userObj.getString("loginName") + ")"
            : "default_app";
        sysFile.setCreateBy(createBy);
//        JSONObject fileDataById = fileService.findFileDataById(sysFile.getId());
//        SysFile sysFileTemp = fileDataById.get("data", SysFile.class);
        SysFile sysFileTemp = super.save(sysFile);
        if (!StringUtils.isNullOrEmpty(sysFileTemp)) {

            List<Parameter> params = null;
            PageRequest pageRequest;
            /* 处理子表     附件字节表 */
            params = Lists.newArrayList();
            params.add(new Parameter("id", "=", sysFile.getId()));
            pageRequest = new PageRequest(params);
            //逻辑其实就是查数据库 吧id一样的全部查出来
            //然后对比如果有有的就修改 ，没有的就是就是删除但是俺不懂为啥子不在一个for里面搞定
            List<SysFileContent> list_SysFileContent = sysFileContentDao.listAll(pageRequest);

            List<SysFileContent> deleteSysFileContents = Lists.newArrayList(); // 删除列表
            List<SysFileContent> insertSysFileContents = Lists.newArrayList(); // 添加列表
            List<SysFileContent> updateSysFileContents = Lists.newArrayList(); // 更新列表
            for(SysFileContent sysFileContentSaved: list_SysFileContent) {
                boolean found = false;   
                for (SysFileContent sysFileContent : sysFile.getSysFileContentList()){
                   if(sysFileContentSaved.getId().equals(sysFileContent.getId())){
                       found = true;
                       break;
                   }
                }
                if(!found) {
                   deleteSysFileContents.add(sysFileContentSaved);
                }
            }
            //通过筛选 查出删除的内容
            if(deleteSysFileContents.size() > 0) {
                for (int i = 0; i < deleteSysFileContents.size(); i++) {
                    //判断 如果正常的话 就执行以前的逻辑
                    fileService.fdeleteById(deleteSysFileContents.get(i).getRemarks());
                }
            }
            //这里通过对比赛选出对比文件
            for (SysFileContent sysFileContent : sysFile.getSysFileContentList()){
            

                if (StringUtils.isBlank(sysFileContent.getId())) {
                    sysFileContent.setId(sysFileTemp.getId());  // 子表id与父表id一致
                    //- -其实这写一个主表id和附表id一致就很迷惑
                    //如果一样的话 那tm不就一对一的关系了嘛 ，这里循环爪子
                    preInsert(sysFile,sysFileContent);
                    insertSysFileContents.add(sysFileContent);
                } else {
                    preInsert(sysFile,sysFileContent);
                    updateSysFileContents.add(sysFileContent);
                }

            }
            if(updateSysFileContents.size() > 0) {
                for (int i = 0; i < updateSysFileContents.size(); i++) {
                //这里更新就调用那边先删除 在添加了 //后续有版本控制的时候可以考虑源文件更新
                    String id = updateSysFileContents.get(i).getId();
                    JSONObject entries = fileService.fdeleteById(id);

                    insertSysFileContents.get(i).setDelFlag("2");
                    byte[] fileByte = insertSysFileContents.get(i).getFileByte();
                    MultipartFile multipartFile = null;
                    InputStream inputStream = new ByteArrayInputStream(fileByte);
                    try {
                        multipartFile = new MockMultipartFile(sysFile.getName(),sysFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                        SysFileMinIO sysFileMinIO = new SysFileMinIO();
                        sysFileMinIO.setObjectId(String.valueOf(sysFile.getObjectId()));
                        sysFileMinIO.setBucket("dcode-cloud");
                        sysFileMinIO.setAppName("dcode-cloud");
                        sysFileMinIO.setObjectName("dcode-cloud");
                        sysFileMinIO.setCreateby(sysFile.getCreateBy());
                        JSONObject entriess = fileService.uploadSingle(multipartFile,com.alibaba.fastjson.JSONObject.toJSONString(sysFileMinIO));
                        if (entries.get("code").equals(500)){
                            throw new RuntimeException("上传到文件服务失败！");
                        }
                        SysFileMinIO data = entriess.get("data", SysFileMinIO.class);
                        insertSysFileContents.get(i).setRemarks(data.getId());
                        insertSysFileContents.get(i).setFileByte(null);
                    } catch (IOException e) {
                        throw new RuntimeException("文件转化失败！");
                    }
                }
                sysFileContentDao.bulkUpdate(updateSysFileContents);
            }
            if(insertSysFileContents.size() > 0) {
                //处理数据 - -这里的文件上传改动 我真的想在这里一对一的格式写 打老壳
                for (int i = 0; i < insertSysFileContents.size(); i++) {
                    // 更新代码后续内容全部改为minio存储
                    insertSysFileContents.get(i).setDelFlag("2");
                    byte[] fileByte = insertSysFileContents.get(i).getFileByte();
//                    File file = FileUtils.fileToBytes(fileByte,insertSysFileContents.get(i).getId() +insertSysFileContents.get(i).getFileType());
//                    MultipartFile multipartFile = FileUtils.getMultipartFile(file);
                    MultipartFile multipartFile = null;
                    InputStream inputStream = new ByteArrayInputStream(fileByte);
                    try {
                         multipartFile = new MockMultipartFile(sysFile.getName(),sysFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                        SysFileMinIO sysFileMinIO = new SysFileMinIO();
                        sysFileMinIO.setObjectId(String.valueOf(sysFile.getObjectId()));
                        sysFileMinIO.setBucket("dcode-cloud");
                        sysFileMinIO.setAppName("dcode-cloud");
                        sysFileMinIO.setObjectName("dcode-cloud");
                        sysFileMinIO.setCreateby(sysFile.getCreateBy());
                        JSONObject entries = fileService.uploadSingle(multipartFile,com.alibaba.fastjson.JSONObject.toJSONString(sysFileMinIO));
                        if (entries.get("code").equals(500)){
                            throw new RuntimeException("上传到文件服务失败！");
                        }
                        SysFileMinIO data = entries.get("data", SysFileMinIO.class);
                        insertSysFileContents.get(i).setRemarks(data.getId());
                        insertSysFileContents.get(i).setFileByte(null);
                    } catch (IOException e) {
                       throw new RuntimeException("文件转化失败！");
                    }
                }
                sysFileContentDao.bulkInsert(insertSysFileContents);
            }
        }else {
            byte[] fileByte = sysFile.getSysFileContentList().get(0).getFileByte();
            InputStream inputStream = new ByteArrayInputStream(fileByte);
            MultipartFile multipartFile = null;
            try {
                multipartFile = new MockMultipartFile(sysFile.getName(),sysFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            SysFileMinIO sysFileMinIO = new SysFileMinIO();
            sysFileMinIO.setObjectId(String.valueOf(sysFile.getObjectId()));
            sysFileMinIO.setBucket("dcode-cloud");
            sysFileMinIO.setAppName("dcode-cloud");
            sysFileMinIO.setObjectName("dcode-cloud");
            sysFileMinIO.setCreateby(sysFile.getCreateBy());
            JSONObject entries = fileService.uploadSingle(multipartFile,com.alibaba.fastjson.JSONObject.toJSONString(sysFileMinIO));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sysFileTemp;
    }

    /**
     * multipartFiles文件转为SysFile集合对象
     * @param multipartFiles
     * @param objectId
     * @return
     */
    @Transactional(readOnly = false)
    public List<SysFile> changeAndSaveSysFileList(MultipartFile[] multipartFiles, String objectId) throws IOException {
        List<SysFile> fileList = new ArrayList<>(); // 文件集合对象
        if(Objects.nonNull(multipartFiles)){
            for(MultipartFile file : multipartFiles) {
                List<SysFileContent> fileContentList = new ArrayList<>(); // 文件内容集合对象
                SysFile sysFile = new SysFile();
                String originalFilename = file.getOriginalFilename();
                int begin = originalFilename.indexOf(".");
                int last = originalFilename.length();
                //获得文件后缀名
                String suffix = originalFilename.substring(begin, last);
                sysFile.setFileType(suffix); // 文件类型

                sysFile.setObjectId(Long.parseLong(objectId)); // 业务表ID
                sysFile.setName(file.getOriginalFilename()); // 文件名
                sysFile.setFileSize(file.getSize()); // 文件大小

                SysFileContent fileContent = new SysFileContent();
                InputStream is = file.getInputStream();
                byte[] fContent = new byte[(int) file.getSize()];
                is.read(fContent);
                fileContent.setFileByte(fContent); // 字节
                fileContent.setFileType(suffix); // 文件类型
                fileContentList.add(fileContent); // 文件存储内容
                sysFile.setSysFileContentList(fileContentList);
                fileList.add(sysFile);
                save(sysFile);
            }
        }
        return fileList;
    }

    /**
     * 根据objectId删除附件和附件存储表信息信息
     * @param objectId
     */
    @Transactional(readOnly = false)
    public void deleteByObjectId(String objectId) {
        List<Parameter> params = null;
        PageRequest pageRequest;
        params = Lists.newArrayList();
        params.add(new Parameter("object_id", "=", objectId));
        pageRequest = new PageRequest(params);
        List<SysFile> sysFiles = dao.listAll(pageRequest);
        for (SysFile file : sysFiles) {
            delete(file); // 删除附件包含存储表信息
        }
        /*if (sysFiles.size() > 0) {
            dao.bulkDelete(sysFiles); // 删除附件表信息
        }*/

    }


    /**
     * @param sysFile
     * @param sysFileContent
     * 为了保存系统附件和系统字节表更新时间一致，用户回显图片做浏览器缓存使用
     * 用原来的创建附件、更改附件用的是preInsert ,preUpdate方法 每张表更新都会new date() 会导致时间不同
     */

    private void preInsert(SysFile sysFile, SysFileContent sysFileContent) {
        sysFileContent.setCreateDate(sysFile.getCreateDate());
        sysFileContent.setUpdateDate(sysFile.getUpdateDate());
        sysFileContent.setCreateBy(sysFile.getCreateBy());
        sysFileContent.setUpdateBy(sysFile.getUpdateBy());
    }

    /**
     * 删除
     * @param sysFile
     */
    @Override
    @Transactional(readOnly = false)
    public int delete(SysFile sysFile) {
        List<Parameter> params = null;
        PageRequest pageRequest;
        /* 处理子表     附件字节表 */
        params = Lists.newArrayList();
        params.add(new Parameter("id", "=", sysFile.getId()));
        pageRequest = new PageRequest(params);
        sysFile.setSysFileContentList(sysFileContentDao.listAll(pageRequest));
        JSONObject entries = null;
        if(sysFile.getSysFileContentList() != null && sysFile.getSysFileContentList().size() > 0) {
            List<SysFileContent> sysFileContentList = sysFile.getSysFileContentList();
            //这里循环主要判断是否是minio服务用过的 ，用过就去调minio服务
            for (int i = 0; i < sysFileContentList.size(); i++) {
                 entries = fileService.fdeleteById(sysFileContentList.get(i).getId());
            }
//            sysFileContentDao.bulkDelete(sysFile.getSysFileContentList());
        }

//        int rows = super.delete(sysFile);
        return ObjUtil.isNull(entries)? 1 : (int)entries.get("data");
    }

    @Transactional(readOnly = false)
    public void delete(String[] ids) {
        //直接调用循环删除 可优化
        for (String id : ids) {
//            SysFileContent sysFileContent = sysFileContentDao.get(id);
            JSONObject entries = fileService.fdeleteById(id);
        }
        // 根据数组ID，删除附件
//        dao.deleteByIds(ids);
        // 根据数组ID，删除附件存储表
//        sysFileContentDao.deleteByIds(ids);

    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, SysFile entity) {
        Action action = super.createAction(actionTypeId, entity);
        if(action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if(ActionConstants.ACTION_DELETED.equals(actionTypeId)) {   
            for(SysFileContent child: entity.getSysFileContentList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String)Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);            
            }
        }
        return action;
    }
    /**
     * 查询列表数据
     * @param parameters
     * @param orderby
     * @return
     */
    @Override
    public List<SysFile> listAll(List<Parameter> parameters, String orderby) {
//        PageRequest pageRequest = new PageRequest(parameters, orderby);

        JSONObject fileDataByObjectId = fileService.findFileDataByObjectId(String.valueOf(parameters.get(0).getValue()));
        JSONArray data = fileDataByObjectId.getJSONArray("data");
        List<SysFile> objects = new ArrayList<>();
        for (Object datum : data) {
            SysFile sysFile = com.alibaba.fastjson.JSONObject.parseObject(String.valueOf(datum), SysFile.class);
            objects.add(sysFile);
        }
        return objects;
    }
}