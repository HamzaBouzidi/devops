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
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.Iservices.ISupplierService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)

@WebMvcTest(SupplierController.class)
public class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISupplierService supplierService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getSuppliersTest() throws Exception {
        List<Supplier> suppliers = Arrays.asList(
                new Supplier(1L, "S123", "Supplier One", SupplierCategory.ORDINAIRE, null),
                new Supplier(2L, "S124", "Supplier Two", SupplierCategory.CONVENTIONNE, null)
        );

        Mockito.when(supplierService.retrieveAllSuppliers()).thenReturn(suppliers);

        mockMvc.perform(get("/supplier"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(suppliers.size())))
                .andExpect(jsonPath("$[0].code", is("S123")))
                .andExpect(jsonPath("$[1].code", is("S124")));
    }

    @Test
    public void retrieveSupplierTest() throws Exception {
        Supplier supplier = new Supplier(1L, "S123", "Supplier One", SupplierCategory.ORDINAIRE, null);

        Mockito.when(supplierService.retrieveSupplier(1L)).thenReturn(supplier);

        mockMvc.perform(get("/supplier/{supplierId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("S123")))
                .andExpect(jsonPath("$.label", is("Supplier One")));
    }

    @Test
    public void addSupplierTest() throws Exception {
        Supplier supplier = new Supplier(null, "S125", "Supplier Three", SupplierCategory.CONVENTIONNE, null);
        Supplier savedSupplier = new Supplier(3L, "S125", "Supplier  ", SupplierCategory.CONVENTIONNE, null);

        Mockito.when(supplierService.addSupplier(any(Supplier.class))).thenReturn(savedSupplier);

        mockMvc.perform(post("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSupplier", is(3)))
                .andExpect(jsonPath("$.code", is("S125")));
    }

    @Test
    public void removeSupplierTest() throws Exception {
        Mockito.doNothing().when(supplierService).deleteSupplier(1L);

        mockMvc.perform(delete("/supplier/{supplierId}", 1L))
                .andExpect(status().isOk());

        verify(supplierService).deleteSupplier(1L);
    }

    @Test
    public void modifySupplierTest() throws Exception {
        Supplier supplier = new Supplier(1L, "S123", "Updated Supplier", SupplierCategory.ORDINAIRE, null);

        Mockito.when(supplierService.updateSupplier(any(Supplier.class))).thenReturn(supplier);

        mockMvc.perform(put("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplier)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label", is("Updated Supplier")));
    }

}