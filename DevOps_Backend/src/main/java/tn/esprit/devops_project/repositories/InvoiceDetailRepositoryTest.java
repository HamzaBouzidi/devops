package tn.esprit.devops_project.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.devops_project.entities.InvoiceDetail;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InvoiceDetailRepositoryTest {

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    private InvoiceDetail invoiceDetail;

    @Before
    public void init() {
        // Initialize your InvoiceDetail instance here and save it to the repository if necessary.
        invoiceDetail = new InvoiceDetail();
        // ... set properties on invoiceDetail
        invoiceDetail = invoiceDetailRepository.save(invoiceDetail); // Make sure the instance is saved in the repo
    }

    @Test
    public void whenFindById_thenReturnInvoiceDetail() {
        // Ensure the InvoiceDetail exists
        Optional<InvoiceDetail> found = invoiceDetailRepository.findById(invoiceDetail.getIdInvoiceDetail());
        assertThat(found.isPresent(), is(true));
        assertThat(found.get(), equalTo(invoiceDetail));
    }

    @Test
    public void whenFindAll_thenReturnAllInvoiceDetails() {
        List<InvoiceDetail> invoiceDetails = (List<InvoiceDetail>) invoiceDetailRepository.findAll();
        assertThat(invoiceDetails, is(not(empty())));
        assertThat(invoiceDetails.contains(invoiceDetail), is(true));
    }

    @Test
    public void whenSaveInvoiceDetail_thenIncreaseCount() {
        long countBeforeInsert = invoiceDetailRepository.count();
        InvoiceDetail newInvoiceDetail = new InvoiceDetail();
        // Set properties for the new InvoiceDetail
        // ... set properties on newInvoiceDetail
        invoiceDetailRepository.save(newInvoiceDetail);
        long countAfterInsert = invoiceDetailRepository.count();
        assertThat(countAfterInsert, is(greaterThan(countBeforeInsert)));
    }

    @Test
    public void whenDeleteInvoiceDetail_thenDecreaseCount() {
        // Ensure the InvoiceDetail exists and is saved before deleting
        InvoiceDetail savedInvoiceDetail = invoiceDetailRepository.save(new InvoiceDetail());
        long countBeforeDelete = invoiceDetailRepository.count();
        invoiceDetailRepository.deleteById(savedInvoiceDetail.getIdInvoiceDetail());
        long countAfterDelete = invoiceDetailRepository.count();
        assertThat(countAfterDelete, is(lessThan(countBeforeDelete)));
    }
}