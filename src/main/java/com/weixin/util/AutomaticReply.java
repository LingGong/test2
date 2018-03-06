package com.weixin.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxapi.WxApiCall.WxApiCall;
import com.wxapi.model.RequestModel;

/**   
*    
* 项目名称：test2   
* 类名称： 自动回复类    
* 类描述：  自动回复功能
* 创建人：gl   
* 创建时间：  
* 修改人：   
* 修改时间：   
* 修改备注：   
* @version  
*    
*/
public class AutomaticReply {
	
	
	/**  
	* @Title: getReply  
	* @Description: 图灵机器人自动回复  
	* @param  info 接受的消息
	* @param  loc 地理信息 不可为null 可为空字符串
	* @param openId 用户唯一id
	* @param   
	* @return String 回复内容   
	* @throws  
	*/ 
	@SuppressWarnings("unchecked")
	public static String getReply(String info,String loc,String openId){
		System.out.println("info:"+info);
		System.out.println("openId:"+openId);
		RequestModel model = new RequestModel();
		model.setGwUrl("https://way.jd.com/turing/turing");
		model.setAppkey("e261152180cf3dc74888f82ea410eb98");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("info",info); //访问参数
		queryMap.put("loc",loc); //访问参数
		queryMap.put("userid",openId); //访问参数
		model.setQueryParams(queryMap);
		WxApiCall call = new WxApiCall();
		call.setModel(model);
		String msg=call.request();
		ObjectMapper om=new ObjectMapper();
		Map<String, Object> map;
		try {
			map = om.readValue(msg, Map.class);
			Object o=map.get("result");
			Map<String,String> maps=(Map<String, String>)o;
			String text=maps.get("text");
			return text;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static String getReplyXml(String msg,Map<String,String> map){
		 Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间  
		StringBuffer str = new StringBuffer();  
        str.append("<xml>");  
        //神坑 回复的时候 FromUserName和 ToUserName和接受时候的对调   
        str.append("<ToUserName><![CDATA[" + map.get("FromUserName") + "]]></ToUserName>");  
        str.append("<FromUserName><![CDATA[" +map.get("ToUserName") + "]]></FromUserName>");  
        str.append("<CreateTime>" + returnTime + "</CreateTime>");  
        str.append("<MsgType><![CDATA[" + map.get("MsgType") + "]]></MsgType>");  
        str.append("<Content><![CDATA["+msg+"]]></Content>");
        str.append("</xml>");  
		return str.toString();
	}
	
	
	
	
	
	public static void main(String[] args) {
		String info="1";
		String openId="2";
		String loc="";
		System.out.println("info:"+info);
		System.out.println("openId:"+openId);
		RequestModel model = new RequestModel();
		model.setGwUrl("https://way.jd.com/turing/turing");
		model.setAppkey("e261152180cf3dc74888f82ea410eb98");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("info",info); //访问参数
		queryMap.put("loc",loc); //访问参数
		queryMap.put("userid",openId); //访问参数
		model.setQueryParams(queryMap);
		WxApiCall call = new WxApiCall();
		call.setModel(model);
		
		
	
	}
}
