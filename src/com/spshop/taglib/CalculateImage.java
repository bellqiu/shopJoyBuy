package com.spshop.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spshop.admin.shared.ImageSize;
import com.spshop.model.Image;
import com.spshop.model.Product;
import com.spshop.utils.ImageTools;

public class CalculateImage extends TagSupport {

    /**
     *
     */
    private static final long serialVersionUID = 3115898552687881970L;

    private List<Product> prodList = new ArrayList<Product>();
    private String width = "width";
    private String imgSize = "imageSize";
    private String paddingSize = "paddingSize";
    private String heightVal = "heightValue";

    public int doStartTag() throws JspException {
        Image img = new Image();
        Float currentW = Float.valueOf(width);
        Float paddingH = 0f;
        Float heightValue = 0f;
        Float maxW = 0f;
        Float maxH = 0f;
        Float minH = 600f;
        
        if (null != this.prodList) {
            Float tempW = 0f;
            Float tempH = 0f;
            for (Product p : prodList) {
                img = p.getImages().get(0);
                tempW = Float.valueOf(ImageTools.getXY(img.getSizeType(), ImageSize.valueOf(imgSize))[0]);
                tempH = Float.valueOf(ImageTools.getXY(img.getSizeType(), ImageSize.valueOf(imgSize))[1]);
                if(tempH > maxH){
                    maxH = tempH;
                    maxW = tempW;
                }
                if (tempH <= minH) {
                    minH = tempH;
                }
            }
            Float zoom = currentW/maxW;
            paddingH = zoom * ((maxH - minH)/2);
            heightValue = zoom * maxH;
        }
        pageContext.setAttribute(paddingSize, paddingH.intValue());
        pageContext.setAttribute(heightVal, heightValue.intValue());
        return EVAL_BODY_INCLUDE;
    }
    
    @Override
    public void release() {
        super.release();
        this.prodList = null;
    }


    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public List<Product> getProdList() {
        return prodList;
    }

    public void setProdList(List<Product> prodList) {
        this.prodList = prodList;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public String getPaddingSize() {
        return paddingSize;
    }

    public void setPaddingSize(String paddingSize) {
        this.paddingSize = paddingSize;
    }

    public void setHeightVal(String heightVal) {
        this.heightVal = heightVal;
    }

    public String getHeightVal() {
        return heightVal;
    }

}
