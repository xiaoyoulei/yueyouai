package com.yueyouai.app.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 注册信息封装
 * @author Ted
 */
public class RegisterBean{
	private String username;	//用户名
	private String mail;		//用户账号
	private String psw;			//用户密码
	private String sex;			//性别
	private String birthday;	//生日
	private String prov;		//所在省份
	private String city;		//所在城市
	
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

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 将Json转为对象
	 * @param jsonStr
	 * @return
	 */
	public static RegisterBean parse(String jsonStr) {
		RegisterBean bean = null;
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			bean = new RegisterBean();
			if(jsonObj.has("username"))
				bean.setUsername(jsonObj.getString("username"));
			if(jsonObj.has("mail"))
				bean.setMail(jsonObj.getString("mail"));
			if(jsonObj.has("psw"))
				bean.setPsw(jsonObj.getString("psw"));
			if(jsonObj.has("sex"))
				bean.setSex(jsonObj.getString("sex"));
			if(jsonObj.has("birthday"))
				bean.setBirthday(jsonObj.getString("birthday"));
			if(jsonObj.has("prov"))
				bean.setProv(jsonObj.getString("prov"));
			if(jsonObj.has("city"))
				bean.setCity(jsonObj.getString("city"));
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
			jsonObj.put("username", username);
			jsonObj.put("mail", mail);
			jsonObj.put("psw", psw);
			jsonObj.put("sex", sex);
			jsonObj.put("birthday", birthday);
			jsonObj.put("prov", prov);
			jsonObj.put("city", city);
			return jsonObj.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
