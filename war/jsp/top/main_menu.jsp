<%@page import="com.spshop.utils.Constants"%>
<%@include file="../include.jsp" %>
<ss:menubar items="menuItems" specialOffers="specialOfferItems">
<div class="main_menu" id="main_menu">
	<ul>
	<c:forEach items="${menuItems}" var="category" varStatus="idx" step="1">
		<li class="first">
			<span>
			<c:if test='${category.name != "home"}'>
				<c:if test="${empty category.url}">
					<a title="${category.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${category.name}">
						${category.displayName}
					</a>
				</c:if>
				<c:if test="${!empty category.url}">
					<a title="${category.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${category.url}">
						${category.displayName}
					</a>
				</c:if>
			</c:if>
			<c:if test='${category.name == "home"}'>
				<a title="${category.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/">
						${category.displayName}
				</a>
			</c:if>
			</span>
			<div class="sub_menu navigationKind${idx.index-1} sub_menu_2_1" style="display: none;">
				<div class="inner_box2">
				<c:forEach items="${category.subCategories}" var="subCategory">
				<c:if test="${!subCategory.specialOffer}">
					<dl class="inner_list">
						<dt>
						<c:if test="${empty subCategory.url}">
							<a title="${subCategory.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${subCategory.name}">
								${subCategory.displayName}
							</a>
						</c:if>
						<c:if test="${!empty subCategory.url}">
							<a title="${subCategory.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${subCategory.url}">
								${subCategory.displayName}
							</a>
						</c:if>
						</dt>
						<c:forEach items="${subCategory.subCategories}" var="childCategory">
						<c:if test="${!childCategory.specialOffer}">
						<dd>
						<c:if test="${empty childCategory.url}">
							<a title="${childCategory.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${childCategory.name}"> 
								${childCategory.displayName}
							</a>
						</c:if>
						<c:if test="${!empty childCategory.url}">
							<a title="${childCategory.displayName}" href="<%=Constants.HTTP_PROTOCOL%>${childCategory.url}"> 
								${childCategory.displayName}
							</a>
						</c:if>
						</dd>
						</c:if>
						</c:forEach>
						<c:if test="${fn:length(subCategory.subCategories) != 0}">
						<dd class="end_more">
							<c:if test="${empty subCategory.url}">
								<a title="${subCategory.displayName}"
									href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${subCategory.name}">
									View All
								</a>
							</c:if>
							<c:if test="${!empty subCategory.url}">
								<a title="${subCategory.displayName}"
									href="<%=Constants.HTTP_PROTOCOL%>${subCategory.url}">
									View All
								</a>
							</c:if>
						</dd>
						</c:if>
					</dl>
				</c:if>
				</c:forEach>
				</div>
				<div class="inner_box_along">
				<c:if test=""></c:if>
				<c:forEach items='${specialOfferItems}' var="specialOffer">
					<c:if test="${specialOffer.key == category.name && fn:length(specialOffer.value) != 0}">
						<c:forEach items="${specialOffer.value}" var="special">
						<dl>
							<dt>
							<c:if test="${empty special.url}">
								<a title="${special.displayName}"
									href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${special.name}">
									${special.displayName}
								</a>
							</c:if>
							<c:if test="${!empty special.url}">
								<a title="${special.displayName}"
									href="<%=Constants.HTTP_PROTOCOL%>${special.url}">
									${special.displayName}
								</a>
							</c:if>
							</dt>
						</dl>
						</c:forEach>
					<div style="clear: both;"></div>
					<div class="topNavClassAdWrapper">
						<div class=" SpecialboxShadow topNavAdSpecialOffer">
							<span class="topNavAdSpecialOffertxt Specialtextshadow ">Special offer</span>
						</div>
						<ul class="topNavClassAd">
							<li>
							<c:if test="${empty specialOffer.value[0].url}">
								<a title="${specialOffer.value[0].displayName}"
									href="<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/<%= Constants.CATEGORY_URL %>/${specialOffer.value[0].name}">
									<img width="174" height="84" border="0" src="${specialOffer.value[0].specialOfferImage.noChangeUrl}">
								</a>
							</c:if>
							<c:if test="${!empty specialOffer.value[0].url}">
								<a title="${specialOffer.value[0].displayName}"
									href="<%=Constants.HTTP_PROTOCOL%>${specialOffer.value[0].url}">
									<img width="174" border="0" src="${specialOffer.value[0].specialOfferImage.noChangeUrl}">
								</a>
							</c:if>
							</li>
						</ul>
					</div>
					</c:if>
				</c:forEach>
				</div>
			</div>
		</li>
	</c:forEach>
	</ul>
</div>
</ss:menubar>