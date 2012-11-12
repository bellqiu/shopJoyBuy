<style type="text/css">
 .kuanjia_box {
	margin: 0px;
	border: 0px;
	padding: 10px 10px 10px 20px;
}

.notice_box {
	margin: 0px;
}

.notice_top {
	padding: 5px 12px;
	font: 14px Verdana, Arial, Helvetica, sans-serif;
	color: #222323;
}

.notice_center {
	margin: 0px;
	padding: 0px;
	border: 0px;
	height: 5px;
}

.notice_content {
	margin: 0px;
	padding: 0 5px 5px 25px;
	border: 0px;
	font: 12px Verdana, Arial, Helvetica, sans-serif;
	line-height: 16px;
	color: #bd4604;
}

.notice_content ul {
	margin-left: 0;
}

.notice_content ul li {
	list-style: none;
	background:
		url(http://184.22.252.66/image/endefault/mobantupian/images/cirle.jpg)
		no-repeat left 7px;
	padding-left: 12px;
}

.sp_content {
	margin: 0px;
	padding: 5px 5px 5px 20px;
	border: 0px;
	font: 12px/18px Verdana, Arial, Helvetica, sans-serif;
	color: #000000;
}

.bt_box {
	margin: 0px;
	padding: 5px 16px;
	border: 0px;
	font: bold 13px Verdana, Arial, Helvetica, sans-serif;
	line-height: 15px;
	color: #000000;
	clear: both;
}

.tempnr_box {
	margin: 0px;
	padding: 5px 15px 15px 27px;
	border: 0px;
	font: 12px/18px Verdana, Arial, Helvetica, sans-serif;
	color: #000000;
	list-style: disc outside;
}

.tablestyle {
	border-collapse: collapse;
}

.tablestyle tr td {
	height: 25px;
	border: 1px solid #666;
	text-align: center;
	font-size: 12px;
}

.tempnr_box table {
	font-size: 12px;
}

.images_zuo {
	float: left;
	margin: 2px 15px 0px 0px;
}

.image_box {
	margin: 0px;
	padding: 0px;
	border: 0px;
}

.nr_right {
	margin: 60px 0px 0px 0px;
	width: 420px;
	font: 12px/16px Verdana, Arial, Helvetica, sans-serif;
	color: #000000;
	float: left;
	list-style: disc outside;
}

.image {
	margin: 4px 5px 4px 11px;
	border: 1px solid #e3dfdf;
	float: left;
}

.bottom_mbpage {
	height: 5px;
	margin: 0px;
	padding: 0px;
	border: 0px;
	clear: both;
}

.wzys {
	color: #000000;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-weight: bold;
	line-height: 30px;
}

.wzys2 {
	font: 12px Verdana, Arial, Helvetica, sans-serif;
	color: #000000;
}

.wzys3 {
	font: 12px Verdana, Arial, Helvetica, sans-serif;
	color: #000000;
}

.neirong_box {
	margin-top: 10px;
	margin-bottom: 10px;
	font: 12px Verdana, Arial, Helvetica, sans-serif;
	color: #353432;
	line-height: 20px;
	list-style: disc outside;
	clear: both;
	padding-right: 4px;
	padding-left: 4px
}
</style>
<div class="item_tab">
	<div class="item_tabMenu">
		<ul class="size_tab">
			<li id="size_title1" class="select">Description</li>
			<c:if test="${not empty pageForm.pageProperties.productDetail.manualKey}">
					<c:forTokens items="${pageForm.pageProperties.productDetail.manualKey }" delims="," var="manKey" varStatus="stat">
					  	<ss:html var="html" htmlId="${manKey}">
					  		<li id="size_title${stat.index+2 }">
					   	 		${html.name }
					   	 	</li>
					    </ss:html>
					 </c:forTokens>
    			</c:if>
		</ul>
	</div>
	<div class="item_tabContent">
		<a id="p_more"></a>
		<div class="kuanjia_box">
			<div id="size_tab1">
				<c:out value="${pageForm.pageProperties.productDetail.detail}"
					escapeXml="false" />
			</div>
				<c:if test="${not empty pageForm.pageProperties.productDetail.manualKey}">
					<c:forTokens items="${pageForm.pageProperties.productDetail.manualKey }" delims="," var="manKey" varStatus="stat">
					  	<ss:html var="html" htmlId="${manKey}">
					  		<div style="display: none;" id="size_tab${stat.index+2 }">
					   	 		<c:out value="${html.content}" escapeXml="false"></c:out>
					   	 	</div>
					    </ss:html>
					 </c:forTokens>
    			</c:if>
		</div>
	</div>
</div>
<script type="text/javascript">
	jq("li[id^=size_title]").each(function(idx,el){
		jq(el).click(function(){
			jq("li[id^=size_title]").removeClass("select");
			jq(this).addClass("select");
			jq("div[id^=size_tab]").hide();
			jq(jq("div[id^=size_tab]")[idx]).show();
		});
	});
</script>
