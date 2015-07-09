package com.bokesoft.thirdparty.model.config;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.model.ModelHandle;
import com.bokesoft.thirdparty.weixin.WeixinContext;
import com.bokesoft.thirdparty.weixin.bean.SOAResponseMessage;
import com.bokesoft.thirdparty.weixin.bean.UserInfo;
import com.bokesoft.thirdparty.weixin.bean.WeixinApiResult;
import com.bokesoft.thirdparty.weixin.bean.WeixinPublicNumber;
import com.bokesoft.thirdparty.weixin.service.WeixinApiInvoker;

public class WeixinMenuCreateHandle extends ModelHandle  {

	private static final Logger logger = Logger.getLogger(WeixinMenuCreateHandle.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SOAResponseMessage handle(WeixinContext context, HttpServletRequest request,
			UserInfo userinfo) throws Exception {
		String menu = request.getParameter("menu");
		logger.info("ORA MENU:"+menu);
		JSONObject jsonObject = JSONObject.parseObject(menu);
		List jsonArray = jsonObject.getJSONArray("button");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject map = (JSONObject) jsonArray.get(i);
			if(map.get("enable") == Boolean.FALSE){
				jsonArray.remove(i);
				i--;
			}else{
				JSONArray sub_button = map.getJSONArray("sub_button");
				if (sub_button!= null) {
					for (int j = 0; j < sub_button.size(); j++) {
						JSONObject sub_map = sub_button.getJSONObject(j);
						if (!sub_map.getBooleanValue("enable")) {
							sub_button.remove(j);
							j--;
						}else{
							sub_map.remove("enable");
							String type = sub_map.getString("type");
							if("click".equals(type)){
								sub_map.remove("url");
							}else{
								sub_map.remove("key");
							}
						}
					}
					if (sub_button.size() > 0) {
						map.remove("type");
						map.remove("key");
						
						map.remove("url");
					}else{
						String type = map.getString("type");
						if("".equals(type)){
							jsonArray.remove(i);
							
						}else if("click".equals(type)){
							map.remove("url");
						}else{
							map.remove("key");
						}
						map.remove("sub_button");
					}
					map.remove("enable");
				}
			}
		}
		doCompare(jsonArray);
		removeCompare(jsonArray);
		WeixinApiInvoker weixinApiInvoker = context.getWeixinApiInvoker();
		WeixinPublicNumber publicNumber = userinfo.getWeixinPublicNumber();
		String create_menu = jsonObject.toJSONString();
		logger.info("create menu>>>>>"+create_menu);
		WeixinApiResult result = weixinApiInvoker.createWeixinMenu(publicNumber.genAccess_token(context),create_menu);
		logger.info("create menu result>>>>>"+result.getErrmsg());
		publicNumber.setWeixinMenu(menu);
		context.updateWeixinPublicNumber( publicNumber);
		return new SOAResponseMessage(result.getErrcode(), result.getErrmsg());
//		return SOAResponseMessage.SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void removeCompare(List<Map> jsonArray){
		for (int i = 0; i < jsonArray.size(); i++) {
			Map map =  jsonArray.get(i);
			map.remove("compare");
			List<Map> sub_button = (List<Map>) map.get("sub_button");
			if (sub_button!= null) {
				for (int j = 0; j < sub_button.size(); j++) {
					Map sub_map = sub_button.get(j);
					sub_map.remove("compare");
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void doCompare(List<JSONObject> buttonList){
		Collections.sort(buttonList,new Comparator<JSONObject>() {
			public int compare(JSONObject o1, JSONObject o2) {
				return o1.getIntValue("compare") - o2.getIntValue("compare");
			}
		});
		
		for (JSONObject map :buttonList) {
			List sub_button = map.getJSONArray("sub_button");
			if (sub_button != null) {
				Collections.sort(sub_button,new Comparator<JSONObject>() {
					 public int compare(JSONObject o1, JSONObject o2){
						 return o1.getIntValue("compare") - o2.getIntValue("compare");
					 }
				});
			}
		}
	}
}
