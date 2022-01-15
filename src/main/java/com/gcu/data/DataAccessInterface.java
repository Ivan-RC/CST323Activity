package com.gcu.data;

import java.util.List;

import com.gcu.model.LoginModel;

public interface DataAccessInterface <T>{

	public T findById(int id);
	public List<T> findAll();
	public List<T> findAllById(int id);
	public int findByUsernameAndPassword(T t);
	public int create(T t);
	public int update(T t);
	public int delete(T t);
	public T findByUsername(String username);
}