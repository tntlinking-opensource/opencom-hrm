package com.geeke.induction.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.geeke.induction.entity.TsmEmployeeAttachments;
import com.geeke.sys.entity.DictItem;
import com.geeke.sys.entity.SysFile;
import com.geeke.sys.service.SysFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yi.luo
 * @date 2023-05-23 14:41
 */
@Service("inductionAppService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InductionAppService {
    private final SysFileService sysFileService;
    private final TsmInductionInfoService tsmInductionInfoService;
    private final TsmEmployeeAttachmentsService tsmEmployeeAttachmentsService;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void uploadMany(String inductionId, MultipartFile[] files, String fileName, String[] deletes) throws IOException {
        //tuple<filename,MultipartFile>
        List<MultipartFile> collect = Arrays.stream(files).collect(Collectors.toList());
        Map<String, JSONArray> filesMap = JSONUtil.toBean(fileName, Map.class);

        if (ArrayUtil.isNotEmpty(deletes)) {
            sysFileService.delete(deletes);
        }

        // 遍历Map对象并输出所有文件信息
        for (String key : filesMap.keySet()) {
            JSONArray jsonArray = filesMap.get(key);
            String[] filenames = jsonArray.toArray(new String[jsonArray.size()]);

            List<MultipartFile> filesGroup = new ArrayList<>();
            for (String filename : filenames) {
                collect.stream()
                    .filter(file -> Objects.equals(file.getOriginalFilename(), filename))
                    .findFirst().ifPresent(x -> {
                        filesGroup.add(x);
                        collect.remove(x);
                });
            }

            if (filesGroup.size() == 0) {
                continue;
            }

            sysFileService
                .changeAndSaveSysFileList(filesGroup.toArray(new MultipartFile[filesGroup.size()]), inductionId);
            tsmEmployeeAttachmentsService
                .save(new TsmEmployeeAttachments(inductionId, StrUtil.join(StrUtil.COMMA, filenames), key, new DictItem("0"), new DictItem("1")));
        }
    }

    public Map<String, List<SysFile>> downMany(String inductionId) {
        List<SysFile> entity = sysFileService.getByObjectId(inductionId);
        List<TsmEmployeeAttachments> attachmentInfo = tsmEmployeeAttachmentsService.getByInductionId(inductionId);
        Map<String, List<TsmEmployeeAttachments>> attachmentInfoMap = attachmentInfo.stream()
            .collect(Collectors.groupingBy(TsmEmployeeAttachments::getAttachmentUrl));

        Map<String, List<SysFile>> mapSysFile = new HashMap<>(8);
        for (String code : attachmentInfoMap.keySet()) {
            List<TsmEmployeeAttachments> attachments = attachmentInfoMap.get(code);
            List<SysFile> sysFiles = Optional.ofNullable(mapSysFile.get(code)).orElse(new ArrayList<>());

            attachments.stream()
                .map(x -> x.getAttachmentName().split(","))
                .flatMap(Arrays::stream)
                .forEach(attachmentName -> {
                    entity.stream()
                        .filter(file -> Objects.equals(file.getName(), attachmentName))
                        .findFirst().ifPresent(x -> {
                        sysFiles.add(x);
                        entity.remove(x);
                    });
                });

            mapSysFile.put(code, sysFiles);
        }
        return mapSysFile;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void dataSubmission(String inductionId) {
        Optional.ofNullable(tsmInductionInfoService.getByTsmInductionInfo(inductionId))
            .filter(x -> x.size() > 0)
            .map(x -> x.get(0))
            .ifPresent(x -> {
                x.setDataSubmission("1");
                tsmInductionInfoService.save(x);
            });
    }
}
