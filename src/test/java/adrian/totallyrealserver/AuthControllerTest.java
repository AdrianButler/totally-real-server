package adrian.totallyrealserver;

import adrian.totallyrealserver.dtos.auth.SignUpRequest;
import adrian.totallyrealserver.exceptions.UserAlreadyExistsException;
import adrian.totallyrealserver.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest
{
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AuthService mockAuthService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testCreateUserMapping() throws Exception
	{
		SignUpRequest signUpRequest = new SignUpRequest();

		doNothing().when(mockAuthService)
		           .createUser(any());

		mockMvc.perform(post("/auth/signup").content(objectMapper.writeValueAsString(signUpRequest))
		                                    .contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isCreated());
	}

	@Test
	public void testCreateUserThrowsException() throws Exception
	{
		SignUpRequest signUpRequest = new SignUpRequest();

		doThrow(UserAlreadyExistsException.class).when(mockAuthService)
		                                         .createUser(any());

		mockMvc.perform(post("/auth/signup").content(objectMapper.writeValueAsString(signUpRequest))
		                                    .contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isConflict());
	}

	@Test
	public void testCreateUserCallsService() throws Exception
	{
		SignUpRequest signUpRequest = new SignUpRequest();

		doNothing().when(mockAuthService)
		           .createUser(any());

		mockMvc.perform(post("/auth/signup").content(objectMapper.writeValueAsString(signUpRequest))
		                                    .contentType(MediaType.APPLICATION_JSON))
		       .andReturn();

		verify(mockAuthService, times(1)).createUser(any());
	}
}
