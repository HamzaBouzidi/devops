package tn.esprit.devops_project.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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

    @Before
    public void setUp() {
        // Set up your test data here
        stock = new Stock(/* parameters for stock */);
        product = new Product(/* parameters for product */);
        product.setStock(stock);
        productList = Arrays.asList(product); // Add more products if necessary
    }

    @Test
    public void whenAddProduct_thenSaveProduct() {
        Long stockId = 1L;
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product created = productService.addProduct(product, stockId);

        assertThat(created, is(notNullValue()));
        assertThat(created.getStock(), is(stock));
        verify(stockRepository).findById(stockId);
        verify(productRepository).save(product);
    }

    @Test
    public void whenRetrieveProduct_thenProductShouldBeFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product found = productService.retrieveProduct(productId);

        assertThat(found, is(notNullValue()));
        assertThat(found, is(product));
        verify(productRepository).findById(productId);
    }

    @Test(expected = NullPointerException.class)
    public void whenRetrieveProductNotPresent_thenThrowException() {
        Long productId = 2L;
        when(productRepository.findById(productId)).thenThrow(new NullPointerException("Product not found"));

        productService.retrieveProduct(productId);
    }

    @Test
    public void whenRetrieveAllProducts_thenAllProductsShouldBeFound() {
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> foundProducts = productService.retreiveAllProduct();

        assertThat(foundProducts, is(notNullValue()));
        assertThat(foundProducts.size(), is(productList.size()));
        verify(productRepository).findAll();
    }

    @Test
    public void whenRetrieveProductsByCategory_thenFilteredProductsShouldBeFound() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        when(productRepository.findByCategory(category)).thenReturn(productList);

        List<Product> foundProducts = productService.retrieveProductByCategory(category);

        assertThat(foundProducts, is(notNullValue()));
        assertThat(foundProducts, is(productList));
        verify(productRepository).findByCategory(category);
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
        Long stockId = 1L;
        when(productRepository.findByStockIdStock(stockId)).thenReturn(productList);

        List<Product> foundProducts = productService.retreiveProductStock(stockId);

        assertThat(foundProducts, is(notNullValue()));
        assertThat(foundProducts.size(), is(productList.size()));
        verify(productRepository).findByStockIdStock(stockId);
    }
}