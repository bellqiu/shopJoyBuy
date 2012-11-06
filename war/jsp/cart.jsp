<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="include.jsp"%>
<html>
<head>
<base href="http://${pageForm.site.domain }/" />
<link media="all" href="//s7.addthis.com/static/r07/widget58.css"
	type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">

<title>${pageForm.pageProperties.productDetail.title}</title>
<meta name="description" content=" Spark's Shop ">
<meta name="keywords"
	content="${pageForm.pageProperties.productDetail.keywords}">

<link rel="stylesheet" type="text/css" href="../css/header.css">
<link rel="stylesheet" type="text/css" href="../css/general.css">
<link rel="stylesheet" type="text/css" href="../css/index.css">
<link rel="stylesheet" type="text/css" href="../css/footer.css">
<link rel="stylesheet" type="text/css" href="../css/global.css">
<link rel="stylesheet" type="text/css" href="../css/head.css">
<link rel="stylesheet" type="text/css" href="../css/cart.css">
<link rel="stylesheet" type="text/css" href="../css/base.css">
<link rel="stylesheet" type="text/css" href="../css/checkout.css">

<script src="../js/jquery-1.5.1.js" type="text/javascript"></script>
<script>
	var Image_url = 'http://127.0.0.1:8888/css', root_url = 'http://127.0.0.1:8888/', seller_lang = 'en-uk';
	var head_SearchKeywordsNo = 'Enter search keywords or item code here';
	var SearchKeyword = 'Enter search keywords or item code here';
	var jq = jQuery.noConflict();
</script>
<script src="../js/common.js" type="text/javascript"></script>
<script src="../js/ajax.js" type="text/javascript"></script>
<script src="../js/shoppingCart.js" type="text/javascript"></script>
<script src="http://cdn.gigya.com/js/socialize.js?apiKey=2_1UXpzvtcCUTsgsqVzgFllQgReHnrU1ZScSL7tJAC1ftCy_DMCPUi2iTExpYFaddy"></script>
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
	<!-- Main box start -->
	<div class="main_box">
		<jsp:include page="wrapper/path_title.jsp"></jsp:include>

		<div class="item_ad_box"></div>
		<c:if test="${!showCheckOption }">
			<jsp:include page="cart/cartDetail.jsp"></jsp:include>
		</c:if>
		<jsp:include page="wrapper/delivery.jsp"></jsp:include>
	</div>
	<!-- Bottom start -->
	<jsp:include page="bottom/bottom_box.jsp"></jsp:include>
	<script type="text/javascript" src="js/footer.js"></script>
	
<!-- Google Code for &#36141;&#20080; Conversion Page --> <script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 1010858884;
var google_conversion_language = "en";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "gLaLCNyOvgIQhPeB4gM"; var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript"  
src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt=""  
src="http://www.googleadservices.com/pagead/conversion/1010858884/?label=gLaLCNyOvgIQhPeB4gM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>
	
</body>
</html>