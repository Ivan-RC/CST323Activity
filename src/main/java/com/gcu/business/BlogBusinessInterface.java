package com.gcu.business;

import java.util.List;

import com.gcu.model.BlogModel;

public interface BlogBusinessInterface {
	public boolean insert(BlogModel blog);
	public BlogModel findById(int id);
	public List<BlogModel> findAll();
	public List<BlogModel> findAllById(int id);
	public boolean edit(BlogModel blog);
	public boolean remove(BlogModel blog);
}