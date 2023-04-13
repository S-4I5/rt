package com.example.rt;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RtApplicationTests {
    private final MockMvc mockMvc;

    private String token;

    @Autowired
    public RtApplicationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private void register() throws Exception {
        String email = "sus331@gmail.com";

        var registerRequest = mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"email\": \"" + email + "\", \"password\": \"1234566\" }"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        token = JsonPath.read(registerRequest.getResponse().getContentAsString(), "$.token");
    }

    private void postCheckNews(int idShouldCreateWith) throws Exception {
        mockMvc.perform(
                        post("/news")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                .andExpect(jsonPath("$.id").value(idShouldCreateWith));
    }

    @Test
    public void testNews() throws Exception {
        register();

        postCheckNews(1);
        postCheckNews(2);

        mockMvc.perform(
                get("/news?pageSize=1&pageNo=1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("2"));
    }

    private void checkFirstPlannedActivity(String requiredState) throws Exception {
        mockMvc.perform(
                        get("/planned-activities?pageSize=1&pageNo=0")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].state").value(requiredState));
    }

    @Test
    public void testPlannedActivities() throws Exception {
        register();

        mockMvc.perform(
                        post("/planned-activities")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                            {
                                                "title": "titl",
                                                "description": "desc",
                                                "placeName": "place",
                                                "photo": "photo",
                                                "plannedDate": "2022-04-02",
                                                "authorId": 1
                                            }
                                        """
                                ))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        checkFirstPlannedActivity("IN_REVIEWING");

        mockMvc.perform(
                        put("/planned-activities/1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON));

        checkFirstPlannedActivity("APPROVED");
    }

    
}
