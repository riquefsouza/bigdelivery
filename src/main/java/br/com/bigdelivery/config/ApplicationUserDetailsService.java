package br.com.bigdelivery.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.bigdelivery.model.Customer;
import br.com.bigdelivery.repo.CustomerRepository;

@Component
public class ApplicationUserDetailsService implements UserDetailsService {

	private final CustomerRepository customerRepo;

	@Autowired
	public ApplicationUserDetailsService(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		Optional<Customer> customer = customerRepo.findByName(name);
		
		//user: henrique
		//password: admin
		
		return new User(customer.get().getName(), customer.get().getPassword(),
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		
		
		//return new User("henrique", "$2a$10$y7jArsSYCAJjIudWb6zbkuMQZxNFGePkmYJQM0ChB4slgwtUG9RLy",
			//AuthorityUtils.createAuthorityList("ROLE_USER"));		
	}

}
