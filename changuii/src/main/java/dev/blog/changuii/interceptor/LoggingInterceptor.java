package dev.blog.changuii.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();
        logger.info("URL :" + request.getRequestURL());
        logger.info("METHOD : "+ request.getMethod());
        logger.info("QueryString : " + request.getQueryString());
        logger.info("Client : " + request.getRemoteAddr());

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();
        logger.info("URL :" + request.getRequestURL());
        logger.info("METHOD : "+ request.getMethod());
        logger.info("QueryString : " + request.getQueryString());
        logger.info("Client : " + request.getRemoteAddr());
        logger.info("Process Result : "+response.getStatus());
    }
}
