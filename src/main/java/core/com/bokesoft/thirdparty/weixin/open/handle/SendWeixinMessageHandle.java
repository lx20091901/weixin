package com.bokesoft.thirdparty.weixin.open.handle;

import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOARequestMessage;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.WeixinApiResult;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

/**
 * 
 * 发送客服消息
 *
 */
public class SendWeixinMessageHandle extends AbstractWeixinSOAServieHandle  {
	
	public static String KEY_NAME = null;
	
	public SOAResponseMessage handle(WeixinContext context, SOARequestMessage requestMessage)
			throws Exception {
		String uname = requestMessage.getUname();
		WeixinPublicNumber weixinPublicNumber = context.getWeixinPublicNumber(uname);
		if (weixinPublicNumber == null) {
			return new SOAResponseMessage(1000,"unknow uname:"+uname);
		}
		WeixinApiInvoker apiInvoker = context.getWeixinApiInvoker();
		WeixinApiResult result = apiInvoker.sendMessage(weixinPublicNumber.genAccess_token(context)
				, requestMessage.getStringParam());
		if (result.getErrcode() == 0) {
			return SOAResponseMessage.SUCCESS;
		}else{
			return new SOAResponseMessage(1000, result.getErrmsg());
		}
	}

}
