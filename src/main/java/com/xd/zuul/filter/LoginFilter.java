package com.xd.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 张立军 on 2020/1/14.
 * Project Name : SpringCloud
 * Package Name : com.xd.zuul.filter
 */
/*创建服务过滤器*/
@Component
public class LoginFilter extends ZuulFilter {
    @Override
    /*filteType
     * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型
     * pre 路由之前
     * routing 路由之时
     * post 路由之后
     * error 发送错误调用
     * */
    public String filterType() {
        return "pre";
    }

    /*filterOrder 过滤顺序*/
    @Override
    public int filterOrder() {
        return 0;
    }

    /*shouldFilter 是否需要过滤，这里是true,需要过滤*/
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*run 是过滤器的具体业务代码*/
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext=RequestContext.getCurrentContext();
        HttpServletRequest request=currentContext.getRequest();
        String token=request.getParameter("token");
        if (token==null){
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            try {
                HttpServletResponse response=currentContext.getResponse();
                response.setContentType("text/html;charset=utf-8");
                currentContext.getResponse().getWriter().write("非法请求");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
