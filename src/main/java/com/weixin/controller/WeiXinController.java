package com.weixin.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.util.Constants;
import com.weixin.base.WXBizMsgCrypt;
import com.weixin.base.XMLParse;
import com.weixin.util.AutomaticReply;
import com.weixin.util.SignUtil;

@Controller
@RequestMapping("weixin")
public class WeiXinController {
	/**微信消息回调地址接口
	 * @author gl
	 * @datetime 2018-2-27
	 * */
	@RequestMapping("/msgIn")
	public String msgIn(HttpServletRequest request,HttpServletResponse response) {
		try {
			  PrintWriter out = response.getWriter();
			if("GET".equals(request.getMethod())){
				// 微信加密签名 
		        String sVerifyMsgSig = request.getParameter("signature");
		        // 时间戳
		        String sVerifyTimeStamp = request.getParameter("timestamp");
		        // 随机数
		        String sVerifyNonce = request.getParameter("nonce");
		        // 随机字符串
		        String sVerifyEchoStr = request.getParameter("echostr");
		      
		        try {
		            // 验证URL成功，将sEchoStr返回
		        	if(SignUtil.checkSignature(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce)){
		        		 out.print(sVerifyEchoStr);  
		        	}
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
			}else if("POST".equals(request.getMethod())){
					
							try {
							// TODO Auto-generated method stub
							  // 从request中取得输入流    
						       InputStream inputStream = request.getInputStream();    
						         
						       BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));  
						       String line;  
						       StringBuffer buf=new StringBuffer();  
						       while((line=reader.readLine())!=null){  
						        buf.append(line);  
						       }  
						       reader.close();  
						       inputStream.close();  
						        
						       WXBizMsgCrypt wxCeypt=new WXBizMsgCrypt(Constants.WEIXIN_TOKEN, Constants.WEIXIN_EncodingAESKey,Constants.WEINXIN_AppId ); 
						       // 微信加密签名    
						       String msgSignature = request.getParameter("msg_signature");    
						       // 时间戳    
						       String timestamp = request.getParameter("timestamp");    
						       // 随机数    
						       String nonce = request.getParameter("nonce");    
						         
						       String respXml=wxCeypt.decryptMsg(msgSignature, timestamp, nonce, buf.toString());  
						        
						       Map<String, String> map =  XMLParse.extractMsg(respXml);
						       
						      // String msg=AutomaticReply.getReply(map.get("Content"), "",map.get("FromUserName"));
						       System.out.println("////"+wxCeypt.encryptMsg("回复", timestamp, nonce));
						       out.print(wxCeypt.encryptMsg("回复", timestamp, nonce));  
						       out.close();
							} catch (Exception e) {
								// TODO: handle exception
							};
					
			     
			       
   
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
