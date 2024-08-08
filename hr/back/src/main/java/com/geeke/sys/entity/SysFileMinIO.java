package com.geeke.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel
@Data
public class SysFileMinIO {
    /**
     * 标识
     */
    @ApiModelProperty("标识")
    private String id;

    @ApiModelProperty("应用名称")
    private String appName;

    /**
     * 业务表
     */
    @ApiModelProperty("业务表id")
    private String objectId;

    /**
     * 业务名称
     */
    @ApiModelProperty("业务名称")
    private String objectName;

    /**
     * 租户id 不一定对方多租户所以不加
     */
    @ApiModelProperty("租户id")
    private String tenantId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 文件类型
     */
    @ApiModelProperty("文件类型")
    private String fileType;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Long fileSize;

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String fileUrl;

    /**
     * 文件桶名称
     */
    @ApiModelProperty("文件桶名称")
    private String bucket;

    /**
     *备注
     */
    private String remarks;

    /**
     * 创建人
     */
    private String createby;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 更新时间
     */
    private String updateby;
    /**
     * 更新时间
     */
    private Date updatedate;
    /**
     * 删除标记
     */
    private String delflag = "0";

}