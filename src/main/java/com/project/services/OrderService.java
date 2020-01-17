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
import com.project.services.distributionstrategies.LeastOrdersinCityStrategy;
import com.project.services.interfaces.OrderServiceI;
import com.project.services.interfaces.ProductServiceI;
import com.project.services.interfaces.UserServiceI;

//service class that wraps the data access for order
//and provides application logic

@Service
public class OrderService implements OrderServiceI {

	//repository bean instantiated at runtime
	@Autowired
	private OrderRepository orderRepo;

	//service beans
	@Autowired
	private ProductServiceI prodDao;

	@Autowired
	private UserServiceI userDao;

	//strategy
	private DistributionStrategy strategy;

	
	//adds a new order to the database
	//sends it to the courier selecting the strategy
	//and checks logic
	@Override
	public Order createOrder(User user, Address address) {
		if (user == null)
			return null;
		if (address == null)
			return null;
		Order o;
		if (address.getCountry() == null || address.getCountry().equals("") || address.getCity() == null
				|| address.getCity().equals("") || address.getStreet() == null || address.getStreet().equals("")
				|| address.getNumber() == null || address.getZipCode() == null || address.getZipCode().equals("")) {
			o = new Order(user, user.getAddress());
			address = o.getAddress();
		} else
			o = new Order(user, address);
		o.setStatus(OrderStatus.PROCESSING);
		selectStrategy(address);
		o.setCourierName(strategy.selectCourier(o, userDao.getByRole(UserType.COURIER)).getUsername());
		o = orderRepo.save(o);
		userDao.addCourierOrder(strategy, o);
		return o;
	}

	//strategy selector function
	private void selectStrategy(Address address) {
		for (User u : userDao.getByRole(UserType.COURIER)) {
			if (u.getAddress().getCity().equals(address.getCity())) {
				strategy = new LeastOrdersinCityStrategy();
				return;
			}
		}
		strategy = new LeastOrdersStrategy();

	}

	
	//changes the order's status to COMPLETED
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

	//changes the order status to CANCELED
	//done by the admin
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
		found.setStatus(OrderStatus.CANCELLED);
		for (Order o : courier.getCourierOrders()) {
			if (o.getId().equals(found.getId()))
				o.setStatus(found.getStatus());
			;
		}
		userDao.updateUser(courier);
		// !!!!!!!!!!!!!!!!!!!!!!!!
		return orderRepo.save(found);
	}

	//request cancellation method
	//done by user
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
		userDao.updateUser(courier);
		return orderRepo.save(found);
	}

	//update the order's details
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
