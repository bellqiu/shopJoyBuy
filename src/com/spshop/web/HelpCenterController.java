package com.spshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/helpCenter")
@Controller
public class HelpCenterController extends BaseController{
	@RequestMapping("")
	public String helpIndex(Model model) {
		
		return "helpCenter";
	}
	
	@RequestMapping("/{pageName}")
	public String helpPages(Model model, @PathVariable("pageName") String pageName) {
		
		return "hcPages/"+pageName;
	}
}
