package com.weixin.util;

import java.util.HashMap;
import java.util.Map;

import com.wxapi.WxApiCall.WxApiCall;
import com.wxapi.model.RequestModel;

public class AutomaticReply {
	
	/*
	 * loc 不能为null  应该为空字符串
	 * 
	 */
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
		return msg;
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
		String msg=call.request();
		System.out.println(msg);
	}
}
