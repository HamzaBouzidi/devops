package tn.esprit.devops_project.repositories;

import org.junit.jupiter.api.BeforeEach

;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.devops_project.entities.Invoice;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    private Invoice invoice;

    @BeforeEach


    public void setUp() {
        // Initialize your Invoice entity here and save it
        invoice = new Invoice(); // assuming default constructor is available
        invoice.setAmountInvoice(1000);
        invoice.setDateCreationInvoice(new Date());
        invoice.setArchived(false);

        // Save the invoice to the database
        invoice = invoiceRepository.save(invoice);
    }

    @Test
    public void whenFindById_thenReturnInvoice() {

        // when
        Optional<Invoice> found = invoiceRepository.findById(invoice.getIdInvoice());

        // then
        assertTrue(found.isPresent());
        assertEquals(invoice.getIdInvoice(), found.get().getIdInvoice());
    }

    @Test
    public void whenFindAll_thenReturnInvoicesList() {
        // given - setUp

        // when
        List<Invoice> invoices = (List<Invoice>) invoiceRepository.findAll();

        // then
        assertNotNull(invoices);
        assertFalse(invoices.isEmpty()); // ensuring the list is not empty
        assertTrue(invoices.contains(invoice));
    }

    @Test
    public void whenSave_thenPersistInvoice() {
        // given
        Invoice newInvoice = new Invoice();
        newInvoice.setAmountInvoice(2000);
        newInvoice.setDateCreationInvoice(new Date());
        newInvoice.setArchived(true);
        // ... set other properties as needed

        // when
        Invoice savedInvoice = invoiceRepository.save(newInvoice);

        // then
        assertNotNull(savedInvoice.getIdInvoice());
        assertEquals(newInvoice.getAmountInvoice(), savedInvoice.getAmountInvoice(), 0.01);
    }

    @Test
    public void whenDelete_thenRemoveInvoice() {
        // given - setUp

        // when
        invoiceRepository.deleteById(invoice.getIdInvoice());
        Optional<Invoice> deletedInvoice = invoiceRepository.findById(invoice.getIdInvoice());

        // then
        assertFalse(deletedInvoice.isPresent());
    }

    // Additional tests can be added here, such as testing custom query methods, etc.
}