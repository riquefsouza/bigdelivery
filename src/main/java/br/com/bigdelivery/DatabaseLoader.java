package br.com.bigdelivery;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.bigdelivery.model.Cousine;
import br.com.bigdelivery.model.Customer;
import br.com.bigdelivery.model.Order;
import br.com.bigdelivery.model.OrderItem;
import br.com.bigdelivery.model.Product;
import br.com.bigdelivery.model.Store;
import br.com.bigdelivery.repo.CousineRepository;
import br.com.bigdelivery.repo.CustomerRepository;
import br.com.bigdelivery.repo.OrderItemRepository;
import br.com.bigdelivery.repo.OrderRepository;
import br.com.bigdelivery.repo.ProductRepository;
import br.com.bigdelivery.repo.StoreRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final CustomerRepository customerRepo;
	private final ProductRepository productRepo;
	private final OrderItemRepository orderItemRepo;
	private final OrderRepository orderRepo;
	private final StoreRepository storeRepo;
	private final CousineRepository cousineRepo;

	@Autowired
	public DatabaseLoader(CustomerRepository customerRepo, ProductRepository productRepo, OrderItemRepository orderItemRepo,
			OrderRepository orderRepo, StoreRepository storeRepo, CousineRepository cousineRepo) {
		this.customerRepo = customerRepo;
		this.productRepo = productRepo;
		this.orderItemRepo = orderItemRepo;
		this.orderRepo = orderRepo;
		this.storeRepo = storeRepo;
		this.cousineRepo = cousineRepo;
	}

	@Override
	public void run(String... strings) throws Exception {
		
		//user: henrique
		//password: admin

		//PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
		//String password = PASSWORD_ENCODER.encode("admin");
		
		Customer c1 = customerRepo.save(new Customer("henrique", "Rua General Edmundo Gastão da Cunha",
				"riquefsouza@gmail.com",// password)); 
				"$2a$10$y7jArsSYCAJjIudWb6zbkuMQZxNFGePkmYJQM0ChB4slgwtUG9RLy"));
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("henrique",
				"USUARIO COMUM", AuthorityUtils.createAuthorityList("ROLE_USER")));
		
		Cousine s1 = cousineRepo.save(new Cousine("cousine number 1"));
		cousineRepo.save(new Cousine("cousine nr 2"));

		Store r1 = storeRepo.save(new Store("Princesa da Lapa", "alberto", "21 34430434", "alberto@teste.com", "Rua da Lapa, 128", 2, s1));
		
		Product p1 = productRepo.save(new Product("pizza mussarela", "super pizza mussarela", new BigDecimal(20.34), r1));
		Product p2 =productRepo.save(new Product("spaghetti", "super spaghetti", new BigDecimal(10.34), r1));
		
		Order o1 = orderRepo.save(new Order("contact1","rua real","wait", c1, r1));
		
		orderItemRepo.save(new OrderItem(o1, p1, new BigDecimal(23.45), 2L));
		orderItemRepo.save(new OrderItem(o1, p2, new BigDecimal(13.45), 4L));
		
		SecurityContextHolder.clearContext();
	}
}
