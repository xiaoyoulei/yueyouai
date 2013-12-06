package com.yueyouai.app.data;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 测试类，酒店数据解析器
 * @author TedYin 
 *
 */
public class HotelMessageBean implements Serializable{
	
	private static final long serialVersionUID = 20130512L;
	
	private String photoUrl = "";		//酒店图片链接
	private String address = "";		//酒店地址
	private String name = "";			//酒店名称
	private String price = "";			//酒店价格

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * 将json解析成HotelBean对象
	 * @param is
	 * @return
	 */
	public static HotelMessageBean parseMessage(String json) {
		HotelMessageBean bean = new HotelMessageBean();
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(!jsonObj.has("street_address")) return null;
			if(!jsonObj.has("name")) return null;
			if(!jsonObj.has("nightly_rate")) return null;
			bean.setAddress(jsonObj.getString("street_address"));
			bean.setName(jsonObj.getString("name"));
			if(jsonObj.has("thumbnail"))
				bean.setPhotoUrl(jsonObj.getString("thumbnail"));
			bean.setPrice(jsonObj.getString("nightly_rate"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
