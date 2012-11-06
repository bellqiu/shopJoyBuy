package com.spshop.web.view;

import com.spshop.model.Category;

public class BaseFrontendView {
    private Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
