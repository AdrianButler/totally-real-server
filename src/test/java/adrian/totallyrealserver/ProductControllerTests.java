package adrian.totallyrealserver;

import adrian.totallyrealserver.models.Product;
import adrian.totallyrealserver.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
//@Import(SecurityConfig.class)
public class ProductControllerTests
{
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testGetFeaturedProduct() throws Exception
	{
		MvcResult results = mockMvc.perform(MockMvcRequestBuilders.get("/product/featured"))
				.andReturn();
		String responseBody = results.getResponse()
				.getContentAsString();

		Product recievedProduct = objectMapper.readValue(responseBody, Product.class);
		Product expectedProduct = productRepository.findById(1L).get();

		assertEquals(recievedProduct, expectedProduct);
	}

	@Test
	public void testGetProduct() throws Exception
	{
		MvcResult results = mockMvc.perform(MockMvcRequestBuilders.get("/product/1"))
				.andReturn();
		String responseBody = results.getResponse()
				.getContentAsString();

		Product recievedProduct = objectMapper.readValue(responseBody, Product.class);
		Product expectedProduct = productRepository.findById(1L).get();

		assertEquals(recievedProduct, expectedProduct);
	}
}
