package com.project.services.distributionstrategies;

import java.util.Comparator;
import java.util.List;

import com.project.entities.Order;
import com.project.entities.User;

public class LeastOrdersinCityStrategy implements DistributionStrategy {

	@Override
	public User selectCourier(Order order, List<User> couriers) {
		return couriers.stream().filter(x -> x.getAddress().getCity().equals(order.getAddress().getCity()))
				.sorted(Comparator.comparing((User x) -> x.getCourierOrders().size(), Comparator.reverseOrder()))
				.findFirst().orElse(null);
	}

}
