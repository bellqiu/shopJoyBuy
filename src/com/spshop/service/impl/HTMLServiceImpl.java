package com.spshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.spshop.dao.intf.HTMLDAO;
import com.spshop.model.HTML;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.HTMLService;
import com.spshop.validator.HTMLValidator;

public class HTMLServiceImpl extends AbstractService<HTML, HTMLDAO, Long> implements HTMLService{

	@Override
	public HTML saveHTML(HTML html) {
		new HTMLValidator(html).validate();
		return save(html).clone();
	}

	@Override
	public HTML getHTML(long id) {
		return findById(id).clone();
	}

	@SuppressWarnings({"rawtypes" })
	@Override
	public List<HTML> getHTMLs(String ids) {
		List<HTML> htmls = new ArrayList<HTML>();
		String hql = "from HTML where id in ("+ids+")";
		List comps= getDao().queryByHQL(hql,0,10);
		for (Object object : comps) {
			htmls.add((HTML)object);
		}
		return htmls;
	}
	
}
