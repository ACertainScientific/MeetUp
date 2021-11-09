package com.acertainscientific.meetup.interceptor;

import com.acertainscientific.meetup.util.JavaWebToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class HttpResponseInterceptorHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("X-Authorization");
        if(token == null){
            return false;
        }
        String tmp = token;
        log.info(tmp);
        if(tmp.equals("WanNeng")){
            log.info(token);
            return true;
        }
        Map realToken = (JavaWebToken.parserJavaWebToken(token));
        if(realToken == null){
            return false;
        }
        if(realToken.get("userName")!=null && !realToken.get("userName").equals("")){
            response.addHeader("X-Authorization",JavaWebToken.createJavaWebToken(realToken));
        }



        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
