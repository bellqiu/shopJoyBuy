<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="include.jsp" %>
<%@page import="com.spshop.utils.Constants"%>
<html>
<head>
	<link media="all" href="//s7.addthis.com/static/r07/widget58.css" type="text/css" rel="stylesheet"> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
	   
	<title>${pageForm.pageProperties.productDetail.title}</title>
	<meta name="description" content="Buy ${pageForm.pageProperties.productDetail.title} at wholesale price from joybuy.co.uk, all free shipping! Buy Now!">
	<meta name="keywords" content="${pageForm.pageProperties.productDetail.keywords}">
	  
	<link rel="stylesheet" type="text/css" href="../css/header.css?799">
	<link rel="stylesheet" type="text/css" href="../css/general.css?799">	
	<link rel="stylesheet" type="text/css" href="../css/index.css?799">
	<link rel="stylesheet" type="text/css" href="../css/footer.css?799">
	<link rel="stylesheet" type="text/css" href="../css/global.css?799">
	<link rel="stylesheet" type="text/css" href="../css/head.css?799">
	
	<script src="../js/jquery-1.5.1.js" type="text/javascript"></script>
	
		<script type="text/javascript">
			var jq = jQuery.noConflict();
		</script>
		<script src="/script/js/common2.js?999" type="text/javascript"></script>
		<script src="/script/js/jquery.validation.js" type="text/javascript"></script>
	<script>
		var Image_url = 'http://127.0.0.1:8888/css',root_url = 'http://127.0.0.1:8888/',seller_lang='en-uk';
		var head_SearchKeywordsNo='Enter search keywords or item code here';
		var SearchKeyword='Enter search keywords or item code here';
		var jq=jQuery.noConflict();
	</script>
	<script src="../js/common.js" type="text/javascript"></script>
	<script src="../js/ajax.js" type="text/javascript"></script>
	<script src="http://cdn.gigya.com/js/socialize.js?apiKey=3_gGObGZB96M9EJm8iSReWnC3GSUBsHggOVGoAkPobuADEsMzwI-Hw8h4daBJpiQ8Y"></script>
	<script src="../js/ga.js" type="text/javascript"></script>
	<link href="https://plus.google.com/109978880912693970020" rel="publisher" />
</head>
<body>
	<!-- Header start -->
	<jsp:include page="common/header_top.jsp"></jsp:include>
	<div style="height:27px"></div>
	<!-- Header end -->
	
	<div id="allBanner" class="main_box" style="width: 972px; height: 0px;"></div>
	
	<!-- Top box start -->
	<jsp:include page="top/top_box.jsp"></jsp:include>
	<script type="text/javascript" src="../js/header.js?799"></script>
	<!-- Top box end -->

	<script src="../js/addthis.js" type="text/javascript"></script>
	
	<script>var pic_high=0;</script>
	
	<link type="text/css" rel="stylesheet" href="../css/thing_item.css?799">
	<link type="text/css" rel="stylesheet" href="../css/default_thing_item.css?799">
	<link media="screen" href="../css/jquery.fancybox-1.3.4.css" type="text/css" rel="stylesheet">
	
	<script type="text/javascript" src="../js/jquery.mousewheel-3.0.4.pack.js"></script>
	<script type="text/javascript" src="../js/jquery.fancybox-1.3.4.pack.js"></script>

	<!-- Main box start -->
	<div class="main_box">
		<form name="cusform" method="post" action="/uc/shoppingCart" onsubmit="return checkItem()">
			<jsp:include page="wrapper/path_title.jsp"></jsp:include>
			<div class="item_ad_box"> </div>
			<jsp:include page="goods/correlation_thing.jsp"></jsp:include>
			<div class="item_ad_box"> </div>
			<jsp:include page="goods/goods_info_top.jsp"></jsp:include>
			
			<!-- goods related -->
			<jsp:include page="goods/goodsRelated.jsp"></jsp:include>
			<!-- goods related  end-->
		</form>
	</div>
	<!-- Main box end -->
	
	
		<!-- Bottom start -->
	<jsp:include page="bottom/bottom_box.jsp"></jsp:include>
	<script type="text/javascript" src="js/footer.js"></script>
	<script src="http://connect.facebook.net/en_US/all.js#xfbml=1"></script>
	<img style="display:none;" alt="" src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${pageForm.pageProperties.productDetail.images[0].thumbnailUrl}">
</body>
</html>