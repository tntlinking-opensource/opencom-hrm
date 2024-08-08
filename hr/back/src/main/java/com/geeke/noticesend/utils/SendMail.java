package com.geeke.noticesend.utils;


import com.geeke.notice.entity.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@PropertySource({"classpath:mail-config.properties"})
@Component
@Slf4j
public class SendMail {
   @Value("${mail.user}")
   private  String from;             //发件人
   @Value("${mail.password}")
   private  String pass;             //密码
   @Value("${mail.smtp.host}")
   private  String server;           //发件服务器
   @Value("${mail.smtp.port}")
   private  String port;             //端口

    // 创建配置文件
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", server);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", true);
        return properties;
    }

    /**
     * 发送邮件
     *
     * @param email 邮件信息
     */
    public void sentEmail(Email email, Boolean isHtml) {
        Properties properties = getProperties();
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getSenderFromEmail(), email.getSenderPwd());
            }
        });

        // 开启debug模式
        session.setDebug(true);
        try{
            // 获取连接对象
            Transport transport = session.getTransport();
            // 连接服务器
            transport.connect(server
                , email.getSenderFromEmail()
                , email.getSenderPwd());
            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);
            // 邮件发信人
            message.setFrom(new InternetAddress(email.getSenderFromEmail()));
            // 邮件收信人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getRcpt()));
            // 主题
            message.setSubject(email.getSubject());
            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            // 内容
            if(isHtml){
                messageBodyPart.setContent(email.getInfo(), "text/html;charset=UTF-8");
            }else{
                messageBodyPart.setText(email.getInfo());
            }
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            // 设置发送的日期
            message.setSentDate(new Date());
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            // 关闭连接
            transport.close();
            log.info("邮件发送成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("邮件发送失败：\n内容为==》{} ",e.getMessage());
        }
    }

    /**
     * 发送邮件
     * @param subject 主题
     * @param rcpt 接收人邮箱
     * @param info 发送信息
     * @param isHtml 是否是html内容
     */
    public void sentEmail(String subject,String rcpt,String info,Boolean isHtml) {
        Properties properties = getProperties();
        // 创建session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        // 开启debug模式
        session.setDebug(true);
        try{
            // 获取连接对象
            Transport transport = session.getTransport();
            // 连接服务器
            transport.connect(server, from, pass);
            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);
            // 邮件发信人
            message.setFrom(new InternetAddress(from));
            // 邮件收信人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(rcpt));
            // 主题
            message.setSubject(subject);
            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();
            // 内容
            if(isHtml){
                messageBodyPart.setContent(info, "text/html;charset=UTF-8");
            }else{
                messageBodyPart.setText(info);
            }
            // 创建多重消息
            Multipart multipart = new MimeMultipart();
            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            // 设置发送的日期
            message.setSentDate(new Date());
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            // 关闭连接
            transport.close();
            log.info("邮件发送成功");
        }catch (Exception e){
            e.printStackTrace();
            log.error("邮件发送失败：\n内容为==》{} ",e.getMessage());
        }
    }

    public  StringBuilder getTableStart(String title,String head[]){
        StringBuilder table=new StringBuilder();
        table.append("    <html>");
        table.append("      <head>");
        table.append("      <title> New Document </title>");
        table.append("     </head>");
        table.append("    ");
        table.append("    <style type=\"text/css\">");
        table.append("    table { ");
        table.append("      margin: 10px 0 30px 0;");
        table.append("      font-size: 13px;");
        table.append("    }");
        table.append("    ");
        table.append("    table caption { ");
        table.append("      text-align:left;");
        table.append("    }");
        table.append("    ");
        table.append("    table tr th { ");
        table.append("      background: #3B3B3B;");
        table.append("      color: #FFF;");
        table.append("      padding: 7px 4px;");
        table.append("      text-align: left;");
        table.append("    }");
        table.append("    ");
        table.append("    table tr td { ");
        table.append("      color: #FFF;");
        table.append("      padding: 7px 4px;");
        table.append("      text-align: left;");
        table.append("    }");
        table.append("    ");
        table.append("    table tr.odd{");
        table.append("        background-color:#cef;");
        table.append("    }");
        table.append("    ");
        table.append("    table tr.even{");
        table.append("        background-color:#ffc;");
        table.append("    }");
        table.append("      ");
        table.append("    table tr td { ");
        table.append("      color: #47433F;");
        table.append("      border-top: 1px solid #FFF;");
        table.append("    }");
        table.append("     </style>");
        table.append("    ");
        table.append("     <body>");
        table.append("<h2>"+title+"<h2/>");
        table.append("    <table style=\"width:610px; border-spacing:0;border:1px solid \">  ");
        table.append("       <tr>  ");
        for (int i=0;i<head.length;i++){
            table.append(" <th>"+head[i]+"</th>  ");
        }
        table.append(" </tr>");
        return table;
    }
    public  StringBuilder getTableEnd(StringBuilder table) {
        table.append("    </table> ");
        table.append("     </body>");
        table.append("    </html>");
        return table;
    }

    //合同到期/终止提醒
    public  StringBuilder setContractHint(String head[], String title, List<Map<String,String>> list){
        StringBuilder table = getTableStart(title,head);
        for (int i=0;i<list.size();i++){
            Map<String,String> map = list.get(i);
            String tr = "<tr class=\"odd\">";
            if (i%2==1){
                tr = "<tr class=\"even\">";
            }
            table.append("     "+tr+"    ");
            table.append("         <td style='width:200px'><a href=\"http://localhost:3000/#/leaseContract\">"+map.get("code")+"</a></td>  ");
            table.append("         <td style='width:200px'>"+map.get("name")+"</td>  ");
            Object date = map.get("leaseTermTo");
            table.append("         <td style='width:200px'>"+ date.toString()+"</td>  ");
            table.append("         <td style='width:200px'>"+map.get("type")+"</td>  ");
            table.append("         <td style='width:200px'>"+map.get("lesseeName")+"</td>  ");
            table.append("       </tr>  ");
        }
        return  getTableEnd(table);
    }



    //合同到期预警
    public  StringBuilder setContractWarning(String head[],String columns[], String title, List<Map<String,String>> list){
        StringBuilder table = getTableStart(title,head);
        for (int i=0;i<list.size();i++){
            Map<String,String> map = list.get(i);
            String tr = "<tr class=\"odd\">";
            if (i%2==1){
                tr = "<tr class=\"even\">";
            }
            table.append("     "+tr+"    ");
            setData(table,map,columns);
            table.append("       </tr>  ");
        }
        return  getTableEnd(table);
    }


    public void setData(StringBuilder table,Map<String,String> map,String columns[]){
        for (String column : columns) {
            Object obj = map.get(column);
            if(null != obj){
                table.append(" <td style='width:200px'>"+ obj.toString() +"</td>  ");
            }
        }
    }

}