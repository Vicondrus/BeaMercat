package com.project.services.distributionstrategies;

import java.util.List;

import com.project.entities.Order;
import com.project.entities.User;

public interface DistributionStrategy {

	public User selectCourier(Order order, List<User> couriers);
	
}
