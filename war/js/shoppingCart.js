/**
 * 
 */

function removeItem(id){
	jq("#"+id).submit();
	
	return false;
}

function updateShoppingItem(id){
	jq("#"+id).submit();
	
	return false;
}

jq(document).ready(function(){
	/*	jq(".cartOptionsDashboard").hide();	
		var showOptionLink = jq(".showCartOptions");
		showOptionLink.each(function(index, el){
			jq(el).mouseenter(function(){
				jq(".cartOptionsDashboard").hide();	
				jq(this).siblings().next("div.cartOptionsDashboard").show("normal")
			});
			jq(el).mouseleave(function(){
				jq(".cartOptionsDashboard").hide();	
			});
		});
		
		if(window.defaultPrice){
			jq("#cDileveryPrice").html(window.defaultPrice);
		}
		
		if(totalPrice){
			if(totalPrice > freePrice){
				jq("#DileveryPrice").html(0);
				jq("#cDileveryPrice").html(0);
			}
		}
		
		jq("#MemberState_new").change(function(){
			var dePrice = 0;
			if(totalPrice > freePrice){
				dePrice = 0;
			}else{
				dePrice = eval("country_"+jq("#MemberState_new").val());
			}
			jq("#DileveryPrice").html(dePrice);
			jq("#cDileveryPrice").html(dePrice);
		});
		
		var adType = jq("#OrderAddressTypeID").val();
		
		if(adType!="MA"){
			jq("#ship_ul").hide();
		}
		
		jq("#OrderAddressTypeID").change(function(){
			
			var adType = jq("#OrderAddressTypeID").val();
			
			if(adType!="MA"){
				jq("#ship_ul").hide();
			}else{
				jq("#ship_ul").show();
			}
		});*/
});
