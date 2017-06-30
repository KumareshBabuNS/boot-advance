package com.example.workshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WorkshopApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	PersonRepository pr;

	@Test
	public void getAPerson() throws Exception {

		String fn = "Vince";
		String ln = "Russo";

		Person person = new Person(fn,ln);
		Person expected = pr.save(person);


		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}",expected.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		Person actual = mapper.readValue(result.getResponse().getContentAsString(), Person.class);

		assertThat(actual).isNotNull();
		assertThat(actual.getFirstName()).isEqualTo(fn);
		assertThat(actual.getLastName()).isEqualTo(ln);

	}

	@Test
	public void getAnotherPerson() throws Exception {

		String fn = "Someone";
		String ln = "Else";

		Person person = new Person(fn,ln);
		Person expected = pr.save(person);


		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}",expected.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		Person actual = mapper.readValue(result.getResponse().getContentAsString(), Person.class);

		assertThat(actual).isNotNull();
		assertThat(actual.getFirstName()).isEqualTo(fn);
		assertThat(actual.getLastName()).isEqualTo(ln);

	}


}
