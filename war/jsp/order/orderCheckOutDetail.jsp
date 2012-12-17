<%@ include file="../include.jsp"%>
<div id="container">
	<button class="button_orange left" type="button"
		onclick="location.href='http://www.joybuy.co.uk/'">Continue
		Shopping</button>
	<div id="right_column">
		<p id="order_title">Let me pay it now!</p>
		<div id="check_box">
			<p>
				TOTAL: <span>
						
							${currency }<fmt:formatNumber value="${pageForm.pageProperties.orderDetail.totalPrice * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber>
				</span>
			</p>

			<input type="button" value="PAY"
				onclick="javascript:window.location.href='pay.do'">

		</div>
		<div id="safe_box">
			<ul class="bankList">
				<li class=""><a target="_blank" 
					href="#"
					> <img val="bank1" class="bankIcon"
									src="../../css/bank1.jpg"
									>
				</a><input type="radio" ></li>
				<li class=""><a target="_blank" 
					href="#"
					> <img val="bank1" class="bankIcon"
									src="../../css/bank1.jpg"
									>
				</a><input type="radio" ></li>
			</ul>
		</div>
	</div>
	<div id="left_column">
		<form action="http://www.joybuy.co.uk/shop/Cart.html" method="post"
			id="cart_form">
			<input name="act" id="act" type="hidden" value="edit">
			<div class="cart_box">
				<p class="cart_title">
					<strong>Shopping Cart</strong>
				</p>
				<table id="shopping_list" cellspacing="0">
					<thead>
						<tr id="shopping_list_title">
							<th id="title_first">items</th>
							<th>Item Price</th>
							<th>Qty</th>
							<th>Price</th>
							<th>Status</th>
						</tr>

					</thead>
					<tbody>
						<c:forEach items="${pageForm.pageProperties.orderDetail.items}"
							var="item" varStatus="itemIdx" step="1">
							<tr>
								<td><a
									href="${item.product.productUrl}?itemIndex=${itemIdx.index}"
									target="_blank"> <c:forEach items="${item.product.images}"
											var="image" varStatus="idx" step="1">
											<c:if test="${idx.index eq 0}">
												<img val="${image.iconUrl}" src="${image.iconUrl}"
													class="left MR10">
								</a> <span> <a
										href="${item.product.productUrl}?itemIndex=${itemIdx.index}"
										target="_blank"><strong>${item.product.title}</strong> </a>
								</span> </c:if>
						</c:forEach>
						</td>
						<td class="center red"><c:out value="" />
						
						${currency }<fmt:formatNumber value="${item.product.price * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber>
						
						</td>
						<td class="center"><c:out value="${item.quantity}" /></td>
						<td class="center red">	${currency }<fmt:formatNumber value="${item.product.finalPrice * currencies[currency]}" currencyCode="${currency }" maxFractionDigits="2"></fmt:formatNumber><br>
						</td>
						<td class="center red"><c:out value="${pageForm.pageProperties.orderDetail.status.status}" /><br>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>

	</div>
</div>