<%@page import="com.spshop.utils.Constants"%>
<%@ include file="../include.jsp" %>
<ss:cal imgSize="LARGE_SIZE" prodList="${pageForm.pageProperties.productsInCategoryPage}" width="225" paddingSize="paddingSize" heightVal="heightValue">
<div id="contain_change">
	<div class="line1"></div>
	<div class="page_box">
		<div class="page_num">
			<form action="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}" method="post">
				<c:if test="${requestScope.startIndex > 1}">
					<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}?<%=Constants.PAGE_NUM %>=${requestScope.pageNum - 1}">Prev</a>
				</c:if>
				<c:if test="${requestScope.endIndex < requestScope.productsCount}">
					<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}?<%=Constants.PAGE_NUM %>=${requestScope.pageNum + 1}">Next</a>
				</c:if>
				<c:forEach items="${requestScope.pageIndex}" var="pageIdx">
					<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}?pageNum=${pageIdx}">${pageIdx}</a>
				</c:forEach>
				<input type="text" name="<%=Constants.PAGE_NUM %>" class="page_input" value="${requestScope.pageNum}">
				<input type="hidden" name="<%=Constants.START_INDEX %>" value="${requestScope.startIndex}">
				<input type="hidden" name="<%=Constants.END_INDEX %>" value="${requestScope.endIndex}">
				<input type="submit" value="ok">
			</form>
		</div>
		<span class="font_size11">Showing Results ${requestScope.startIndex} - ${requestScope.endIndex} of ${requestScope.productsCount}</span>
	</div>
	<div class="hei5"></div>

	<div class="hei10"></div>
	<div class="B_Categories Gallery_pic" style="background: none repeat scroll 0 0 #FBF9F7; border: 1px solid #D1D1D1; padding-top: 10px;">
		<ul>
		<c:forEach items="${pageForm.pageProperties.productsInCategoryPage}" var="product">
			<li class="goods_list box_shadow">
				<div class="goods_picture" style="height: ${heightValue}px;">
					<c:if test="${(1 - product.actualPrice / product.price) * 100 > 40}">
						<div style='background:url("/css/sales_bg.png") no-repeat scroll 0 0 transparent; position:absolute; top: -11px; right: -11px; width:65px; height: 65px;'>
							<label style="font-size: 12px; position: relative; top: 7px; color: white;">
								Save
							</label>
							<label style="font-size: 22px; position: relative; top: 5px; color: white; font-weight: bold;">
								<fmt:formatNumber type="number" value="${(1 - product.actualPrice / product.price) * 100}" maxFractionDigits="0"/>%
							</label>
						</div>
					</c:if>
						<a onclick="redirect('<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/${product.name}')"
							href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/${product.name}" title="">
							<c:if test='${product.images[0].strSizeType eq "PRODUCT_SQUARE"}'>
								<img style="padding-top:${paddingSize}px;"
								alt="${product.title}"
								src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${product.images[0].largerUrl}"> 
							</c:if>
							<c:if test='${product.images[0].strSizeType eq "PRODUCT_NORMAL"}'>
								<img
								alt="${product.title}"
								src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${product.images[0].largerUrl}"> 
							</c:if>
						</a>
				</div> 
				<strong class="goods_name"> 
					<a	title="${product.title}"
						href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/${product.name}">
						${product.title}
					</a>
				</strong>
				<div class="Reference_Price"> 
					<c:if test="${product.actualPrice > 80}">
						<img alt="Free" src="/css/shipping.jpg" style="float:left; width:15px;">
						<span style="float:left; color:gray;">Free Shipping</span>
					</c:if>
					${currency }<del>
					<fmt:formatNumber value="${product.price * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber>
					</del> <br />
					<span class="red fontbold" style="font-size: 18px;"><fmt:formatNumber value="${product.actualPrice * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber></span> 
				</div>
				<div class="list_stars">
					<span class="Reference_Price"> 
						<!-- TODO Rate -->
					</span>
				</div>
				</li>
		</c:forEach>
		</ul>
		<div class="hei20"></div>
	</div>
	
	<div class="page_box">
		<div class="page_num">
			<form action="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}" method="post">
				<c:if test="${requestScope.startIndex > 1}">
					<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}?<%=Constants.PAGE_NUM %>=${requestScope.pageNum - 1}">Prev</a>
				</c:if>
				<c:if test="${requestScope.endIndex < requestScope.productsCount}">
					<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}?<%=Constants.PAGE_NUM %>=${requestScope.pageNum + 1}">Next</a>
				</c:if>
				<c:forEach items="${requestScope.pageIndex}" var="pageIdx">
					<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${pageForm.category.name}?pageNum=${pageIdx}">${pageIdx}</a>
				</c:forEach>
				<input type="text" name="<%=Constants.PAGE_NUM %>" class="page_input" value="${requestScope.pageNum}">
				<input type="hidden" name="<%=Constants.START_INDEX %>" value="${requestScope.startIndex}">
				<input type="hidden" name="<%=Constants.END_INDEX %>" value="${requestScope.endIndex}">
				<input type="submit" value="ok">
			</form>
		</div>
		<span class="font_size11">Showing Results ${requestScope.startIndex} - ${requestScope.endIndex} of ${requestScope.productsCount}</span>
	</div>
	<div class="line1"></div>
</div>
</ss:cal>