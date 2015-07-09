package test.thirdparty.weixin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bokesoft.dee.yigo.util.BokeDeeHttpRequest;
import com.bokesoft.dee.yigo.util.GrnStringToList;

public class TestBokeDee {

	private static final String URL = "http://202.108.199.184:8100/http/post";
	
	public static void main(String[] args) throws Throwable {
		String param = "454067CN#BJTJSF";
		String[] params = param.split("#");
		if(params.length != 2){
			
		}
		String queryStr = "454067CN,,,,,,BJTJSF";
		String responseMsg = "<MESSAGE><HEADER><RETURNCODE>1</RETURNCODE><RETURNMSG>[[wuliaoleibie_code:001, pihao:D141100B, shixiaoriqi:2016-05-31 00:00:00.0, wlname:VACUETTE 4ml 血清管,促凝剂/凝胶(红盖黄环), huozhu_code:BJTJSF , zhucezhenghao:国食药监械（进）字2012第2661479号（更）, wuliaozhuangtai_code:01, wuliaodaima:454067CN, kcsl:3100.000000, zyhjkc:0.000000, sccscode:008, yxq:2011-07-13 00:00:00.0, kykcsl:3100.000000], [wuliaoleibie_code:001, pihao:D141101H, shixiaoriqi:2016-05-31 00:00:00.0, wlname:VACUETTE 4ml 血清管,促凝剂/凝胶(红盖黄环), huozhu_code:BJTJSF , zhucezhenghao:国食药监械（进）字2012第2661479号（更）, wuliaozhuangtai_code:01, wuliaodaima:454067CN, kcsl:10250.000000, zyhjkc:0.000000, sccscode:008, yxq:2011-07-13 00:00:00.0, kykcsl:10250.000000], [wuliaoleibie_code:001, pihao:D150100E, shixiaoriqi:2016-07-31 00:00:00.0, wlname:VACUETTE 4ml 血清管,促凝剂/凝胶(红盖黄环), huozhu_code:BJTJSF , zhucezhenghao:国食药监械（进）字2012第2661479号（更）, wuliaozhuangtai_code:01, wuliaodaima:454067CN, kcsl:540000.000000, zyhjkc:0.000000, sccscode:008, yxq:2011-07-13 00:00:00.0, kykcsl:540000.000000]]</RETURNMSG></HEADER></MESSAGE>";
		try {
			String midStr = responseMsg.substring(responseMsg.indexOf("[["), responseMsg.indexOf("]]")+2);
			List<Map<String,String>> list = new GrnStringToList().StringToList(midStr);
			Iterator<Map<String,String>> it = list.iterator();
			while(it.hasNext()){
				Map<String, String> map = it.next();
//				System.out.println("wuliaoleibie_code ："+map.get("wuliaoleibie_code"));
//				System.out.println("pihao ："+map.get("pihao"));
//				System.out.println("shixiaoriqi ："+map.get("shixiaoriqi"));
//				System.out.println("wlname ："+map.get("wlname"));
//				System.out.println("huozhu_code ："+map.get("huozhu_code"));
//				System.out.println("zhucezhenghao ："+map.get("zhucezhenghao"));
//				System.out.println("wuliaozhuangtai_code ："+map.get("wuliaozhuangtai_code"));
//				System.out.println("wuliaodaima ："+map.get("wuliaodaima"));
//				System.out.println("kcsl ："+map.get("kcsl"));
//				System.out.println("zyhjkc ："+map.get("zyhjkc"));
//				System.out.println("sccscode ："+map.get("sccscode"));
//				System.out.println("yxq ："+map.get("yxq"));
//				System.out.println("kykcsl ："+map.get("kykcsl"));
				System.out.println("物料代码："+map.get("wuliaodaima")+" 物料名称 ："+map.get("wlname") +" 批号："+map.get("pihao") +" 失效日期："+map.get("shixiaoriqi")+" 库存数量："+map.get("kcsl"));
				System.out.println("-------------------------------------------------------");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
