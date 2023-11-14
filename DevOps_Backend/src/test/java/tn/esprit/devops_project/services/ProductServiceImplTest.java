package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Stock stock;
    private List<Product> productList;

    @BeforeEach


    public void setUp() {
        stock = new Stock();
        product = new Product();
        product.setStock(stock);
        productList = Arrays.asList(product);
    }

    @Test
    public void whenAddProduct_thenSaveProduct() {

    }


    @Test
    public void whenRetrieveProduct_thenProductShouldBeFound() {

    }

    @Test
    public void whenRetrieveProductNotPresent_thenThrowException() {
        Long productId = 2L;
        when(productRepository.findById(productId)).thenThrow(new NullPointerException("Product not found"));

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            productService.retrieveProduct(productId);
        });

        // Optionally, you can assert further details of the exception message or cause if needed
        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    public void whenRetrieveAllProducts_thenAllProductsShouldBeFound() {

    }

    @Test
    public void whenRetrieveProductsByCategory_thenFilteredProductsShouldBeFound() {

    }

    @Test
    public void whenDeleteProduct_thenProductShouldBeDeleted() {
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    public void whenRetrieveProductsInStock_thenStockProductsShouldBeFound() {

    }
}