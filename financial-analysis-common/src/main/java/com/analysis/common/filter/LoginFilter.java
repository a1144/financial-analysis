package com.analysis.common.filter;

import com.analysis.common.utils.GetVerificationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lvshuzheng
 * @className LoginFilter
 * @description
 * @date 2020/4/21
 */
@WebFilter(filterName = "loginFilter", urlPatterns = {"/*"})
@Component
public class LoginFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);
    private static final String NO_LOGIN = "您未登录";
    private String[] excludeUrls = new String[]{"/page/login/login.html", "/register", "/user/login", "/check"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("loginFilter初始化。。。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        //生成验证码
        if ("/page/login/login.html".equals(uri)) {
            GetVerificationCode getVerificationCode = new GetVerificationCode();
            String verificationCode = getVerificationCode.imgDemo3(new FileOutputStream(getVerificationCode.getClass()
                    .getResource("/").getPath() + "/static/images/verificationCode.jpg"));
            Cookie cookie = new Cookie("verificationCode", verificationCode);
            response.addCookie(cookie);
            System.out.println("cookieName: " + cookie.getName() + ",cookieValue: " + cookie.getValue());
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        boolean needFilter = isNeedFilter(uri);
        if (!needFilter) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (session.getAttribute("userName") != null) {
                filterChain.doFilter(request, response);
            } else {
                String requestType = request.getHeader("X-Requested-With");
                if (requestType != null && "XMLHttpRequest".equals(requestType)) {
                    response.getWriter().write(NO_LOGIN);
                } else {
                    GetVerificationCode getVerificationCode = new GetVerificationCode();
                    String verificationCode = getVerificationCode.imgDemo3(new FileOutputStream(getVerificationCode.getClass()
                            .getResource("/").getPath() + "/static/images/verificationCode.jpg"));
                    Cookie cookie = new Cookie("verificationCode", verificationCode);
                    response.addCookie(cookie);
                    System.out.println("cookieName: " + cookie.getName() + ",cookieValue: " + cookie.getValue());
                    response.sendRedirect(request.getContextPath() + "/page/login/login.html");
                    return;
                }
                return;
            }
        }

    }

    @Override
    public void destroy() {
        LOGGER.info("loginFilter销毁。。。");
    }

    public boolean isNeedFilter(String uri) {
        String regex = "\\S*(\\.jpg|\\.js|\\.css|\\.ico|\\.png)";
        for (String includeUrl : excludeUrls) {
            if (includeUrl.equals(uri)) {
                return false;
            }
        }
        if (uri.matches(regex)) {
            return false;
        }
        return true;
    }
}
