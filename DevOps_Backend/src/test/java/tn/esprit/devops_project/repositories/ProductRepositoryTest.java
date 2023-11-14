package tn.esprit.devops_project.repositories;

import org.junit.jupiter.api.BeforeEach;
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
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    private Stock stock;

    @BeforeEach


    public void setUp() {
        // Assuming Stock entity has an all-args constructor
        stock = new Stock(/* parameters */);
        stock = stockRepository.save(stock);
    }

    @Test
    public void whenFindByCategory_thenReturnProducts() {
        // given
        Product product = new Product(/* parameters */);
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setStock(stock);
        productRepository.save(product);

        // when
        List<Product> foundProducts = productRepository.findByCategory(ProductCategory.ELECTRONICS);

        // then
        assertEquals(1, foundProducts.size());
        assertEquals(product.getIdProduct(), foundProducts.get(0).getIdProduct());
    }

    @Test
    public void whenFindByStockId_thenReturnProducts() {
        // given
        Product product = new Product(/* parameters */);
        product.setStock(stock);
        productRepository.save(product);

        // when
        List<Product> foundProducts = productRepository.findByStockIdStock(stock.getIdStock());

        // then
        assertTrue(foundProducts.stream().anyMatch(p -> p.getIdProduct().equals(product.getIdProduct())));
    }

    @Test
    public void whenAddProduct_thenProductIsAdded() {
        // given
        Product product = new Product(/* parameters */);
        product.setStock(stock);

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertEquals(product.getIdProduct(), savedProduct.getIdProduct());
    }

    @Test
    public void whenDeleteProduct_thenProductIsDeleted() {
        // given
        Product product = new Product(/* parameters */);
        product.setStock(stock);
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getIdProduct();

        // when
        productRepository.delete(savedProduct);

        // then
        assertTrue(!productRepository.existsById(productId));
    }

}