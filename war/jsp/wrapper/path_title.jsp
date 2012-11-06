<%@include file="../include.jsp"%>
<%@page import="com.spshop.utils.Constants" %>
<div class="Path_title Light_gray2">
	<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}" class="Light_gray2">Home</a>
	<c:forEach items="${pageForm.pathNodes}" var="node">
		<span><%=Constants.GREATER %></span>
		<c:if test="${empty node.url}">
			<a title="${node.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${node.name}">
				${node.displayName} </a>
		</c:if>
		<c:if test="${!empty node.url}">
			<a title="${node.displayName}"
				href="<%=Constants.HTTP_PROTOCOL%>${node.url}">
				${node.displayName} </a>
		</c:if>
	</c:forEach>
	<c:if test="${pageForm.pageProperties.productDetail != null}">
		<span><%=Constants.GREATER %></span>
		<a title="${pageForm.pageProperties.productDetail.title}"
				href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/${pageForm.pageProperties.productDetail.name}">
				${pageForm.pageProperties.productDetail.title} </a>
	</c:if>
</div>