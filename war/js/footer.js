/*底部搜索栏*/
jq(function(){
	jq('#textfield0').w_nullInputState(head_SearchKeywordsNo.htmlToStr());
	jq('#textfield0').next().bind('click',checksearch2);	
});
function checksearch2(){
		if(jq.trim(jq('#textfield0').val())=='' || jq('#textfield0').val()==head_SearchKeywordsNo.htmlToStr()){
			return false;
		}
}
/*DFP系统广告加载*/
var clock;
jq(function(){
	var $brandtmp=jq('#brand_bigAD_tmp'),$brand=jq('#brand_bigAD');
	
	if($brand.length>0) {
		$brand.css({width:$brandtmp.outerWidth(),height:$brandtmp.outerHeight()});
		var pos=$brand.position();
		$brandtmp.css({position:'absolute',left:pos.left,top:pos.top}).show();
	}
	
	var $brandtmp=jq('#allBanner_tmp'),$brand=jq('#allBanner');
		if($brand.length>0) {
			$brand.css({width:$brandtmp.outerWidth(),height:$brandtmp.outerHeight()});
			var pos=$brand.offset();
			$brandtmp.css({position:'absolute',left:pos.left,top:pos.top}).show();
			jq(window).resize(function(){
				$brand.css({width:$brandtmp.outerWidth(),height:$brandtmp.outerHeight()});
				if(jq.browser.version=='7.0'){
					if(!clock) {
						clock = setInterval("setLeft()",1);
						clearInterval(clock);
					 }
				} else {
					var brannerPos=$brand.offset();
					$brandtmp.css({position:'absolute',left:brannerPos.left,top:brannerPos.top}).show();
				}
			});
		}
	
});

	function setLeft(){
		clearInterval(clock);
		$brandtmp=jq('#allBanner_tmp'),$brand=jq('#allBanner');
		var brannerPos=$brand.offset();
		//alert(brannerPos.left);
		$brandtmp.css({position:'absolute',left:brannerPos.left,top:brannerPos.top}).show();
		
	}
	
/*
 * 生成顶部DFP广告位的倒计时
 * @param string adContainerId 广告容器ID
 * @param int  sec 倒计时剩余秒数
 */
function genTopAddCountDown(adContainerId,sec)
{			
	var adContainer = jq('#'+adContainerId);
	if(sec > 0 && adContainer.length>0)
	{
		
		var clockStr = '<span id="countdown_top" class="countdown_top"><span><input class="clockH" readonly />:<input class="clockM" readonly />:<input class="clockS"  readonly /></span></span>';
		adContainer.css('position','relative');
		adContainer.prepend(clockStr);		
		jq('#countdown_top').timedown({sec:sec});		
	}
	
}	
	