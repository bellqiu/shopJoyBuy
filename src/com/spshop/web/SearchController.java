package com.spshop.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.ProductService;

@Controller
public class SearchController extends BaseController {
    private static final String CATEGORIES_UI = "categories";
    private static final String MARKET_ONLY_UI = "market";
    
    @RequestMapping("/search")
    public String renderHome(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        PrintWriter out = response.getWriter();
        String key = request.getParameter("key");
        
        if("search".equals(retrieveCMDURL(request))){
            if(null!=key && key.length()>0){
                Map<String, String> rs = ServiceFactory.getService(ProductService.class).search(key, 0, 20);
                out.print("{\"d\":[");
                    Iterator<String> iter = rs.keySet().iterator();
                    while(iter.hasNext()){
                        out.print("[");
                        String k = iter.next();
                        out.print("\"" + k.replace("\"", "").replace(key, "<b>"+key+"</b>") +"\"" +" ,");
                        out.print("\""+rs.get(k)+"\"");
                        if(iter.hasNext()){
                            out.print("],");
                        }else{
                            out.print("]");
                        }
                    }
                out.print("]}");
            }else{
                out.print("{\"d\":[]}");
            }
            return null;
        }
        
        return CATEGORIES_UI;
    }
    
    private String retrieveCMDURL(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.indexOf("cmd/") != -1) {
            uri = uri.substring(uri.indexOf("cmd/") + "cmd/".length());
        }
        return uri;
    }
}
