package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;


    @Test
    public void whenRetrieveStock_thenReturnStock() {
        Long stockId = 1L;
        Stock stock = new Stock();
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        Stock found = stockService.retrieveStock(stockId);

        assertThat(found, is(notNullValue()));
        verify(stockRepository).findById(stockId);
    }

    @Test
    public void whenRetrieveAllStocks_thenReturnAllStocks() {
        List<Stock> stockList = Arrays.asList(new Stock(), new Stock());
        when(stockRepository.findAll()).thenReturn(stockList);

        List<Stock> stocks = stockService.retrieveAllStock();

        assertThat(stocks, hasSize(2));
        verify(stockRepository).findAll();
    }




}