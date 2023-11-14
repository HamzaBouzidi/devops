package tn.esprit.devops_project.repositories;

import org.junit.jupiter.api.BeforeEach

;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class SupplierRepositoryTest {

    @Autowired
    private SupplierRepository supplierRepository;

    private Supplier supplier;

    @BeforeEach


    public void setUp() {
        // Initialize your test data
        Supplier supplier1 = new Supplier(1L, "S123", "Supplier One", SupplierCategory.ORDINAIRE, null);
        Supplier supplier2 = new Supplier(2L, "S124", "Supplier Two", SupplierCategory.CONVENTIONNE, null);
        // Save them to the in-memory database

        supplierRepository.save(supplier1);
        supplierRepository.save(supplier2);
    }

    @Test
    public void whenFindAll_thenGetSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        assertNotNull(suppliers);
        assertFalse(suppliers.isEmpty());
        assertEquals(2, suppliers.size());
    }



    @Test
    public void whenSave_thenGetNewSupplier() {
        Supplier supplier = new Supplier(null, "S125", "Supplier Three", SupplierCategory.CONVENTIONNE, null);
        supplier = supplierRepository.save(supplier);
        assertNotNull(supplier);
        assertNotNull(supplier.getIdSupplier());
    }


}