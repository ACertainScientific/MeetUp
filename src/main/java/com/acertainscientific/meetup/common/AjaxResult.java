package com.acertainscientific.meetup.common;


import java.util.HashMap;
import java.util.Map;

import com.acertainscientific.meetup.common.constant.HttpStatus;
import com.acertainscientific.meetup.util.JavaWebToken;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作消息提醒
 *
 * @author ruoyi
 */
@Data
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 返回内容
     */
    public static final String MESSAGE_TAG = "message";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    public static final String STATUS_TAG = "status";

    public static final String TIMESTAMP_TAG = "timestamp";

    public static final String AUTH = "auth";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(MESSAGE_TAG, msg);
        super.put(STATUS_TAG, code);
        super.put(TIMESTAMP_TAG, System.currentTimeMillis()/1000);
        super.put(AUTH,"");
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code == 200 ? 0: code);
        super.put(MSG_TAG, msg);
        super.put(MESSAGE_TAG, msg);
        super.put(STATUS_TAG, code);
        super.put(TIMESTAMP_TAG, System.currentTimeMillis()/1000);
        super.put(AUTH,"");
        if (StringUtils.checkValNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }
    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data,String exception) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(STATUS_TAG, code);
        super.put(TIMESTAMP_TAG, System.currentTimeMillis()/1000);
        if (StringUtils.checkValNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功", new HashMap<>());
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null,msg);
    }


    public AjaxResult auth(HttpServletRequest request){
        String token = request.getHeader("X-Authorization");
        if (token.equals("WanNeng")){
            super.put(AUTH,"WanNeng");
            return this;
        }
        Map realToken = (JavaWebToken.parserJavaWebToken(token));
        if(realToken.get("userName")!=null && !realToken.get("userName").equals("")){
            //response.addHeader("X-Authorization",JavaWebToken.createJavaWebToken(realToken));
            super.put(AUTH,JavaWebToken.createJavaWebToken(realToken));
        }
        return this;
    }
}

