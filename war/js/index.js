//Daily Madness选项卡切换
jq('#Promotions_tab').w_tab({
child:'h3[aim]',
cssS:'link_now',
event:'hover'
}).w_tab(0);
//Baby Sale选项卡切换
jq('#babysale_tab').w_tab({
child:'li[aim]',
cssS:'display_now',
event:'hover'
}).w_tab(0);
function setScrollStatus(statusContainer,params)
{
var curIndex = Math.ceil(params.rightIndex/params.itemsPerRow)-1;
if(curIndex<0)return false;
var status = jq('#'+statusContainer+'>li');
if(!status.eq(curIndex).get(0))
{
jq('#'+statusContainer).prepend('<li></li>');
}
status = jq('#'+statusContainer+'>li');
status.each(function(){
jq(this).removeClass('scrollStatusShow');
});
status.eq(3-curIndex).addClass('scrollStatusShow').fadeOut(1).fadeIn(1);
}
