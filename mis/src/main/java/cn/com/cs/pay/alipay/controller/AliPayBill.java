package cn.com.cs.pay.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;

import cn.com.cs.common.util.JsonUtil;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class AliPayBill {

	public static void main(String[] args) {
		String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhd3+6R3hKfMFcTZxe8XtaMwVG553+qcXb19W/h/TpkQd11YGd6JFl0vENMqxeYYlG16O9QcgFSItnw00PQm2M9Rr1a6GKGkmtoy4oyHOiuGALAFD1/IYDqQlugtcezxRm8htDdubbm28lrjYS6UEAxMUJIzYXMMvpcv8EECncAobE1BgfIMqrVsN/4yQ3ww4+sTeIYcWUArkKg+FIVqDzFVSco7EKmWG5wf+yh3b25gVdX7ceX1kRaRCUOUdLJlA2mSF6mkw7IlNJUN2AnebM1Sr0NL9kODVTP0BaBxjlIptHdmlrAtufaM3w1/4chXrp7A1hlxhhjmpl9Uu6lqpcwIDAQAB";
		String app_id = "2017082108302714";
		String you_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0JJJgqChuX1hnLLqPT5zTW0mohze4EYriPAYXLwnva4HNY/Kh0NVj/hVf9EqCV1u2TBiBqbxeMCqrlHpHpbQ49ee9IySsMbgAGfHbvYP9P/i6bQKXnPwu0M+zwDMfIYlIv7mdLiRTooPo8V77ORHwHMwIVdt2dM8+X2icvTkVPz/qeXSYB7ii3nfZamaNOVwBhwxnQMtTzR1kHoghWA7G41g/816p46pH90edxgBbj5jN4l6nZEFWNqH1IFqDHMNC7SiV+uu13zDx8ecPHl7bD9hn46JYZNLp+OMombafJN8c4KO0TC3YSqOtBxiGWgi5wszdbUUeIBYPN1lf68FDAgMBAAECggEABTHwzrzKvbQeNouBrdRb73R4gVW086f921xjyJNgs/jju2qgeq4tGT8gmq3O4Edoq68yHbspZYX6FQypIhvTQLlGv5ud2SFt7wLQiMfEYK9Rmv69zmWGmEsJUmO+la0/6kstLS1Beu/64eLkHFae/8+0yqC/PYFM4L2bpLmIReaKe6mx/VEJmsQ+B4n7VmcTy907/ixtKMNWM9H6/ggHN8/caX2Rm/ApUIdPg8ueMQY1nRwZp8y7sBCpFMnt7jCsLb8O23TCXLuM7+BPorQUhH3uENsb5n807xPHF7mbT7AY1+cN1cZPiS4eMo5/73WkwpWFH2Ew9jsCEklMAx6RgQKBgQD/3otl5Qg+GKP4ZRA42o5XbMjK6Opo14xrlu2MR9nyvZQy5yEsBBbZLJ4hfO/vc0QHeOkSAM7IRgpiONEVgpwamASR5SwVuG9R/M+YQDzb+v1EEJnxbjfuPQxP4LavuF7pxEmhx6OxMxHjhgHBypEkT2DrmZCpj9H1WsdpRVwgIwKBgQC0PCA4jTQt5uWZODYHuEz9I6N0xhGqp4eKF+9Q6REJ6GJZco8jAJUc3X+cXI0yJBwS6gHdUxgdqXRsgjNE/Ft+5HFPpTYSvYQ7rs1PwRqBcXwnmpUraiktgkvornaG3I8qK+fFZtBnCtsaip4POLn2iPS/R+H6uEx2IjyyjZ1cYQKBgQCPILnxgbCeq8HtTPtKElfVZ7DacHfPteOCu6ad9gNg2dNwtgHEUXVGfsLmELFWzK+dTnFx9Km3NhbQ4t9ynOEdrzO/OOb0L3t17WlizWw90aSi0naKqxD2X4fnltgaa5QMSBS4Pqi+ksbK80dAVEPkf9LDd9+IOj9EdEdgB8CE6QKBgDxS73neEPvYDzrELJlS3znOptPbw2f54IygzDGpgFFvnRNRG4VcmYvqut5rv7priZXvCqa4PBcZc2UqAtzxFfKXpTFCG6IUGmEbdY5fFG8Eu8hJnenFA3k+5gc/9V3YDnbP8SgJ7HDAmmr8wEBZ/Sv91OT8uy7+ZmEXvncInQWhAoGBAKSGzUIgA0pqAst3nOH+Kbldm5/Dh2nC7g7Bvo33rxTdHeKqukqR/Ev0OdeuFOssrsX9A1CDXUza41T9ZXpCO85eqGRDkjRrSjmYakMpA/Q6irIhoXA8bPAUpHq7Ozx/4+f9pvie4ouPLmczz9sgDJIm7mVG0U0NBpHqbaX5Kaka";
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",app_id, you_private_key,"json","GBK",alipay_public_key,"RSA2");
		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		request.setBizContent("{" +
		"\"bill_type\":\"trade\"," +
		"\"bill_date\":\"2017-12-13\"" +
		"  }");
		AlipayDataDataserviceBillDownloadurlQueryResponse response;
		try {
			response = alipayClient.execute(request);
			if(response.isSuccess()){
				System.out.println(response.getBody());
				//http://dwbillcenter.alipay.com/downloadBillFile.resource?bizType=trade&userId=20889110551340340156&fileType=csv.zip&bizDates=20171213&downloadFileName=20889110551340340156_20171213.csv.zip&fileId=%2Ftrade%2F20889110551340340156%2F20171213.csv.zip&timestamp=1513326332&token=2eadd9bf3b3aad287ac8843e9e4715e9
				System.out.println(response.getBillDownloadUrl());;
				JSONObject json = JSONObject.fromObject(response.getBody());
				/**
				 * {
    "sign": "dVUgQqHT1yImyPKsoR/HPk/5kUQVJSMgNaD5N3BlS9WzjDAgGkBd9KRBMXxWtaPl5KTgnE1Ju2nmwx76AmgNR9g3RS+nxqSRLF0EYX17W4tz9c3J7CsjjcAuq/mM0vPihDGIyNyHgtL5ox6LdngF2JBEWdFaWTgeIOzgGiplzkNWNlEQUmENvoWO1Qhx+PyE0okecw2VIvK9YwwQnNCgDvRT64UAs6ERY6RF2EGCSmy6+iG53VBtGD8E+WttbKtXS9A7qKwpmCxn5+lsH7r/fCezmyJqwQq/B9grngjoKQlUXcQGbQseOCkr0LnvW6vTfWONzMbKxNm6+UJ9OyKT+w==", 
    "alipay_data_dataservice_bill_downloadurl_query_response": {
        "msg": "Success", 
        "code": "10000", 
        "bill_download_url": "http://dwbillcenter.alipay.com/downloadBillFile.resource?bizType=trade&userId=20889110551340340156&fileType=csv.zip&bizDates=20171213&downloadFileName=20889110551340340156_20171213.csv.zip&fileId=%2Ftrade%2F20889110551340340156%2F20171213.csv.zip×tamp=1513326332&token=2eadd9bf3b3aad287ac8843e9e4715e9"
    }
}
				 * */
				System.out.println(json.getJSONObject("alipay_data_dataservice_bill_downloadurl_query_response").get("code"));
				System.out.println(json.getJSONObject("alipay_data_dataservice_bill_downloadurl_query_response").get("msg"));
				System.out.println(json.getJSONObject("alipay_data_dataservice_bill_downloadurl_query_response").get("bill_download_url"));
				System.out.println(json.get("alipay_data_dataservice_bill_downloadurl_query_response"));
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
				System.out.println(response.getMsg());;
				System.out.println(response.getSubMsg());;
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}
}
