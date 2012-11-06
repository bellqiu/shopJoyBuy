<%@page import="com.spshop.utils.Constants"%>
<%@ include file="../include.jsp" %>
<div id="contain_change">
	<div class="line1"></div>
	<div class="hei5"></div>

	<div class="hei10"></div>
	<div class="B_Categories Gallery_pic" style="background: none repeat scroll 0 0 #FBF9F7; border: 1px solid #D1D1D1;">
		<ul>
		<c:forEach items="${pageForm.pageProperties.subCategoryProducts }" var="subProducts">
		<span class="red font_size16" style="margin-bottom:5px; padding-top:8px; display:inline-block; width:100%; background:url('/css/Promotions_tab.gif') repeat-x scroll 0 0 transparent; height:39px;text-shadow: 0 1px 0 rgba(255, 255, 255, 0.8);">
			<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${subProducts.key.name}" style="font-weight:bold; margin-left: 15px;">${subProducts.key.displayName}</a>
			<a style="float:right; margin-right:10px;" href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/<%=Constants.CATEGORY_URL %>/${subProducts.key.name}">more..</a>
		</span>
		<div>
		<ss:cal imgSize="LARGE_SIZE" prodList="${subProducts.value}" width="225" paddingSize="paddingSize" heightVal="heightValue">
		<c:forEach items="${subProducts.value}" var="product">
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
					</del><br/>
					<span class="red fontbold" style="font-size: 18px;"><fmt:formatNumber value="${product.actualPrice * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber></span> 
				</div>
				<div class="list_stars">
					<span class="Reference_Price"> 
						<!-- TODO Rate -->
					</span>
				</div>
			</li>
		</c:forEach>
		</ss:cal>
		</div>
		<div class="hei10"></div>
		</c:forEach>
		<ss:cal imgSize="LARGE_SIZE" prodList="${pageForm.pageProperties.restProducts}" width="225" paddingSize="paddingSize" heightVal="heightValue">
		<c:forEach items="${pageForm.pageProperties.restProducts}" var="product">
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
					</del><br/>
					<span class="red fontbold" style="font-size: 18px;"><fmt:formatNumber value="${product.actualPrice * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber></span> 
				</div>
				<div class="list_stars">
					<span class="Reference_Price"> 
						<!-- TODO Rate -->
					</span>
				</div>
			</li>
		</c:forEach>
		</ss:cal>
		</ul>
		<div class="hei20"></div>
	</div>
	
	<div class="line1"></div>
</div>