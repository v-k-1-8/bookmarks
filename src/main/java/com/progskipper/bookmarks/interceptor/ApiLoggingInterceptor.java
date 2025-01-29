package com.progskipper.bookmarks.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Log4j2
@Component
public class ApiLoggingInterceptor implements HandlerInterceptor {
    ///  Intercepts Every Request on this server and logs it

    // Before going to controller
    @Override
    public boolean preHandle(HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        log.info("API Call Started with path {}", request.getRequestURI());
        return true;
    }

    // After Completing Service
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,@NonNull Object handler, Exception ex) {
        long startTime = (long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;
        log.info("API Call Completed: {} | Time Taken: {} ms | Status: {}",
                request.getRequestURI(), duration, response.getStatus());
    }

}
