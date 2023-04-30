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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RtApplicationTests {

    /*private final MockMvc mockMvc;

    private String email = "sus331@gmail.com";

    private String token;

    @Autowired
    public RtApplicationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private void register() throws Exception {
        var body = "{ \"email\": \"" + email + "\", \"password\": \"1234566\" }";

        var registerRequest = mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        token = JsonPath.read(registerRequest.getResponse().getContentAsString(), "$.token");
    }

    private void postCheckNews(int expectedIdOfCreatedNews) throws Exception {
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
                .andExpect(jsonPath("$.id").value(expectedIdOfCreatedNews));
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

    private void checkFirstPlannedActivity(String expectedState) throws Exception {
        mockMvc.perform(
                get("/planned-activities?pageSize=1&pageNo=0")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].state").value(expectedState));
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
                                        "plannedDate": "2022-04-02"
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

    private void checkFirstMembership(String expectedState) throws Exception {
        mockMvc.perform(
                        get("/applications?pageSize=1&pageNo=0")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].state").value(expectedState));
    }

    @Test
    public void testMembership() throws Exception {
        register();

        mockMvc.perform(
                        post("/applications")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                            "photo": "a.png"
                                        """
                                ))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        checkFirstMembership("IN_REVIEWING");

        mockMvc.perform(
                put("/applications/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON));

        checkFirstMembership("APPROVED");
    }

    private void postComment(int parentId) throws Exception {
        var body = "{\"email\": \"" + email + "\", \"message\": \"fuck\", \"parentId\": \"" + parentId + "\"}";
        mockMvc.perform(
                post("/news/1/comments")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));
    }

    @Test
    public void testComments() throws Exception {
        email = "sus1@gmail.com";
        register();

        postCheckNews(1);

        postComment(-1);

        email = "sus2@gmail.com";
        register();

        postComment(1);

        mockMvc.perform(
                get("/news/1/comments?pageSize=2&pageNo=0")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[1].parent").value(1))
                .andExpect(jsonPath("$[0].authorId").value(1))
                .andExpect(jsonPath("$[1].authorId").value(2))
                .andDo(print());
    }

    private void likeFirstNews() throws Exception {
        mockMvc.perform(
                post("/news/1/likes")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void testLikes() throws Exception {
        email = "sus1@gmail.com";
        register();

        postCheckNews(1);

        likeFirstNews();

        email = "sus2@gmail.com";
        register();

        likeFirstNews();

        mockMvc.perform(
                        get("/news/1/likes")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[1].userId").value(2));
    }*/
}
