package com.aim.questionnaire.common.xss;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XssFilter implements Filter {
    private String excludedPages;
    private String[] excludedPageArray;

    public XssFilter() {
    }

    public void init(FilterConfig config) throws ServletException {
        this.excludedPages = config.getInitParameter("excludedPages");
        if (StringUtils.isNotEmpty(this.excludedPages)) {
            this.excludedPageArray = this.excludedPages.split(",");
        }

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest)request);
        boolean isExcludedPage = false;
        String[] var6 = this.excludedPageArray;
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String page = var6[var8];
            if (((HttpServletRequest)request).getServletPath().equals(page)) {
                isExcludedPage = true;
                break;
            }
        }

        if (isExcludedPage) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(xssRequest, response);
        }

    }

    public void destroy() {
    }
}
