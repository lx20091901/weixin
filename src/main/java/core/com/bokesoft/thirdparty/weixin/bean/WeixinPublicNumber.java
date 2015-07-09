package com.bokesoft.thirdparty.weixin.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bokesoft.myerp.common.ByteUtil;
import com.bokesoft.myerp.common.StringUtil;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinRemoteLocationEventMessage;
import com.bokesoft.thirdparty.weixin.bean.message.WeixinReplyTextMessage;
import com.bokesoft.thirdparty.weixin.common.APIURL;
import com.bokesoft.thirdparty.weixin.common.Kaptcha;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.bokesoft.thirdparty.weixin.common.TConstant;

/**
 * 微信公众号对象 
 *
 */
public class WeixinPublicNumber implements Serializable{
	
	public static final String ACCESS_TOKEN = "access_token";
	
	public static final String APP_ID = "app_id";
	
	public static final String APP_SECRET = "app_secret";

	public static final String DEFAULTREPLY_MESSAGE = "defaultReplyMessage";
	
	public static final String EDIT_KEYWORDS_REPLYMESSAGE = "editKeywordsReplyMessage";

	public static final String ENCRYPT_REMOTE_MESSAGE = "encrypt_remote_message";

	private static final int[] KEYWORDS_SIZE = new int[]{50,100,200,500};

	public static final String KEYWORDSREPLY_MESSAGE = "keywordsReplyMessage";

	public static final String MESSAGE_TOKEN = "message_token";
	
	public static final String PASSWORD = "password";

	public static final String PUBLIC_TYPE = "public_Type";

	public static final String REMOTE_MESSAGE_ENCRYPT = "remote_message_encrypt";

	private static final long serialVersionUID = 5303254656424300728L;

	public static final String SERVICE_TIME = "service_time";

	public static final String STATUS = "status";
	
	public static final String SUBSCRIBEREPLY_MESSAGE = "subscribeReplyMessage";
	
	public static final String UNAME = "uname";
	
	public static final String WEIXIN_REMOTE_LOCATIONEVENT_MESSAGE = "weixinRemoteLocationEventMessage";
	
	public static final String WEIXINMENU = "weixinMenu";
	
	public static WeixinPublicNumber createDefault(){
		WeixinPublicNumber publicNumber = new WeixinPublicNumber();
		publicNumber.setPublic_Type(WeixinPublicNumberType.SUBSCRIPTION);
//		WeixinRemoteImageMessage weixinRemoteImageMessage = new WeixinRemoteImageMessage();
//		weixinRemoteImageMessage.setRemoteType("http");
//		weixinRemoteImageMessage.setUrl("请输入图片远程消息调用地址");
//		publicNumber.setWeixinRemoteImageMessage(weixinRemoteImageMessage);
		
		WeixinRemoteLocationEventMessage weixinRemoteLocationEventMessage = new WeixinRemoteLocationEventMessage();
		weixinRemoteLocationEventMessage.setRemoteType("http");
		weixinRemoteLocationEventMessage.setUrl("请输入地理位置消息远程调用地址");
		publicNumber.setWeixinRemoteLocationEventMessage(weixinRemoteLocationEventMessage);
		
		publicNumber.setWeixinMenu("{\"button\":[]}");
		publicNumber.setMessage_token(Kaptcha.randomCode(16));
		
		return publicNumber;
	}
	
	public static WeixinPublicNumber parse(byte[] bytes) {
		try {
			return (WeixinPublicNumber) ByteUtil.toObject(bytes);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String access_token = "";
	
	private String app_id = null;
	
	private String app_secret = null;
	
	private long createTime = 0;
	
	private WeixinMessage defaultReplyMessage = new WeixinReplyTextMessage(TConstant.DEFAULT_REPLY_MESSAGE);
	
//	public static final String WEIXIN_REMOTE_IMAGE_MESSAGE = "weixinRemoteImageMessage";
	
	private Map<String, WeixinMessage> editKeywordsReplyMessage = new HashMap<String, WeixinMessage>();

	private boolean encrypt_remote_message = false;

	private long expires_in = 0;

	private String get_access_token_url = null;

	private Map<String, WeixinMessage> keywordsReplyMessage = new HashMap<String, WeixinMessage>();

	private int level = 0;

	private String message_token = null;
	
	private boolean needRegenerate = true;
	
	private String password = null;

	private WeixinPublicNumberType public_Type = null;

	private String remote_message_encrypt = "";
	
	//过期时间
	private long service_time;

	private boolean showPassword;

	//0=试用 1=使用 -1=禁用 2=永久
	private int status  = 0;
	
	private WeixinMessage subscribeReplyMessage = new WeixinReplyTextMessage(TConstant.SUBSCRIBE_MESSAGE);

	private int system_version = 0;

	private String token = null;
	
	private String uname = null;

	private long version = 0;

	private String weixinMenu = null;

	private WeixinRemoteLocationEventMessage weixinRemoteLocationEventMessage = null;
	
	public String genAccess_token(WeixinContext context) throws Exception{
		synchronized (access_token) {
			if (this.needGen()) {
				this.generate("client_credential", this.app_id,this.app_secret);
				context.updateWeixinPublicNumber(this);
			}
			return access_token;
		}
	} 
	
	private String generate(String grant_type, String appid, String secret) throws IOException {
		if (needRegenerate || get_access_token_url == null) {
			needRegenerate = get_access_token_url == null;
			get_access_token_url = APIURL.GEN_ACCESS_TOKEN_URL + "grant_type=" + grant_type
					+ "&appid=" + appid + "&secret=" + secret;
		}
		this.createTime = System.currentTimeMillis();
		String result = SimpleHttpClient.invokeGet4String(get_access_token_url,3000);
		JSONObject jsonObject = JSONObject.parseObject(result);
		if (jsonObject.containsKey("errcode")) {
			throw new IOException(result);
		}
		this.expires_in = jsonObject.getLong("expires_in")*1000;
		this.access_token = jsonObject.getString("access_token");
		return this.access_token;
	} 
	
	public String getAccess_token() {
		return access_token;
	} 
	
	public String getApp_id() {
		return app_id;
	} 
	
	public String getApp_secret() {
		return app_secret;
	} 
	
	public long getCreateTime() {
		return createTime;
	}
	
	public WeixinMessage getDefaultReplyMessage() {
		return defaultReplyMessage.copyMessage();
	}
	
	public Map<String, WeixinMessage> getEditKeywordsReplyMessage() {
		return editKeywordsReplyMessage;
	}

//	private WeixinRemoteImageMessage weixinRemoteImageMessage = null;

	public WeixinMessage getEditKeywordsReplyMessage(String key) {
		WeixinMessage message = editKeywordsReplyMessage.get(key);
		if (message == null) {
			return null;
		}
		return message;
	}

//	private WeixinRemoteLocationMessage weixinRemoteLocationMessage = null;

	public long getExpires_in() {
		return expires_in;
	}
	
	public Map<String, WeixinMessage> getKeywordsReplyMessage() {
		return keywordsReplyMessage;
	}
	
	public WeixinMessage getKeywordsReplyMessage(String key) {
		WeixinMessage message = keywordsReplyMessage.get(key);
		if (message == null) {
			return null;
		}
		return message.copyMessage();
	}
	
	public int getLevel() {
		return level;
	}

	public String getMessage_token() {
		return message_token;
	}

	public synchronized String getPassword() {
		if (showPassword) {
			this.showPassword = false;
			return password;
		}
		return null;
	}

	public WeixinPublicNumberType getPublic_Type() {
		return public_Type;
	}

	public String getRemote_message_encrypt() {
		return remote_message_encrypt;
	}
	
	public long getService_time() {
		return service_time;
	}
	
	public int getStatus() {
		return status;
	}
	
	public WeixinMessage getSubscribeReplyMessage() {
		return subscribeReplyMessage.copyMessage();
	}
	
	public int getSystem_version() {
		return system_version;
	}
	
	public String getToken() {
		return token;
	}

	public String getUname() {
		return uname;
	}

	public long getVersion() {
		return version;
	}

	public String getWeixinMenu() {
		return weixinMenu;
	}

	public WeixinRemoteLocationEventMessage getWeixinRemoteLocationEventMessage() {
		if (weixinRemoteLocationEventMessage == null) {
			return null;
		}
		return weixinRemoteLocationEventMessage.copyMessage();
	}

	public boolean isEncrypt_remote_message() {
		return encrypt_remote_message;
	}

	private boolean needGen() {
		return System.currentTimeMillis() - createTime > expires_in;
	}
	
	public void putEditKeywordsReplyMessage(String key,WeixinMessage message) {
		if (key == null) {
			return ;
		}
		if (key != message.getServiceKey()) {
			key = message.getServiceKey();
		}
		String [] splitKeys = key.split(" ");
		synchronized (editKeywordsReplyMessage) {
			this.editKeywordsReplyMessage.put(key, message);	
		}
		synchronized (this.keywordsReplyMessage) {
			for (int i = 0; i < splitKeys.length; i++) {
				String splitKey = splitKeys[i];
				if (!StringUtil.isBlankOrNull(splitKey)) {
					splitKey = splitKey.trim();
//					message.setServiceKey(splitKey);
					this.keywordsReplyMessage.put(splitKey, message);
				}
			}
		}
	}

	public void putKeywordsReplyMessage(String key,WeixinMessage message) throws Exception {
		synchronized (this.keywordsReplyMessage) {
			if (this.keywordsReplyMessage.size() > KEYWORDS_SIZE[level]) {
				throw new Exception("关键词个数超出限制，当前等级："+level+"，关键词最多个数："+KEYWORDS_SIZE[level]);
			}
			this.keywordsReplyMessage.put(key, message);
		}
	}

//	public WeixinRemoteImageMessage getWeixinRemoteImageMessage() {
//		return weixinRemoteImageMessage;
//	}

	public void removeEditKeywordsReplyMessage(String key) {
		if (key == null) {
			return ;
		}
		String [] splitKeys = key.split(" ");
		synchronized (editKeywordsReplyMessage) {
			this.editKeywordsReplyMessage.remove(key);
		}
		synchronized (this.keywordsReplyMessage) {
			for (int i = 0; i < splitKeys.length; i++) {
				String splitKey = splitKeys[i];
				if (!StringUtil.isBlankOrNull(splitKey)) {
					this.keywordsReplyMessage.remove(splitKey.trim());
				}
			}
		}
	}
	
//	public WeixinRemoteLocationMessage getWeixinRemoteLocationMessage() {
//		if (weixinRemoteLocationMessage == null) {
//			return null;
//		}
//		return weixinRemoteLocationMessage.copyMessage();
//	}
	
	public void removeKeywordsReplyMessage(String key) {
		synchronized (this.keywordsReplyMessage) {
			this.keywordsReplyMessage.remove(key);
		}
	}

	public void setAccess_token(String access_token) {
		if (access_token!= null && access_token.length() > 100) {
			return ;
		}
		this.access_token = access_token;
	}
	
	public void setApp_id(String appid) {
		if (appid!= null && appid.length() > 100) {
			return ;
		}
		this.setNeedRegenerate(true);
		this.app_id = appid;
	}

	public void setApp_secret(String app_secret) {
		if (app_secret!= null && app_secret.length() > 100) {
			return ;
		}
		this.setNeedRegenerate(true);
		this.app_secret = app_secret;
	}
	
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public void setDefaultReplyMessage(WeixinMessage defaultReplyMessage) {
		this.defaultReplyMessage = defaultReplyMessage;
	}

	public void setEncrypt_remote_message(boolean encrypt_remote_message) {
		this.encrypt_remote_message = encrypt_remote_message;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public void setLevel(int level) {
		if (level > 3) {
			level = 3;
		}
		if (level < 0) {
			level = 0;
		}
		this.level = level;
		
	}

	public void setMessage_token(String message_token) {
		this.message_token = message_token;
	}

	public void setNeedRegenerate(boolean needRegenerate){
		this.needRegenerate = needRegenerate;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPublic_Type(WeixinPublicNumberType publicType) {
		this.public_Type = publicType;
	}

	public void setRemote_message_encrypt(String remote_message_encrypt) {
		if (remote_message_encrypt!= null && remote_message_encrypt.length() > 32) {
			return;
		}
		this.remote_message_encrypt = remote_message_encrypt;
	}

	public void setService_time(long service_time) {
		this.service_time = service_time;
	}

	public void setShowPassword(boolean showPassword) {
		this.showPassword = showPassword;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setSubscribeReplyMessage(WeixinMessage subscribeReplyMessage) {
		this.subscribeReplyMessage = subscribeReplyMessage;
	}

	public void setSystem_version(int system_version) {
		this.system_version = system_version;
	}

	public void setToken(String token) {
		if (token!= null && token.length() > 100) {
			return;
		}
		this.token = token;
	}

	public void setUname(String uname) {
		if (uname!= null && uname.length() > 100) {
			return; 
		}
		this.uname = uname;
	}

	public void setVersion(long version) {
		this.version = version;
	}

//	public void setWeixinRemoteImageMessage(WeixinRemoteImageMessage weixinRemoteImageMessage) {
//		this.weixinRemoteImageMessage = weixinRemoteImageMessage;
//	}

	public void setWeixinMenu(String weixinMenu) {
		if (weixinMenu!= null && weixinMenu.length() > 10240) {
			return ;
		}
		this.weixinMenu = weixinMenu;
	}

//	public void setWeixinRemoteLocationMessage(WeixinRemoteLocationMessage weixinRemoteLocationMessage) {
//		this.weixinRemoteLocationMessage = weixinRemoteLocationMessage;
//	}

	public void setWeixinRemoteLocationEventMessage(
			WeixinRemoteLocationEventMessage weixinRemoteLocationEventMessage) {
		this.weixinRemoteLocationEventMessage = weixinRemoteLocationEventMessage;
	}
	
	public byte [] toBytes(){
		try {
			return ByteUtil.toBytes(this);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String toString(){
		return JSONObject.toJSONString(this,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.PrettyFormat);
	}

	public String toString(boolean showPassword){
		this.showPassword = showPassword;
		String temp = JSONObject.toJSONString(this,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.PrettyFormat);
		this.showPassword = false;
		return temp;
	}
}
