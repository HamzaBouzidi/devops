package tn.esprit.devops_project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.Iservices.IProductService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addProduct_ShouldAddProduct() throws Exception {
        Product product = new Product(null, "New Product", 10.00f, 100, ProductCategory.ELECTRONICS, null);
        Product savedProduct = new Product(1L, "New Product", 10.00f, 100, ProductCategory.ELECTRONICS, null);

        Mockito.when(productService.addProduct(any(Product.class), any(Long.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/product/{idStock}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduct", is(1)))
                .andExpect(jsonPath("$.title", is("New Product")));
    }

    @Test
    public void retrieveProduct_ShouldReturnProduct() throws Exception {
        Product product = new Product(1L, "Existing Product", 20.00f, 50, ProductCategory.ELECTRONICS, null);

        Mockito.when(productService.retrieveProduct(1L)).thenReturn(product);

        mockMvc.perform(get("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Existing Product")))
                .andExpect(jsonPath("$.price", is(20.0)));
    }

    @Test
    public void retreiveAllProduct_ShouldReturnProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product One", 10.00f, 100, ProductCategory.ELECTRONICS, null),
                new Product(2L, "Product Two", 15.50f, 200, ProductCategory.CLOTHING, null)
        );

        Mockito.when(productService.retreiveAllProduct()).thenReturn(products);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(products.size())))
                .andExpect(jsonPath("$[0].title", is("Product One")))
                .andExpect(jsonPath("$[1].title", is("Product Two")));
    }


}