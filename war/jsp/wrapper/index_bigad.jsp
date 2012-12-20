<%@include file="../include.jsp" %>

<div class="index_bigad" id="index_bigad">
<c:if test='${isHome == "true"}'>
		<div class="measure_dashboard">
		<div class="measure_dashboard_content">
			<div class="measure_panel" style="display: none;">
				<a href="http://joybuy.co.uk/c/Pet-Supplies"><img width="972px" height="428px" src="/image/homebanner8.jpg" alt="Pet Supplies" title="Pet Supplies"></a>
			</div>
			<div class="measure_panel" style="position:relative">
				<a href="http://joybuy.co.uk/c/Prom-Dresses-2013"><img alt="Prom Dresses 2013" title="Prom Dresses 2013" width="972px" height="428px" src="/image/home-banner-1.jpg"></a>
			</div>
			<div class="measure_panel">
				<a href="http://joybuy.co.uk/c/Special-Occasion-Dresses"><img alt="Special Occasion Dresses" title="Special Occasion Dresses"  width="972px" height="428px" src="/image/home-dresses.jpg"></a>
			</div>
			<div class="measure_panel" style="position:relative">
				<img alt="Suits" title="Suits"  width="972px" height="428px" src="/image/home-suit.jpg">
				<div style="position:absolute; top:0; left:0; width:972px"><a href="http://joybuy.co.uk/c/Custom-Jackets" alt="Custom-Jackets" title="Custom-Jackets" style="width:65%; height:428px; display:block; float:left;"></a>
				<a href="http://joybuy.co.uk/c/Suits" alt="Suits" title="Suits" style="width:35%; height:428px; display:block; float:left;"></a></div>
			</div>
			<div class="measure_panel">
				<a href="http://joybuy.co.uk/c/Womens-Shoes"><img alt="Womens-Shoes" title="Womens-Shoes"  width="972px" height="428px" src="/image/home-shoe.jpg"></a>
			</div>
			<div class="measure_panel">
				<a href="http://joybuy.co.uk/c/Ties"><img alt="Ties" title="Ties"  width="972px" height="428px" src="/image/ties-banner.jpg"></a>
			</div>
<p class="clearBoth"></p>
		</div>
		<div class="measure_navigation">
			<div class="measure_controller">
	            <img class="pagination_active_m" src="/style/image/1x1.png" title="1">
				<img class="pagination_m" src="/style/image/1x1.png" title="2">
				<img class="pagination_m" src="/style/image/1x1.png" title="3">
				<img class="pagination_m" src="/style/image/1x1.png" title="4">
				<img class="pagination_m" src="/style/image/1x1.png" title="5">
				<img class="pagination_m" src="/style/image/1x1.png" title="6">
			</div>
		</div>
	</div>
	<script>
	var slider = jq(".measure_dashboard").slider().cycle();
</script>
</c:if>
<c:if test='${isHome != "true"}'>
		<c:out escapeXml="false" value="${pageForm.category.marketContent}"></c:out>
</c:if>
</div>