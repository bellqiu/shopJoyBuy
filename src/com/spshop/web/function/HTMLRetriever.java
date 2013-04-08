package com.spshop.web.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.HTML;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class HTMLRetriever implements TemplateMethodModel {

    @Override
    public Object exec(List arg0) throws TemplateModelException {
        List<String> manualKeys = Arrays.asList(String.valueOf(arg0.get(0)).split(","));
        List<HTML> htmls = new ArrayList<HTML>();
        for (String key : manualKeys) {
            if (StringUtils.isNotBlank(key)) {
                htmls.add(SCacheFacade.getHTML(Long.valueOf(key),false));
            }
        }
        return htmls;
    }

}
