package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach

;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class OperatorServiceImplTest {

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorServiceImpl operatorService;

    private Operator operator;

    @BeforeEach


    public void setUp() {
        operator = new Operator();
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
    public void whenDeleteOperator_thenRepositoryMethodCalled() {
        doNothing().when(operatorRepository).deleteById(anyLong());

        operatorService.deleteOperator(1L);

        verify(operatorRepository).deleteById(1L);
    }


    @Test
    public void whenRetrieveNonExistentOperator_thenThrowException() {
        when(operatorRepository.findById(anyLong())).thenThrow(new NullPointerException("Operator not found"));

        assertThrows(NullPointerException.class, () -> operatorService.retrieveOperator(2L));
    }

}