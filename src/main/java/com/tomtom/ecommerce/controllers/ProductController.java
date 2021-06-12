package com.tomtom.ecommerce.controllers;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tomtom.ecommerce.entities.Product;
import com.tomtom.ecommerce.exceptions.EcommerceException;
import com.tomtom.ecommerce.service.IEcommerceService;

@RestController
@RequestMapping("v1/products")
public class ProductController {

	Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	IEcommerceService service;

//	1. ------------ User can search product with different ways;
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProductById(@RequestParam("id") String id) {
		// validations of null/empty id
		/**
		 * this exception will be caught in EcommerceExceptionController this validation
		 * block can be put wherever needed response to UI will be :
		 * {"code":"400","message":"Invalid data passed"} //BAD REQUEST
		 */
		boolean validationFailed = false;
		if (validationFailed) { // this block is for example
			throw new EcommerceException("Invalid data passed", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Product>(service.getProductById(Long.parseLong(id)), HttpStatus.OK);
	}

	@RequestMapping(value = "all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<List<Product>>(service.getAllProducts(), HttpStatus.OK);
	}

	@RequestMapping(value = "/name/{pname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getProductsByname(@PathVariable("pname") String pname) {
		/**
		 * lest's say if product is not found with name > then we can throw exception
		 * from service layer........... like : throw new EcommerceException("No Product
		 * found with this name"); here response to Ui will be :
		 * {"code":"204","message":"No Product found with this name"} // NO CONTENT
		 */
		return new ResponseEntity<List<Product>>(service.getProductByName(pname), HttpStatus.OK);
	}

//	2. ------------ Seller user should be able to post products  -----------------------------
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addProduct(@RequestBody Product product, @RequestParam String userId) {
		/**
		 * User id should be present in request, each request will be validated in
		 * AuthenticateInterceptor
		 * 
		 * this request should be from seller, so it can be checked in interceptor,
		 * Whether this user is a type of UserTypes.SELLER
		 */
		return new ResponseEntity<Long>(service.addProduct(product, Long.parseLong(userId)), HttpStatus.OK);
	}

	@RequestMapping(value = "cart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addProductToCart(@RequestBody Product product, @RequestParam String userId,
			@RequestParam String cartId) {
		return new ResponseEntity<Long>(service.addProductToCart(product, Long.parseLong(userId), Long.parseLong(cartId)), HttpStatus.OK);
	}
}
