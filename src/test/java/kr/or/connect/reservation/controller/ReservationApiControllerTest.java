package kr.or.connect.reservation.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.or.connect.reservation.config.ApplicationConfig;
import kr.or.connect.reservation.config.MvcConfig;
import kr.or.connect.reservation.service.CategoryService;
import kr.or.connect.reservation.service.ReservationService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MvcConfig.class, ApplicationConfig.class })
public class ReservationApiControllerTest {
	@InjectMocks
	public ReservationApiController reservationApiController;

	@Mock
	CategoryService categoryService;

	@Mock
	ReservationService reservationService;
	
	private MockMvc mockMvc;

	@Before
	public void createController() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(reservationApiController).build();
	}

	@Test
	public void getCategorylist() throws Exception {
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/categories").contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void getProductList() throws Exception {
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/api/displayinfos").contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(reqBuilder).andExpect(status().isOk()).andDo(print());
	}
}
