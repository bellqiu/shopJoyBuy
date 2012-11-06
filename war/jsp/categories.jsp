<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="include.jsp" %>
<%@page import="com.spshop.utils.Constants"%>
<html>
<head>
	<base href="http://${pageForm.site.domain }/" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8">
	
	
	<c:if test="${requestScope.pageNum != null}">
	<title>${pageForm.category.pageTitle} - page ${requestScope.pageNum}</title>
	<meta name="description" content="${pageForm.category.description} - page ${requestScope.pageNum}">
	</c:if>
	<c:if test="${requestScope.pageNum == null}">
	<title>${pageForm.category.pageTitle}</title>
	<meta name="description" content="${pageForm.category.description}">
	</c:if>
	<meta name="keywords" content="${pageForm.category.relatedKeyword}">
	
	<link rel="stylesheet" type="text/css" href="../css/header.css">
	<link rel="stylesheet" type="text/css" href="../css/general.css">
	<link rel="stylesheet" type="text/css" href="../css/index.css">
	<link rel="stylesheet" type="text/css" href="../css/footer.css">
	<link rel="stylesheet" type="text/css" href="../css/global.css">
	<link rel="stylesheet" type="text/css" href="../css/head.css">
	<c:if test="${(requestScope.pageNum-1)>0}">
	<link rel="prev" herf="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}?pageNum=${requestScope.pageNum-1}"/>
	</c:if>
	<c:if test="${(requestScope.pageNum+1)<(fn:length(requestScope.pageIndex)+1)}">
	<link rel="next" herf="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}?pageNum=${requestScope.pageNum+1}"/>
	</c:if>
	
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
	<div class="main_box"></div>
	<div class="main_box">
		<jsp:include page="wrapper/path_title.jsp"></jsp:include>
		<div class="hei10"></div>
		
		<!-- Left box start -->
		<div class="left_box">
			<jsp:include page="wrapper/category_name.jsp"></jsp:include>
			<div class="hei10"></div>
			<jsp:include page="wrapper/top_selling.jsp"></jsp:include>
			<div class="hei10"></div>
			<jsp:include page="wrapper/mail_box.jsp"></jsp:include>
		</div>
		<!-- Left box end -->
		
		<!-- Right box start -->
		<div class="right_box">
			<jsp:include page="wrapper/ad_list_box.jsp"></jsp:include>
			<div class="hei20"></div>
			<c:if test="${fn:length(pageForm.category.subCategories) != 0}">
				<jsp:include page="wrapper/parentCategoryContent.jsp"></jsp:include>
			</c:if>
			<c:if test="${fn:length(pageForm.category.subCategories) == 0}">
				<jsp:include page="wrapper/contain_change.jsp"></jsp:include>
			</c:if>
			<div class="hei10"></div>
		</div>
		<!-- Right box end -->
	</div>
	<!-- Main box end -->
	
	<!-- Bottom start -->
	<div class="hei10"></div>
	<jsp:include page="bottom/bottom_box.jsp"></jsp:include>
	<!-- Bottom end -->
	
	<script type="text/javascript" src="../js/footer.js"></script>
</body>
</html>