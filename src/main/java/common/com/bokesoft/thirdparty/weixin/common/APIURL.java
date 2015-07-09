package com.bokesoft.thirdparty.weixin.common;

public class APIURL {

	public static String API_TYPE = null;
	
	public static String GEN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?access_token=";

	public static String CREATE_WEIXIN_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

	public static String SEND_WEIXIN_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

	public static String GET_WEIXIN_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";

	public static String UPLOAD_WEIXIN_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";

	public static String DOWNLOAD_WEIXIN_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
	
	public static String GET_OPENID_URL =  "https://api.weixin.qq.com/sns/oauth2/access_token?";
}
