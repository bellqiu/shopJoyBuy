package com.spshop.web.function;

import java.util.List;

import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;
import com.spshop.utils.Utils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class CalculateCountOfProPerCategory implements TemplateMethodModel  {

    @Override
    public Object exec(List arg0) throws TemplateModelException {
        Long count = 0l;
        Category category = Utils.populateCategoryForCategoryPage(String.valueOf(arg0.get(0)));
        if (category != null) {
            count = SCacheFacade.getProductCountsByCategory(category);
        }
        category.setProductCount(count);
        return category;
    }

}
