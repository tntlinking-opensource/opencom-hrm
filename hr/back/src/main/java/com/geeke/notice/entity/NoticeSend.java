package com.geeke.notice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 消息记录Entity
 * @author
 * @version
 */

@ApiModel(description = "生成get和set方法")
public class NoticeSend extends DataEntity<NoticeSend> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1295330388076265506L;

    @ApiModelProperty("发送人id")
    private String senderId; // 发送人id

    @ApiModelProperty("标题")
    private String title; // 标题

    @ApiModelProperty("是否发送站内")
    private String isSite; // 是否发送站内

    @ApiModelProperty("是否发送微信")
    private String isWechat; // 是否发送微信

    @ApiModelProperty("是否发送邮件")
    private String isEmail; // 是否发送邮件

    @ApiModelProperty("是否发送短信")
    private String isSms; // 是否发送短信

    @ApiModelProperty("站内内容")
    private String siteContent; // 站内内容

    @ApiModelProperty("邮件内容")
    private String emailContent; // 邮件内容

    @ApiModelProperty("微信内容")
    private String wechatContent; // 微信内容

    @ApiModelProperty("短信内容")
    private String smsContent; // 短信内容

    @ApiModelProperty("接收人")
    private String receivedBy; // 接收人


	public NoticeSend() {
		super();
		this.isSite = "0";
		this.isEmail = "0";
		this.isWechat = "0";
		this.isSms = "0";
	}

    public NoticeSend(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 21, message = "发送人id长度必须介于 1 和 21 之间")
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Length(min = 1, max = 50, message = "标题长度必须介于 1 和 50 之间")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(min = 1, max = 1, message = "是否发送站内长度必须介于 1 和 1 之间")
    public String getIsSite() {
        return isSite;
    }

    public void setIsSite(String isSite) {
        this.isSite = isSite;
    }

    @Length(min = 1, max = 1, message = "是否发送微信长度必须介于 1 和 1 之间")
    public String getIsWechat() {
        return isWechat;
    }

    public void setIsWechat(String isWechat) {
        this.isWechat = isWechat;
    }

    @Length(min = 1, max = 1, message = "是否发送邮件长度必须介于 1 和 1 之间")
    public String getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(String isEmail) {
        this.isEmail = isEmail;
    }

    @Length(min = 1, max = 1, message = "是否发送短信长度必须介于 1 和 1 之间")
    public String getIsSms() {
        return isSms;
    }

    public void setIsSms(String isSms) {
        this.isSms = isSms;
    }


	@Length(min=0, max=10000, message="站内消息内容长度必须介于 0 和 10000 之间")
	public String getSiteContent() {
		return siteContent;
	}
	public void setSiteContent(String siteContent) {
		this.siteContent = siteContent;
	}


	@Length(min=0, max=500, message="微信消息内容长度必须介于 0 和 500 之间")
	public String getWechatContent() {
		return wechatContent;
	}
	public void setWechatContent(String wechatContent) {
		this.wechatContent = wechatContent;
	}


	@Length(min=0, max=10000, message="邮件消息内容长度必须介于 0 和 10000 之间")
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}


	@Length(min=0, max=500, message="短信消息内容长度必须介于 0 和 500 之间")
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}


	@Length(min=1, max=500, message="接收人长度必须介于 1 和 500 之间")
	public String getReceivedBy() {
		return receivedBy;
	}
	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}





	/**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "notice_send";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1295330388076265506";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
