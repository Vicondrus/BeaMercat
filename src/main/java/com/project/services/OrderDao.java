package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Address;
import com.project.entities.Order;
import com.project.entities.OrderStatus;
import com.project.entities.Product;
import com.project.entities.User;
import com.project.repos.OrderRepository;
import com.project.services.interfaces.OrderDaoI;
import com.project.services.interfaces.ProductDaoI;

@Service
public class OrderDao implements OrderDaoI {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ProductDaoI prodDao;

	@Override
	public Order createOrder(User user, Address address) {
		if (user == null)
			return null;
		if (address == null)
			return null;
		Order o = new Order(user, address);
		o.setStatus(OrderStatus.PROCESSING);
		return orderRepo.save(o);
	}

	@Override
	public Order completeOrder(Order order) {
		if (order == null)
			return null;
		Order found = orderRepo.findById(order.getId()).orElse(null);
		if (found == null)
			return null;
		if (!found.getStatus().equals(OrderStatus.PROCESSING))
			return null;
		found.setStatus(OrderStatus.COMPLETED);
		return orderRepo.save(found);
	}

	@Override
	public Order cancelOrder(Order order) {
		if (order == null)
			return null;
		Order found = orderRepo.findById(order.getId()).orElse(null);
		if (found == null)
			return null;
		if (!found.getStatus().equals(OrderStatus.PROCESSING))
			return null;
		found.setStatus(OrderStatus.CANCELLED);
		// !!!!!!!!!!!!!!!!!!!!!!!!
		found.getProducts().stream().forEach(x -> {
			Product prod = prodDao.getByName(x.getProduct());
			prod.setStock(prod.getStock() + x.getQuant());
		});
		// !!!!!!!!!!!!!!!!!!!!!!!!
		return orderRepo.save(found);
	}

	@Override
	public Order updateOrder(Order order) {
		if (order == null)
			return null;
		Order found = orderRepo.findById(order.getId()).orElse(null);
		if (found == null)
			return null;
		order.setId(found.getId());
		return orderRepo.save(order);
	}

	@Override
	public List<Order> findByCustomer(User customer) {
		if (customer == null)
			return null;
		return orderRepo.findByCustomerUsername(customer.getUsername());
	}

}