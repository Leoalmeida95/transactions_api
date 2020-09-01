package guiabolso.challenge.api.service;

import com.github.javafaker.Faker;
import guiabolso.challenge.api.configuration.ConstantsConfig;
import guiabolso.challenge.api.configuration.exceptions.GenericApiException;
import guiabolso.challenge.api.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@CacheConfig(cacheNames = {"transactions"})
public class TransactionService {

    //region Properties
    private final Validation validation;
    //endregion

    //region Public Methods
    @Cacheable
    public List<Transaction> findTransactions(Integer id, String year, String month){
        try {

            validation.checkTransactionRequirements(id,year,month);

            return buildTransactions(year,month);
        }
        catch (GenericApiException ex){
            throw ex;
        }
        catch (Exception ex){
            throw GenericApiException.builder()
                    .code(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Error getting transactions")
                    .build();
        }
    }
    //region

    //region Private Methods
    private List<Transaction> buildTransactions(String year, String month) throws ParseException {
        Faker faker = new Faker();
        int transactions_generate = faker.number().numberBetween(ConstantsConfig.MIN_TRANSACTIONS_TO_GENERATE,
                                                                 ConstantsConfig.MAX_TRANSACTIONS_TO_GENERATE);
        List<Transaction> responses = new ArrayList<Transaction>();
        SimpleDateFormat formatter = new SimpleDateFormat(ConstantsConfig.FORMAT_DATA);

        for(int i=0; i < transactions_generate; i++){

            Date date = formatter.parse(year + "-" + month + "-" + faker.number().numberBetween(1,31));

            Transaction response = Transaction.builder()
                    .description(faker.lorem().fixedString(60))
                    .date(date.getTime())
                    .value(faker.number().numberBetween(ConstantsConfig.MIN_TRANSACTIONS_VALUE,
                                                        ConstantsConfig.MAX_TRANSACTIONS_VALUE))
                    .duplicated(false)
                    .build();

            responses.add(response);
        }

        checkDuplicity(responses);

        return responses;
    }

    private void checkDuplicity(List<Transaction> transactions){
        for(Transaction tran : transactions){

            int frequency = Collections.frequency(transactions, tran);
            if(frequency > 1){
                tran.setDuplicated(true);
            }
        }
    }
    //region
}
