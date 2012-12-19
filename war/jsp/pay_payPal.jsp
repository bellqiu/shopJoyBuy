<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="include.jsp"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
<base href="http://${pageForm.site.domain }/" />

<title>${pageForm.category.pageTitle}</title>
<meta name="description" content="${pageForm.category.description}">
<meta name="keywords" content="${pageForm.category.relatedKeyword}">

<link rel="stylesheet" type="text/css" href="../css/header.css">
<link rel="stylesheet" type="text/css" href="../css/general.css">
<link rel="stylesheet" type="text/css" href="../css/index.css">
<link rel="stylesheet" type="text/css" href="../css/footer.css">
<link rel="stylesheet" type="text/css" href="../css/global.css">
<link rel="stylesheet" type="text/css" href="../css/head.css">
<link rel="stylesheet" type="text/css" href="../css/base.css">

<script src="../js/jquery-1.5.1.js" type="text/javascript"></script>

<script>
	var Image_url = 'http://127.0.0.1:8888/css', root_url = 'http://127.0.0.1:8888/', seller_lang = 'en-uk';
	var head_SearchKeywordsNo = 'Enter search keywords or item code here';
	var SearchKeyword = 'Enter search keywords or item code here';
	var jq = jQuery.noConflict();
</script>
<script src="../js/common.js" type="text/javascript"></script>
<script src="../js/ajax.js" type="text/javascript"></script>
<script src="http://cdn.gigya.com/js/socialize.js?apiKey=3_gGObGZB96M9EJm8iSReWnC3GSUBsHggOVGoAkPobuADEsMzwI-Hw8h4daBJpiQ8Y"></script>
<script src="../js/ga.js" type="text/javascript"></script>
</head>
<body>
	<!-- Header start -->
	<jsp:include page="common/header_top.jsp"></jsp:include>
	<div style="height: 27px"></div>
	<!-- Header end -->

	<div id="allBanner" class="main_box" style="width: 972px; height: 0px;"></div>

	<!-- Top box start -->
	<jsp:include page="top/top_box.jsp"></jsp:include>
	<script type="text/javascript" src="../js/header.js"></script>
	<!-- Top box end -->
	<div
		style="color: Black; text-align: center; margin-left: auto; margin-right: auto; margin-top: 20px; font-size: 24px; font-weight: bold;">
		<div style="color: Black; text-align:center; margin-left:auto; margin-right:auto; font-size: 24px;">
        <div style="font-size: 24px">Thank you, ${defaultOrder.customerName}!</div>
        <div style="font-size: 24px">
        	 Your order is almost complete, please click OK button!
        </div>
<!--        <div style="font-size:24px" class="s14"> -->
<!--         	UP TO <b style="font-size: 36px" class="red fontbold">80% OFF</b> on Selected ITEMS + <b style="font-size: 36px" class="red fontbold">FREE SHIPPING</b> ON ALL DRESSES  -->
<!--         </div> -->
      </div>
	</div>
	<div class="main_box">
		<div style="width: 240px; float: left; margin: 10px" class="box_item">
			<div class="box_title">Recent Activity</div>
			<div class="box_item_content">
				<div class="fb-activity" data-site="www.joybuy.co.uk"
					data-width="240" data-header="false" data-linktarget="_blank"
					data-recommendations="true" data-border-color="#fff"></div>
			</div>
		</div>

		<div style="width: 425px; float: left; margin: 10px" class="box_item">
			<div class="box_title">Order Infomation</div>
			<div class="box_item_content">
				<c:if test="${not empty defaultOrder}">
				<pre style="font-size: 15px;">
				Order NO.: ${defaultOrder.name }
				Total  : ${currency } <fmt:formatNumber
						value="${defaultOrder.totalPrice * currencies[currency]}"
						maxFractionDigits="2" currencyCode="${currency}"></fmt:formatNumber>+<fmt:formatNumber
						value="${defaultOrder.dePrice  * currencies[currency]}"
						maxFractionDigits="2" currencyCode="${currency}"></fmt:formatNumber> - <fmt:formatNumber
						value="${defaultOrder.couponCutOff  * currencies[currency]}"
						maxFractionDigits="2" currencyCode="${currency}"></fmt:formatNumber> = <fmt:formatNumber
						value="${(defaultOrder.totalPrice + defaultOrder.dePrice - defaultOrder.couponCutOff) * currencies[currency]}"
						maxFractionDigits="2" currencyCode="${currency}"></fmt:formatNumber>
				</pre>
					<form action="https://www.paypal.com/cgi-bin/webscr" method="post"
						id="paypaysubmitForm">
						<input type="hidden" name="cmd" value="_xclick"> <input
							type="hidden" name="business" value="paypal@joybuy.co.uk">
						<input type="hidden" name="item_name"
							value="${defaultOrder.name }"> <input type="hidden"
							name="amount"
							value="<fmt:formatNumber currencyCode='${currency}' pattern='###.##'  value='${(defaultOrder.totalPrice + defaultOrder.dePrice - defaultOrder.couponCutOff) * currencies[currency]}'></fmt:formatNumber>">
						<input type="hidden" name="currency_code" value="${currency}">
						<input type="hidden" name="lc" value="US"> <input
							type="hidden" name="notify_url"
							value="http://www.joybuy.co.uk/checkorder"> <input
							type="hidden" name="return"
							value="http://www.joybuy.co.uk/orders?id=${defaultOrder.name }">
						<%-- 
			<input type="button" id="paypaysubmit" style="background-image: url(https://www.paypal.com/en_US/i/btn/x-click-but23.gif);width: 72px;height: 27px"
			border="0" alt="Make payments with PayPal - it's fast, free and secure!" >--%>
						<button
							style="background: url('/css/Image_mosaic.png') no-repeat scroll 0 -374px transparent; border-style: none; border-width: 0; color: #FFFFFF; cursor: pointer; font-size: 13px; font-weight: bold; height: 31px; margin: 10px 32px; text-align: center; text-decoration: none; width: 88px; margin-left: 150px"
							type="submit">OK</button>
					</form>
				</c:if>
			</div>
		</div>
		<div style="width: 240px; float: left; margin: 10px" class="box_item">
			<div class="box_title">Notification</div>
			<div class="box_item_content">
				<p style="padding: 5px">You will receive an email confirmation
					of your purchase at Sales@joybuy.co.uk. Any additional account
					notices will also be sent to this email address.</p>
			</div>
		</div>
	</div>






	<!-- Main box end -->
	<script type="text/javascript">
		/*jq(document).ready(function(){
			setTimeout(function(){
				jq("#paypaysubmitForm").submit();
			},5000);
		});*/
	</script>
	<!-- Bottom start -->
	<jsp:include page="bottom/bottom_box.jsp"></jsp:include>
	<!-- Bottom end -->

	<script type="text/javascript" src="../js/footer.js"></script>
	<div id="fb-root"></div>
	<script>
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id))
				return;
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>
	
<!-- Google Code for &#20184;&#27454; Conversion Page --> <script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 1010858884;
var google_conversion_language = "en";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "aifLCOSNvgIQhPeB4gM"; var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript"  
src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt=""  
src="http://www.googleadservices.com/pagead/conversion/1010858884/?label=aifLCOSNvgIQhPeB4gM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>

	
</body>
</html>