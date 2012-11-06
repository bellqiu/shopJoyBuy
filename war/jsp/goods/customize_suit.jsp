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
					<img style="height:350px;"
						src="../css/custom_suit.png" />
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
							<td class="custItem">A Chest Width:</td>
							<td><input id="Customszie1" name="Chest Width"
								type="text" size="8"><span id="unit1">cm</span><span
								id="cussize1" style="display: none;"><img
									src="../css/unchecked.gif" />
							</span>
							</td>
						</tr>
						<tr><td colspan="2">This measurement should be taken just under the arms, closely around the body.
						</td></tr>
						<tr>
							<td class="custItem">B Sleeve Size:</td>
							<td><input id="Customszie2" name="Sleeve Size"
								type="text" size="8"><span id="unit2">cm</span><span
								id="cussize2" style="display: none;"><img
									src="../css/unchecked.gif" />
							</span>
							</td>
						</tr>
						<tr><td colspan="2">Measure from your shoulder point to the wrist.</td></tr>
						<tr>
							<td class="custItem">C Shoulder Length:</td>
							<td><input id="Customszie3" name="Shoulder Size"
								type="text" size="8"><span id="unit3">cm</span><span
								id="cussize3" style="display: none;"><img
									src="../css/unchecked.gif" />
							</span>
							</td>
						</tr>
						<tr><td colspan="2">With both arms down by your side, measurement is taken from the tip of the left shoulder point, to the tip of the right shoulder.</td></tr>
						<tr>
							<td class="custItem">D Jacket Length:</td>
							<td><input id="Customszie4" name="Jacket Length"
								type="text" size="8"><span id="unit4">cm</span><span
								id="cussize4" style="display: none;"><img
									src="../css/unchecked.gif" />
							</span>
							</td>
						</tr>
						<tr><td colspan="2">Measure down the back from the base of the collar to the length required.</td></tr>
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
