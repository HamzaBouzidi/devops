package tn.esprit.devops_project.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;
    private List<Supplier> supplierList;

    @Before
    public void setUp() {
        // Initialize the supplier and supplier list with actual values before each test
        supplier = new Supplier(); // Assume a constructor or setters to initialize
        supplierList = Arrays.asList(supplier); // A list containing the above supplier
    }

    @Test
    public void whenRetrieveAllSuppliers_thenReturnAllSuppliers() {
        when(supplierRepository.findAll()).thenReturn(supplierList);

        List<Supplier> result = supplierService.retrieveAllSuppliers();

        assertThat(result, hasSize(1));
        assertThat(result.get(0), is(supplier));
        verify(supplierRepository).findAll();
    }

    @Test
    public void whenAddSupplier_thenSaveSupplier() {
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier result = supplierService.addSupplier(supplier);

        assertThat(result, is(notNullValue()));
        verify(supplierRepository).save(supplier);
    }

    @Test
    public void whenUpdateSupplier_thenSaveSupplier() {
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier result = supplierService.updateSupplier(supplier);

        assertThat(result, is(notNullValue()));
        verify(supplierRepository).save(supplier);
    }

    @Test
    public void whenDeleteSupplier_thenSupplierShouldBeDeleted() {
        Long supplierId = 1L;
        doNothing().when(supplierRepository).deleteById(supplierId);

        supplierService.deleteSupplier(supplierId);

        verify(supplierRepository).deleteById(supplierId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenRetrieveSupplier_withInvalidId_thenThrowException() {
        Long supplierId = 1L;
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.empty());

        supplierService.retrieveSupplier(supplierId);

        // Exception is expected to be thrown
    }

    @Test
    public void whenRetrieveSupplier_withValidId_thenReturnSupplier() {
        Long supplierId = 1L;
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.retrieveSupplier(supplierId);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(supplier));
        verify(supplierRepository).findById(supplierId);
    }
}