package com.aim.questionnaire.common.xss;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest;
    private static final HTMLFilter htmlFilter = new HTMLFilter();

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.orgRequest = request;
    }

    public ServletInputStream getInputStream() throws IOException {
        if (!super.getHeader("Content-Type").equalsIgnoreCase("application/json")) {
            return super.getInputStream();
        } else {
            String json = IOUtils.toString(super.getInputStream(), "utf-8");
            if (StringUtils.isBlank(json)) {
                return super.getInputStream();
            } else {
                json = this.xssEncode(json);
                final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
                return new ServletInputStream() {
                    public boolean isFinished() {
                        return true;
                    }

                    public boolean isReady() {
                        return true;
                    }

                    public void setReadListener(ReadListener readListener) {
                    }

                    public int read() throws IOException {
                        return bis.read();
                    }
                };
            }
        }
    }

    public String getParameter(String name) {
        String value = super.getParameter(this.xssEncode(name));
        if (StringUtils.isNotBlank(value)) {
            value = this.xssEncode(value);
        }

        return value;
    }

    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters != null && parameters.length != 0) {
            for(int i = 0; i < parameters.length; ++i) {
                parameters[i] = this.xssEncode(parameters[i]);
            }

            return parameters;
        } else {
            return null;
        }
    }

    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap();
        Map<String, String[]> parameters = super.getParameterMap();
        Iterator var3 = parameters.keySet().iterator();

        while(var3.hasNext()) {
            String key = (String)var3.next();
            String[] values = (String[])parameters.get(key);

            for(int i = 0; i < values.length; ++i) {
                values[i] = this.xssEncode(values[i]);
            }

            map.put(key, values);
        }

        return map;
    }

    public String getHeader(String name) {
        String value = super.getHeader(this.xssEncode(name));
        if (StringUtils.isNotBlank(value)) {
            value = this.xssEncode(value);
        }

        return value;
    }

    private String xssEncode(String input) {
        return htmlFilter.filter(input);
    }

    public HttpServletRequest getOrgRequest() {
        return this.orgRequest;
    }

    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        return request instanceof XssHttpServletRequestWrapper ? ((XssHttpServletRequestWrapper)request).getOrgRequest() : request;
    }
}
