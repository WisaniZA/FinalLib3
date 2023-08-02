package com.lib.service;

import com.lib.bean.Category;
import java.util.List;

public interface CategoryService{
    boolean removeCategory(int catId);

    boolean updateCategory(Category category);

    boolean addCategory(Category category);

    Category getCategory(int catId);

    List<Category> getCategories();
}
