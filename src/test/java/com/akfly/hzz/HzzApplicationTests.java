package com.akfly.hzz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLDecoder;

@SpringBootTest
class HzzApplicationTests {

	@Test
	void contextLoads() {


	}

	public static void main(String[] args) {
		String s = URLDecoder.decode("%3Cform+name%3D%22punchout_form%22+method%3D%22post%22+action%3D%22https%3A%2F%2Fopenapi.alipay.com%2Fgateway.do%3Fcharset%3DUTF-8%26method%3Dalipay.trade.wap.pay%26sign%3DkXNuzcjPBhJ0NocnpwTkM8NoFw6AMYXkNKQ6NT9%252B7rTPrdDXkUNZF4pOHrZU8CTjfCUSQIkZhfCPXjnZ4qtXZmgmLvdPtf%252FvNYaY05Tx4hbKs5Mfb%252F0%252FOQd0QEaykiEnFI2JjRx1nXDTLkQlQ3U8pbMocf8YH57UN8CRVg8HjMbPa9wbMY2ZopDIDjvm2S%252BpVJ%252Bl6rUsLSBZtnVeBBMxB9hxcpnwwQeolGKmEHsGAJpRamEIBfivHU5dSzBzBD7FRs%252BNLMEkrUKsYrVC%252B%252BP%252FU1eT8AWj910IBs5QmGv1XLxWOz8i1Ofjxu8Hkgxj0ZuL%252FIYeDzXfjRKxAv2eDfSg5Q%253D%253D%26return_url%3Dhttps%253A%252F%252F192.144.230.48%252FalipayFrontNotify%26notify_url%3Dhttps%253A%252F%252F192.144.230.48%252FaliPayNotify%26version%3D1.0%26app_id%3D2021002122672225%26sign_type%3DRSA2%26timestamp%3D2021-02-02%2B22%253A08%253A23%26alipay_sdk%3Dalipay-easysdk-java%26format%3Djson%22%3E%0A%3Cinput+type%3D%22hidden%22+name%3D%22biz_content%22+value%3D%22%7B%26quot%3Btime_expire%26quot%3B%3A%26quot%3B2021-02-03+00%3A08%26quot%3B%2C%26quot%3Bout_trade_no%26quot%3B%3A%26quot%3B13%26quot%3B%2C%26quot%3Btotal_amount%26quot%3B%3A%26quot%3B0.1%26quot%3B%2C%26quot%3Bsubject%26quot%3B%3A%26quot%3B%E8%AE%A2%E5%8D%95%26quot%3B%2C%26quot%3Btimeout_express%26quot%3B%3A%26quot%3B120m%26quot%3B%2C%26quot%3Bdisable_pay_channels%26quot%3B%3A%26quot%3Bcredit_group%26quot%3B%2C%26quot%3Bproduct_code%26quot%3B%3A%26quot%3BQUICK_WAP_PAY%26quot%3B%7D%22%3E%0A%3Cinput+type%3D%22submit%22+value%3D%22%E7%AB%8B%E5%8D%B3%E6%94%AF%E4%BB%98%22+style%3D%22display%3Anone%22+%3E%0A%3C%2Fform%3E%0A%3Cscript%3Edocument.forms%5B0%5D.submit%28%29%3B%3C%2Fscript%3E");
		System.out.println(s);

	}

}
