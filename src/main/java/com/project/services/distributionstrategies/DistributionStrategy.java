package com.project.services.distributionstrategies;

import java.util.List;

import com.project.entities.Order;
import com.project.entities.User;

//strategy interface (used in strategy design pattern)
//used to delegate orders to couriers
public interface DistributionStrategy {

	public User selectCourier(Order order, List<User> couriers);
	
}
