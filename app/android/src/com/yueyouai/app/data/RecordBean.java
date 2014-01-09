package com.yueyouai.app.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 记录数据的封装
 * @author Ted
 *
 */
public class RecordBean {
	
	private int id;					//id
	private int type;				//消息类型
	private String title;
	private String desc;
	private String introduce;		//简介替换掉数据接口中的abstract
	private String[] pic;
	private long createTime;		//创建时间
	private long lastModifyTime;	//最后一次修改时间		
	private long releaseTime;		//发布时间
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
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String[] getPic() {
		return pic;
	}
	public void setPic(String[] pic) {
		this.pic = pic;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public long getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(long releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("type", type);
			obj.put("title", title);
			obj.put("desc", desc);
			obj.put("introduce", introduce);
			obj.put("createTime", createTime);
			obj.put("releaseTime", releaseTime);
			obj.put("lastModifyTime", lastModifyTime);
			JSONArray array = new JSONArray();
			if(pic != null){
				for(String url : pic){
					array.put(url);
				}
			}
			obj.put("pic", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/**
	 * 将String
	 * @param jsonStr
	 * @return
	 */
	public static RecordBean parse(String jsonStr){
		RecordBean bean = new RecordBean();
		try {
			JSONObject obj = new JSONObject(jsonStr);
			if(!obj.has("id")) return null;
			if(!obj.has("type")) return null;
			if(!obj.has("title")) return null;
			if(!obj.has("desc")) return null;
			if(!obj.has("introduce")) return null;
			if(!obj.has("pic")) return null;
			if(!obj.has("createTime")) return null;
			if(!obj.has("releaseTime")) return null;
			if(!obj.has("lastModifyTime")) return null;
			
			bean.setId(obj.getInt("id"));
			bean.setType(obj.getInt("type"));
			bean.setTitle(obj.getString("title"));
			bean.setDesc(obj.getString("desc"));
			bean.setIntroduce(obj.getString("introduce"));
			JSONArray array = obj.getJSONArray("pic");
			String[] pic = new String[array.length()];
			for(int i = 0; i<array.length();i++){
				pic[i] = array.getString(i);
			}
			bean.setPic(pic);
			bean.setCreateTime(obj.getLong("createTime"));
			bean.setReleaseTime(obj.getLong("releaseTime"));
			bean.setLastModifyTime(obj.getLong("lastModifyTime"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
