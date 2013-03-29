<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div id="suitOpts">
	<div class="measure_dashboard" style="position: absolute;left: -650px;top:-50px;z-index: 302;float: left;background: #fff;border: 1px solid #bbb;display: none;">
		<div class="measure_dashboard_content">

			<div class="measure_panel">
				<div class="measure_sidebar">
					<h2>4</h2>
					<p>Notch lapels are the most widely used and most versatile. Peak lapels are for a touch of flair. Slim lapels are more fashion forward, noticeably slimmer than the average suit.</p>
					<p class="error red" id="JACKET_LAPELS_noti" style="color: #CC0000"></p>
				</div>
				<div class="measure_content">
						<ul class="img_opt four_img_item">
							<li>
								<img src="/style/image/peak.png" value="Peak" target="JACKET_LAPELS" checkableGroup="JACKET_LAPELS" >
								<h3 value="Peak" target="JACKET_LAPELS" checkableGroup="JACKET_LAPELS">Peak</h3>
							</li>
							<li>
								<img src="/style/image/notch.png" value="Notch" target="JACKET_LAPELS" checkableGroup="JACKET_LAPELS">
								<h3 value="Notch" target="JACKET_LAPELS" checkableGroup="JACKET_LAPELS">Notch</h3>
							</li>
							<li>
								<img src="/style/image/notch_slim.png" value="Notch (Slim)" target="JACKET_LAPELS" checkableGroup="JACKET_LAPELS">
								<h3 value="Notch (Slim)" target="JACKET_LAPELS" checkableGroup="JACKET_LAPELS">Notch (Slim)</h3>
							</li>
							<li>
								<img src="/style/image/shawl.png" value="Shawl" target="JACKET_LAPELS" checkableGroup="JACKET_LAPELS">
								<h3  value="Shawl" target="JACKET_LAPELS" checkableGroup="JACKET_LAPELS">Shawl</h3>
							</li> 
						</ul>
						<input type="hidden" value="" id="JACKET_LAPELS"  name="text@JACKET_LAPELS" checkRule="required" filedName="height" msg="Please select a JACKET LAPELS" msgField="JACKET_LAPELS_noti">
					<p class="clearBoth"></p>
				</div>
			</div>
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>JACKET VENTS</h2>
					<p>One and two vent options are interchangeable and a matter of personal preference. No vent jackets are reserved for tuxedos.</p>
					<p class="error red" id="JACKET_VENTS_noti" style="color: #CC0000"></p>
				</div>
				<div class="measure_content">
					<ul class="img_opt three_img_item">
							<li>
								<img src="/style/image/vents/none.png" value="None" target="JACKET_VENTS" checkableGroup="JACKET_VENTS">
								<h3 value="None" target="JACKET_VENTS" checkableGroup="JACKET_VENTS">None</h3>
							</li>
							<li>
								<img src="/style/image/vents/one.png" value="One" target="JACKET_VENTS" checkableGroup="JACKET_VENTS">
								<h3  value="One" target="JACKET_VENTS" checkableGroup="JACKET_VENTS">One</h3>
							</li>
							<li>
								<img src="/style/image/vents/two.png" value="Two" target="JACKET_VENTS" checkableGroup="JACKET_VENTS">
								<h3  value="Two" target="JACKET_VENTS" checkableGroup="JACKET_VENTS">Two</h3>
							</li>
						</ul>
						<input type="hidden" value="" id="JACKET_VENTS"  name="text@JACKET_VENTS" checkRule="required" filedName="height" msg="Please select a JACKET VENTS" msgField="JACKET_VENTS_noti">
					<p class="clearBoth"></p>
				</div>
			</div>
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>JACKET BUTTONS</h2>
					<p>Two button suits are often your best choice. One button suits are more trendy. Three button suits are a matter of preference and work well with a taller frame.</p>
					<p>Double-breasted jackets offer a bolder yet elegant look, and are recommended to be worn with a peak lapel.</p>
					
					<p class="error red" id="JACKET_BUTTONS_noti" style="color: #CC0000"></p>
				</div>
				<div class="measure_content">
					<ul class="img_opt four_img_item">
							<li>
								<img src="/style/image/btns/one.png" value="One" target="JACKET_BUTTONS" checkableGroup="JACKET_BUTTONS">
								<h3 value="One" target="JACKET_BUTTONS" checkableGroup="JACKET_BUTTONS">One</h3>
							</li>
							<li>
								<img src="/style/image/btns/two.png" value="Two" target="JACKET_BUTTONS" checkableGroup="JACKET_BUTTONS">
								<h3  value="Two" target="JACKET_BUTTONS" checkableGroup="JACKET_BUTTONS">Two</h3>
							</li>
							<li>
								<img src="/style/image/btns/three.png" value="Three" target="JACKET_BUTTONS" checkableGroup="JACKET_BUTTONS">
								<h3 value="Three" target="JACKET_BUTTONS" checkableGroup="JACKET_BUTTONS">Three</h3>
							</li>
							<li>
								<img src="/style/image/btns/double_breasted.png" value="Double Breasted" target="JACKET_BUTTONS" checkableGroup="JACKET_BUTTONS">
								<h3 value="Double Breasted" target="JACKET_BUTTONS" checkableGroup="JACKET_BUTTONS">Double Breasted</h3>
							</li>
						</ul>
						<input type="hidden" value="" id="JACKET_BUTTONS"  name="text@JACKET_BUTTONS" checkRule="required" filedName="height" msg="Please select a JACKET_BUTTONS" msgField="JACKET_BUTTONS_noti">
					<p class="clearBoth"></p>
				</div>
			</div>
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>JACKET LINING</h2>
					<p>The best way to add personality to your jacket is through the lining.</p>
					<img src="/style/image/j_lining_thumb.jpg">
					
					<p class="error red" id="JACKET_LINING_noti" style="color: #CC0000"></p>
				</div>
				<div class="measure_content">
					<ul class="img_opt eight_img_item">
							<li>
								<img class="small" src="/style/image/materials/300_brown_dots.jpg" value="Brown Dots" target="JACKET_LINING" checkableGroup="JACKET_LINING">
								<h3 value="Brown Dots" target="JACKET_LINING" checkableGroup="JACKET_LINING">Brown Dots</h3>
							</li>
							<li>
								<img class="small" src="/style/image/materials/300_gold_dots.jpg"  value="Gold Dots.jpg" target="JACKET_LINING" checkableGroup="JACKET_LINING">
								<h3 value="Gold Dots" target="JACKET_LINING" checkableGroup="JACKET_LINING">Gold Dots</h3>
							</li>
							<li>
								<img class="small" src="/style/image/materials/300_purple_dots.jpg"  value="Purple Dots" target="JACKET_LINING" checkableGroup="JACKET_LINING">
								<h3 value="Purple Dots" target="JACKET_LINING" checkableGroup="JACKET_LINING">Purple Dots</h3>
							</li>
							<li>
								<img class="small" src="/style/image/materials/black.png"  value="Black" target="JACKET_LINING" checkableGroup="JACKET_LINING">
								<h3 value="Black" target="JACKET_LINING" checkableGroup="JACKET_LINING">Black</h3>
							</li>
							<li>
								<img class="small" src="/style/image/materials/brown.png"  value="Brown" target="JACKET_LINING" checkableGroup="JACKET_LINING">
								<h3 value="Brown" target="JACKET_LINING" checkableGroup="JACKET_LINING">Brown</h3>
							</li>
							<li>
								<img class="small" src="/style/image/materials/dark_beige.png"  value="Dark Beige" target="JACKET_LINING" checkableGroup="JACKET_LINING">
								<h3 value="Dark Beige" target="JACKET_LINING" checkableGroup="JACKET_LINING">Dark Beige</h3>
							</li>
							<li>
								<img class="small" src="/style/image/materials/gold.png"  value="Gold" target="JACKET_LINING" checkableGroup="JACKET_LINING">
								<h3 value="Gold" target="JACKET_LINING" checkableGroup="JACKET_LINING">Gold</h3>
							</li>
							<li>
								<img class="small" src="/style/image/materials/medium_brown.png"  value="Medium Brown" target="JACKET_LINING" checkableGroup="JACKET_LINING">
								<h3 value="Medium Brown" target="JACKET_LINING" checkableGroup="JACKET_LINING">Medium Brown</h3>
							</li>
								<input type="hidden" value="" id="JACKET_LINING"  name="text@JACKET_LINING" checkRule="required" filedName="height" msg="Please select a JACKET_LINING" msgField="JACKET_LINING_noti">
						</ul>
					<p class="clearBoth"></p>
				</div>
			</div>
			
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>JACKET MONOGRAM</h2>
					<p>Make your jacket truly say something with the only 40 character monogramming in the world.</p>
					<img src="/style/image/j_lining_thumb.jpg">
					
					<p class="error red" id="JACKET_MONOGRAM_noti" style="color: #CC0000"></p>
				</div>
				<div class="measure_content">
					<div class="front_opt">
						<h3>TEXT</h3>
						<div class="front_sub_opt">
							<label>LINE1</label><input type="text" name="text@JACKET MONOGRAM LINE1" class="txt_input" checkRule="" filedName="height" msg="Please select a JACKET_MONOGRAM_FONT" msgField="JACKET_MONOGRAM_noti">
						</div>
						<div class="front_sub_opt">
							<label>LINE2</label><input type="text" class="txt_input"  name="text@JACKET MONOGRAM LINE2" class="txt_input" checkRule="" filedName="height" msg="Please select a JACKET_MONOGRAM_FONT" msgField="JACKET_MONOGRAM_noti">
						</div>
						<div>
							<div>
								<h3>FONT</h3>
								<img class="img_opt" src="/style/image/font/timesroman.png"  value="TimesRoman" target="JACKET_MONOGRAM_FONT" checkableGroup="JACKET_MONOGRAM_FONT">
								<img class="img_opt active" src="/style/image/font/brushscript.png"  value="BrushScript" target="JACKET_MONOGRAM_FONT" checkableGroup="JACKET_MONOGRAM_FONT">
								<input type="hidden"  id="JACKET_MONOGRAM_FONT"  name="text@JACKET_MONOGRAM_FONT" checkRule="" filedName="height" msg="Please select a JACKET_MONOGRAM_FONT" msgField="JACKET_MONOGRAM_noti">
							</div>
							<div>
								<h3>COLOR</h3>
								<img class="img_opt" src="/style/image/font/jacket-monogram-1.png"  title="White" alt="White" value="White" target="JACKET_MONOGRAM_COLOR" checkableGroup="JACKET_MONOGRAM_COLOR">
								<img class="img_opt" src="/style/image/font/jacket-monogram-2.png"  title="Silver Gray" alt="Silver Gray" value="Silver Gray" target="JACKET_MONOGRAM_COLOR" checkableGroup="JACKET_MONOGRAM_COLOR">
								<img class="img_opt" src="/style/image/font/jacket-monogram-3.png"  title="Blue" alt="Blue" value="Blue" target="JACKET_MONOGRAM_COLOR" checkableGroup="JACKET_MONOGRAM_COLOR">
								<input type="hidden"  id="JACKET_MONOGRAM_COLOR"  name="text@JACKET_MONOGRAM_COLOR" checkRule="" filedName="height" msg="Please select a JACKET_MONOGRAM_COLOR" msgField="JACKET_MONOGRAM_noti">
							</div>
						</div>
					</div>
					<p class="clearBoth"></p>
				</div>
			</div>
			
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>PANTS PLEATS</h2>
					<p>Joybuy always endorses pants without pleats (flat front). For the man preferring pleats, please choose so accordingly.</p>
					
					<p class="error red" id="PANTS_PLEATS_noti" style="color: #CC0000"></p>
				</div>
				<div class="measure_content">
					<ul class="img_opt three_img_item">
							<li>
								<img src="/style/image/pleats/none.png" value="None" target="PANTS_PLEATS" checkableGroup="PANTS_PLEATS">
								<h3 value="None" target="PANTS_PLEATS" checkableGroup="PANTS_PLEATS">None</h3>
							</li>
							<li>
								<img src="/style/image/pleats/one.png" value="One" target="PANTS_PLEATS" checkableGroup="PANTS_PLEATS">
								<h3 value="One" target="PANTS_PLEATS" checkableGroup="PANTS_PLEATS">One</h3>
							</li>
							<input type="hidden" value="" id="PANTS_PLEATS"  name="text@PANTS_PLEATS" checkRule="required" filedName="height" msg="Please select a PANTS_PLEATS" msgField="PANTS_PLEATS_noti">
						</ul>
					<p class="clearBoth"></p>
				</div>
			</div>
			<p class="clearBoth"></p>
		</div>
		<div class="measure_navigation">
			<script type="text/javascript">
			 function checkFields(){
				 return jq("#suitOpts").fieldCheck();
			 }
			</script>
			<div class="measure_buttons">
				<input type="button" class="button_01" value="<         PREVIOUS">
				<input type="button" class="button_01" value="NEXT             >">
				<input type="submit" id="nosubitem_addBag" class="button_01" value="FINISH           >" onclick="return checkFields()">
				<input type="button" class="button_01" value="Cancel">
			</div>
			<div class="measure_controller">
				<img class="pagination_active_m" src="/style/image/1x1.png" title="JACKET LAPELS">
				<img class="pagination_m" src="/style/image/1x1.png" title="JACKET VENTS">
				<img class="pagination_m" src="/style/image/1x1.png" title="JACKET BUTTONS">
				<img class="pagination_m" src="/style/image/1x1.png" title="JACKET LINING">
				<img class="pagination_m" src="/style/image/1x1.png" title="JACKET MONOGRAM">
				<img class="pagination_m" src="/style/image/1x1.png" title="PANTS PLEATS">
			</div>
		</div>
	</div>
</div>