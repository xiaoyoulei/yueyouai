package com.yueyouai.app.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 点子数据的封装
 * @author Ted
 */
public class IdeaBean {
	
	private int id;
	private int type;			//type :2/3(事件/点评)
	private String title;
	private String desc;		
	private String[] pic;
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
	public String[] getPic() {
		return pic;
	}
	public void setPic(String[] pic) {
		this.pic = pic;
	}
	
	@Override
	public String toString() {
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
			obj.put("type", type);
			obj.put("title", title);
			obj.put("desc", desc);
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
	 * 将String转化为IdeaBean对象
	 * @param jsonStr
	 * @return
	 */
	public static IdeaBean parse(String jsonStr){
		IdeaBean bean = new IdeaBean();
		try {
			JSONObject obj = new JSONObject(jsonStr);
			if(!obj.has("id")) return null;
			if(!obj.has("type")) return null;
			if(!obj.has("title")) return null;
			if(!obj.has("desc")) return null;
			if(!obj.has("pic")) return null;
			
			bean.setId(obj.getInt("id"));
			bean.setType(obj.getInt("type"));
			bean.setTitle(obj.getString("title"));
			bean.setDesc(obj.getString("desc"));
			
			JSONArray array = obj.getJSONArray("pic");
			String[] pic = new String[array.length()];
			for(int i = 0; i<array.length();i++){
				pic[i] = array.getString(i);
			}
			bean.setPic(pic);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
}
