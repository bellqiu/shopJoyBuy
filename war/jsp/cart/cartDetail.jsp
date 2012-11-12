<%@ include file="../include.jsp" %>
<%@page import="com.spshop.utils.AllConstants"%>
<div id="container">
	<button class="button_orange left" type="button"
		onclick="location.href='<%= AllConstants.HTTP_PROTOCOL %>${pageForm.site.domain}'">Continue
		Shopping</button>
	<div id="right_column">
		<p id="order_title">Let me order now!</p>
		<div id="check_box">
			<p>
				TOTAL: <span>${shoppingcart.order.currency } 
					<fmt:formatNumber value="${shoppingcart.order.totalPrice * currencies[currency] }" maxFractionDigits="2" currencyCode="${currency }"></fmt:formatNumber>					
				</span>
				<script type="text/javascript">
					var totalPrice = ${shoppingcart.order.totalPrice * currencies[currency] };
				</script>
			</p>
			<form action="/shoppingCart" method="post">
				<input type="hidden" name="operation" value="goToCheck">
				<input type="submit" value="CHECKOUT">
			</form>
		</div>
		<div id="safe_box">
			<table width="135" border="0" cellpadding="2" cellspacing="0"
				title="Click to Verify - This site chose VeriSign Trust Seal to promote trust online with consumers.">
				<tr>
					<td width="135" align="center" valign="top"><script
							type="text/javascript"
							src="https://seal.verisign.com/getseal?host_name=184.22.252.66&amp;size=L&amp;use_flash=YES&amp;use_transparent=YES&amp;lang=en"></script><br />
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="left_column">
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
							<th></th>
						</tr>

					</thead>
					<tbody>
					<c:forEach items="${shoppingcart.order.items}" var="item" >
						<tr>
						<c:forEach items="${item.product.images}" var="image" end="0">
							<td valign="top">
								<div style="float: left;">
								<input type="hidden" name="itemName" value="${item.name }">
								<a href="/${item.product.name}"
									target="_blank">
									<img src="${image.iconUrl}"
										class="left MR10">
										</a>
								</div>
								<div style="float: left;margin-left: 10px;width: 300px">
									<span>
										<a href="/${item.product.name}" 
											target="_blank"><strong>${item.product.title}</strong>
										</a>
									</span>
									<%-- 
									<c:forEach items="${item.userOptions}" var="opt">
										<c:if test="${opt.name eq 'Color' }">
											<div style="padding: 3px">
												<img alt='${fn:split(opt.value,"##")[0] }' src='${fn:split(opt.value,"##")[1] }' title='${fn:split(opt.value,"##")[0] }' width="24" height="24">
											</div>
										</c:if>
									</c:forEach>
									--%>
									<c:forEach items="${item.userOptions}" var="opt">
										<div class="cartOptionsDashboard_Content" style="color: #ff0000;"> 
											<c:choose>
											<c:when test="${opt.name eq 'Color' }">
												<span>${opt.name } :</span>
												<c:choose>
													<c:when test='${opt.value eq "The Same As Picture" || fn:split(opt.value,"##")[1] eq "ASP"}'>
														The Same As Picture
													</c:when>
													<c:otherwise>
														<img alt='${fn:split(opt.value,"##")[0] }' src='${fn:split(opt.value,"##")[1] }' title='${fn:split(opt.value,"##")[0] }' width="18" height="18">
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<span>${opt.name } :</span>
												<span>${opt.value}</span>
											</c:otherwise>
											</c:choose>
										</div>
									</c:forEach>
								</div>
							</td>
						</c:forEach>
							<td class="center red">
							${currency }
								<fmt:formatNumber value="${item.finalPrice*currencies[currency]}" maxFractionDigits="2" currencyCode="${currency }"></fmt:formatNumber>
							</td>
							<td class="center">
								<form action="/shoppingCart" method="post" id="${item.name}">
									<input name="ProductsId[2]" type="hidden"
									value="119012"><input name="qty" type="text" value="${item.quantity}"
									id="qty${itemIdx.index}"
									class="input_num"> 
									<a href="#" class="link_remove" onclick="javascript:return updateShoppingItem('${item.name}')">Update</a>
										<div style="display: none;">
												<input name="operation" value="updateItem">
												<input name="itemName" value="${item.name}">
										</div>
								</form>
							</td>
							<td class="center red">
								<%-- 
								<a class="link_remove showCartOptions">Show Detail</a><br>
								
								<div style="position: absolute;" class="cartOptionsDashboard">
									
								</div>--%>
								<fmt:formatNumber value="${item.itemTotalPrice*currencies[currency]}" maxFractionDigits="2" currencyCode="${currency }"></fmt:formatNumber>
								<br> <a
								href="#" onclick="javascript:return removeItem('${item.name}_remove')"
								class="link_remove">Remove</a>
								<div style="display: none;">
									<form action="/shoppingCart" method="post" id="${item.name}_remove">
											<input name="operation" value="removeItem">
											<input name="itemName" value="${item.name}">
									</form>
								</div>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
</div>