<%@include file="../include.jsp" %>
<%@page import="com.spshop.utils.Constants"%>
<ss:tabSelling var="tabData">
<div class="right_box borderstyle_01 index_tab">
	<div class="Promotions_tab" id="Promotions_tab">
	<c:forEach items="${tabData.tabs}" var="tabTitle" varStatus="idx" step="1">
		<c:if test="${idx.index == 0}">
		<h3 aim="Promotions_con&gt;.daily_madness countdown_sun go_dailymadness"
			class="link_now">
				<a style="display: block">${tabTitle.name}</a>
		</h3>
		</c:if>
		<c:if test="${idx.index > 0}">
		<h3 aim="Promotions_con&gt;.Spotlight${idx.index} countdown_sun go_dailymadness">
				<a style="display: block">${tabTitle.name}</a>
		</h3>
		</c:if>
	</c:forEach>
<!-- 		<h3 aim="Promotions_con&gt;.Spotlight"> -->
<!-- 			<a href="http://www.honeybuy.com/promotions/spotlight.html" -->
<!-- 			   style="display: block">Spotlight</a> -->
<!-- 		</h3> -->
	</div>
	<div style="background: #FBF9F7;" id="Promotions_con" class="tab_content">
	<style type="text/css">
			.scrollContral {
				position: absolute;
				z-index: 4;
				top: 345px;
				left: 0;
				width: 771px;
				background: #f3eee8;
			}
			
			.scrollButton {
				height: 40px;
			}
			
			.scrollButtonLeft {
				float: left;
				display: block;
				height: 40px;
				padding: 0 20px;
				line-height: 40px;
				color: #000;
				font-size: 16px;
			}
			
			.scrollButtonRight {
				float: right;
				display: block;
				height: 40px;
				padding: 0 20px;
				line-height: 40px;
				color: #000;
				font-size: 16px;
			}
			
			.scrollStatus {
				width: 771px;
				height: 3px;
				top: 382px;
				left: 1px;
				z-index: 10;
				position: absolute;
			}
			
			.scrollStatus li {
				height: 3px;
				font-size: 3px;
				line-height: 3px;
				display: block;
				float: left;
				margin-right: 1px;
				width: 191.7px;
				background: #B0B0B0;
			}
			
			.scrollStatus li.scrollStatusShow {
				background: #ffb500;
			}
			
			#NewarrivalList,#spotlightList {
				border: 0px solid;
				width: 760px;
				height: 335px;
			}
		</style>
	<c:forEach items="${tabData.tabs}" var="tabContent" varStatus="idx" step="1">
		<c:if test="${idx.index == 0}">
		<ss:cal imgSize="THUM_SIZE" prodList="${tabContent.products}" width="164" paddingSize="dmPaddingSize" heightVal="dmHeightVal">
		<div class="daily_madness" style="display: block;">
		<!-- Needn't use it temporarily -->
		<%-- <jsp:include page="daily_madness_title.jsp"></jsp:include> --%>
			<ul>
			<c:forEach items="${tabContent.products}" var="tabProduct">
				<li class="goods_list box_shadow">
					<div class="goods_picture">
						<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}<%=Constants.URL_SEPERATOR %>${tabProduct.name}" 
						   title="${tabProduct.title}">
							<c:if test='${tabProduct.images[0].strSizeType eq "PRODUCT_SQUARE"}'>
							<img style="width:164px; padding-top:${dmPaddingSize}px;padding-bottom:${dmPaddingSize}px;" src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${tabProduct.images[0].thumbnailUrl}" alt="${tabProduct.title}" />
							</c:if>
							<c:if test='${tabProduct.images[0].strSizeType eq "PRODUCT_NORMAL"}'>
							<img style="width:164px;" src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${tabProduct.images[0].thumbnailUrl}" alt="${tabProduct.title}" />
							</c:if>
						</a>
					</div>
					<strong class="goods_name">
						<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}<%=Constants.URL_SEPERATOR %>${tabProduct.name}" title="${tabProduct.title}">
							${tabProduct.title}
						</a>
					</strong>
					<div class="dilyMadness_save">
						<c:if test="${tabProduct.actualPrice ne tabProduct.price }">
						<p class="save_title">Save</p>
						<p class="save_num">
							<fmt:formatNumber type="number" value="${(1 - tabProduct.actualPrice / tabProduct.price) * 100}" maxFractionDigits="0"/>%
						</p>
						</c:if>
					</div>
					<del>
						${currency }<fmt:formatNumber value="${tabProduct.price  * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber>
					</del>
					<p class="dilyMadness_priceTip">
					${currency }<fmt:formatNumber value="${tabProduct.actualPrice  * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber>
					</p>
				</li>
			</c:forEach>
			</ul>
		</div>
		</ss:cal>
		</c:if>
		<c:if test="${idx.index > 0}">
		<ss:cal imgSize="THUM_SIZE" prodList="${tabContent.products}" width="97" paddingSize="slPaddingSize" heightVal="slHeightVal">
		<div id="Spotlight" class="Spotlight${idx.index}" style="display: none;">
			<div class="other_Progoods" id="spotlightList">
				<div>
				<c:forEach items="${tabContent.products}" var="tabProduct">
					<dl>
						<dt>
							<a title="${tabProduct.name}"
								href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}<%=Constants.URL_SEPERATOR %>${tabProduct.name}">
								<c:if test='${tabProduct.images[0].strSizeType eq "PRODUCT_SQUARE"}'>
								<img style="padding-top:${slPaddingSize}px;padding-bottom:${slPaddingSize}px;" alt="${tabProduct.name}"
								     src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${tabProduct.images[0].thumbnailUrl}">
								</c:if>
								<c:if test='${tabProduct.images[0].strSizeType eq "PRODUCT_NORMAL"}'>
								<img alt="${tabProduct.name}"
								     src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${tabProduct.images[0].thumbnailUrl}">
								</c:if>
							</a>
						</dt>
						<dd>
							<p class="goods_name">
								<a title="${tabProduct.name}"
								   href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}<%=Constants.URL_SEPERATOR %>${tabProduct.name}">
								   ${tabProduct.title}
								</a>
							</p>
							<p class="save_per font_size20">
							<c:if test="${tabProduct.price ne tabProduct.actualPrice}">
								Save:<fmt:formatNumber type="number" value="${(1 - tabProduct.actualPrice / tabProduct.price) * 100}" maxFractionDigits="0"/>%
							</c:if>
							</p>
							<del>
							 ${currency} <fmt:formatNumber currencyCode="${currency }" maxFractionDigits="2" value="${tabProduct.price * currencies[currency]}"></fmt:formatNumber>
							</del>
							<p class="goods_price">
							 ${currency} <fmt:formatNumber currencyCode="${currency }" maxFractionDigits="2" value="${tabProduct.actualPrice * currencies[currency]}"></fmt:formatNumber></p>
						</dd>
					</dl>
				</c:forEach>
				</div>
			</div>
		</div>
		</ss:cal>
		</c:if>
	</c:forEach>
	</div>
</div>
</ss:tabSelling>