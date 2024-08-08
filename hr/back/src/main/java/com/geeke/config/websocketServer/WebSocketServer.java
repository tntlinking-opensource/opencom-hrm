package com.geeke.config.websocketServer;

import com.alibaba.fastjson.JSON;
import com.geeke.common.data.Parameter;
import com.geeke.noticesend.entity.NoticeReceiveSite;
import com.geeke.noticesend.service.NoticeReceiveSiteService;
import com.geeke.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class WebSocketServer {

    @Autowired
    private NoticeReceiveSiteService siteServer;          //消息接收
    private static NoticeReceiveSiteService siteService;  //静态对象

    private Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    private static WebSocketServer webSocketServer;

    //在线用户数
    private static int onlineCount = 0;
    //session 连接
    private Session session;
    //用户id
    private String userId;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Map<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        siteService =  this.siteServer;
        webSocketServer = this;
        log.info("--------websocket init---------");
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        log.info("---------websocket open---------" + userId);
        this.session = session;
        this.userId = userId;
        addOnlineCount();
        webSocketMap.put(userId,this);
        List<NoticeReceiveSite> list = this.getRecordSiteData(userId);
        sendMessage(list,userId);
        log.info("当前在线人数为:" + getOnlineCount());
    }

    // 获取当前用户未读消息总数
    private List<NoticeReceiveSite> getRecordSiteData(String userId){
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("receive_by", "=",userId));          //接收者id
        parameters.add(new Parameter("read_status", "=","0"));   //消息状态
        return siteService.listAll(parameters,"");
    }



    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        log.info("【websocket】收到客户端发来的消息：{}",message);
    }


    /**
     * 实现服务器主动推送
     */
    public void sendMessage(List<NoticeReceiveSite> listData,String userId){
        for(String item: webSocketMap.keySet()){
            try {
                if(userId.equals(item) && !StringUtils.isNullOrEmpty(item) && webSocketMap.get(item) != null){
                    Map<String,Object> map = new HashMap<>();
                    map.put("total",listData.size());
                    map.put("data",listData);
                    webSocketMap.get(item).session.getBasicRemote().sendText(JSON.toJSONString(map));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 推送多人数据
    public void sendMessageList(List<String> list){
        for(String item: webSocketMap.keySet()){
            try {
                if(list.contains(item) && !StringUtils.isNullOrEmpty(item) && webSocketMap.get(item) != null){
                    List<NoticeReceiveSite> listData = this.getRecordSiteData(item);
                    Map<String,Object> map = new HashMap<>();
                    map.put("total",listData.size());
                    map.put("data",listData);
                    webSocketMap.get(item).session.getBasicRemote().sendText(JSON.toJSONString(map));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(!"".equals(userId)){
            webSocketMap.remove(userId);  //从set中删除
        }
        subOnlineCount();
        log.info("用户退出:");
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }



    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
