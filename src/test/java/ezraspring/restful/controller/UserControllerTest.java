package ezraspring.restful.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ezraspring.restful.model.RegisterUserRequest;
import ezraspring.restful.model.WebResponse;
import ezraspring.restful.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder.*;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterUserRequest request=new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("password");
        request.setName("testname");

        mockMvc.perform(post("/api/users").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request))).andExpectAll(status().isOk()).andDo(result->{
            WebResponse<String>response=objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertEquals("OK",response.getData());
        });
    }

    @Test
    void testRegisterFailed() throws Exception{
        RegisterUserRequest request=new RegisterUserRequest();
        request.setUsername("");
        request.setPassword("");
        request.setName("");

        mockMvc.perform(post("/api/users").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request))).andExpectAll(status().isBadRequest()).andDo(result -> {WebResponse<String>response=objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertEquals("null",response.getData());
        });
    }





}