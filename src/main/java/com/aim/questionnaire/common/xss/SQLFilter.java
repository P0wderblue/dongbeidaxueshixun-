package com.aim.questionnaire.common.xss;

import com.aim.questionnaire.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

public class SQLFilter {
    public SQLFilter() {
    }

    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            str = StringUtils.replace(str, "'", "");
            str = StringUtils.replace(str, "\"", "");
            str = StringUtils.replace(str, ";", "");
            str = StringUtils.replace(str, "\\", "");
            str = str.toLowerCase();
            String[] keywords = new String[]{"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};
            String[] var2 = keywords;
            int var3 = keywords.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String keyword = var2[var4];
                if (str.indexOf(keyword) != -1) {
                    throw new RRException("包含非法字符");
                }
            }

            return str;
        }
    }
}