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
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Invoice invoice;
    private Operator operator;
    private Supplier supplier;

    @BeforeEach


    public void setUp() {
        invoice = new Invoice();
        operator = new Operator();
        supplier = new Supplier();

        Set<Invoice> invoicesSet = new HashSet<>();
        operator.setInvoices(invoicesSet);

        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invoice));
        when(operatorRepository.findById(anyLong())).thenReturn(Optional.of(operator));

        setUpInvoice();
        setUpOperator();
        setUpSupplier();
    }

    private void setUpInvoice() {
    }

    private void setUpOperator() {
    }

    private void setUpSupplier() {
    }

    @Test
    public void whenRetrieveAllInvoices_thenReturnInvoiceList() {
        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(invoice));
        List<Invoice> invoices = invoiceService.retrieveAllInvoices();
        assertThat(invoices, is(not(empty())));
        assertThat(invoices, hasSize(1));
        verify(invoiceRepository).findAll();
    }

    @Test
    public void whenCancelInvoice_thenInvoiceIsArchived() {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invoice));
        invoiceService.cancelInvoice(1L);
        assertThat(invoice.getArchived(), is(true));
        verify(invoiceRepository).save(invoice);
    }

    @Test
    public void whenRetrieveInvoice_thenReturnInvoice() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        Invoice foundInvoice = invoiceService.retrieveInvoice(1L);

        assertThat(foundInvoice, is(notNullValue()));
        verify(invoiceRepository).findById(1L);
    }

    @Test
    public void whenAssignOperatorToInvoice_thenOperatorIsAssignedToInvoice() {
        // Mock the behaviour if necessary to return the existing invoice.
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        invoiceService.assignOperatorToInvoice(1L, 1L);

        assertThat(operator.getInvoices(), hasItem(invoice));
        verify(operatorRepository).save(operator);
        verify(invoiceRepository).findById(1L);
        verify(operatorRepository).findById(1L);
    }

    @Test
    public void whenGetTotalAmountInvoiceBetweenDates_thenReturnTotalAmount() {
        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(any(Date.class), any(Date.class)))
                .thenReturn(100.0f);
        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(new Date(), new Date());
        assertThat(totalAmount, is(100.0f));
        verify(invoiceRepository).getTotalAmountInvoiceBetweenDates(any(Date.class), any(Date.class));
    }
}