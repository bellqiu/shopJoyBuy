<style>
<!--
#newsLetter {
	width: 100%;
}

#newsLetter h3 {
	height: 50px;
	padding: 0 0 0 70px;
	font-family: Arial;
	font-size: 14px;
	background-repeat: no-repeat;
	background-image:
		url("/css/ico_small.jpg");
}

#newsLetter em {
	color: #A72D2C;
	display: block;
	font-weight: bold;
}

#newsLetter input {
	border: 1px solid #B52829;
	color: #666666;
	height: 14px;
	margin-right: 5px;
	padding: 5px;
	width: 140px;
}

.textbtnGray {
	background-position: 0 -90px;
	border-color: #BBBBBB;
	cursor: pointer;
	padding: 4px 10px;
	margin-top: 10px;
}

.col {
	float: left;
	vertical-align: top;
	height: 160px;
}

fieldset {
	padding: 10px;
	height: 130px;
}
-->
</style>
<div class="col" id="newsLetter">
	<form method="get" action="/storeEmail" id="newsletter_sub_form">
		<input type="hidden" value="product_info" name="url">
		<fieldset>
			<h3>
				<em>$50,000 GIVEAWAY</em>Newsletter
			</h3>
			<br />
			
			<input type="text" value="Your Email Address" size="20"
				id="subscribeEmail" name="subscribeEmail">
			<br/>
			<button id="newsletter_submit" class="textbtnGray">Subscribe</button>
			
			<h2 id="feedbackMsg" style="display: none;">Thank you for subscribing us!</h2>
		</fieldset>
	</form>
</div>
<script>

jq(document).ready(function(){
	jq("#subscribeEmail").bind("focus", function(){
		if(this.value == "Your Email Address"){
			this.value = "";
		}
	});
	jq("#subscribeEmail").bind("blur", function(){
		if(this.value == ""){
			this.value = "Your Email Address";
		}
	});
	
	if('<%=request.getParameter("subscribeEmail") %>' != 'null'){
		jq("#subscribeEmail").hide();
		jq("#newsletter_submit").hide();
		jq("#feedbackMsg").show();
		
	}
	
});

</script>