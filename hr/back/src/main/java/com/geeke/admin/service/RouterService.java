package com.geeke.admin.service;

import com.geeke.admin.dao.RouterDao;
import com.geeke.admin.entity.Router;
import com.geeke.common.service.TreeService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.service.SysFileService;
import com.geeke.utils.IdGen;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 路由管理Service
 * @author
 * @version
 */

@Service("routerService")
@Transactional(readOnly = true)
public class RouterService extends TreeService<RouterDao, Router> {

    @Autowired
    private SysFileService sysFileService;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public Router save(Router router, MultipartFile[] iconIdUploads, String[] deleteIds) throws java.io.IOException {
        if (StringUtils.isEmpty(router.getIconId())) {
            router.setIconId(IdGen.uuid());
        }
        // 保存附件
        sysFileService.changeAndSaveSysFileList(iconIdUploads, router.getIconId());

        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }

        Router routerTemp = super.save(router);

        return routerTemp;
    }
}
