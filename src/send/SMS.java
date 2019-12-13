package send;

import java.io.IOException;

import org.json.JSONException;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

public class SMS {
	// TODO Auto-generated method stub
	public static int appid = 1400292341; // 1400开头
	// 短信应用SDK AppKey
	public static String appkey = "e9c408ebbe2ced419850015d9b9bfe28";
	// 短信模板ID，需要在短信应用中申请
	public static int templateId = 486233; // NOTE:
											// 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
	// templateId7839对应的内容是"您的验证码是: {1}"
	// 签名
	public static String smsSign = "jueyevip网";

	public static boolean execute(String to, String str, String voicename) {
		// 需要发送短信的手机号码
		String[] phoneNumbers = { to };
		String url = ">>>她/他的留声https://jueyevip.top/WxTravel/say/" + voicename
				+ "，【微信小程序搜索ITravel智能出行家】";
		try {
			String[] params = { str + url };// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
			SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult result = ssender.sendWithParam("86",
					phoneNumbers[0], templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
		return true;
	}

	public static boolean execute(String to, String str) {
		// 需要发送短信的手机号码
		String[] phoneNumbers = { to };
//		String strs = str + "，【微信小程序搜索ITravel智能出行家】";
		String strs = str;
		try {
			String[] params = {strs,"1" };// 数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
			SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult result = ssender.sendWithParam("86",
					phoneNumbers[0], templateId, params, smsSign, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
		return true;
	}
}
