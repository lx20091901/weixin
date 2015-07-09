package com.bokesoft.thirdparty.weixin.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.bokesoft.thirdparty.weixin.bean.WeixinApiResult;
import com.bokesoft.thirdparty.weixin.common.APIURL;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;

/**
 * 调用微信提供的API
 *
 */
public class WeixinApiInvoker {

	/**
	 * 创建菜单
	 * @param publicNumber
	 * @return
	 * @throws IOException
	 */
	public WeixinApiResult createWeixinMenu(String token,String menu) throws IOException{
		String create_weixin_menu_url = APIURL.CREATE_WEIXIN_MENU_URL+token;
		String result = SimpleHttpClient.invokePostWithBody4String(create_weixin_menu_url,menu,3000);
		return new WeixinApiResult(result);
		
	}
	
	/**
	 * 发送客服消息
	 * @param access_token
	 * @param message
	 * @return
	 * @throws IOException
	 */
	public WeixinApiResult sendMessage(String access_token,String message) throws IOException{
		String send_weixin_message_url  = APIURL.SEND_WEIXIN_MESSAGE_URL+access_token;
		String result = SimpleHttpClient.invokePostWithBody4String(send_weixin_message_url,message,3000);
		return new WeixinApiResult(result);
	}
	
	/**
	 * 获取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 * @throws IOException
	 */
	public String getUserInfo(String access_token,String openid) throws IOException{
		String get_userinfo_url = APIURL.GET_WEIXIN_USERINFO_URL+access_token+"&openid="+openid;
		String result = SimpleHttpClient.invokeGet4String(get_userinfo_url,3000);
		return result;
	}
	
	/**
	 * 上传多媒体文件
	 * @param access_token
	 * @param type
	 * @param array
	 * @param filename
	 * @param fileLength
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
	public String uploadMedia(String access_token,String type,byte[] array,String filename,String fileLength,String contentType) throws IOException{
		String upload_weixin_media_url = APIURL.UPLOAD_WEIXIN_MEDIA_URL+access_token+"&type="+type;
		Map<String,String> media = new HashMap<String, String>();
		media.put("filename", filename);
		media.put("fileLength", fileLength);
		media.put("contentType", contentType);
		String result = SimpleHttpClient.invokePostWithBody4String(upload_weixin_media_url, new ByteArrayInputStream(array),media,3000);
		return result;
	}
	
	/**
	 * 下载多媒体文件
	 * @param access_token
	 * @param media_id
	 * @return
	 * @throws IOException
	 */
	public InputStream downloadMedia(String access_token,String media_id) throws IOException{
		String download_weixin_media_url = APIURL.DOWNLOAD_WEIXIN_MEDIA_URL+access_token+"&media_id="+media_id;
		InputStream result = SimpleHttpClient.invokeGet4Stream(download_weixin_media_url,3000);
		return result;
	}
	
	/**
	 * 
	 * @param appid
	 * @param appsecret
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public String getOpenid(String appid,String appsecret,String code) throws IOException{
		String get_openid_url = APIURL.GET_OPENID_URL+"appid="+appid+"&secret="+appsecret+"&grant_type=authorization_code&code="+code;
		String result = SimpleHttpClient.invokePost4String(get_openid_url, null, 3000);
		return result;
	}
}
