package adrian.totallyrealserver;

import adrian.totallyrealserver.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class TotallyRealServerApplicationTests
{
	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();
	@Test
	void testGetProduct() throws Exception
	{
			MvcResult result = mockMvc.perform(get("/product/1")).andReturn();

			int status = result.getResponse().getStatus();
			assertEquals(status, 200);

			String content = result.getResponse().getContentAsString();

			Product returnedProduct = objectMapper.readValue(content, Product.class);

			assertNotNull(returnedProduct);

			assertNotNull(returnedProduct.getId());
			assertNotNull(returnedProduct.getDescription());
			assertNotNull(returnedProduct.getName());
			assertNotNull(returnedProduct.getImages());
			assertNotEquals(returnedProduct.getPrice(), 0);
			assertNotEquals(returnedProduct.getRating(), 0);
	}

	@Test
	void testGetFeaturedProduct() throws Exception
	{
		MvcResult result = mockMvc.perform(get("/featured-product")).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(status, 200);

		String content = result.getResponse().getContentAsString();

		Product returnedProduct = objectMapper.readValue(content, Product.class);

		assertNotNull(returnedProduct);

		assertNotNull(returnedProduct.getId());
		assertNotNull(returnedProduct.getDescription());
		assertNotNull(returnedProduct.getName());
		assertNotNull(returnedProduct.getImages());
		assertNotEquals(returnedProduct.getPrice(), 0);
		assertNotEquals(returnedProduct.getRating(), 0);
	}
}
