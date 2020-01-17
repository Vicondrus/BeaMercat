package com.project.services.distributionstrategies;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.project.entities.Order;
import com.project.entities.OrderStatus;
import com.project.entities.User;

//take the courier with the least orders
public class LeastOrdersStrategy implements DistributionStrategy {

	@Override
	public User selectCourier(Order order, List<User> couriers) {
		return couriers.stream()
				.sorted(Comparator.comparing((User x) -> x.getCourierOrders().stream()
						.filter(y -> y.getStatus().equals(OrderStatus.PROCESSING)).collect(Collectors.toList()).size()))
				.findFirst().orElse(null);
	}

}
