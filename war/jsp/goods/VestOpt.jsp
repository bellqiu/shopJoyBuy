<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div id="suitOpts">
	<div class="measure_dashboard" style="position: absolute;left: -650px;top:-50px;z-index: 302;float: left;background: #fff;border: 1px solid #bbb;display: none;">
		<div class="measure_dashboard_content">

			<div class="measure_panel">
				<div class="measure_title">
					<h2>Vest Buttons</h2>
					<p class="error red" id="Vest_Buttons_noti" style="color: #CC0000"></p>
				</div>
				<div class="measure_content">
						<ul class="img_opt three_img_item_flat">
							<li>
								<img src="/style/image/peak.png" value="Three" target="Vest_Buttons" checkableGroup="Vest_Buttons" >
								<h3 value="Three" target="Vest_Buttons" checkableGroup="Vest_Buttons">Three</h3>
							</li>
							<li>
								<img src="/style/image/notch.png" value="Five" target="Vest_Buttons" checkableGroup="Vest_Buttons">
								<h3 value="Five" target="Vest_Buttons" checkableGroup="Vest_Buttons">Five</h3>
							</li>
							<li>
								<img src="/style/image/notch_slim.png" value="Seven" target="Vest_Buttons" checkableGroup="Vest_Buttons">
								<h3 value="Seven" target="Vest_Buttons" checkableGroup="Vest_Buttons">Seven</h3>
							</li>
						</ul>
						<input type="hidden" value="" id="Vest_Buttons"  name="text@Vest_Buttons" checkRule="required" filedName="height" msg="Please select a Vest Button style" msgField="Vest_Buttons_noti">
					<p class="clearBoth"></p>
				</div>
			</div>
			<div class="measure_panel" style="display: none">
				<div class="measure_title">
					<h2>Vest Lining</h2>
					<p class="error red" id="Vest_Lining_noti" style="color: #CC0000"></p>
				</div>
				<div class="measure_content">
					<ul class="img_opt six_img_item_flat">
							<li>
								<img  src="/style/image/materials/300_brown_dots.jpg" value="Brown Dots" target="Vest_Lining" checkableGroup="Vest_Lining">
								<h3 value="Brown Dots" target="Vest_Lining" checkableGroup="Vest_Lining">Brown Dots</h3>
							</li>
							<li>
								<img  src="/style/image/materials/300_gold_dots.jpg"  value="Gold Dots.jpg" target="Vest_Lining" checkableGroup="Vest_Lining">
								<h3 value="Gold Dots" target="Vest_Lining" checkableGroup="Vest_Lining">Gold Dots</h3>
							</li>
							<li>
								<img src="/style/image/materials/300_purple_dots.jpg"  value="Purple Dots" target="Vest_Lining" checkableGroup="Vest_Lining">
								<h3 value="Purple Dots" target="Vest_Lining" checkableGroup="Vest_Lining">Purple Dots</h3>
							</li>
							<li>
								<img  src="/style/image/materials/black.png"  value="Black" target="Vest_Lining" checkableGroup="Vest_Lining">
								<h3 value="Black" target="Vest_Lining" checkableGroup="Vest_Lining">Black</h3>
							</li>
							<li>
								<img src="/style/image/materials/brown.png"  value="Brown" target="Vest_Lining" checkableGroup="Vest_Lining">
								<h3 value="Brown" target="Vest_Lining" checkableGroup="Vest_Lining">Brown</h3>
							</li>
							<li>
								<img src="/style/image/materials/dark_beige.png"  value="Dark Beige" target="Vest_Lining" checkableGroup="Vest_Lining">
								<h3 value="Dark Beige" target="Vest_Lining" checkableGroup="Vest_Lining">Dark Beige</h3>
							</li>
						</ul>
						<input type="hidden" value="" id="Vest_Lining"  name="text@Vest_Lining" checkRule="required" filedName="height" msg="Please select a Vest Lining" msgField="Vest_Lining_noti">
					<p class="clearBoth"></p>
				</div>
			</div>
			
			<div class="measure_panel" style="display: none">
					<div class="measure_sidebar">
						<h2>Setup your measurement profile</h2>
						<p>Get started by filling in your height, weight, and age.</p>
					</div>
					<div class="measure_content">
						<p class="red"></p>
						<ul>
							<li><label>HEIGHT</label>
								<div class="measure_value">
									<input type="text" value="" name="measure_height" class="txt_input" checkRule="required,double" filedName="height" msg="Hight must be a number!" msgField="measure_height_noti">&nbsp;IN
								</div>
								<p class="clearBoth"></p></li>
	
							<li><label>WEIGHT</label>
								<div class="measure_value">
									<input type="text" class="txt_input"  value="" name="measure_weight" checkRule="required,double" filedName="height" msg="Weight must be a number!" msgField="measure_height_noti"> KG
								</div>
								<p class="clearBoth"></p></li>
	
	
							<li><label>AGE</label>
								<div class="measure_value">
									<input type="text" class="txt_input" value="" name="measure_age" checkRule="required,double" filedName="height" msg="Age must be a number!" msgField="measure_height_noti">
								</div>
								<p class="clearBoth"></p></li>
	
	
						</ul>
						<div class="red" id="measure_height_noti"></div>
						<p class="clearBoth"></p>
					</div>
			</div>
			
			
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>Chest Size</h2>
					<ol class="tips">
						<li>Measure around the widest part of your chest, usually around the nipples.</li>
						<li>Leave room for 1 finger.</li>
					</ol>
				</div>
				<div class="measure_field">
					<input type="text" class="measure_input" name="measure_chest_size" value="${(measurement.chestSize)!''}" checkRule="required,double" filedName="Chest Size" msg="Chest Size must be a number!" msgField="measure_chest_size_noti"><SPAN style="font-size: 22px;font-weight: bold;">&nbsp;IN </SPAN>
					<p id="measure_chest_size_noti" class="red"></p>
				</div>
				<div class="measure_content">
					<img src="/style/image/suit_measure_img/Chest-Size.jpg" alt="Suits measurement Chest Size" title="Suits measurement Chest Size">
					<p class="clearBoth"></p>
				</div>
			</div>
			
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>Jacket/Shirt Length</h2>
					<ol class="tips">
						<li>Pop Your Collar. Place the tape where the shoulder and neck seams meet..</li>
						<li>Measure straight down to the desired length, usually around the thumb joint. </li>
					</ol>
				</div>
				<div class="measure_field">
					<input type="text" class="measure_input"  value="${(measurement.jacketShirtLenght)!''}" name="jacket_shirt_length" checkRule="required,double" filedName="Jacket/Shirt Length" msg="Shirt Neck must be a number!" msgField="jacket_shirt_length_noti"><SPAN style="font-size: 22px;font-weight: bold;">&nbsp;IN </SPAN>
					<p id="jacket_shirt_length_noti" class="red"></p>
				</div>
				<div class="measure_content">
					<img src="/style/image/suit_measure_img/Jacket-Shirt-Length2.jpg" alt="Suits measurement Jacket or Shirt Length" title="Suits measurement Jacket or Shirt Length">
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