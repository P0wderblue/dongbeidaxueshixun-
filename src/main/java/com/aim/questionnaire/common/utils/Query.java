package com.aim.questionnaire.common.utils;

import com.aim.questionnaire.common.xss.SQLFilter;

import java.util.LinkedHashMap;
import java.util.Map;

public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    private int page = 1;
    private int limit = 10;

    public Query(Map<String, Object> params) {
        this.putAll(params);
        if (params != null) {
            Object oPage = params.get("page");
            if (oPage != null) {
                this.page = Integer.parseInt(params.get("page").toString());
            }

            Object oLimit = params.get("limit");
            if (oLimit != null) {
                this.limit = Integer.parseInt(params.get("limit").toString());
            }

            Object osidx = params.get("sidx");
            String order;
            if (osidx != null) {
                String sidx = params.get("sidx").toString();
                order = params.get("order").toString();
                this.put("sidx", SQLFilter.sqlInject(sidx));
            }

            Object oOrder = params.get("order");
            if (oOrder != null) {
                order = params.get("order").toString();
                this.put("order", SQLFilter.sqlInject(order));
            }
        }

        this.put("offset", (this.page - 1) * this.limit);
        this.put("page", this.page);
        this.put("limit", this.limit);
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}