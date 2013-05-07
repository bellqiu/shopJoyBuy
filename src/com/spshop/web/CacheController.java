package com.spshop.web;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spshop.cache.SCacheFacade;

@Controller
@RequestMapping("/cacheManager________")
public class CacheController extends BaseController {
    
    @RequestMapping("/list")
    public String listCache(Model model) throws IOException {
    	String[] caches = SCacheFacade.getCartCache().getCacheManager().getCacheNames();
    	
    	model.addAttribute("cache", caches);
    	
    	return "cache";
    }
    
    @RequestMapping("/remove")
    public String removeCacheCache(Model model, @RequestParam("el") String cache) throws IOException {
    	
    	 SCacheFacade.getCartCache().getCacheManager().getCache(cache).removeAll();
    	
    	return "redirect:/cacheManager________/list";
    }
    
}
