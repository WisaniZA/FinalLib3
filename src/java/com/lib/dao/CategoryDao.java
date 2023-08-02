package com.lib.dao;

import com.lib.bean.Category;
import java.util.List;

public interface CategoryDao{
  boolean removeCategory(int catId);

  boolean updateCategory(Category category);

  boolean addCategory(Category category);

  Category getCategory(int catId);

  List<Category> getCategories();
}
