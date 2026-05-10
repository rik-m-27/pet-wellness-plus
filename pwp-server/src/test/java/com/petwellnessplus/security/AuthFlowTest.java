package com.petwellnessplus.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest 
@AutoConfigureMockMvc
@ActiveProfiles({"test", "test-local"})
public class AuthFlowTest {

		@Autowired MockMvc mockMvc;
		
		@Test
		void fullAuthFlow() throws Exception{
			
			// sign up flow for user
			mockMvc.perform(post("/auth/signup/user")
						.contentType("application/json")
						.content(
							"""
					          {"username":"usertest@pwp.com","password":"user$pwp123"}
						    """))
						.andExpect(status().is2xxSuccessful());
			
			// sign up flow for doctor
			mockMvc.perform(post("/auth/signup/doctor")
						.contentType("application/json")
						.content(
							"""
					          {"username":"doctortest@pwp.com","password":"doctor$pwp123"}
						    """))
						.andExpect(status().is2xxSuccessful());
			
			
			// user login1
		      MvcResult userResult1 = mockMvc.perform(post("/auth/login")
		           .contentType("application/json")
		           .content(
							"""
					          {"username":"usertest@pwp.com","password":"user$pwp123"}
						    """))
		           .andExpect(status().isOk())
		           .andReturn();

		      String userToken1 = extractToken(userResult1);
		      
		   // user login2
		      MvcResult userResult2 = mockMvc.perform(post("/auth/login")
		           .contentType("application/json")
		           .content(
							"""
					          {"username":"usertest@pwp.com","password":"user$pwp123"}
						    """))
		           .andExpect(status().isOk())
		           .andReturn();

		      String userToken2 = extractToken(userResult2);
		      
		   // doctor login
		      MvcResult doctorLogin = mockMvc.perform(post("/auth/login")
		           .contentType("application/json")
		           .content(
							"""
					          {"username":"doctortest@pwp.com","password":"doctor$pwp123"}
						    """))
		           .andExpect(status().is4xxClientError())
		           .andReturn();
		      printTheResult("Result of doctorLogin: \n", doctorLogin);
		      
		   // logout of userToken1
		      MvcResult userLogout1 = mockMvc.perform(post("/auth/logout")
		           .header("Authorization","Bearer "+userToken1))
		           .andExpect(status().is4xxClientError())
		           .andReturn();
		      printTheResult("Result of userToken1: \n", userLogout1);
		      
		   // logout of userToken2
		      MvcResult userLogout2 = mockMvc.perform(post("/auth/logout")
		           .header("Authorization","Bearer "+userToken2))
		           .andExpect(status().is2xxSuccessful())
		           .andReturn();
		      printTheResult("Result of userToken2: \n", userLogout2);
			
		}
		
		private String extractToken(MvcResult result) throws Exception {
		    String json = result.getResponse().getContentAsString();

		    ObjectMapper mapper = new ObjectMapper();
		    JsonNode node = mapper.readTree(json);

		    return node.get("data").asText();
		}
		
		private void printTheResult(String str, MvcResult result) throws Exception{
			String json = result.getResponse().getContentAsString();
			System.out.println("\n***********"+str + json+"\n");
		}
	
}
