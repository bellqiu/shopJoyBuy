// JavaScript Document
var iscustom='';
jq("#currencyAll,.currencyAll").hover(function(){
	jq(".currencyAll").slideDown("fast").data("show",1); setTimeout("hide('.','currencyAll')",1000);
},function(){
	jq(".currencyAll").data("show",0);setTimeout("hide('.','currencyAll')",100);
});
function hide(symbol,divName){	
	symbol=String(symbol)||'#';	var value=jq(symbol+divName).data("show");if(!value){jq(symbol+divName).hide();}	
}
/****/

jq("#nosub").hover(function(){jq("#sub_outDiv").show();},function(){jq("#sub_outDiv").hide();});
/****/

(function($){
	$.fn.tanCeng = function(action,method,outDocument_id,background){
		if(method=='show')
		{			
			this.bind(action,function(){dod();});					
		}
		if(method=='absolute')
		{			
			dod();				
		}
		function dod(){
				var z_index=900;							 
				if(background!==1)$("#musicwhp_backgroundDiv").css({"opacity":"0.5","height":$(document).height(),"width":"100%","z-index":z_index,"background-color":"#333333","position":"absolute","top":"0px" , "left":"0px"}).show();
				var windowWidth = jq(window).width();
				var windowHeight = jq(window).height();
				var divWidth = jq("#Customfloat").width();
				var divHeight = jq("#Customfloat").height();			
				var divLeft = windowWidth/2-divWidth/2;			 
				var divTop = divHeight>windowHeight?50+jq(window).scrollTop():windowHeight/2-divHeight/2+jq(window).scrollTop();	
				divLeft = divLeft-200; 
				divTop = divTop -200;		
				jq(outDocument_id).css({"position":"absolute","z-index":++z_index,"top":divTop,"left":divLeft}).show();				
		}		
		if(method=='hide'){ this.bind(action,function(){ if($(outDocument_id).get(0).style.display!='none')$(outDocument_id+",#musicwhp_backgroundDiv").hide();});}				
		return this;
	}
})(jQuery);

jq(function(){
	jq("a[rel=thing_item_pics]").fancybox({
		'overlayColor'		: '#000',
		'overlayOpacity'	: 0.2,
		'speedIn'			: 400,
		'speedOut'			: 100,
		'transitionIn'		: 'elastic',
		'transitionOut'		: 'fade',
		'titlePosition' 	: 'inside',
		'titleShow'			:false
	});
	jq("a[rel=wholesale_pics]").fancybox({
		'overlayColor'		: '#000',
		'overlayOpacity'	: 0.2,
		'speedIn'			: 400,
		'speedOut'			: 100,
		'transitionIn'		: 'elastic',
		'transitionOut'		: 'fade',
		'titlePosition' 	: 'inside',
		'titleShow'			:false
	});
		var href;
		href = jq("#imageNormalBox").attr("val");
		//alert(href);
		jq("#linkNormalBox").attr("href",href);
		jq('.smallPic').click(function(){
		var imgSrc;
		var sNum;
			imgSrc = jq(this).attr("val");
			jq("#imageNormalBox").attr("src",imgSrc).parent("a").attr("href",imgSrc);
			sNum = jq(".smallPic").index(jq(this)); //æ˜¾ç¤ºæˆ‘æ˜¯ç¬¬å‡ ä¸ª
			jq(".noneBox").attr("rel","thing_item_pics")
			jq(".noneBox:eq("+sNum+")").removeAttr("rel");
		})
	jq('.currencyAll,.sub_menu,.user_menu_hidd,#universalCart').bgiframe();
})

