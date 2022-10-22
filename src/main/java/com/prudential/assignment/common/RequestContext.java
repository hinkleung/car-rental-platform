package com.prudential.assignment.common;

import com.prudential.assignment.common.constants.Constants;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestContext {

    private static final ThreadLocal<Map<String, String>> contextMap = new ThreadLocal<>();

    protected static Map<String, String> contextMap() {
        initRequestContentMap();
        return RequestContext.contextMap.get();
    }

    public static void setRequestContent(String key, String value) {
        contextMap().put(key, value);
    }
    public static Long getUserId() {
        String userId = contextMap().get(Constants.REQUEST_CONTENT_USER_ID);
        if (StringUtils.isEmpty(userId)) {
            return Constants.NON_LOGIN_DEFAULT_ID;
        }
        return Long.parseLong(userId);
    }

    private static void initRequestContentMap() {
        if (RequestContext.contextMap.get() == null) {
            RequestContext.contextMap.set(new HashMap<>());
        }
    }

    public static void resetRequestContent() {
        RequestContext.contextMap.remove();
    }


}
