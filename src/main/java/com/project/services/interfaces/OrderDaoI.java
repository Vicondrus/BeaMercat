package com.project.services.interfaces;

import java.util.List;

import com.project.entities.Address;
import com.project.entities.Order;
import com.project.entities.User;

public interface OrderDaoI {

	Order createOrder(User user, Address address);

	Order completeOrder(Order order);

	Order cancelOrder(Order order);

	Order updateOrder(Order order);

	List<Order> getByCustomer(User customer);

	Order getById(Order order);

	List<Order> getAll();

}
