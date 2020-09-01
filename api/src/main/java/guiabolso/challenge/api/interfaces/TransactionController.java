package guiabolso.challenge.api.interfaces;

import guiabolso.challenge.api.configuration.exceptions.ApiError;
import guiabolso.challenge.api.configuration.exceptions.GenericApiException;
import guiabolso.challenge.api.domain.Transaction;
import guiabolso.challenge.api.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@ApiOperation(value = "Operações relacionadas as transações", response = TransactionController.class)
public class TransactionController {

    //region Property
    private final TransactionService transactionService;
    //endregion

    @ApiOperation(value = "Retorna as transações de um usuário.",
            notes="Recupera todas as transações de um usuário específico em um determinado mês e ano.",
            response = Transaction[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista com as transações do cliente", response = Transaction[].class),
            @ApiResponse(code = 400, message = "A requisição não poder ser atendida devivo a uma sintaxe incorreta"),
            @ApiResponse(code = 404, message = "Nenhuma transação foi encontrada"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar as transações"),
    })
    @GetMapping( value = "/{id}/transacoes/{year}/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findTransactions(@PathVariable(value = "id") @ApiParam(value = "id", example = "1000", name= "Id do usuário", required = true ) Integer id,
                                  @PathVariable(value = "year") @ApiParam(value = "year", example = "2", name= "Ano da transação", required = true) String year,
                                  @PathVariable(value = "month") @ApiParam(value = "month", example = "1995", name= "Mês da transação", required = true) String month){
        try{
            List<Transaction> responses =  transactionService.findTransactions(id,year,month);

            return Optional.ofNullable(responses)
                    .map(x -> ResponseEntity.ok().body(responses))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        catch (GenericApiException ex){
            log.error("Error getting transactions ", ex);
            return ResponseEntity.status(ex.getStatusCode()).body(new ApiError(ex));
        }
        catch (Exception ex){
            log.error("Error getting transactions ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
