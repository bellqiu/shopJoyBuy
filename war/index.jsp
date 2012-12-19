<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="jsp/include.jsp"%>
<html>
<head>
<base href="http://${pageForm.site.domain }/" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">

<meta property="fb:page_id" content="181834808501125" />

<title>${pageForm.category.pageTitle}</title>
<meta name="description" content="${pageForm.category.description}">
<meta name="keywords" content="${pageForm.category.relatedKeyword}">

<link rel="stylesheet" type="text/css" href="css/header.css?799">
<link rel="stylesheet" type="text/css" href="css/general.css?799">
<link rel="stylesheet" type="text/css" href="css/index.css?799">
<link rel="stylesheet" type="text/css" href="css/footer.css?799">
<link rel="stylesheet" type="text/css" href="css/global.css?799">
<link rel="stylesheet" type="text/css" href="css/head.css?799">
<link href="css/mainbox.css" type="text/css" rel="stylesheet" />

<script src="js/jquery-1.5.1.js" type="text/javascript"></script>
<script>
	var Image_url = 'http://127.0.0.1:8888/css', root_url = 'http://127.0.0.1:8888/', seller_lang = 'en-uk';
	var head_SearchKeywordsNo = 'Enter search keywords or item code here';
	var SearchKeyword = 'Enter search keywords or item code here';
	var jq = jQuery.noConflict();
</script>
<script src="js/common.js" type="text/javascript"></script>
<script src="js/ajax.js" type="text/javascript"></script>
<script src="http://cdn.gigya.com/js/socialize.js?apiKey=3_gGObGZB96M9EJm8iSReWnC3GSUBsHggOVGoAkPobuADEsMzwI-Hw8h4daBJpiQ8Y"></script>
<script src="js/ga.js" type="text/javascript"></script>
	<!-- Google Code for All Remarketing List -->
	<script type="text/javascript">
	/* <![CDATA[ */
	var google_conversion_id = 1010858884;
	var google_conversion_language = "en";
	var google_conversion_format = "3";
	var google_conversion_color = "ffffff";
	var google_conversion_label = "SHHBCIyg2wIQhPeB4gM";
	var google_conversion_value = 0;
	/* ]]> */
	</script>
	<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
	</script>
	<noscript>
	<div style="display:inline;">
	<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/1010858884/?label=SHHBCIyg2wIQhPeB4gM&amp;guid=ON&amp;script=0"/>
	</div>
	</noscript>
<link href="https://plus.google.com/109978880912693970020" rel="publisher" />

	<script src="/script/js/common2.js?799" type="text/javascript"></script>
	<script src="/script/js/jquery.validation.js?799" type="text/javascript"></script>

</head>
<body>
	<c:set var="isHome" scope="request">true</c:set>
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

	<!-- Header start -->
	<jsp:include page="jsp/common/header_top.jsp"></jsp:include>
	<div style="height: 27px"></div>
	<!-- Header end -->

	<div id="allBanner" class="main_box" style="width: 972px; height: 0px;"></div>

	<!-- Top box start -->
	<jsp:include page="jsp/top/top_box.jsp"></jsp:include>
	<script type="text/javascript" src="js/header.js"></script>
	<!-- Top box end -->

	<!-- Main box start -->
	<jsp:include page="jsp/wrapper/main_box.jsp"></jsp:include>
	<!-- Main box end -->

	<div id="allBanner_tmp" class="main_box"
		style="position: absolute; left: 305.5px; top: 27px;"></div>

	<!-- Bottom start -->
	<jsp:include page="jsp/bottom/bottom_box.jsp"></jsp:include>
	<!-- Bottom end -->

	<script type="text/javascript" src="js/index.js?799"></script>
	<script type="text/javascript" src="js/footer.js?799"></script>
</body>
</html>