<div class="alert_thing_box" style="position: absolute;display:none;top: 100px;left: 200px;z-index: 100" id="alert_thing_box_customizedsize">
	<script type="text/javascript">
		function changeunit(value) {
			if (value == 'in')
				value = 'inch';

			for ( var i = 1; i < customnum; i++) {
				if ($('unit' + i)) {
					if (i == 2
							&& value == "cm"
							&& ($('unit' + i).innerHTML == "kg" || $('unit' + i).innerHTML == 'lb')) {
						$('unit' + i).innerHTML = 'kg';
					} else if (i == 2
							&& value == "inch"
							&& ($('unit' + i).innerHTML == "kg" || $('unit' + i).innerHTML == 'lb')) {
						$('unit' + i).innerHTML = 'lb';
					} else {
						$('unit' + i).innerHTML = value;
					}
				}
			}
		}
		//
	</script>
	<div class="alert_thing_bg">
		<div class="alert_thing_top">
			<h5>Custom Options</h5>
			<DIV
				style="FONT-WEIGHT: bold; FONT-SIZE: 12px; LEFT: 226px; COLOR: #9e5201; POSITION: absolute; TOP: 20px"></DIV>
		</div>
		<div class="alert_thing_main">
			<div id="custom1" style="display: none;"></div>
			<script language="javascript">
				var customnum = 8;
			</script>
			<div id="custom2">
				<div class="left_img">
					<img style="height: 350px;"
						src="../css/custom_dress.png" />
				</div>
				<div class="right_box">
					<table>
						<tr>
							<td colspan="2" align="right">Please select:<select
								name="units" onchange="changeunit(this.value);" id="customizeUnit"><option
										value="in">in</option>
									<option value="cm">cm</option>
							</select>
							</td>
						</tr>
						<tr>
							<td class="custItem">A Bust Size:</td>
							<td><input id="Customszie2" name="Bust Size"
								type="text" size="8"><span id="unit2">cm</span><span
								id="cussize2" style="display: none;"><img
									src="../css/unchecked.gif" />
							</span>
							</td>
						</tr>
						<tr>
							<td class="custItem">B Waist Size:</td>
							<td><input id="Customszie3" name="Waist Size"
								type="text" size="8"><span id="unit3">cm</span><span
								id="cussize3" style="display: none;"><img
									src="../css/unchecked.gif" />
							</span>
							</td>
						</tr>
						<tr>
							<td class="custItem">C Hip Size:</td>
							<td><input id="Customszie4" name="Hip Size"
								type="text" size="8"><span id="unit4">cm</span><span
								id="cussize4" style="display: none;"><img
									src="../css/unchecked.gif" />
							</span>
							</td>

						</tr>
						<tr>
							<td class="custItem">D Hollow to Hem:</td>
							<td><input id="Customszie5" name="Hollow2floor"
								type="text" size="8"><span id="unit5">cm</span><span
								id="cussize5" style="display: none;"><img
									src="../css/unchecked.gif" />
							</span>
							</td>
						</tr>
						<tr>
							<td colspan="2"><p>Usually we call this data Hollow to hem. Stand straight and measure from the hollow of your neck to the place where you want the hem of the dress to be. Record the length as your hollow to hem measurement. If your dress comes to the knee, your hollow to hem would be from the neck to your knee. If you are creating a floor length gown, your hollow to hem would be measured from your neck to the floor. Please add your shoes if you will wear high heels on the dress.</p></td>
						</tr>
						<tr>
							<td>Special Requirements:</td>
							<td>
								<textarea id="CustomszieSpecial" name="Special Req" rows="5" cols="20"></textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<script language="javascript">
				changeunit('in');
			</script>
		</div>
		<div class="alert_thing_sub">
			<input name="Submit" type="button" value="Submit" class="submit_7" id="alert_thing_box_customizedsize_submit" /> 
			<input name="Cancel" type="reset" value="Cancel" class="submit_7"  id="alert_thing_box_customizedsize_cancel" />

		</div>
	</div>
</div>
<!--custom-->
