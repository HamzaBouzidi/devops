package tn.esprit.devops_project.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.devops_project.entities.Stock;

import java.util.Optional;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void whenSaveStock_thenGetStock() {
        // Create a new Stock instance and set its properties
        Stock stock = new Stock();
        stock.setTitle("Electronics");
        // Assuming you have set other necessary fields

        Stock savedStock = stockRepository.save(stock);
        Optional<Stock> foundStock = stockRepository.findById(savedStock.getIdStock());
        assertThat(foundStock.isPresent(), is(true));
        assertThat(foundStock.get(), equalTo(savedStock));
    }

    @Test
    public void whenFindById_thenReturnStock() {
        Stock stock = new Stock();
        stock.setTitle("Electronics");
        Stock savedStock = stockRepository.save(stock);

        Optional<Stock> foundStock = stockRepository.findById(savedStock.getIdStock());
        assertThat(foundStock.isPresent(), is(true));
        assertThat(foundStock.get().getTitle(), is("Electronics"));
    }

    @Test
    public void whenFindAll_thenReturnListOfStock() {
        Stock stock1 = new Stock();
        stock1.setTitle("Electronics");
        Stock stock2 = new Stock();
        stock2.setTitle("Clothing");
        stockRepository.save(stock1);
        stockRepository.save(stock2);

        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList, hasSize(greaterThanOrEqualTo(2)));
    }

    @Test
    public void whenDeleteStock_thenStockShouldBeDeleted() {
        Stock stock = new Stock();
        stock.setTitle("Electronics");
        Stock savedStock = stockRepository.save(stock);

        stockRepository.delete(savedStock);

        Optional<Stock> deletedStock = stockRepository.findById(savedStock.getIdStock());
        assertThat(deletedStock.isPresent(), is(false));
    }

    @Test
    public void whenUpdateStock_thenStockShouldBeUpdated() {
        Stock stock = new Stock();
        stock.setTitle("Electronics");
        Stock savedStock = stockRepository.save(stock);

        savedStock.setTitle("Home Electronics");
        Stock updatedStock = stockRepository.save(savedStock);

        Optional<Stock> foundStock = stockRepository.findById(updatedStock.getIdStock());
        assertThat(foundStock.isPresent(), is(true));
        assertThat(foundStock.get().getTitle(), is("Home Electronics"));
    }
}