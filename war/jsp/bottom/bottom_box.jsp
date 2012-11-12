<div class="bottom">
<%--	<div class="content_box">
		<div class="left_box">
			<div class="hei10"></div>
		</div>
		
		<!--div class="float_left Light_gray2 fontbold shareus">SHARE US TO:<a href="#"><img src="http://www.mlo.me/image/endefault/share_logom.gif" alt="honeybuy" /></a> <a rel="nofollow" href="#"><img src="http://www.mlo.me/image/endefault/share_logot.gif" alt="twitter" /></a> <a rel="nofollow" href="#"><img src="http://www.mlo.me/image/endefault/share_logof.gif" alt="facebook" /></a> <a rel="nofollow" href="#"><img src="http://www.mlo.me/image/endefault/share_logoy.gif" alt="youtube" /></a></div-->
		<div class="float_left">
			<form onsubmit="return checksearch();" id="formss1" name="formss1" method="post" action="http://184.22.252.66/search">
				<div class="search_box">
					<input type="hidden" value="0" name="class"> <input
						type="text" autocomplete="off"
						class="search_text font_size11 Light_gray" value=""
						id="textfield0" name="textfield"> <input type="button"
						onclick="formss1.submit();" name="" class="search_button">
				</div>
			</form>
		</div>
	</div>

	<div class="hei10"></div>
	
	<div class="footermenu_box">
		<jsp:include page="bottom_item.jsp"></jsp:include>
		<script>jq(function(){jq('#bottom_ltem').footer_c();});</script>
		<div class="hei10"></div>
	</div>
	
	<div class="otherinfo_box">
		<jsp:include page="other_info.jsp"></jsp:include>
	</div>
	 --%>
	<div class="content_box">
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</div>