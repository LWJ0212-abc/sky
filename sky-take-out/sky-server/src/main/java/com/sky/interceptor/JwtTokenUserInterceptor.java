package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: sky-take-out
 * @package: com.sky.interceptor
 * @className: JwtTokenUserInterceptor
 * @author: lwj
 * @description: TODO
 * @date: 2026/6/28 00:27
 * @version: 1.0
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtProperties jwtProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //获取令牌
        String token=request.getHeader(jwtProperties.getUserTokenName());

        //检验令牌
        try{
            log.info("user jwt:{}",token);
            Claims claims= JwtUtil.parseJWT(token,jwtProperties.getUserTokenName());
            Long userId=Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("userId:{}",userId);
            BaseContext.setCurrentId(userId);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }
}
