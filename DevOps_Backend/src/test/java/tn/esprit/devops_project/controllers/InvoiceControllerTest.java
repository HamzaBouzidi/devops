package tn.esprit.devops_project.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.Iservices.IInvoiceService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInvoiceService invoiceService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Test
    public void getInvoicesTest() throws Exception {
        List<Invoice> invoices = Arrays.asList(new Invoice(), new Invoice());
        when(invoiceService.retrieveAllInvoices()).thenReturn(invoices);

        mockMvc.perform(get("/invoice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(invoices.size())));
    }

    @Test
    public void retrieveInvoiceTest() throws Exception {
        Invoice invoice = new Invoice(1L, 0, 100, new Date(), new Date(), false, new HashSet<>(), null);
        when(invoiceService.retrieveInvoice(invoice.getIdInvoice())).thenReturn(invoice);

        mockMvc.perform(get("/invoice/{invoiceId}", invoice.getIdInvoice()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idInvoice", is(invoice.getIdInvoice().intValue())))
                .andExpect(jsonPath("$.amountInvoice", is(100.0)));
    }

    @Test
    public void cancelInvoiceTest() throws Exception {
        Long invoiceId = 1L;
        doNothing().when(invoiceService).cancelInvoice(invoiceId);

        mockMvc.perform(put("/invoice/{invoiceId}", invoiceId))
                .andExpect(status().isOk());
    }

    @Test
    public void getInvoicesBySupplierTest() throws Exception {
        Long supplierId = 1L;
        List<Invoice> invoices = Arrays.asList(new Invoice(), new Invoice());
        when(invoiceService.getInvoicesBySupplier(supplierId)).thenReturn(invoices);

        mockMvc.perform(get("/invoice/supplier/{supplierId}", supplierId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(invoices.size())));
    }

    @Test
    public void assignOperatorToInvoiceTest() throws Exception {
        Long idOperator = 1L;
        Long idInvoice = 1L;
        doNothing().when(invoiceService).assignOperatorToInvoice(idOperator, idInvoice);

        mockMvc.perform(put("/invoice/operator/{idOperator}/{idInvoice}", idOperator, idInvoice))
                .andExpect(status().isOk());
    }



}