package com.bokesoft.thirdparty.weixin.open.handle;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

public class GetOpenidByCode extends AbstractWeixinSOAServieHandle{

	@Override
	public SOAResponseMessage handle(WeixinContext context,SOARequestMessage message) throws Exception {
		String uname = message.getUname();
		WeixinPublicNumber publicNumber = context.getWeixinPublicNumber(uname);
		if(publicNumber == null){
			return new SOAResponseMessage(1000, "unknow uname:"+uname);
		}
		WeixinApiInvoker invoker = context.getWeixinApiInvoker();
		String result = invoker.getOpenid(publicNumber.getApp_id(), publicNumber.getApp_secret(), message.getStringParam());
		JSONObject resultJson = JSONObject.parseObject(result);
		if(resultJson.get("errcode") == null){
			return new SOAResponseMessage(0, resultJson.getString("openid"));
		}else{
			return new SOAResponseMessage(1000, resultJson.getString("errmsg"));
		}
	}


}
