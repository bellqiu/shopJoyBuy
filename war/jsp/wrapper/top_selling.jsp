<%@include file="../include.jsp" %>
<%@page import="com.spshop.utils.Constants"%>
<div class="top5selling">
<ss:topSelling var="topData">
	<h3 class="red font_size16">Top Selling</h3>
	<ul>
	<c:forEach items="${topData.products}" var="topProduct">
		<li>
			<a title="${topProduct.title}" href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}<%=Constants.URL_SEPERATOR %>${topProduct.name}">
				<c:if test='${topProduct.images[0].strSizeType eq "PRODUCT_SQUARE"}'>
				<img src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${topProduct.images[0].thumbnailUrl}"
					 alt="${topProduct.title}"
					 style="padding-top:${(87-65)/2}px; padding-bottom:${(87-65)/2}px;">
				</c:if>
				<c:if test='${topProduct.images[0].strSizeType eq "PRODUCT_NORMAL"}'>
				<img src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${topProduct.images[0].thumbnailUrl}"
					 alt="${topProduct.title}"
					 style="height: 87px;">
				</c:if>
			</a><br>
			
			<a title="" href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}<%=Constants.URL_SEPERATOR %>${topProduct.name}"
				class="font_size11 lineheight14">
				${topProduct.title}
			</a>
		</li>
	</c:forEach>
	</ul>
	<div class="hei1"></div>
</ss:topSelling>
</div>