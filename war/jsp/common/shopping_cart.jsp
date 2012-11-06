<%@ include file="../include.jsp"%>
<div class="shopping_cart" id="top_Cart">
	<a rel="nofollow" href="/uc/shoppingCart"
		class="cart_icon wihte_D_shadow Dark_gray">My bag <span
		class="items_num"> (<c:if test="${shoppingcart eq null}">0</c:if>${shoppingcart.itemCount})</span>
	</a>
	<div id="universalCart" style="display: none;">
		<div class="box_top"></div>
		<div class="box_body">
			<div id="universalCartContent">
				<div class="information_cart">
						<span><a
							onclick="javascript:document.getElementById('universalCart').style.display='none';"
							href="javascript:void(0)"><img
								src="/im/btn-closeCart.gif"
								alt="close cart">
						</a>
						</span>${shoppingcart.itemCount} Item(s) in the Shopping Bag
						<c:if test="${shoppingcart.itemCount > 0}" >
							<table width="100%" cellspacing="0" cellpadding="0" class="items_table">  
				                   <tbody id="header_tab_1">
				                   <c:forEach items="${shoppingcart.order.items }" var="item">
					                   <tr>
					                      <td align="center" class="picture"><a href="/${item.product.name }"><img height="50" src="${item.product.images[0].smallUrl }" alt="${item.product.title }"></a></td>
					                      <td class="fontsize">
												${item.product.title }					                                                                             
					                      </td> 
					                      <td align="center" class="red">${item.quantity }</td>                     
					                      <td align="center" class="price">
					                      ${currency }
					                      <fmt:formatNumber value=" ${item.finalPrice * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber>
					                     </td>
					                      <td class="edit">&nbsp;</td>
					                   </tr>
				                  </c:forEach>
							   </tbody>
	  					</table>
  					</c:if>
				</div>
				<c:if test="${shoppingcart.itemCount < 1}">
					<span style="color: #9C3;">&nbsp;&nbsp;&nbsp;&nbsp;You have
						no items in your shopping cart.</span>
				</c:if>
			</div>
		</div>
		<div class="box_bottom"></div>
	</div>
</div>