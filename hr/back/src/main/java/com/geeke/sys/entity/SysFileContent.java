package com.geeke.sys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.geeke.common.persistence.DataEntity;
/**
 * 系统附件Entity
 * @author szy
 * @version 2021-09-22
 */
@ApiModel(description = "系统附件Entity")
public class SysFileContent extends DataEntity<SysFileContent> {

	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 592905357446275244L;
	@ApiModelProperty("图片字节")
	private byte[] fileByte;		// 图片字节
	@ApiModelProperty("文件类型")
	private String fileType;		// 文件类型

	
	public SysFileContent() {
		super();
	}

	public SysFileContent(String id){
		super(id);
	}
	

	public byte[] getFileByte() {
		return fileByte;
	}
	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}
	
	
	@Length(min=0, max=64, message="文件类型长度必须介于 0 和 64 之间")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
	
	
	
	/**
     * 获取实体对应表名
    */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_file_content";
    }
    
    /**
     * 获取实体对应Id
    */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "592905357446275244";
    }
    
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
            return true;
    }
}