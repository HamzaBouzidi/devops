package tn.esprit.devops_project.repositories;

import org.junit.jupiter.api.BeforeEach

;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.devops_project.entities.Operator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class OperatorRepositoryTest {

    @Autowired
    private OperatorRepository operatorRepository;

    private Operator operator;

    @BeforeEach


    public void setUp() {
        // Initialize your Operator entity here and save it
        operator = new Operator(); // assuming default constructor is available
        operator.setFname("John");
        operator.setLname("Doe");
        // ... set other properties as needed

        // Save the operator to the database
        operator = operatorRepository.save(operator);
    }

    @Test
    public void whenFindById_thenReturnOperator() {
        // given - setUp

        // when
        Optional<Operator> found = operatorRepository.findById(operator.getIdOperateur());

        // then
        assertTrue(found.isPresent());
        assertEquals(operator.getIdOperateur(), found.get().getIdOperateur());
    }

    @Test
    public void whenFindAll_thenReturnOperatorsList() {
        // given - setUp

        // when
        List<Operator> operators = (List<Operator>) operatorRepository.findAll();

        // then
        assertNotNull(operators);
        assertEquals(1, operators.size()); // assuming the setUp runs before each test and there's only one entry
    }

    @Test
    public void whenSave_thenPersistOperator() {
        // given
        Operator newOperator = new Operator();
        newOperator.setFname("Jane");
        newOperator.setLname("Roe");
        // ... set other properties as needed

        // when
        Operator savedOperator = operatorRepository.save(newOperator);

        // then
        assertNotNull(savedOperator.getIdOperateur());
    }

    @Test
    public void whenDelete_thenRemoveOperator() {
        // given - setUp

        // when
        operatorRepository.deleteById(operator.getIdOperateur());
        Optional<Operator> deletedOperator = operatorRepository.findById(operator.getIdOperateur());

        // then
        assertFalse(deletedOperator.isPresent());
    }

}