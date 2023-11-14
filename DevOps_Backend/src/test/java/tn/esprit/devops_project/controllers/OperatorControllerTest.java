package tn.esprit.devops_project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.Iservices.IOperatorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OperatorController.class)
public class OperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOperatorService operatorService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getOperators_ShouldReturnOperatorsList() throws Exception {
        List<Operator> operators = Arrays.asList(
                new Operator(1L, "Ipsum", "Lorem", "12345678", null),
                new Operator(2L, "Ipsum2", "Lorem", "password321", null)
        );

        Mockito.when(operatorService.retrieveAllOperators()).thenReturn(operators);

        mockMvc.perform(get("/operator"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(operators.size())))
                .andExpect(jsonPath("$[0].fname", is("Ipsum")))
                .andExpect(jsonPath("$[1].fname", is("Ipsum2")));
    }

    @Test
    public void retrieveOperator_ShouldReturnOperator() throws Exception {
        Operator operator = new Operator(1L, "Ipsum", "Lorem", "12345678", null);
        Mockito.when(operatorService.retrieveOperator(1L)).thenReturn(operator);

        mockMvc.perform(get("/operator/{operatorId}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idOperateur", is(1))) 
                .andExpect(jsonPath("$.fname", is("Ipsum")))
                .andExpect(jsonPath("$.lname", is("Lorem")));
    }

    @Test
    public void addOperator_ShouldCreateOperator() throws Exception {
        Operator operator = new Operator(null, "Ipsum", "Lorem", "12345678", null);
        Operator savedOperator = new Operator(1L, "Ipsum", "Lorem", "12345678", null);

        Mockito.when(operatorService.addOperator(any(Operator.class))).thenReturn(savedOperator);

        mockMvc.perform(post("/operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.idOperateur", is(1)))
                .andExpect(jsonPath("$.fname", is("Ipsum")))
                .andExpect(jsonPath("$.lname", is("Lorem")));

        verify(operatorService).addOperator(any(Operator.class));
    }

    @Test
    public void removeOperator_ShouldDeleteOperator() throws Exception {
        mockMvc.perform(delete("/operator/{operatorId}", 1L))
                .andExpect(status().isOk()); 

        verify(operatorService).deleteOperator(1L);
    }

}