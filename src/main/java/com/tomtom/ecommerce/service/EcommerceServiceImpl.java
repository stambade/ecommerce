package com.tomtom.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tomtom.ecommerce.dao.IEcommerceDao;
import com.tomtom.ecommerce.entities.Cart;
import com.tomtom.ecommerce.entities.IGenericVo;
import com.tomtom.ecommerce.entities.Order;
import com.tomtom.ecommerce.entities.Product;
import com.tomtom.ecommerce.entities.User;
import com.tomtom.ecommerce.exceptions.EcommerceException;

@Service
public class EcommerceServiceImpl implements IEcommerceService {

	@Autowired
	IEcommerceDao dao;

	@Override
	public Long addProduct(Product product, Long userId) {
		// validations
		User seller = (User) dao.getById(User.class, userId);
		if (seller == null) {
			throw new EcommerceException("bad request > No user found to map", HttpStatus.BAD_REQUEST);
		}
		// check usertype if needed
		product.setUser(seller);
		return dao.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<Product>();

		List<IGenericVo> objs = dao.getAll(Product.class);
		for (IGenericVo obj : objs) {
			Product product = (Product) obj;
			product.setUser(null);
			list.add(product);
		}
		return list;
	}

	@Override
	public List<Product> getProductByName(String productName) {
		// validations of null/empty strings

		List<Product> list = new ArrayList<Product>();

		List<IGenericVo> objs = dao.getAllByKeyValue(Product.class, "name", productName);
		for (IGenericVo obj : objs) {
			Product product = (Product) obj;
			product.setUser(null);
			list.add(product);
		}
		return list;

	}

	@Override
	public Product getProductById(long id) {
		// validations
		Product p = (Product) dao.getById(Product.class, id);
		p.setUser(null);

		// exception if not found or custom error message
		return p;
	}

	@Override
	public Order getOrderById(long id) {
		// validations
		Order o = (Order) dao.getById(Order.class, id);
		// o.setProducts(products);
		// exception if not found or custom error message
		return o;
	}

	@Override
	public List<Order> getAllOrdersOfUser(long userId) {
		return dao.getAllOrdersOfUser(userId);
	}

	@Override
	public Long placeOrder(Order order, Long userId) {
		// validations
		User customer = (User) dao.getById(User.class, userId);
		if (customer == null) {
			throw new EcommerceException("bad request > No user found to map", HttpStatus.BAD_REQUEST);
		}
		// create/ Modify order object
		// check product ids in orcer object that came from Ui/rest request
		// set products to order
		return dao.save(order);
	}

	@Override
	public Long addProductToCart(Product product, long userId, long cartId) {
		// validations
		User customer = (User) dao.getById(User.class, userId);
		if (customer == null) {
			throw new EcommerceException("bad request > No user found to map", HttpStatus.BAD_REQUEST);
		}
		Cart cart = customer.getCart(); // or directly get by carrtID if authenticated userid in interceptor
		cart.setProducts(null); // add products here
		return dao.save(customer); // or get cart by cartId and save cart
	}
}
