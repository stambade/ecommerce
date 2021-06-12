package com.tomtom.ecommerce.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tomtom.ecommerce.entities.IGenericVo;
import com.tomtom.ecommerce.entities.Order;
import com.tomtom.ecommerce.exceptions.EcommerceException;

@Component
@SuppressWarnings("rawtypes")
public interface IEcommerceDao {

	IGenericVo getById(Class cls, Serializable id) throws EcommerceException;

	List<IGenericVo> getAll(Class cls) throws EcommerceException;

	List<IGenericVo> getAllByKeyValue(Class cls, String key, String value) throws EcommerceException;

	Long save(IGenericVo s) throws EcommerceException;

	List<Order> getAllOrdersOfUser(long userId) throws EcommerceException;

}
