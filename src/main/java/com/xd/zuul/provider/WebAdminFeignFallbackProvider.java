package com.xd.zuul.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 张立军 on 2020/1/14.
 * Project Name : SpringCloud
 * Package Name : com.xd.zuul.provider
 */
@Component
public class WebAdminFeignFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {  //ServiceId,如果需要所有的调用都支持回退，则return "*"或return null
        return "hello-spring-cloud-web-admin-feign";
    }
    /*
     * 如果请求服务失败了，则返回指定的信息给调用者
     * */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            /*
             * 网关api服务请求失败了，但是客户端向网关发送的请求是成功的，(客户端即浏览器请求网关是成功的，但是网关请求服务出错)
             * 不应该把api的404,500等问题抛给客户端。应该返回成功请求。HttpStatus.OK
             * 网关和api服务集群对于客户端来说是黑盒
             * @return
             * @throws IOException
             * */
            @Override  //返回状态码
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();  //返回的是状态码的值
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {   //响应内容
                ObjectMapper objectMapper=new ObjectMapper();//封装json
                Map<String,Object> map=new HashMap<>();
                map.put("status",200);
                map.put("message","无法连接，请检查您的网络");
                return new ByteArrayInputStream(objectMapper.writeValueAsString(map).getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                //和getBody中的内容编码一致
                HttpHeaders headers=new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}
