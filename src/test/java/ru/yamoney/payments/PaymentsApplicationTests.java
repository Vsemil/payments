package ru.yamoney.payments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = "/create-payments.sql")
public class PaymentsApplicationTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAllTest() throws Exception {
        this.mockMvc.perform(get("/allPayments")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.length()", is(4)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].senderUser.id").exists())
                .andExpect(jsonPath("$[0].recipientUser.id").exists())
                .andExpect(jsonPath("$[0].amount").exists());
    }

    @Test
    public void addPaymentTest() throws Exception {
        this.mockMvc.perform(post("/addPayment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "senderUserId", "2",
                        "recipientUserId", "3",
                        "amount", "5000")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.senderUser.id", is(2)))
                .andExpect(jsonPath("$.recipientUser.id", is(3)))
                .andExpect(jsonPath("$.amount", is(5000)));

        this.mockMvc.perform(get("/allPayments")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.length()", is(5)));
    }

    @Test
    public void addPaymentNotSenderUserFailTest() throws Exception {
        this.mockMvc.perform(post("/addPayment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "recipientUserId", "3",
                        "amount", "5000")))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void addPaymentOneUserFailTest() throws Exception {
        this.mockMvc.perform(post("/addPayment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "senderUserId", "2",
                        "recipientUserId", "2",
                        "amount", "5000")))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void addPaymentNotRecipientUserFailTest() throws Exception {
        this.mockMvc.perform(post("/addPayment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "senderUserId", "3",
                        "amount", "5000")))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void addPaymentNotAmountUserFailTest() throws Exception {
        this.mockMvc.perform(post("/addPayment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "senderUserId", "3",
                        "recipientUserId", "2")))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void addPaymentUserNotFoundFailTest() throws Exception {
        this.mockMvc.perform(post("/addPayment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "senderUserId", "147",
                        "recipientUserId", "2",
                        "amount", "1.30")))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void getAmountOfExpensesTest() throws Exception {
        this.mockMvc.perform(get("/amountOfExpenses").param("userId", "2")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.userId", is(2)))
                .andExpect(jsonPath("$.amount", is(0.3000)));
    }

    @Test
    public void getAmountOfExpensesNotUserIdFailTest() throws Exception {
        this.mockMvc.perform(get("/amountOfExpenses")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void getAmountOfExpensesUserNotFoundFailTest() throws Exception {
        this.mockMvc.perform(get("/amountOfExpenses").param("userId", "147")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void getAmountOfExpensesAfterAddPaymentTest() throws Exception {
        this.mockMvc.perform(post("/addPayment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "senderUserId", "2",
                        "recipientUserId", "3",
                        "amount", "5000")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        this.mockMvc.perform(get("/amountOfExpenses").param("userId", "2")
                .accept(parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.userId", is(2)))
                .andExpect(jsonPath("$.amount", is(5000.3000)));
    }

    private String buildUrlEncodedFormEntity(String... params) {
        if( (params.length % 2) > 0 ) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i=0; i<params.length; i+=2) {
            if( i > 0 ) {
                result.append('&');
            }
            try {
                result.append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name()))
                        .append('=')
                        .append(URLEncoder.encode(params[i+1], StandardCharsets.UTF_8.name()));
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }

}
