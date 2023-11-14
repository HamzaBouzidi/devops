package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach

;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.SupplierRepository;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;
    private List<Supplier> supplierList;

    @BeforeEach


    public void setUp() {
        supplier = new Supplier();
        supplierList = Arrays.asList(supplier);
    }

    @Test
    public void whenRetrieveAllSuppliers_thenReturnAllSuppliers() {

    }

    @Test
    public void whenAddSupplier_thenSaveSupplier() {

    }

    @Test
    public void whenUpdateSupplier_thenSaveSupplier() {

    }

    @Test
    public void whenDeleteSupplier_thenSupplierShouldBeDeleted() {
        Long supplierId = 1L;
        doNothing().when(supplierRepository).deleteById(supplierId);

        supplierService.deleteSupplier(supplierId);

        verify(supplierRepository).deleteById(supplierId);
    }



    @Test
    public void whenRetrieveSupplier_withValidId_thenReturnSupplier() {

    }
}