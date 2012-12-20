<%@include file="../include.jsp" %>
<%@page import="com.spshop.utils.Constants" %>
<ss:site var="site">
	<div class="top_box">
		<div class="logo_box" style="background:url(${site.logo.noChangeUrl}) no-repeat;height:80px;width:230px;">
			<a title="home" href="http://joybuy.co.uk/">Home page</a>
		</div>
		<div class="search">
			<span style="display: inline-block; width:233px; background: url('/image/register_banner.png'); position: relative; left: 90px; height: 29px; top: -10px;"></span>
			<form onsubmit="" id="formss" name="formss" method="get" action="">
				<jsp:include page="search_box.jsp"></jsp:include>
			</form>
		</div>
		<div class="security">
			<a target="_blank" href="${site.featuredCatURL}" ref="nofollow">
				<span style="width:165px;height:50px;background: url(${site.featuredCat.noChangeUrl }) no-repeat 0px 0px;display:inline-block;float:left" ></span>
			</a>
			<a target="_blank" href="${site.deliveryURL}" ref="nofollow"><span style="width: 165px;height: 50px;background: url(${site.delivery.noChangeUrl }) no-repeat 0px 0px;;display:inline-block;float:left"></span>
			</a>
		</div>
		<jsp:include page="main_menu.jsp"></jsp:include>
	</div>
</ss:site>