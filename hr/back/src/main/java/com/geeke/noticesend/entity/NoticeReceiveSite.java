package com.geeke.noticesend.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.geeke.common.persistence.DataEntity;
/**
 * 消息接收站点Entity
 * @author ycy
 * @version 2021-11-24
 */
@ApiModel(description = "消息接收站点Entity")
public class NoticeReceiveSite extends DataEntity<NoticeReceiveSite> {

	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 726473703584546839L;
	@ApiModelProperty("消息发送记录")
	private String sendId;		   // 消息发送记录
	@ApiModelProperty("消息接收者")
	private String receiveBy;		// 消息接收者
	@ApiModelProperty("已读")
	private String readStatus;		// 已读

	@ApiModelProperty("读取时间")
	private Date readTime;		// 读取时间

	@ApiModelProperty("站内消息内容")
	private String siteContent;		// 站内消息内容
	@ApiModelProperty("标题")
	private String title;			//标题

	@Length(min=1, max=50, message="标题长度必须介于 1 和 50 之间")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Length(min=0, max=10000, message="站内消息内容长度必须介于 0 和 10000 之间")
	public String getSiteContent() {
		return siteContent;
	}
	public void setSiteContent(String siteContent) {
		this.siteContent = siteContent;
	}

	public NoticeReceiveSite() {
		super();
	}

	public NoticeReceiveSite(String id){
		super(id);
	}
	

	@Length(min=1, max=20, message="消息发送记录长度必须介于 1 和 20 之间")
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	
	
	@Length(min=1, max=20, message="消息接收者长度必须介于 1 和 20 之间")
	public String getReceiveBy() {
		return receiveBy;
	}
	public void setReceiveBy(String receiveBy) {
		this.receiveBy = receiveBy;
	}
	
	
	@Length(min=0, max=1, message="已读长度必须介于 0 和 1 之间")
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	
	
	
	
	/**
     * 获取实体对应表名
    */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "notice_receive_site";
    }
    
    /**
     * 获取实体对应Id
    */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "726473703584546839";
    }
    
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
            return true;
    }
}