package br.com.bigdelivery;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.com.bigdelivery.model.Cousine;
import br.com.bigdelivery.model.Customer;
import br.com.bigdelivery.model.Order;
import br.com.bigdelivery.model.Product;
import br.com.bigdelivery.model.Store;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    
    private MockMvc mockMvc;
    
    private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;
    
    private Customer customer, customer2;
    
    private Order order;
    
    private List<Product> productList = new ArrayList<>();

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    void setConverters(HttpMessageConverter<Object>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        
		//PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
		//String password = PASSWORD_ENCODER.encode("admin");
 
        this.customer = new Customer("henrique", "Rua General Edmundo Gastão da Cunha", "riquefsouza@gmail.com",// password);
        		"$2a$10$y7jArsSYCAJjIudWb6zbkuMQZxNFGePkmYJQM0ChB4slgwtUG9RLy");
        this.customer.setId(1L);
        
        Cousine s1 = new Cousine("cousine number 1");
        Store r1 = new Store("Princesa da Lapa", "alberto", "21 34430434", "alberto@teste.com", "Rua da Lapa, 128", 2, s1);
        
        Product p1 = new Product("pizza mussarela", "super pizza mussarela", new BigDecimal(20.34), r1);
        p1.setId(1L);
        productList.add(p1);
        
        Product p2 = new Product("spaghetti", "super spaghetti", new BigDecimal(10.34), r1);
        p2.setId(2L);
        productList.add(p2);        
        
        this.order = new Order("contact2","rua velha","new", this.customer, r1);
         
        //String password2 = PASSWORD_ENCODER.encode("123");
        
        this.customer2 = new Customer("laura", "Rua Nova", "laura@yahoo.com",// password2);
        		"$2a$10$y7jArsSYCAJjIudWb6zbkuMQZxNFGePkmYJQM0ChB4slgwtUG9RLy");
        this.customer2.setId(2L);
    }
    
    @Test
    public void a_readSingleCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/Customer/"
                + this.customer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.customer.getId().intValue())))
                .andExpect(jsonPath("$.name", is("henrique")));
    }
        
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
    
    @Test
    public void b_authCustomer() throws Exception {
        mockMvc.perform(post("/api/v1/Customer/auth/"+this.customer.getEmail()+"/"+this.customer.getPassword())
                .content(this.json(this.customer))
                .contentType(contentType))
                .andExpect(status().isOk());
    }
        
    @Test
    public void c_queryProducts() throws Exception {
        mockMvc.perform(get("/api/v1/Product/search/super"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.productList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(this.productList.get(0).getName())))
                .andExpect(jsonPath("$[0].description", is(this.productList.get(0).getDescription())))
                .andExpect(jsonPath("$[1].id", is(this.productList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(this.productList.get(1).getName())))
                .andExpect(jsonPath("$[1].description", is(this.productList.get(1).getDescription())));
    }
    
    //Receive Orders;
    @Test
    public void d_receiveOrders() throws Exception {
        mockMvc.perform(post("/api/v1/Order")
                .content(this.json(this.order))
                .contentType(contentType))
                .andExpect(status().isOk());
    }
    
    //Status Cancel Order;
    @Test
    public void e_cancelStatusOrder() throws Exception {
        mockMvc.perform(get("/api/v1/Order/cancelStatus/1"))
                .andExpect(status().isOk());
    }
    
    //Get Order Status;
    @Test
    public void f_getOrderStatus() throws Exception {
        mockMvc.perform(get("/api/v1/Order/status/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("CANCEL"));
    }
    
    //Cancel Order;
    @Test
    public void g_cancelOrder() throws Exception {
        mockMvc.perform(delete("/api/v1/Order/cancel/1"))
                .andExpect(status().isNoContent());
    }
    
    //Post Costumer;
    @Test
    public void h_postCustomer() throws Exception {
        mockMvc.perform(post("/api/v1/Customer")
                .content(this.json(this.customer2))
                .contentType(contentType))
                .andExpect(status().isOk());
    }
}
