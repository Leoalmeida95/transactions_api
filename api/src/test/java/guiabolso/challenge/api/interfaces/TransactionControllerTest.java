package guiabolso.challenge.api.interfaces;

import guiabolso.challenge.api.configuration.ConstantsConfig;
import guiabolso.challenge.api.configuration.exceptions.GenericApiException;
import guiabolso.challenge.api.domain.Transaction;
import guiabolso.challenge.api.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;
    TransactionController transactionController;
    TransactionService mockService = mock(TransactionService.class);

    @Before
    public void setUp() {
        transactionController = new TransactionController(mockService);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testSuccessFindTransactions() throws Exception{

        int id =1;
        String year = "2020";
        String month = "2";
        Object[] params = new Object[]{id, year, month};
        String url = MessageFormat.format("/{0}/transacoes/{1}/{2}", params);

        List<Transaction> transactions = new ArrayList<Transaction>();
        Transaction t = Transaction.builder()
                        .description("TESTE")
                        .value(1000)
                        .date(10000l)
                        .duplicated(false)
                        .build();
        transactions.add(t);

        when(mockService.findTransactions(id,year,month)).thenReturn(transactions);

        MockHttpServletResponse response = mockMvc.perform(
                get(url)
                 .accept(MediaType.APPLICATION_JSON))
                 .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"description\":\"TESTE\",\"date\":10000,\"value\":1000,\"duplicated\":false}]",
                                response.getContentAsString());
    }

    @Test
    public void testNotFoundFindTransactions() throws Exception{

        int id =1;
        String year = "100";
        String month = "2";
        Object[] params = new Object[]{id, year, month};
        String url = MessageFormat.format("/{0}/transacoes/{1}/{2}", params);

        when(mockService.findTransactions(id,year,month)).thenReturn(null);

        MockHttpServletResponse response = mockMvc.perform(
                get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void testBadRequestFindTransactions() throws Exception{

        int id =1;
        String year = "100";
        String month = "2";
        Object[] params = new Object[]{id, year, month};
        String url = MessageFormat.format("/{0}/transacoes/{1}/{2}", params);

        when(mockService.findTransactions(id,year,month)).thenThrow(
                                                GenericApiException.builder()
                                                .code(Integer.toString(HttpStatus.BAD_REQUEST.value()))
                                                .statusCode(HttpStatus.BAD_REQUEST)
                                                .message("The year must be between 1960 and " + ConstantsConfig.CURRENT_YEAR)
                                                .build());

        MockHttpServletResponse response = mockMvc.perform(
                get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"code\":\"400\",\"message\":\"The year must be between 1960 and 2020\"}",
                response.getContentAsString());
    }

    @Test
    public void testInternalServerErrorFindTransactions() throws Exception{

        int id =1;
        String year = "100";
        String month = "2";
        Object[] params = new Object[]{id, year, month};
        String url = MessageFormat.format("/{0}/transacoes/{1}/{2}", params);

        when(mockService.findTransactions(id,year,month)).thenThrow(new NullPointerException("Some Error"));

        MockHttpServletResponse response = mockMvc.perform(
                get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
    }
}
