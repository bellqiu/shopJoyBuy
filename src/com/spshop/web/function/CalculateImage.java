package com.spshop.web.function;

import java.util.ArrayList;
import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class CalculateImage implements TemplateMethodModel {

    @Override
    public Object exec(List args) throws TemplateModelException {
        List<Float> hAndPadding = new ArrayList<Float>();
        Float minH = Float.valueOf(String.valueOf(args.get(0)));
        Float maxH = Float.valueOf(String.valueOf(args.get(1)));
        Float maxW = Float.valueOf(String.valueOf(args.get(2)));
        Float currentW = Float.valueOf(String.valueOf(args.get(3)));
        Float paddingH = 0f;
        Float heightValue = 0f;
        
        Float zoom = currentW/maxW;
        paddingH = zoom * ((maxH - minH)/2);
        heightValue = zoom * maxH;
        
        hAndPadding.add(heightValue);
        hAndPadding.add(paddingH);
        return hAndPadding;
    }

}
