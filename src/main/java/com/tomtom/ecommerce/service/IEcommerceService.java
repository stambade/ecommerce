package com.tomtom.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tomtom.ecommerce.entities.Order;
import com.tomtom.ecommerce.entities.Product;

@Service
public interface IEcommerceService {

	Long addProduct(Product product, Long userId);

	List<Product> getAllProducts();

	Product getProductById(long id);

	List<Product> getProductByName(String productName);

	Order getOrderById(long id);

	List<Order> getAllOrdersOfUser(long userId);

	Long placeOrder(Order order, Long userId);

	Long addProductToCart(Product product, long parseLong, long parseLong2);

}
