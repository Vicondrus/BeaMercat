package com.project.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.entities.Status;
import com.project.entities.User;
import com.project.entities.UserType;
import com.project.services.UserDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		user.setUsername(username);
		user = userDao.getByUsername(user);
		if (user == null)
			throw new UsernameNotFoundException(username + " not found");
		if (user.getUserStatus().equals(Status.DELETED))
			throw new UsernameNotFoundException(username + " deleted");
		UserBuilder builder;
		builder = org.springframework.security.core.userdetails.User.withUsername(username);
		System.out.println(username);
		builder.password(user.getPassword());
		if (user.getUserType().equals(UserType.ADMIN))
			builder.roles("ADMIN");
		else if (user.getUserType().equals(UserType.COURIER))
			builder.roles("COURIER");
		else
			builder.roles("USER");
		return builder.build();
	}

}
