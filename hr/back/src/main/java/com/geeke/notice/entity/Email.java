package com.geeke.notice.entity;

import com.geeke.basic.entity.UserPropDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

  /**
   * 主题
   */
  private String subject;
  /**
   * 收件人地址
   */
  private String rcpt;
  /**
   * 内容
   */
  private String info;


  /**
   * 发送者id
   */
  private String senderId;

  //发件人
  private String senderFromEmail;
  private String senderUrl;
  private String senderPort;
  private String senderPwd;

  public void setSenderInfo (UserPropDetails userPropDetails) {
    this.setSenderFromEmail(userPropDetails.getAttribute0());
    this.setSenderPwd(userPropDetails.getAttribute1());
  }

}
