package com.spshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;


public class Testor{
	public static void main(String[] args) throws IOException {
		
		String str = "cmd=_notify-validate&last_name=User&test_ipn=1&address_name=Test+User&txn_type=web_accept&receiver_email=S1%40HP.COM&residence_country=US&address_city=San+Jose&payment_gross=2.00&payment_date=06%3A53%3A28+Nov+03%2C+2011+PDT&address_zip=95131&payment_status=Pending&address_street=1+Main+St&first_name=Test&payer_email=buyer2_1320237301_per%40hp.com&protection_eligibility=Ineligible&payer_id=LMJ6G22T2YPA4&verify_sign=AydgRIRujLKUKEdd-D53oSHOB3lgAjzDaaU.gKWPZ.L4mhiOy4wK4GfY&payment_type=instant&business=S1%40HP.COM&address_country_code=US&mc_fee=0.38&address_status=confirmed&transaction_subject=Order782467GABNC270444&quantity=1&notify_version=3.4&mc_currency=USD&custom=&address_state=CA&payment_fee=0.38&handling_amount=0.00&payer_status=verified&shipping=0.00&item_name=Order782467GABNC270444&tax=0.00&charset=gb2312&pending_reason=paymentreview&item_number=&ipn_track_id=FzCMF2E28f9Y6vAkPzC2JQ&mc_gross=2.00&txn_id=2L67276816055650C&receiver_id=3WJ2MJ36TT9T4&address_country=United+States";
		
		//URL u = new URL("http://www.sandbox.paypal.com/cgi-bin/webscr");
		URL u = new URL("https://www.sandbox.paypal.com/c2/cgi-bin/webscr");
		URLConnection uc = u.openConnection();
		uc.setDoOutput(true);

		uc.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		PrintWriter pw = new PrintWriter(uc.getOutputStream());
		pw.println(str);
		pw.close();
		// 接受 PayPal 对 IPN 回发的回复信息
		BufferedReader in = new BufferedReader(new InputStreamReader(
				uc.getInputStream()));
		String res = in.readLine();
		in.close();
		
		System.out.println(res);
		
	}
}
