package com.tomtom.ecommerce.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.tomtom.ecommerce.dao.EcommerceDaoImpl;
import com.tomtom.ecommerce.dao.IEcommerceDao;
import com.tomtom.ecommerce.dao.IUserDao;
import com.tomtom.ecommerce.dao.UserDaoImpl;
import com.tomtom.ecommerce.entities.Product;
import com.tomtom.ecommerce.entities.User;
import com.tomtom.ecommerce.enums.UserTypes;

public class EcomUtil {

	static Logger logger = LogManager.getLogger(EcomUtil.class);

	static ApplicationContext context = null;

	static IUserDao userdao = null;
	static IEcommerceDao genericDao;

	public static void setContextAndInit(ApplicationContext ctx) {
		context = ctx;
		userdao = context.getBean(UserDaoImpl.class);
		genericDao = context.getBean(EcommerceDaoImpl.class);

		addUsersAtStartup();
	}

	public static void addUsersAtStartup() {

		User sagar = new User("sagar", "sagar@tomtom.com", UserTypes.CUSTOMER.name());
		userdao.save(sagar);

		User manish = new User("manish", "manish@tomtom.com", UserTypes.SELLER.name());
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("himalaya", 45d, "resources:/products/himalayaSoap.png").setUser(manish));
		manish.setProducts(products);
		userdao.save(manish);

		User suraj = new User("suraj", "suraj@tomtom.com", UserTypes.SELLER.name());
		List<Product> products2 = new ArrayList<Product>();
		products2.add(new Product("colgate", 45d, "resources:/products/colgate.png").setUser(suraj));
		suraj.setProducts(products2);
		userdao.save(suraj);

		logger.info("All Users : \n" + userdao.getAllUsers());
		logger.info("All Products : \n" + genericDao.getAll(Product.class));

	}
}
