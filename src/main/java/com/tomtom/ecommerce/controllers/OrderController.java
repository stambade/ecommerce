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

import com.tomtom.ecommerce.dto.PaymentInfo;
import com.tomtom.ecommerce.entities.Order;
import com.tomtom.ecommerce.service.IEcommerceService;

@RestController
@RequestMapping("v1/orders")
public class OrderController {

	Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	IEcommerceService service;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> getOrderByID(@RequestParam("id") String id) {
		// validations of null/empty id
		return new ResponseEntity<Order>(service.getOrderById(Long.parseLong(id)), HttpStatus.OK);
	}

	@RequestMapping(value = "all/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> getProducts(@PathVariable String userId) {
		return new ResponseEntity<List<Order>>(service.getAllOrdersOfUser(Long.parseLong(userId)), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> placeOrder(@RequestBody Order order, @RequestParam String userId) {
//		This api will have product Ids in order parameter
		return new ResponseEntity<Long>(service.placeOrder(order, Long.parseLong(userId)), HttpStatus.OK);
	}

	@RequestMapping(value = "pay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaymentInfo> makePayment(@RequestBody PaymentInfo payment) {
//		Payment gateway handling
		return new ResponseEntity<PaymentInfo>(payment, HttpStatus.OK);
	}

}
