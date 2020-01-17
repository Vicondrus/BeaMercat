package com.project.services.distributionstrategies;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.project.entities.Order;
import com.project.entities.OrderStatus;
import com.project.entities.User;

//take the courier found in the same city as the order, with the least orders
//that were not yet delivered
public class LeastOrdersinCityStrategy implements DistributionStrategy {

	@Override
	public User selectCourier(Order order, List<User> couriers) {
		return couriers.stream().filter(x -> x.getAddress().getCity().equals(order.getAddress().getCity()))
				.sorted(Comparator.comparing((User x) -> x.getCourierOrders().stream()
						.filter(y -> y.getStatus().equals(OrderStatus.PROCESSING)).collect(Collectors.toList()).size(),
						Comparator.reverseOrder()))
				.findFirst().orElse(null);
	}

}
