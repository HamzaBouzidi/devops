package tn.esprit.devops_project.services;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    // Existing test for addStock method

    @Test
    public void whenRetrieveStock_thenReturnStock() {
        Long stockId = 1L;
        Stock stock = new Stock(); // Initialize stock with the correct parameters
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        Stock found = stockService.retrieveStock(stockId);

        assertThat(found, is(notNullValue()));
        verify(stockRepository).findById(stockId);
    }

    @Test
    public void whenRetrieveAllStocks_thenReturnAllStocks() {
        List<Stock> stockList = Arrays.asList(new Stock(), new Stock()); // Initialize stocks with the correct parameters
        when(stockRepository.findAll()).thenReturn(stockList);

        List<Stock> stocks = stockService.retrieveAllStock();

        assertThat(stocks, hasSize(2));
        verify(stockRepository).findAll();
    }




}