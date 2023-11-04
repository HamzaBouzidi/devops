package tn.esprit.devops_project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.services.Iservices.IStockService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Hamcrest matchers
import static org.hamcrest.Matchers.is;

// If you use verify
import static org.mockito.Mockito.verify;
@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStockService stockService;

    @Test
    public void whenPostStock_thenCreateStock() throws Exception {
        Stock newStock = new Stock(); // assuming other properties
        newStock.setTitle("New Stock"); // Set the title to match the expected value in the assertion

        Mockito.when(stockService.addStock(any(Stock.class))).thenReturn(newStock);

        mockMvc.perform(post("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newStock)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Stock")));

        verify(stockService).addStock(any(Stock.class));
    }

    @Test
    public void whenGetStockById_thenRetrieveStock() throws Exception {
        Stock existingStock = new Stock();
        existingStock.setTitle("Existing Stock");
        long stockId = 1L;
        existingStock.setIdStock(stockId);

        Mockito.when(stockService.retrieveStock(stockId)).thenReturn(existingStock);

        mockMvc.perform(get("/stock/{id}", stockId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStock", is((int) stockId)))
                .andExpect(jsonPath("$.title", is(existingStock.getTitle())));

        verify(stockService).retrieveStock(stockId);
    }

    @Test
    public void whenGetAllStocks_thenRetrieveStocksList() throws Exception {
        Stock stock1 = new Stock(1L, "Stock1", null); // assuming constructor with args
        Stock stock2 = new Stock(2L, "Stock2", null);
        List<Stock> allStocks = Arrays.asList(stock1, stock2);

        Mockito.when(stockService.retrieveAllStock()).thenReturn(allStocks);

        mockMvc.perform(get("/stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idStock", is((int) stock1.getIdStock())))
                .andExpect(jsonPath("$[0].title", is(stock1.getTitle())))
                .andExpect(jsonPath("$[1].idStock", is((int) stock2.getIdStock())))
                .andExpect(jsonPath("$[1].title", is(stock2.getTitle())));

        verify(stockService).retrieveAllStock();
    }

}
