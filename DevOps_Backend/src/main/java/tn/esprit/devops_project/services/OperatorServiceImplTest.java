package tn.esprit.devops_project.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OperatorServiceImplTest {

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorServiceImpl operatorService;

    private Operator operator;

    @Before
    public void setUp() {
        operator = new Operator(); // Initialize operator with correct parameters
        // e.g., operator.setId(1L);
    }

    @Test
    public void whenRetrieveAllOperators_thenReturnOperatorList() {
        when(operatorRepository.findAll()).thenReturn(Arrays.asList(operator));

        List<Operator> operators = operatorService.retrieveAllOperators();

        assertThat(operators, is(not(empty())));
        assertThat(operators, hasSize(1));
        assertThat(operators.get(0), is(operator));
        verify(operatorRepository).findAll();
    }

    @Test
    public void whenAddOperator_thenReturnOperator() {
        when(operatorRepository.save(any(Operator.class))).thenReturn(operator);

        Operator savedOperator = operatorService.addOperator(operator);

        assertThat(savedOperator, is(notNullValue()));
        verify(operatorRepository).save(operator);
    }

    @Test
    public void whenUpdateOperator_thenReturnUpdatedOperator() {
        when(operatorRepository.save(any(Operator.class))).thenReturn(operator);

        Operator updatedOperator = operatorService.updateOperator(operator);

        assertThat(updatedOperator, is(notNullValue()));
        verify(operatorRepository).save(operator);
    }

    @Test
    public void whenDeleteOperator_thenRepositoryMethodCalled() {
        doNothing().when(operatorRepository).deleteById(anyLong());

        operatorService.deleteOperator(1L);

        verify(operatorRepository).deleteById(1L);
    }

    @Test
    public void whenRetrieveOperator_thenReturnOperator() {
        when(operatorRepository.findById(anyLong())).thenReturn(Optional.of(operator));

        Operator foundOperator = operatorService.retrieveOperator(1L);

        assertThat(foundOperator, is(notNullValue()));
        assertThat(foundOperator, is(operator));
        verify(operatorRepository).findById(1L);
    }

    @Test(expected = NullPointerException.class)
    public void whenRetrieveNonExistentOperator_thenThrowException() {
        when(operatorRepository.findById(anyLong())).thenThrow(new NullPointerException("Operator not found"));

        operatorService.retrieveOperator(2L);
    }
}