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
						<ul class="img_opt two_img_item_flat">
							<li>
								<img src="/style/image/peak.png" value="Three" target="Vest_Buttons" checkableGroup="Vest_Buttons" >
								<h3 value="Three" target="Vest_Buttons" checkableGroup="Vest_Buttons">Three</h3>
							</li>
							<li>
								<img src="/style/image/notch.png" value="Five" target="Vest_Buttons" checkableGroup="Vest_Buttons">
								<h3 value="Five" target="Vest_Buttons" checkableGroup="Vest_Buttons">Five</h3>
							</li>
						</ul>
						<input type="hidden" value="" id="Vest_Buttons"  name="text@Vest_Buttons" checkRule="required" filedName="height" msg="Please select a Vest Button style" msgField="Vest_Buttons_noti">
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
					<h2>Thigh Size</h2>
					<ol class="tips">
						<li>Starting at the top of your inseam, measure around your thigh.</li>
						<li>Measure snug with room for one finger.</li>
					</ol>
				</div>
				<div class="measure_field">
					<input type="text" class="measure_input" name="measure_thigh_size" value="${(measurement.thighSize)!''}" checkRule="required,double" filedName="Thigh Size" msg="Thigh Size must be a number!" msgField="measure_thigh_size_noti"><SPAN style="font-size: 22px;font-weight: bold;">&nbsp;IN </SPAN>
					<p id="measure_thigh_size_noti" class="red"></p>
				</div>
				<div class="measure_content">
					<img src="/style/image/suit_measure_img/Thigh-Size.jpg" alt="Suits measurement Thigh Size" title="Suits measurement Thigh Size">
					<p class="clearBoth"></p>
				</div>
			</div>
			
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>Pants Length</h2>
					<ol class="tips">
						<li>Start from the top of the pants' waistband.</li>
						<li>Measure along the side pants seam to the bottom of your pants or roughly 1 to 1.5 inches from the ground.</li>
					</ol>
				</div>
				<div class="measure_field">
					<input type="text" class="measure_input" name="measure_pants_length"  value="${(measurement.pantsLength)!''}"  checkRule="required,double" filedName="Pants Length" msg="Pants Length must be a number!" msgField="measure_pants_length_noti"><SPAN style="font-size: 22px;font-weight: bold;">&nbsp;IN </SPAN>
					<p id="measure_pants_length_noti" class="red"></p>
				</div>
				<div class="measure_content">
					<img src="/style/image/suit_measure_img/Pants-Length2.jpg" alt="Suits measurement Pants Length" title="Suits measurement Pants Length">
					<p class="clearBoth"></p>
				</div>
			</div>
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>Waist</h2>
					<ol class="tips">
						<li>Have the tape follow the top of your pants the whole way around.</li>
						<li>Imagine the tape is your actual pants' waist and adjust to your desired snugness.</li>
					</ol>
				</div>
				<div class="measure_field">
					<input type="text" class="measure_input" name="measure_waist" value="${(measurement.waist)!''}" checkRule="required,double" filedName="Waist" msg="Waist must be a number!" msgField="measure_waist_noti"><SPAN style="font-size: 22px;font-weight: bold;">&nbsp;IN </SPAN>
					<p id="measure_waist_noti" class="red"></p>
				</div>
				<div class="measure_content">
					<img src="/style/image/suit_measure_img/Waist.jpg" alt="Suits measurement Waist" title="Suits measurement Waist">
					<p class="clearBoth"></p>
				</div>
			</div>
			
			
			
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>Crotch</h2>
					<ol class="tips">
						<li>Place the tape at the middle of your waist.</li>
						<li>Follow the crotch seam through your legs, up to the front of the pants..</li>
					</ol>
				</div>
				<div class="measure_field">
					<input type="text" class="measure_input" name="measure_crotch" value="${(measurement.crotch)!''}" checkRule="required,double" filedName="Crotch" msg="Crotch must be a number!" msgField="measure_crotch_noti"><SPAN style="font-size: 22px;font-weight: bold;">&nbsp;IN </SPAN>
					<p id="measure_crotch_noti" class="red"></p>
				</div>
				<div class="measure_content">
					<img src="/style/image/suit_measure_img/Crotch.jpg" alt="Suits measurement Crotch" title="Suits measurement Crotch">
					<p class="clearBoth"></p>
				</div>
			</div>
			<div class="measure_panel" style="display: none">
				<div class="measure_sidebar">
					<h2>Knee Size</h2>
					<ol class="tips">
						<li>Measure around your knee cap, snug, with room for one finger.</li>
					</ol>
				</div>
				<div class="measure_field">
					<input type="text" class="measure_input" name="measure_knee_size" value="${(measurement.kneeSize)!''}"  checkRule="required,double" filedName="Knee Size" msg="Knee Size must be a number!" msgField="measure_knee_size_noti"><SPAN style="font-size: 22px;font-weight: bold;">&nbsp;IN </SPAN>
					<p id="measure_knee_size_noti" class="red"></p>
				</div>
				<div class="measure_content">
					<img src="/style/image/suit_measure_img/Knee-Size.jpg" alt="Suits measurement Knee Size" title="Suits measurement Knee Size">
					<p class="clearBoth"></p>
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