package com.youcode.basmaonlinestore.service;

import com.youcode.basmaonlinestore.entity.ProductCategory;

import java.util.List;


public interface CategoryService {

    List<ProductCategory> findAll();

    ProductCategory findByCategoryType(Integer categoryType);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);


}
