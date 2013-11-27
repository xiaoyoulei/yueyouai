package com.yueyouai.app.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 首页数据
 * 
 * @author Ted
 */
public class DoMainBean {

	private int nowid; 							// 当前数据中最后一个id ，整型数
	private int id; 							// 标识字段
	private int type; 							// 1/2/3 (对应点子，事件，点评) 建议不要混入点评
	private String title; 						// 标题(256byte)
	private String desc; 						// 描述信息
	private String thumbnail; 					// Icon Url
	private String picUrl; 						// 大图Url

	public int getNowid() {
		return nowid;
	}

	public void setNowid(int nowid) {
		this.nowid = nowid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * 解析首页数据
	 * @param jsonStr
	 * @return
	 */
	public static List<DoMainBean> parse(String jsonStr) {
		List<DoMainBean> list = new ArrayList<DoMainBean>();
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			if (!jsonObj.has("nowid"))
				return null;
			if (jsonObj.has("data")) {
				JSONArray array = jsonObj.getJSONArray("data");
				for (int i = 0; i < array.length(); i++) {
					DoMainBean bean = parseDomainBean(array.getJSONObject(i));
					if(bean != null){
						list.add(bean);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		if (list.size() > 0)
			return list;
		else
			return null;
	}

	/**
	 * Json转Bean
	 * @param tempObj
	 * @return
	 * @throws JSONException
	 */
	private static DoMainBean parseDomainBean(JSONObject tempObj)
			throws JSONException {
		DoMainBean tempBean = new DoMainBean();
		if (!tempObj.has("id"))
			return null;
		if (!tempObj.has("type"))
			return null;
		if (!tempObj.has("title"))
			return null;
		if (!tempObj.has("desc"))
			return null;
		if (!tempObj.has("pic"))
			return null;
		tempBean.setId(tempObj.getInt("id"));
		tempBean.setType(tempObj.getInt("type"));
		tempBean.setTitle(tempObj.getString("title"));
		tempBean.setDesc(tempObj.getString("desc"));
		JSONArray pic = tempObj.getJSONArray("pic");
		// TODO：确定图片的顺序
		tempBean.setThumbnail(pic.get(0).toString());
		tempBean.setPicUrl(pic.get(1).toString());
		return tempBean;
	}
}
