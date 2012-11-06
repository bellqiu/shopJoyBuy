//语言菜单
jq('#dif_language').hover(function(){
	jq('#dif1').hide();
	jq('#dif2').show();
},function(){
	jq('#dif2').hide();
	jq('#dif1').show();
});


//顶部三个下拉菜单,登录、帮助、货币，当其它一个显示时，需要将其它菜单(包括购物车)马上隐藏
var $oldusermenuli='';
jq('#user_menu').children('ul').children('li:not([nosel])').mouseover(
	function(){
		var el=jq(this);
		el.children('.user_menu_hidd').hideTime('stop').hideTime(150,['show',[0,function(){
			el.addClass('current');
			if($oldusermenuli!='' && $oldusermenuli[0]!=el[0]){$oldusermenuli.removeClass('current').children('.user_menu_hidd').hideTime('stop').hide();}
			if(jq('#universalCart').is(':visible')){
				jq('#universalCart').hideTime('stop').hide();
			}
			$oldusermenuli=el;
		}]]);
	}).mouseout(function(){
		jq(this).children('.user_menu_hidd').hideTime('stop').hideTime(150,['hide',[0,function(){jq(this).parent().removeClass('current');$oldusermenuli='';}]]);
	}
);

// ** Pop function
var js_popup_timer = null;

jq('.js_popup').hover(
	function() {
		clearTimeout( js_popup_timer );
		
		var poper = jq( '#' + jq( this ).attr( 'rel' ) );
		
		if( poper.is( ':hidden' ) ) {
			poper.fadeIn( 200 );
		}
	},
	function() {
		var is_onpop = false, poper = jq( '#' + jq( this ).attr( 'rel' ) );
		
		// If mouseover the poper element
		poper.hover(
			function() { clearTimeout( js_popup_timer ); is_onpop = true; },
			function() {
				clearTimeout( js_popup_timer ); 
				
				js_popup_timer = setTimeout( function() {
					poper.fadeOut( 200, function() { js_popup_timer = null; } );
				}, 400 );
			}
		);
		
		js_popup_timer = setTimeout( function() {
			if( !is_onpop ) poper.fadeOut( 200, function() { js_popup_timer = null; } );
		}, 400 );
		
	}
);
// ** END pop function

//顶部客服人员滚动显示
jq('#marqueebox1').w_roll({way:1,speed:50});
//搜索栏自动选择
jq('#textfield').w_nullInputState(head_SearchKeywordsNo.htmlToStr());
//jq(function(){jq('#textfield').w_autoComplete({url:'/index.php?action=ajax&menu=searchkeyword',nodeclick:function(a){jq('#formss').submit();},waittime:300});});
//搜索提交判断功能
jq('#textfield').next().bind('click',checksearch);
function checksearch(){
	if(jq.trim(jq('#textfield').val())=='' || jq('#textfield').val()==head_SearchKeywordsNo.htmlToStr()){
		return false;
	}
}
//导航条横向类别菜单
var $oldmainmenuli=jq('#main_menu').children('ul').children('li.nav_on');
//鼠标当前所在的导航菜单条目
var curMenuItem;

var tChild = [];

jq('#main_menu').children('ul').children('li').hover(
	function(){
		var el = jq(this);
		var nl = el.find('a:first').text().toString();
		clearTimeout(tChild['hide'+nl]);
		tChild['show'+nl] = setTimeout(function(){
			if(!el.is('.first,.last')){
    			el.prev().addClass('nav_onprev');
    		}
			el.addClass('nav_on');
			if (el.children('.sub_menu').children('.inner_box2').children().size() > 0)
			el.children('.sub_menu').show();
		}, 200);
	},
	function(){
		var el = jq(this);
		var nl = el.find('a:first').text().toString();
		clearTimeout(tChild['show'+nl]);
		tChild['hide'+nl] = setTimeout(function(){
			el.removeClass('nav_on');
			el.prev().removeClass('nav_onprev');
			el.children('.sub_menu').hide();
		}, 200);
	}
);
/*jq('#main_menu').children('ul').children('li').hover(
	function(){
		var el=jq(this);
		curMenuItem=el;
		var dateO=new Date();
		el.data('timingStart',dateO.getSeconds()*1000+dateO.getMilliseconds());
		setTimeout(function(){//延时150
		    var dateO=new Date(); 
		    var hoverDuration = dateO.getSeconds()*1000+dateO.getMilliseconds()-el.data('timingStart');		    
		    if(hoverDuration<140 || curMenuItem!=el)
		    {
		        return false;
		    }
    		if(!el.is('.first,.last')){
    			el.prev().addClass('nav_onprev');
    		}
    		el.addClass('nav_on').children('.sub_menu').hideTime('stop').show();
    		if($oldmainmenuli!='' && $oldmainmenuli[0]!=el[0]){$oldmainmenuli.removeClass('nav_on').children('.sub_menu').hideTime('stop').hide();$oldmainmenuli.prev().removeClass('nav_onprev');}
    		$oldmainmenuli=el;
		},150);
	},function(){
		var el=jq(this);
		var dateO=new Date();
		el.data('timingStart',dateO.getSeconds()*1000+dateO.getMilliseconds());
		if(el.children('.sub_menu').length>0){
			el.children('.sub_menu').hideTime(350,['slideUp',[{duration:100,easing:'easeInExpo',complete:function(){el.removeClass('nav_on');$oldmainmenuli='';el.prev().removeClass('nav_onprev');}}]]);
		}else{
			$oldmainmenuli='';
			el.removeClass('nav_on');
			el.prev().removeClass('nav_onprev');
		}
	}
);*/
//左侧商品类别菜单
if(jq('#sortMenucilck').length>0){
	var $ts_menuD=jq('#TS_menuD');
	jq('#sortMenucilck').mouseover(function(){$ts_menuD.removeClass('TS_menu_hidd');});
	$ts_menuD.hover(function(){$ts_menuD.hideTime('stop');},function(){$ts_menuD.hideTime(500,['addClass',['TS_menu_hidd']]);});
}
var $ts_menu=jq('#TS_menu');
$ts_menu.children('li').w_hoverClass({css:'acitve'})
	.hover(function(){
		jq(this).children('div').show();
	},function(){
		jq(this).children('div').hide();
	});

//购物车
jq('#top_Cart').mouseover(function(){
	if(jq('#universalCart').is(':visible')){
		//jq('#universalCart').hideTime('stop');
		jq('#universalCart').show();
	}else{
		jq('#universalCart').show();
	}
}).mouseout(function(){
	jq('#universalCart').hide();
});
//会员登录改ajax操作
function loginAjax(loginusername,loginuserpass,loginmethod,url){
	jq.ajax({
		cache:"false",
		url:url,
		type: "post", 
		data:"loginusername="+loginusername+"&loginuserpass="+loginuserpass+"&loginmethod="+loginmethod,
		success:function(data){
				if(data.substr(0,1)==1){
					jq("#base_unlogin_li").hide();
					jq("#hide_login_span").text(data.substr(1));
					jq("#hide_login_li").show();
				}else{
					jq("#warn_login_span").html("<br/><br/><font color=red>"+data.substr(1)+"</font>");
				}
		}
	});
	return false;
}
//顶部固定条的CSS3效果
if(!is_ie || is_ie=='9.0'){
	var isheadershadow=0;
	jq(window).scroll(headershadow);
}else{
if(is_ie=='6.0'){
	var isheadershadow=0;
	jq(window).scroll(headershadwoie6)
	}
}
function headershadwoie6(){
	if(jq(window).scrollTop()>0){
		if(isheadershadow==0){
			jq('#header_top').css({progid:"DXImageTransform.Microsoft.Shadow(color='#b7b9b6', Direction=135, Strength=3)",border:"solid 0px white",backgroundColor:"#fff"});
			isheadershadow=1;
		}
	}else{
		jq('#header_top').css({boxShadow:'none'});
		isheadershadow=0;
	}
}
function headershadow(){
	if(jq(window).scrollTop()>0){
		if(isheadershadow==0){
			jq('#header_top').css({boxShadow:'0px 0px 3px #666666'});
			isheadershadow=1;
		}
	}else{
		jq('#header_top').css({boxShadow:'none'});
		isheadershadow=0;
	}
}

//品牌栏目效果
/*
jq('#box_brand>dd>a>span').css({left:66});
jq('#box_brand>dd>a').hover(function(){
	jq(this).children('img').stop(true).animate({left:-66},200,'easeOutExpo');
	jq(this).children('span').stop(true).animate({left:0},100,'easeOutExpo');
},function(){
	jq(this).children('img').stop(true).animate({left:0},100,'easeOutExpo');
	jq(this).children('span').stop(true).animate({left:66},200,'easeOutExpo');
});
*/
jq('#box_brand>dd').hover(function(){
	jq(this).addClass('box_brand_item');
	jq(this).children().children('img').hide();
	jq(this).children().children('span').show();
},function(){
	jq(this).removeClass('box_brand_item');
	jq(this).children().children('span').hide();
	jq(this).children().children('img').show();
});

















