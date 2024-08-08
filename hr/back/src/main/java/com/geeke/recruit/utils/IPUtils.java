package com.geeke.recruit.utils;

import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class IPUtils
{

    /**
     * 获取的是本地的IP地址
     *
     * @return
     */
    public static String serviceIp() {
        String result = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            result = address.getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("------>调用 IpUtil.servicerIp，错误信息如下"+e.getMessage());
        }
        return result;
    }

    /**
     * 获取的是该网站的ip地址，比如我们所有的请求都通过nginx的，所以这里获取到的其实是nginx服务器的IP地址
     *
     * @param domain
     * @return
     */
    public static String serviceIp(String domain) {
        String result = "";
        if (!ObjectUtils.isEmpty(domain)) {
            try {
                InetAddress inetAddress = InetAddress.getByName(domain);
                result = inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                System.out.println("------>调用 IpUtil.servicerIp，错误信息如下"+e.getMessage());
            }
        }
        return result;
    }

    /**
     * 根据主机名返回其可能的所有Ip地址
     *
     * @param domain
     * @return
     */
    public static List<String> originalServiceIp(String domain) {
        List<String> result = new ArrayList<>();
        if (!ObjectUtils.isEmpty(domain)) {
            try {
                InetAddress[] addresses = InetAddress.getAllByName(domain);
                for (InetAddress addr : addresses) {
                    result.add(addr.getHostAddress());
                }
            } catch (UnknownHostException e) {
                System.out.println("------>调用 IpUtil.servicerIp，错误信息如下"+e.getMessage());
            }
        }
        return result;
    }

    /**
     * @param request
     * @return
     * @description: 如果通过了多级反向代理的话，
     * X-Forwarded-For的值并不止一个， 而是一串IP值，
     * 究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取 X-Forwarded-For中第一个非unknown的有效IP字符串。
     */
    public static String clientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
