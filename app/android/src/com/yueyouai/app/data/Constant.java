package com.yueyouai.app.data;

/**
 * 常用静态数据
 * @author Ted
 */
public interface Constant {
	/** HOST地址*/
	public static final String HOST = "http://www.yueyouai.com";			
	/** 注册*/
	public static final String URL_REGISTER = HOST + "/client/reg";
	/** 登录*/
	public static final String URL_LOGIN = HOST + "/client/login";
	/** 首页信息*/
	public static final String URL_DO_MAIN = HOST + "/client/home?";
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
	
	/** 数据类型：  1/2/3 (对应点子，事件，点评)*/
	public static final int DATA_TYPE_IDEA = 1;
	public static final int DATA_TYPE_RECORD = 2;
	public static final int DATA_TYPE_COMMENT = 3;
	
	/** 注册登录等返回的状态数据*/
	public static final String KEY_USERNAME = "username";
	public static final String KEY_MAIL = "mail";
	public static final String KEY_INFO = "info";
	
	/** 请求成功*/
	public static final int RESPONSE_STATUS_SUCCESS = 1000;
}
