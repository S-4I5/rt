package com.example.rt;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RtApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void postGetNews() throws Exception {
        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
        System.out.println("output:");
        
        var email = "sus331@gmail.com";

        var registerRequest = mockMvc.perform(
                post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"" + email + "\", \"password\": \"1234566\" }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        var token = JsonPath.read(registerRequest.getResponse().getContentAsString(), "$.token");

        mockMvc.perform(
                post("/news")
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                    "title": "oooosmg",
                                    "description": "post about",
                                    "photo": "sun.userapi"
                                }
                                """
                        ))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));

        //get news..

        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
        System.out.println("AAAAAAAAA TEST AAAAAAAAAAAAa");
    }
}
