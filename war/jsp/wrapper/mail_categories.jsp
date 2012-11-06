<%@ include file="../include.jsp" %>
<%@page import="com.spshop.utils.Constants"%>
<ss:menubar items="categories" specialOffers="specialOfferItems">
<c:forEach items="${categories}" var="category">
<c:if test='${category.name != "home"}'>
<dl class="main_Categories font_size10 lineheight15 float_left">
	<dt class="fontbold font_size11">
		<c:if test="${empty category.url}">
			<a class="blue" title="${category.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${category.name}">
				${category.displayName}
			</a>
		</c:if>
		<c:if test="${!empty category.url}">
			<a class="blue" title="${category.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${category.url}">
				${category.displayName}
			</a>
		</c:if>
	</dt>
	<c:forEach items="${category.subCategories}" var="subCategory">
	<dd>
		<c:if test="${empty subCategory.url}">
			<a class="Light_gray2" title="${subCategory.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${subCategory.name}">
				${subCategory.displayName}
			</a>
		</c:if>
		<c:if test="${!empty subCategory.url}">
			<a class="Light_gray2" title="${subCategory.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${subCategory.url}">
				${subCategory.displayName}
			</a>
		</c:if>
	</dd>
	</c:forEach>
</dl>
</c:if>
</c:forEach>
</ss:menubar>