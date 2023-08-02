package com.lib.service.impl;

import com.lib.bean.Category;
import com.lib.dao.CategoryDao;
import com.lib.dao.impl.CategoryDaoImpl;
import com.lib.service.CategoryService;
import java.util.List;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class CategoryServiceImpl implements CategoryService{
  private final CategoryDao categoryDao;

  public CategoryServiceImpl(BasicDataSource bds){
    categoryDao = new CategoryDaoImpl(bds);
  }

  @Override
  public boolean removeCategory(int catId){
    return catId < 0 ? false : categoryDao.removeCategory(catId);
  }

  @Override
  public List<Category> getCategories(){
    return categoryDao.getCategories();
  }

  @Override
  public Category getCategory(int catId){
    return catId < 0 ? null : categoryDao.getCategory(catId);
  }

  @Override
  public boolean updateCategory(Category category){
    category.setGenre(category.getGenre().toLowerCase());
    return categoryDao.updateCategory(category);
  }

  @Override
  public boolean addCategory(Category category){
    return categoryDao.addCategory(category);
  }
}
