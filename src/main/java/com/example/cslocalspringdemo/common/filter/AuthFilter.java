package com.example.cslocalspringdemo.common.filter;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.example.cslocalspringdemo.common.Util.StringUtil;
import com.example.cslocalspringdemo.common.result.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthFilter implements Filter, Ordered {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("true")
    public boolean isOpenAuth;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        printRequest((HttpServletRequest) servletRequest);
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        res.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Accept, Origin, Authorization, content-type, ModuleId");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,PATCH,OPTIONS");
        res.setHeader("X-Powered-By", "3.2.1");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String path = httpServletRequest.getRequestURI();
        log.info("headerToken：" + httpServletRequest.getHeader("Authorization"));
        if ("OPTIONS".equals(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }

//		if (path.startsWith("/login")) {
//			filterChain.doFilter(servletRequest, servletResponse);
//			return;
//		}

//		if (isOpenAuth) {
//			String headerToken = httpServletRequest.getHeader("Authorization");
//			if (StringUtil.isBlank(headerToken)) {
//				unAuth(servletResponse, "缺失令牌,鉴权失败");
//				return;
//			}
//			Claims claims = SecureUtil.parseJWT(headerToken);
//			if (claims == null) {
//				unAuth(servletResponse, "请求未授权");
//				return;
//			}
//		}
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void printRequest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headerMap.put(name, request.getHeader(name));
        }
        log.info("2024 HttpServletRequest headerMap:{}", JSON.toJSONString(headerMap));
        Enumeration<String> parames = request.getParameterNames();
        Map<String, String> paramMap = new LinkedHashMap<>();
        while (parames.hasMoreElements()) {
            String name = parames.nextElement();
            paramMap.put(name, request.getParameter(name));
        }
        log.info("2024 HttpServletRequest paramMap:{}", paramMap);
    }

//	private boolean isSkip(String path) {
//		return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains)
//			|| authProperties.getSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains);
//	}

    private void unAuth(ServletResponse resp, String msg) {
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        httpResp.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResp.setHeader("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(Response.notOk(40000, msg));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        try {
            httpResp.getOutputStream().write(result.getBytes());
        } catch (IOException e) {
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }
}