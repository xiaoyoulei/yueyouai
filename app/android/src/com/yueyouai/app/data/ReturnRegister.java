package com.yueyouai.app.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 注册返回数据的封装
 * @author Ted
 */
public class ReturnRegister {
	private String xiaoenai_token;		//AAAAA(32位大写字母)用户登录标识
	private String username;			//用户昵称
	private String mail;				//账号
	private String info;				//登录状态"success"为成功
	
	public String getXiaoenai_token() {
		return xiaoenai_token;
	}
	public void setXiaoenai_token(String xiaoenai_token) {
		this.xiaoenai_token = xiaoenai_token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	/**
	 * 将Json转为对象
	 * @param jsonStr
	 * @return
	 */
	public static ReturnRegister parse(String jsonStr){
		ReturnRegister bean = null;
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			bean = new ReturnRegister();
			if(!jsonObj.has("xiaoenai_token")) return null;
			if(!jsonObj.has("username")) return null;
			if(!jsonObj.has("mail")) return null;
			if(!jsonObj.has("info")) return null;
			bean.setXiaoenai_token(jsonObj.getString("xiaoenai_token"));
			bean.setUsername(jsonObj.getString("username"));
			bean.setMail(jsonObj.getString("mail"));
			bean.setInfo(jsonObj.getString("info"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return bean;
	}
	
	@Override
	public String toString() {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("xiaoenai_token", xiaoenai_token);
			jsonObj.put("username", username);
			jsonObj.put("mail", mail);
			jsonObj.put("info", info);
			return jsonObj.toString();
		}catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
