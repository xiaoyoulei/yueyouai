package com.yueyouai.app.data;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


public class MessageBean implements Serializable{
	
	private static final long serialVersionUID = 201312301414L;

	/**
	 * 消息类型 ：0：文本信息；1：图片信息； 2：音频信息； 3：视频信息；4：其他信息
	 * @author Ted
	 */
	public interface MessageType{
		public final int MSG_TYPE_TEXT = 0x0;
		public final int MSG_TYPE_IMAGE = 0x1;
		public final int MSG_TYPE_VOICE = 0x2;
		public final int MSG_TYPE_VIDEO = 0x3;
		public final int MSG_TYPE_OTHER = 0x4;
	}
	
	private int msgType;			//消息类型	
	private String msg;				//消息内容
	private long sendTime;			//发送时间
	private String sendUserId;		//发送用户Id
	private String targetUserId;	//目标用户Id
	private boolean isSend;			//true 为发送的消息； false 为接收的消息
	
	
	/**
	 * 生成消息体
	 * @param msgType
	 * @param msg
	 * @param isSend
	 */
	public MessageBean(int msgType,String msg,boolean isSend){
		this.msgType = msgType;
		this.msg = msg;
		this.sendTime = System.currentTimeMillis();
		this.isSend = isSend;
	}
	
	private MessageBean(){}


	public int getMsgType() {
		return msgType;
	}


	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getSendTime() {
		return sendTime;
	}
	
	public String getSendUserId() {
		return sendUserId;
	}


	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}


	public String getTargetUserId() {
		return targetUserId;
	}


	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
	
	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	@Override
	public String toString() {
		
		JSONObject obj = new JSONObject();
		try {
			obj.put("msg", msg);
			obj.put("sendTime", sendTime);
			obj.put("msgType", msgType);
			obj.put("sendUserId", sendUserId);
			obj.put("targetUserId", targetUserId);
			obj.put("isSend", isSend);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/**
	 * 将Json数据转化为消息对象
	 * @param jsonStr
	 * @return
	 */
	public MessageBean parse(String jsonStr){
		MessageBean bean = null;
		try {
			JSONObject obj = new JSONObject(jsonStr);
			if(!obj.has("msg")) return null;
			if(!obj.has("sendTime")) return null;
			if(!obj.has("msgType")) return null;
			if(!obj.has("sendUserId")) return null;
			if(!obj.has("targetUserId")) return null;
			if(!obj.has("isSend")) return null;
			bean = new MessageBean();
			bean.setMsg(obj.getString("msg"));
			bean.setMsgType(obj.getInt("msgType"));
			bean.setSendUserId(obj.getString("sendUserId"));
			bean.setSendUserId(obj.getString("targetUserId"));
			bean.setSend(obj.getBoolean("isSend"));
			bean.sendTime = obj.getLong("sendTime");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
}
