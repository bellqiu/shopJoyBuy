<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="include.jsp" %>
<html>
<head>
	<base href="http://${pageForm.site.domain }/" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
	
	
	<title>${pageForm.category.pageTitle}</title>
	<meta name="description" content="${pageForm.category.description}">
	<meta name="keywords" content="${pageForm.category.relatedKeyword}">
	
	<link rel="stylesheet" type="text/css" href="../css/header.css">
	<link rel="stylesheet" type="text/css" href="../css/general.css">
	<link rel="stylesheet" type="text/css" href="../css/index.css">
	<link rel="stylesheet" type="text/css" href="../css/footer.css">
	<link rel="stylesheet" type="text/css" href="../css/global.css">
	<link rel="stylesheet" type="text/css" href="../css/head.css">
	
	<script src="../js/jquery-1.5.1.js" type="text/javascript"></script>
	
	<script>
		var Image_url = 'http://127.0.0.1:8888/css', root_url = 'http://127.0.0.1:8888/', seller_lang = 'en-uk';
		var head_SearchKeywordsNo = 'Enter search keywords or item code here';
		var SearchKeyword = 'Enter search keywords or item code here';
		var jq = jQuery.noConflict();
	</script>
	<script src="../js/common.js" type="text/javascript"></script>
	<script src="../js/ajax.js" type="text/javascript"></script>
<script src="../js/ga.js" type="text/javascript"></script>
<script src="http://cdn.gigya.com/js/socialize.js?apiKey=2_1UXpzvtcCUTsgsqVzgFllQgReHnrU1ZScSL7tJAC1ftCy_DMCPUi2iTExpYFaddy"></script>
</head>
<body>
	<!-- Header start -->
	<jsp:include page="common/header_top.jsp"></jsp:include>
	<div style="height:27px"></div>
	<!-- Header end -->
	
	<div id="allBanner" class="main_box" style="width: 972px; height: 0px;"></div>
	
	<!-- Top box start -->
	<jsp:include page="top/top_box.jsp"></jsp:include>
	<script type="text/javascript" src="../js/header.js"></script>
	<!-- Top box end -->
	
	<!-- Main box start -->
	<div class="main_box">
		<div class="hei10"></div>
		<ss:site var="site">
		<div
			style="margin: 0pt auto; overflow: hidden; width: 735px; color: #000000; text-align: left;">
			

			<div>
				<p style="font-size: 26px; color: #ba0001; margin-top: 16px;">
				<img style="height:20px;" src="/css/ico_small.jpg">
					<strong>Thank you for subscribing to the joybuy.co.uk
						Newsletter!</strong>
				</p>

				<p style="font-size: 13px; color: #666666; margin-top: 14px;">
					<strong>You Have Signed Up To Receive Updates At: </strong><span
						id="showemail"><span class="emailspan">${param.subscribeEmail}</span>
					</span>,<strong> You Are Also Now Entered In Our Monthly
						Newsletter Prize Draw. Good Luck, We Hope You Win!</strong>
				</p>

				<p style="font-size: 13px; color: #666666; margin-top: 12px;">From
					now on, you will be sent updates twice a week filled with exclusive
					deals and coupons. Our newsletters introduce products from across
					our various categories, which will be tailored specifically to your
					lifestyle and interests. Your shopping experience just got that
					much better!</p>

				<p
					style="font-size: 13px; color: #666666;; margin-top: 12px; margin-bottom: 26px;">
					To return to joybuy.co.uk, please <a
						href="http://184.22.252.66/" style="color: #ba0001;">click
						here</a>
				</p>
			</div>



			<div style="clear: both;">&nbsp;</div>

			<div>
				<p
					style="font-size: 13px; padding: 5px 20px 5px 0; color: #666666; padding-top: 20PX;">Once
					again, thank you for subscribing to the joybuy.co.uk
					Newsletter!</p>
				<a href="184.22.252.66"
					title="joybuy.co.uk"><img border="0"
					alt="joybuy.co.uk"
					src="${site.logo.noChangeUrl}"
					style="float: right; margin-top: 20px; margin-right: 16px;">
				</a>

				<p
					style="font-size: 13px; padding: 5px 16px 5px 0; color: #666666; padding-top: 16PX;">Sincerely,</p>

				<p style="font-size: 13px;; color: #666666;">joybuy.co.uk</p>
			</div>
		</div>
		<div class="hei10"></div>
		</ss:site>
	</div>
	<!-- Main box end -->
	
	<!-- Bottom start -->
	<jsp:include page="bottom/bottom_box.jsp"></jsp:include>
	<!-- Bottom end -->
	
	<script type="text/javascript" src="../js/footer.js"></script>
</body>
</html>