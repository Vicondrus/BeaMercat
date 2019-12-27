package com.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Address;
import com.project.entities.Order;
import com.project.entities.OrderStatus;
import com.project.entities.Product;
import com.project.entities.User;
import com.project.entities.UserType;
import com.project.repos.OrderRepository;
import com.project.services.distributionstrategies.DistributionStrategy;
import com.project.services.distributionstrategies.LeastOrdersStrategy;
import com.project.services.interfaces.OrderDaoI;
import com.project.services.interfaces.ProductDaoI;
import com.project.services.interfaces.UserDaoI;

@Service
public class OrderDao implements OrderDaoI {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private ProductDaoI prodDao;

	@Autowired
	private UserDaoI userDao;

	@Override
	public Order createOrder(User user, Address address) {
		if (user == null)
			return null;
		if (address == null)
			return null;
		Order o;
		if (address.getCountry() == null || address.getCountry().equals("") || address.getCity() == null
				|| address.getCity().equals("") || address.getStreet() == null || address.getStreet().equals("")
				|| address.getNumber() == null || address.getZipCode() == null || address.getZipCode().equals(""))
			o = new Order(user, user.getAddress());
		else
			o = new Order(user, address);
		o.setStatus(OrderStatus.PROCESSING);
		DistributionStrategy strategy = new LeastOrdersStrategy();
		o.setCourierName(strategy.selectCourier(o, userDao.getByRole(UserType.COURIER)).getUsername());
		o = orderRepo.save(o);
		userDao.addCourierOrder(strategy, o);
		return o;
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
		User courier = userDao.getByUsername(new User(found.getCourierName()));
		for (Order o : courier.getCourierOrders()) {
			if (o.getId().equals(found.getId()))
				o.setStatus(found.getStatus());
		}
		userDao.updateUser(courier);
		return orderRepo.save(found);
	}

	@Override
	public Order cancelOrder(Order order) {
		if (order == null)
			return null;
		Order found = orderRepo.findById(order.getId()).orElse(null);
		if (found == null)
			return null;
		if (!found.getStatus().equals(OrderStatus.CANCEL_REQUESTED))
			if (found.getStatus().equals(OrderStatus.PROCESSING))
				return requestCancellation(order);
		// !!!!!!!!!!!!!!!!!!!!!!!!
		found.getProducts().stream().forEach(x -> {
			Product prod = prodDao.getByName(x.getProduct());
			prod.setStock(prod.getStock() + x.getQuant());
			prodDao.updateProduct(prod);
		});
		User courier = userDao.getByUsername(new User(found.getCourierName()));
		courier.getCourierOrders().remove(found);
		found.setStatus(OrderStatus.CANCELLED);
		userDao.saveUser(courier);
		// !!!!!!!!!!!!!!!!!!!!!!!!
		return orderRepo.save(found);
	}

	private Order requestCancellation(Order order) {
		if (order == null)
			return null;
		Order found = orderRepo.findById(order.getId()).orElse(null);
		if (found == null)
			return null;
		if (!found.getStatus().equals(OrderStatus.PROCESSING))
			return null;
		found.setStatus(OrderStatus.CANCEL_REQUESTED);
		User courier = userDao.getByUsername(new User(found.getCourierName()));
		for (Order o : courier.getCourierOrders()) {
			if (o.getId().equals(found.getId()))
				o.setStatus(found.getStatus());
		}
		userDao.saveUser(courier);
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
	public List<Order> getAll() {
		return orderRepo.findAll();
	}

	@Override
	public List<Order> getByCustomer(User customer) {
		if (customer == null)
			return null;
		return orderRepo.findByCustomerUsername(customer.getUsername());
	}

	@Override
	public Order getById(Order order) {
		return orderRepo.findById(order.getId()).orElse(null);
	}

}
