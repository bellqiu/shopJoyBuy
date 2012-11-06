/*scrollbar*/
jQuery("#AboutUs").ready(function(){

	jQuery("#back-top").hide();
	
	// fade in #back-top
	jQuery(function () {
		jQuery(window).scroll(function () {
			if (jQuery(this).scrollTop() > 100) {
				jQuery('#back-top').fadeIn();
			} else {
				jQuery('#back-top').fadeOut();
			}
		});

		// scroll body to 0px slowly on click
		jQuery('#back-top #slow').click(function () {
			jQuery('body,html').animate({
				scrollTop: jQuery(document).scrollTop() - 5 * jQuery(window).innerHeight()
			}, 500);
			return false;
		});
		
		// scroll body to 0px fast on click
		jQuery('#back-top #fast').click(function () {
			jQuery('body,html').animate({
				scrollTop: 0
			}, 10);
			return false;
		});
	});

	// fade in #back-top
	jQuery(function () {
		jQuery(window).scroll(function () {
			if (jQuery(this).scrollTop() == jQuery(document).height() - jQuery(window).innerHeight()) {
				jQuery('#scroll-bottom').fadeOut();
			} else {
				jQuery('#scroll-bottom').fadeIn();
			}
		});

		// scroll body to 0px slowly on click
		jQuery('#scroll-bottom #slow').click(function () {
			jQuery('body,html').animate({
				scrollTop:  jQuery(document).scrollTop() + 5 * jQuery(window).innerHeight()
			}, 500);
			return false;
		});
		
		// scroll body to 0px fast on click
		jQuery('#scroll-bottom #fast').click(function () {
			jQuery('body,html').animate({
				scrollTop:  jQuery(document).height()
			}, 10);
			return false;
		});
	});

});