package com.spshop.fe.actions;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.ProductService;
import com.spshop.utils.Constants;

public class SearchAction extends BaseAction {
	@Override
	public ActionForward processer(ActionMapping mapping, PageFormBean page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String key = request.getParameter("key");
		
		if("search".equals(retrieveCMDURL(request))){
			if(null!=key && key.length()>0){
				Map<String, String> rs = ServiceFactory.getService(ProductService.class).search(key, 0, 20);
				out.print("{d:[");
					Iterator<String> iter = rs.keySet().iterator();
					while(iter.hasNext()){
						out.print("[");
						String k = iter.next();
						out.print("'" + k.replace("'", "").replace(key, "<b  class=\"red fontbold\">"+key+"</b>") +"'" +" ,");
						out.print("'"+rs.get(k)+"'");
						if(iter.hasNext()){
							out.print("],");
						}else{
							out.print("]");
						}
					}
				out.print("]}");
			}else{
				out.print("{d:[]}");
			}
			return null;
		}
		
		return mapping.findForward(Constants.SUCCESS_VALUE);
	}

}