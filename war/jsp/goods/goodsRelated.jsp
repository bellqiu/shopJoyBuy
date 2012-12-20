<%@include file="../include.jsp" %>
<%@page import="com.spshop.utils.Constants"%>
<div id="tab_middle" class="item_goods_info_box_bottom">

<div class="item_bottom_right">
	<c:if test="${pageForm.pageProperties.productDetail.tabProductKey > 0}">
		<ss:tabProductNames var="names" tabId="${pageForm.pageProperties.productDetail.tabProductKey }">
			<h3>Customers Who Bought This Item Also Bought</h3>
			<div class="item_bottom_funBt">
			</div>
		    <ul class="complete_look_normal related_product">
		      <li class="source"> 
				     <div>
				      	<a title="${pageForm.pageProperties.productDetail.title }" href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}<%=Constants.URL_SEPERATOR %>${pageForm.pageProperties.productDetail.name}">
				      		<c:if test='${pageForm.pageProperties.productDetail.images[0].strSizeType eq "PRODUCT_SQUARE"}'>
				      			<img style="width: 160px; height: 105; padding-top: ${(105-80)/2}px;" border="0" alt="${pageForm.pageProperties.productDetail.title }" src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${pageForm.pageProperties.productDetail.images[0].thumbnailUrl}">
				      		</c:if>
				      		<c:if test='${pageForm.pageProperties.productDetail.images[0].strSizeType eq "PRODUCT_NORMAL"}'>
				      			<img style="width: 160px; height: 105;" border="0" alt="${pageForm.pageProperties.productDetail.title }" src="http://joybuy.co.uk${pageForm.pageProperties.productDetail.images[0].thumbnailUrl}">
				      		</c:if>
				      	</a>
				      </div>
				 </li>
		     <c:forEach items="${names}" var="name" end="7">
		     	<ss:product var="product" productName="${name}" >
				      <li class="unselected"> 
				      	<div>
				      	<a title="${product.title }" href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}<%=Constants.URL_SEPERATOR %>${product.name}">
				      		<c:if test='${product.images[0].strSizeType eq "PRODUCT_SQUARE"}'>
				      		<img style="width: 80px; height: 105; padding-top: ${(105-80)/2}px;" border="0" alt="${product.title }" src="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${product.images[0].thumbnailUrl}">
				      		</c:if>
				      		<c:if test='${product.images[0].strSizeType eq "PRODUCT_NORMAL"}'>
				      		<img style="width: 80px; height: 105;" border="0" alt="${product.title }" src="http://joybuy.co.uk${product.images[0].thumbnailUrl}">
				      		</c:if>
				      	</a>
				        <div>
				        
				        ${currency} <fmt:formatNumber value=" ${product.actualPrice * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber>
				       </div>
				        <div>
							<input type="checkbox" name="relatedProduct" value="${product.name}" />				        	
				       </div>
				       </div>
				      </li>
		      </ss:product>
		     </c:forEach>
		    </ul>
		    
		    <div class="related_product_sum">
		    	<ul>
			       <li><span> Total Price:</span><span id="related_product_sum_total_price"> ${currency} <fmt:formatNumber value=" ${pageForm.pageProperties.productDetail.actualPrice * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber></span></li>
			       <li><input type="button"  value="Buy Together" id="related_product_sum_total_price_submit" class="button_01"></li>
			     </ul>
			     <script type="text/javascript">
			     	
			     	jq("#related_product_sum_total_price_submit").click(function(){
			     		if(jq("#nosubitem_addBag_customize").length>0){
			     			jq("#nosubitem_addBag_customize").click();
			     		}else{
			     			jq("#nosubitem_addBag").click();
			     		}
			     	});
			     </script>
		    </div>
		  
		    
	    </ss:tabProductNames>
    </c:if>
</div>
  <div class="item_reviews">
  <div class="item_tab">
  <%-- 
  	<c:if test="${pageForm.pageProperties.productDetail.manualKey > 0}">
	  	<ss:html var="html" htmlId="${pageForm.pageProperties.productDetail.manualKey}">
	   	 <c:out value="${html.content}" escapeXml="false"></c:out>
	    </ss:html>
    </c:if>
    --%>
    <%@include file="goods-desc.jsp" %>
  </div>
     <div class="pages_box"> 
    	<c:if test="${pageForm.pageProperties.productDetail.showComments}">
<!--     		<H1 style="float: left;">Comments</H1> -->
<%-- 			<fb:comments href='<%=Constants.HTTP_PROTOCOL%>${pageForm.site.domain}/${pageForm.pageProperties.productDetail.name }' num_posts="15" width="972"></fb:comments> --%>
			<h3 style="float: left;">Comments</h3>
			<div id='commentsDiv' style=" clear: left;"></div>
			<script type='text/javascript'>
				var conf = {}
				var params ={
				categoryID: 'JoyBuy',
				streamID: '${pageForm.pageProperties.productDetail.name}',
				containerID: 'commentsDiv',
				cid:'',
				width: '100%'
				}
				gigya.services.socialize.showCommentsUI(conf,params)
			</script>
		</c:if>
      </div>
    <div id="writepl" class="item_reviews_bottom_page">
    <%-- --%>
      <div>
      </div>
    </div>
  </div>  
</div>

<div class="hei10"></div>