package guiabolso.challenge.api.service;

import guiabolso.challenge.api.configuration.ConstantsConfig;
import guiabolso.challenge.api.configuration.exceptions.GenericApiException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Builder
@Configuration
public class Validation {

    //region Public Methods
    public void checkTransactionRequirements(Integer id, String year, String month){
        if(id == null || year == null || month == null){
            throw GenericApiException.builder()
                    .code(Integer.toString(HttpStatus.BAD_REQUEST.value()))
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("User id, month and year are required.")
                    .build();
        }
        else if(id < ConstantsConfig.MIN_USER_ID_VALID || id > ConstantsConfig.MAX_USER_ID_VALID){
            throw GenericApiException.builder()
                    .code(Integer.toString(HttpStatus.BAD_REQUEST.value()))
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("The id must be between 1000 and 1000000000.")
                    .build();
        }
        else if(!isValidYear(year)){
            throw GenericApiException.builder()
                    .code(Integer.toString(HttpStatus.BAD_REQUEST.value()))
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("The year must be between 1960 and " + ConstantsConfig.CURRENT_YEAR)
                    .build();
        }
        else if(!isValidMonth(month))
        {
            throw GenericApiException.builder()
                    .code(Integer.toString(HttpStatus.BAD_REQUEST.value()))
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("Month must be between 1 and 12")
                    .build();
        }
    }
    //region

    //region Private Methods
    private static boolean isValidYear(String year) {
        int year_convert = Integer.parseInt(year);
        if (year_convert >= ConstantsConfig.MIN_YEAR_TRANSACTION_VALID &&
                year_convert <= ConstantsConfig.CURRENT_YEAR)
            return true;
        else if(year.isEmpty() || year.contains(" ")){
            return false;
        }
        else
            return false;
    }

    private static boolean isValidMonth(String month) {
        int month_convert = Integer.parseInt(month);
        if (month_convert >= 1 && month_convert <= 12)
            return true;
        else if(month.isEmpty() || month.contains(" ")){
            return false;
        }
        else
            return false;
    }
    //region
}
