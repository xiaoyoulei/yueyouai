package com.yueyouai.app.data;

/**
 * 常用静态数据
 * @author Ted
 */
public interface Constant {
	/** HOST地址*/
	public static final String HOST = "http://23.226.79.72:8888";
	/** 注册*/
	public static final String URL_REGISTER = HOST + "";
	/** 登录*/
	public static final String URL_LOGIN = HOST + "/login";
	/** 首页信息*/
	public static final String URL_DO_MAIN = HOST + "/home?";
	/** 点子相关*/
	public static final String URL_IDEA = HOST+"";
	/** 事件相关*/
	public static final String URL_EVENT = HOST + "";
	/** 提交事件*/
	public static final String URL_SUB_EVENT = HOST + "";
	/** 用户信息*/
	public static final String URL_USER_INFO = HOST + "";
	/** Cookie名称*/
	public static final String COOKIE = "yueyouai_token";
	/** 每次获取的Data的条数*/
	public static final int DATA_STEP_NUM = 10;
	
	/** 当前消息数量*/
	public static final String KEY_CURRENT_ITEM_NUM = "KEY_CURRENT_ITEM_NUM";
	
}
