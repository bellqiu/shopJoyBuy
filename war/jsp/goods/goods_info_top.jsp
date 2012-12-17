<%@ include file="../include.jsp"%>
<%@page import="com.spshop.utils.Constants"%>
<ss:ancestorCat ancester="ancester" categories="${pageForm.pageProperties.productDetail.categories}">
<c:set var="crossSaleImg">${ancester}.image</c:set>
<c:set var="crossSaleLink">${ancester}.link</c:set>
<div class="item_goods_info_box_top">
	<div class="item_box_left_normal">
		<c:if test="${pageForm.pageProperties.productDetail.categories[0].name ne 'Wedding-Accessories' && 
						(ancester eq 'Wedding-Apparel' || ancester eq 'Special-Occasion-Dresses')}">
		<jsp:include page="customize_dress.jsp"></jsp:include>
		</c:if>
		<c:if test="${(pageForm.pageProperties.productDetail.categories[0].name eq 'Business-Pants') || 
					  (pageForm.pageProperties.productDetail.categories[0].name eq 'Groom-Pants') ||
					  (pageForm.pageProperties.productDetail.categories[0].name eq 'Tuxedos-Pants')}">
		<jsp:include page="customize_pants.jsp"></jsp:include>
		</c:if>
		<c:if test="${(pageForm.pageProperties.productDetail.categories[0].name eq 'Business-Vests') || 
					  (pageForm.pageProperties.productDetail.categories[0].name eq 'Groom-Vests') ||
					  (pageForm.pageProperties.productDetail.categories[0].name eq 'Tuxedos-Vests')}">
		<jsp:include page="customize_suit.jsp"></jsp:include>
		</c:if>
		<c:if test="${(pageForm.pageProperties.productDetail.categories[0].name eq 'Business-Suits') || 
					  (pageForm.pageProperties.productDetail.categories[0].name eq 'Groom-Suits') ||
					  (pageForm.pageProperties.productDetail.categories[0].name eq 'Tuxedos-Suits') ||
					  (pageForm.pageProperties.productDetail.categories[0].name eq 'Womens-Suits')}">
		<jsp:include page="customize_suits.jsp"></jsp:include>
		</c:if>
		<!-- class="item_box_left" to high -->
		<div class="no_float">

			<!-- normal picture -->
			<div class="item_normal_pic" id="item_normal_pic">
				<c:forEach items="${pageForm.pageProperties.productDetail.images}"
						var="image" varStatus="idx" step="1">
						<c:if test="${idx.index eq 0}">
							<div class="item_normal_pic_box">
								<div></div>
										<a class="bighref" id="linkNormalBox" href="${image.noChangeUrl}"
											rel="thing_item_pics"> 
											<img src="../css/zoom_in.png"
												 id="zoomIcon"> 
											<c:if test='${image.strSizeType eq "PRODUCT_SQUARE"}'>
												<img style="width:277px; padding-top:${(370-277)/2}px;" val="${image.noChangeUrl}"
													 alt="${pageForm.pageProperties.productDetail.title}"
													 src="${image.largerUrl}" id="imageNormalBox">
											</c:if>
											<c:if test='${image.strSizeType eq "PRODUCT_NORMAL"}'>
												<img style="width:277px;" val="${image.noChangeUrl}"
													 alt="${pageForm.pageProperties.productDetail.title}"
													 src="${image.largerUrl}" id="imageNormalBox">
											</c:if>
										</a>
							</div>
						</c:if>
						<div style="display: none">
						
							<c:if test="${idx.index eq 0}">
								<a href="${image.noChangeUrl}" class="noneBox"></a>
							</c:if>
							<c:if test="${idx.index gt 0}">
								<a href="${image.noChangeUrl}" class="noneBox" rel="thing_item_pics"></a>
							</c:if>
						</div>
				</c:forEach>
				<!--<div class="item_normal_zoom"> <a href="###" class="link_pic_zoom bighref" target="_blank">Enlarge the Image</a> </div>-->
				<div class="item_normal_socllbar">
					<ul>
						<c:forEach items="${pageForm.pageProperties.productDetail.images}"
							var="image" varStatus="idx" step="1">
							<li dis="${idx.index}" class="smallPic"
								val="${image.largerUrl}">
								<c:if test='${image.strSizeType eq "PRODUCT_SQUARE"}'>
								<img style="width:46px; padding-top:${(63-46)/2}px;" alt="${pageForm.pageProperties.productDetail.title}"
									 src="${image.iconUrl}">
								</c:if>
								<c:if test='${image.strSizeType eq "PRODUCT_NORMAL"}'>
								<img style="width:46px;" alt="${pageForm.pageProperties.productDetail.title}"
									 src="${image.iconUrl}">
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>


			<!-- normal picture -->
		</div>
	</div>
	<!-- shopping function -->

		<div class=" item_shopping_fun">
			<div class="noFlow">
				<h1>
					<c:out value="${pageForm.pageProperties.productDetail.title}" />
				</h1>
			</div>
			<div class="item_shopping_code">Item
				Code:${pageForm.pageProperties.productDetail.id}</div>
			<div style="position: relative;" class="item_shopping_funbox">
				<table>
					<tbody>
						<c:forEach
							items="${pageForm.pageProperties.productDetail.properties}"
							var="property" varStatus="idx" step="1">
							<tr>
								<td width="104px"><c:out value="${property.name}" /> :</td>
								<td><c:out value="${property.value}" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="itme_description">
					<c:out
						value="${pageForm.pageProperties.productDetail.abstractText}" />
				</div>
			</div>
				<div id="divButtons"></div>
				<script>
					// Define UserAction onject
					var ua = new gigya.services.socialize.UserAction(); 
					ua.setLinkBack("<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/${pageForm.pageProperties.productDetail.name}"); 
					ua.setTitle("Enjoy Online Shopping For Fashion Dress, Apparel, Suits With Free Shipping - joybuy.co.uk");
					
					ua.addMediaItem( { type: "image", src: "<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}${pageForm.pageProperties.productDetail.images[0].largerUrl}", href: "<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/${pageForm.pageProperties.productDetail.name}" });
					// Define Share Bar plugin's Parameters	
					var shareBarParams ={ 
						userAction:ua,
						shareButtons: "facebook-like,share,stumbleupon,twitter-tweet,google-plusone,email",
						containerID: 'divButtons', // location of the Share Bar plugin
						showCounts: 'top'
					}
					// Load Share Bar plugin
					gigya.services.socialize.showShareBarUI({},shareBarParams);
				</script>
				<div class="hei10"></div>
				<span><label><b>Processing Time: </b>8-12 days for custom-made items, 1-3 Days for other</br><b>Shipping Time: </b>3-5 business days.
				<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/c/Shipping-Information">See Details...</a></label></span>
				<a href="${crossSales[crossSaleLink]}"> 
					<img style="width: 100%;" alt="Click and buy the this!" src="${crossSales[crossSaleImg]}">
				</a>
		</div>

		<!--custom-->
		<input type="hidden" value="yes" id="gm" name="gm">
		<div class="item_box_right">
			<div></div>
			<div class="item_property">
				<!-- item_property_show is alert-->
				<div class="item_goods_price_older">
					<s>
					${currency }
					 <fmt:formatNumber value="${pageForm.pageProperties.productDetail.price * currencies[currency]}" maxFractionDigits="2" currencyCode="${currency}"></fmt:formatNumber>
					</s>
				</div>
				<div style="z-index: 2;" class="item_price">
					<span class="item_price_currency">${currency }<a id="currencyAll"
						href="javascript:void(0);"><img src="/css/arrow-select.gif">
					</a> </span><span class="item_price_num"> <span id="money">
					
					<fmt:formatNumber value="${pageForm.pageProperties.productDetail.actualPrice * currencies[currency]}" maxFractionDigits="2" currencyCode="${currency}"></fmt:formatNumber></span>
					</span>
					
					<div class="currencyAll">
						<c:forEach items="${currencies }" var="currency">
							<a rel="nofollow" class="link_now" href="/${pageForm.pageProperties.productDetail.name}?currency=${currency.key}">${currency.key }</a>
						</c:forEach>
					</div>
				</div>
				<c:if test="${pageForm.pageProperties.productDetail.optType <1 }">
				<c:forEach items="${pageForm.pageProperties.productDetail.options}"
					var="option" varStatus="idx">
						<c:if test='${(option.strSelectType eq "COLOR_SINGLE") and (!empty option.items) }'>
						<div class="item_colorBox">
							<div class="item_ProBox_title">
								<span><c:out value="${option.name}" />:</span><br>
								<p style="color: #999">If more than one color style,
Please DO NOT use "the same as picture" option.</p>
							</div>
							<input type="hidden"
								name="color@${option.name}"
								id="goodColor" value="">
								<select id="productColorSelector">
									<option value="">Please select...</option>
									<option value="The Same As Picture##asp">The Same As Picture</option>
									<option value="other">Select Other</option>
								</select>
								
								<div class="colorSelectWindow" style="display: none;">
									<img alt="" src="/css/button.png" style="width: 0px; height: 0px">
									<div class="colorWindow-large">
										<c:forEach items="${option.items}" begin="0" end="0" var="item">
											<img alt="${item.name}" src="${fn:endsWith(item.value, '.jpg')?item.value:''}">
										</c:forEach>
										<table align="center" width="90%">
											
											<tr align="center">
												<td align="center"><span id="colorWindow-largeDESC" style="height: 20px"></span><br></td>
											</tr>
											<tr align="center">
												<td>
													<button type="button" op="ok" style="background: url('/css/button.png') no-repeat scroll 0 0 transparent;" title="OK">OK</button>
													<button type="button" op="close" style="background: url('/css/button.png') no-repeat scroll 0 0 transparent;" title="Close">Close</button>
												</td>
											</tr>
										</table>
									</div>
									
									<div class="colorWindow-list">
									
										<c:forEach items="${option.items}" var="item">
											<c:if test="${fn:endsWith(item.value, '.jpg')}">
												<img alt="${item.name}" src="${item.value}" title="${item.name}">
											</c:if>
										</c:forEach>
									</div>
									<script type="text/javascript">
										jq("#goodColor").val("");
										jq("#productColorSelector").change(function(){
											if(jq("#productColorSelector").val()=="other"){
												jq(".colorSelectWindow").show();
											}else{
												var templete = "<table><tr><td>Color: "+name+"</td></tr><tr><td>The Same As Picture</td></tr></table>";
												jq("#CustomizedColorDesp").html(templete);
												jq("#CustomizedColorDesp").show();
												jq("#goodColor").val("The Same As Picture##ASP");
											}
										});
									
										jq(".colorSelectWindow .colorWindow-list img").each(function(index, item){
											jq(item).click(function(){
												jq(".colorSelectWindow .colorWindow-list img").removeClass("color-selected");
												jq(this).addClass("color-selected");
												jq(".colorSelectWindow .colorWindow-large img").attr("src",jq(this).attr("src"));
												jq(".colorSelectWindow .colorWindow-large img").attr("title",jq(this).attr("title"));
												jq(".colorSelectWindow .colorWindow-large img").attr("alt",jq(this).attr("alt"));
												jq("#colorWindow-largeDESC").html(jq(this).attr("title"));
												
											});
										});
										
										jq(".colorSelectWindow .colorWindow-large button[op='ok']").click(function(){
											
											var selectedImg = jq(".colorSelectWindow .colorWindow-list .color-selected");
											
											var value = selectedImg.attr("src");
											var name = selectedImg.attr("title");
											
											if(value && name){
												jq("#goodColor").val(name+"##"+value);
												var templete = "<table><tr><td>Color: "+name+"</td></tr><tr><td><img style='width:36px;height:36px' alt='' src='"+value+"'></td></tr></table>";
												jq("#CustomizedColorDesp").html(templete);
												jq("#CustomizedColorDesp").show();
											}else{
												window.alert("Please choose a color!");
											}
											
											jq(".colorSelectWindow").hide();
										});
										
										jq(".colorSelectWindow .colorWindow-large button[op='close']").click(function(){
											jq(".colorSelectWindow").hide();
										});
									</script>
								</div>
							<%-- <c:forEach items="${option.items}" var="item" varStatus="indx"
								step="1">
								<a dataname="Color0" data="color${indx.index}"
									value="${item.value}" href="javascript:void(0);"
									title="${item.altTitle}" class="colorLink"
									style="border: 1px solid rgb(139, 33, 4);">
									<div
										<c:if test='${option.defaultValue eq item.value}'>class="abPosition selectImg" </c:if>
										class="abPosition"></div>
									<div class="select_${item.name}"
										style="border-color:${item.value}"></div> </a>
							</c:forEach>
							--%>
						</div>
					</c:if>
			</c:forEach>
			
				<c:forEach items="${pageForm.pageProperties.productDetail.options}"
					var="option" varStatus="idx">
						<c:if test='${option.strSelectType eq "INPUT_TEXT"}'>
							<c:if test='${!(option.name eq "Qty")}'>
								<div class="noFlow">
								<input type="text" name="text@${option.name}" id="<c:out value="${option.id}" />"
										value="<c:out value="${option.defaultValue}" />" size="5"
										maxlength="4" class="input_1">
								<div class="item_funTotal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
								</div>
							</c:if>
					</c:if>
					<c:if test='${option.strSelectType eq "SINGLE_LIST"}'>
						<div class="item_sizeBox">
							<div class="item_ProBox_title">
								<span><c:out value="${option.name}" />:</span>
							</div>
							<select
								name="text@${option.name}"
								id="Size0">
								<option value="">Please select</option>
								<c:forEach items="${option.items}" var="item" varStatus="indx"
									step="1">
									<c:if test="${!(item.value eq 'Customized')}">
										<option value="${item.value}"
											<c:if test="${item.value eq option.defaultValue}">selected="selected"</c:if>>${item.name}</option>
									</c:if>
								</c:forEach>
								<c:forEach items="${option.items}" var="item" varStatus="indx"
									step="1">
									<c:if test="${item.value eq 'Customized'}">
										<option value="${item.value}"
											<c:if test="${item.value eq option.defaultValue}">selected="selected"</c:if>>${item.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</c:if>
					<c:if test='${option.strSelectType eq "MULTI_LIST"}'>
						<div class="item_sizeBox">
							<div class="item_ProBox_title">
								<span><c:out value="${option.name}" />:</span><a
									onclick="tab_click(2);"
									href="javascript:jq.goDiv('#tab_middle');"
									class="item_funLink size_chart">Size Chart</a>
							</div>
							<select name="texts@${option.name}"
								id="Size0" MULTIPLE>
								<option value="please">Please select</option>
								<c:forEach items="${option.items}" var="item" varStatus="indx"
									step="1">
									<option value="${item.value}"
										<c:if test="${fn:contains(option.defaultValue, item.value)}">selected="selected"</c:if>>${item.value}</option>
								</c:forEach>
							</select>
						</div>
					</c:if>
				</c:forEach>
				
				<c:forEach items="${pageForm.pageProperties.productDetail.options}"
					var="option" varStatus="idx">
						
							<c:if test='${option.name eq "Qty"}'>
								<div class="noFlow">
									<c:out value="${option.name}" />: 
									<c:if test="${option.defaultValue eq null}">
										<input type="text" name="qty" id="num"
											value="1" size="5" maxlength="4" class="input_1"
											onblur="javascript:if(!Boolean(this.value))  this.value=1;if(parseInt(this.value)===0)this.value=1;this.value=parseInt(this.value,10);if(this.value>9999)this.value=9999;"
											onkeyup="value=value.replace(/[^\d]/g,'');ChangePrice();">
									</c:if>
									<div class="item_funTotal" href="javascript:void(0);">
										<input type="hidden" id="product_inputText_price" name="qty" value="${pageForm.pageProperties.productDetail.actualPrice * currencies[currency]}" />
										<label id="AmountPrice3">Total: <span>${currency}
										<fmt:formatNumber value="${pageForm.pageProperties.productDetail.actualPrice * currencies[currency]}" maxFractionDigits="2" currencyCode="${currency }"></fmt:formatNumber>
										</span></label>
									</div>
									</div>
							</c:if>
						
						</c:forEach>
					</c:if>
				<input type="hidden"
						value="${pageForm.pageProperties.productDetail.name}"
						name="ProductId">
				<input type="hidden" id="CustomizedHidden">
				<input type="hidden"
						name="operation"
						value="addItem">
				<!--musictagstock start-->
				<div style="color: #F33">
					<i id="StocksInfo"></i>
				</div>
				<!--musictagstock end-->

				<%-- <p class="noFlow">
					<a onclick="tab_click(4)" class="item_funHelp"
						href="javascript:jq.goDiv('#tab_middle');">Delivery</a>
				</p>--%>
			</div>
			<script>
				var toutaoPrice =0; 
				var xiaoshu=1; 
				jq("select").change( function() {
					ChangePrice();	
				});
				jq(".colorLink").click(function(){
						jq("#"+jq(this).attr('dataName')).val(jq(this).attr('data'));
						var dataName = jq(this).attr('dataName');
						jq(".colorLink[dataName='"+dataName+"']").css("border","1px solid #717171");
						jq(".colorLink[dataName='"+dataName+"'] div").removeClass('selectImg');
						jq(this).find("div:first").addClass('selectImg');
						jq(this).css("border","1px solid #8b2104"); 
						$('goodColor').value = jq(this).attr('value');
				});
				jq(".item_colorBox").each(function(){ if(jq(this).find(".colorLink").size()==1){jq(this).find(".colorLink:first").click();}});
				function ChangePrice(){
					if($('num').value!=0&&$('num').value!=""){
						ProductsPrice = $('product_inputText_price').value;
								if(iscustom=='custom'){
							ProductsPrice =parseFloat(ProductsPrice)+parseFloat(CustomPrices);
						}
						
						ProductsPrice =parseFloat(ProductsPrice)+parseFloat(toutaoPrice);
								
						$('money').innerHTML=(parseFloat(ProductsPrice)).toFixed(xiaoshu);
						$('AmountPrice3').innerHTML="Total: <span>${currency} <span>"+((parseFloat(ProductsPrice)).toFixed(xiaoshu)*$('num').value).toFixed(xiaoshu)+"</span></span>";
					}	
				}
		</script>
		<div id="CustomizedSizeDesp" style="display: none;padding: 4px;">
			
		</div>
		<div id="CustomizedColorDesp" style="display: none;padding: 4px;">
			
		</div>
		<ul style="display: none;" id="choosePro" class="choosePro">
		</ul>
			<c:if test='${pageForm.pageProperties.displayOrderItem == null}'>
				<div class="addtocart">
					<c:if test="${pageForm.pageProperties.productDetail.optType <1 }">
						<input type="submit" style="display: none;" id="sub"
						value="ADD TO MY BAG" class="item_addBag"> <input
						type="submit" id="nosubitem_addBag" value="ADD TO MY BAG" class="item_addBag">
							<script type="text/javascript">
							jq("#nosubitem_addBag").click(function(){
								//jq("#nosubitem_addBag").attr("disabled","disabled");
								jq("#cusform").submit();
								
							});
						</script>
					</c:if>
					<c:if test="${pageForm.pageProperties.productDetail.optType == 1 }">
						
						<jsp:include page="suitOpt.jsp"></jsp:include>
									
							<input type="submit" style="display: none;" id="sub"
							value="ADD TO MY BAG" class="item_addBag"> <input
							type="submit" id="nosubitem_addBag_customize" value="Customize" class="item_addBag">
							<script type="text/javascript">
								jq("#nosubitem_addBag_customize").click(function(){
									//jq.documentMask(); 
									jq.documentMask({ 
									'opacity': 0.6, 
									'bgcolor': '#000000', 
									'z': 301 
									}); 
									jq(".measure_dashboard").slider();
									return false;
									//jq("#nosubitem_addBag").attr("disabled","disabled");
									//jq("#cusform").submit();
									
								});
							</script>
					</c:if>
					
					<c:if test="${pageForm.pageProperties.productDetail.optType == 2 }">
						
						<jsp:include page="suitPants.jsp"></jsp:include>
									
							<input type="submit" style="display: none;" id="sub"
							value="ADD TO MY BAG" class="item_addBag"> <input
							type="submit" id="nosubitem_addBag_customize" value="Customize" class="item_addBag">
							<script type="text/javascript">
								jq("#nosubitem_addBag_customize").click(function(){
									//jq.documentMask(); 
									jq.documentMask({ 
									'opacity': 0.6, 
									'bgcolor': '#000000', 
									'z': 301 
									}); 
									jq(".measure_dashboard").slider();
									return false;
									//jq("#nosubitem_addBag").attr("disabled","disabled");
									//jq("#cusform").submit();
									
								});
							</script>
					</c:if>
					
					<c:if test="${pageForm.pageProperties.productDetail.optType == 3 }">
						
						<jsp:include page="VestOpt.jsp"></jsp:include>
									
							<input type="submit" style="display: none;" id="sub"
							value="ADD TO MY BAG" class="item_addBag"> <input
							type="submit" id="nosubitem_addBag_customize" value="Customize" class="item_addBag">
							<script type="text/javascript">
								jq("#nosubitem_addBag_customize").click(function(){
									//jq.documentMask(); 
									jq.documentMask({ 
									'opacity': 0.6, 
									'bgcolor': '#000000', 
									'z': 301 
									}); 
									jq(".measure_dashboard").slider();
									return false;
									//jq("#nosubitem_addBag").attr("disabled","disabled");
									//jq("#cusform").submit();
									
								});
							</script>
					</c:if>
					
					<c:if test="${pageForm.pageProperties.productDetail.optType == 4 }">
						
						<jsp:include page="woman_suit.jsp"></jsp:include>
									
							<input type="submit" style="display: none;" id="sub"
							value="ADD TO MY BAG" class="item_addBag"> <input
							type="submit" id="nosubitem_addBag_customize" value="Customize" class="item_addBag">
							<script type="text/javascript">
								jq("#nosubitem_addBag_customize").click(function(){
									//jq.documentMask(); 
									jq.documentMask({ 
									'opacity': 0.6, 
									'bgcolor': '#000000', 
									'z': 301 
									}); 
									jq(".measure_dashboard").slider();
									return false;
									//jq("#nosubitem_addBag").attr("disabled","disabled");
									//jq("#cusform").submit();
									
								});
							</script>
					</c:if>
					
					<%--<div class="sub_outDiv_normal" style="display: none;"
						id="sub_outDiv">
						<div id="notselect_tips" class="notselect_tips">
							<div class="tips_title">PLEASE SELECT</div>
							<ul id="nochoose"></ul>
						</div>
						<div style="" id="notselect_tips1" class="tip_arrow">&nbsp;</div>
					</div> --%>
				</div>
				<%--<a onclick="favorite(${pageForm.pageProperties.productDetail.id})"
					id="favorite_${pageForm.pageProperties.productDetail.id}"
					class="item_potionsFavorite" href="javascript:void(0);">Add to
					my Favorite </a> --%>
			</c:if>
			<div class="item_funWords">
				<div id="favorite" style="display: none;" class="details_l"></div>
			</div>
			<div style="text-align: center;">
				<div style="margin-left:25px">
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
					<h3 style="background-image: url('/css/guarantee-seal.png');" class="linkHead">
					<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/c/Guarantee"><em>QUALITY GUARANTEE</em></a>
				</h3>
				<h3 style="background-image: url('/css/returnPolicy.png');" class="linkHead">
					<a href="<%=Constants.HTTP_PROTOCOL %>${pageForm.site.domain}/c/Return-Exchange"><em>RETURN POLICY</em></a>
				</h3>
			</div>
		</div>
	<div id='musicwhp_backgroundDiv' style="display: none;"></div>
	<script type="text/javascript" src="../js/thing_item.js"></script>

</div>
</ss:ancestorCat>