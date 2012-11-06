<%@page import="com.spshop.utils.Constants"%>
<%@include file="../include.jsp" %>
<div class="Categories_name">
	<h2 style="font-weight: normal" class="dark font_size12">

		<span class="red fontbold">${pageForm.category.displayName}</span>
	</h2>

	<ul style="display: block" class="subCatList" id="Caracoter_lb">
	<c:forEach items="${pageForm.category.subCategories}" var="subCategory">
		<li>
		<c:if test="${empty subCategory.url}">
			<a href="<%= Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${subCategory.name}"
				   title="${subCategory.displayName}" class="cg">
				   ${subCategory.displayName}
				   <img src="/css/thing_hot.gif"
						style="display: none; border: none;" class="thinghot">
			</a>
		</c:if>
		<c:if test="${!empty subCategory.url}">
			<a href="<%=Constants.HTTP_PROTOCOL%>${pageForm.category.url}"
			   title="${subCategory.displayName}" class="cg">
			   ${subCategory.displayName} 
			   <img src="/css/thing_hot.gif"
			   		style="display: none; border: none;" class="thinghot">
			</a>
		</c:if>
		</li>
	</c:forEach>
	</ul>
	<script>
		var tmpContent = jq('&lt;div&gt;&lt;/div&gt;').append(
				jq("#Caracoter_lb").clone()).html();
	</script>
</div>