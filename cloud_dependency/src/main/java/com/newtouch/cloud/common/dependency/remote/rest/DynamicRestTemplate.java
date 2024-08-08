package com.newtouch.cloud.common.dependency.remote.rest;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author FallenRunning(TBH)
 */
public class DynamicRestTemplate {

    private final RestTemplate restTemplate;

    public DynamicRestTemplate(@LoadBalanced RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //get请求 FORM参数
    public <T> T getForm(@NotBlank String serviceId, @NotBlank String path, @NotNull Class<T> responseType, @NotBlank Map<String, ?> params) {
        //替换为使用serviceId调用
        String url = "http://" + serviceId + path;
        return restTemplate.getForObject(url, responseType, params);
    }

    //get请求 JSON参数
    public <T> T getJSON(@NotBlank String serviceId, @NotBlank String path, @NotNull Class<T> responseType, @NotBlank Map<String, ?> params) {
        //替换为使用serviceId调用
        String url = "http://" + serviceId + path;
        return restTemplate.getForObject(url, responseType, params);
    }

    //post请求 FORM参数
    public <T> T postFORM(@NotBlank String serviceId, @NotBlank String path, @NotNull Class<T> responseType, @NotNull LinkedMultiValueMap<String, Object> formData) {
        //替换为使用serviceId调用
        String url = "http://" + serviceId + path;
        HttpHeaders headers = new HttpHeaders();
        //创建请求头
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(formData, headers);
        ResponseEntity<T> response = restTemplate.postForEntity(url, request, responseType);
        return response.getBody();
    }

    //post请求 JSON参数
    public <T> T postJSON(@NotBlank String serviceId, @NotBlank String path, @NotNull Class<T> responseType, @NotNull Object param) {
        //替换为使用serviceId调用
        String url = "http://" + serviceId + path;
        //创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(param, headers);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, entity, responseType);
        return responseEntity.getBody();
    }

    //get请求字节(图片)
    public byte[] getBytes(@NotBlank String serviceId, @NotBlank String path) {
        //替换为使用serviceId调用
        String url = "http://" + serviceId + path;
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new CopyOnWriteArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        headers.setAccept(mediaTypes);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        return response.getBody();
    }

    //get通用方法(自定义请求头)
    public <T> T get(@NotBlank String serviceId, @NotBlank String path, @NotNull Class<T> responseType, @NotNull Map<String, ?> params, @NotNull HttpHeaders headers) {
        //替换为使用serviceId调用
        String url = "http://" + serviceId + path;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType, params);
        return response.getBody();
    }

    //post通用方法(自定义请求头)
    public <T> T post(@NotBlank String serviceId, @NotBlank String path, @NotNull Class<T> responseType, @NotNull Object param, @NotNull HttpHeaders headers) {
        //替换为使用serviceId调用
        String url = "http://" + serviceId + path;
        HttpEntity<?> requestEntity = new HttpEntity<>(param, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
        return response.getBody();
    }

    //get通用方法(自定义请求头)
    public <T> T get(@NotBlank String url, @NotNull Class<T> responseType, @NotNull Map<String, ?> params, @NotNull HttpHeaders headers) {
        //替换为使用serviceId调用
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType, params);
        return response.getBody();
    }

    //post通用方法(自定义请求头)
    public <T> T post(String url, @NotNull Class<T> responseType, @NotNull Object param, @NotNull HttpHeaders headers) {
        //替换为使用serviceId调用
        HttpEntity<?> requestEntity = new HttpEntity<>(param, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
        return response.getBody();
    }

    public RestTemplate restTemplate(){
        return this.restTemplate;
    }
}
