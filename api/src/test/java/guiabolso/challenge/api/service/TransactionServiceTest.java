package guiabolso.challenge.api.service;


import com.github.javafaker.Faker;
import guiabolso.challenge.api.domain.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    TransactionService transactionService;
    Validation validationMock = mock(Validation.class);
    Faker fakerMock = mock(Faker.class);

    @Before
    public void setUp() {
        transactionService = new TransactionService(validationMock);
    }

    @Test
    public void testResultFindTransactions() {

        int id =1;
        String year = "2020";
        String month = "2";

        verify(validationMock, never()).checkTransactionRequirements(id,year,month);

        List<Transaction> transactions = transactionService.findTransactions(id,year,month);

        assertFalse(transactions.isEmpty());
    }
}
