package com.tomtom.ecommerce.dao;

import java.io.Serializable;
import java.util.List;

import com.tomtom.ecommerce.entities.User;
import com.tomtom.ecommerce.exceptions.EcommerceException;

public interface IUserDao {

	User getUserById(Serializable id) throws EcommerceException;

	Long save(User user) throws EcommerceException;

	List<User> getAllUsers() throws EcommerceException;

}
