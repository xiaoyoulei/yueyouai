package com.yueyouai.app.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 点子相关
 * @author Ted
 *
 */
public class IdeaDetailBean {
	private int id;
	private int type;	//2/3 (事件，点评) 
	private String title;
	private String desc;
	private String[] pic = new String[]{};
	
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

	/**
	 * 解析IdeaDetail数据
	 * @param jsonStr
	 * @return
	 */
	public static ArrayList<IdeaDetailBean> parse(String jsonStr){
		ArrayList<IdeaDetailBean> datas = null;
		try {
			JSONObject obj = new JSONObject(jsonStr);
			if(!obj.has("data")) return null;
			datas = new ArrayList<IdeaDetailBean>();
			JSONArray array = obj.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject tempObj = (JSONObject) array.get(i);
				datas.add(parseBean(tempObj));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * 解析单个的Bean
	 * @param obj
	 * @return
	 */
	private static IdeaDetailBean parseBean(JSONObject obj){
		IdeaDetailBean bean = null;
		try {
			if(!obj.has("id")) return null;
			if(!obj.has("type")) return null;
			if(!obj.has("title")) return null;
			if(!obj.has("desc")) return null;
			if(!obj.has("pic")) return null;
			bean = new IdeaDetailBean();
			bean.setId(obj.getInt("id"));
			bean.setType(obj.getInt("type"));
			bean.setTitle(obj.getString("title"));
			bean.setDesc(obj.getString("desc"));
			
			//解析图片URL
			JSONArray array = obj.getJSONArray("pic");
			String[] pics = new String[array.length()]; 
			for (int i = 0; i < array.length(); i++) {
				pics[i] = array.get(i).toString();
			}
			bean.setPic(pics);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	
	
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		try {
			obj.put("id", id);
			obj.put("type", type);
			obj.put("title", title);
			obj.put("desc", desc);
			
			//添加图片URL
			for (int i = 0; i < pic.length; i++) {
				array.put(pic[i]);
			}
			obj.put("pic", array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
}
