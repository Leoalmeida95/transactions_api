package guiabolso.challenge.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Setter
@Getter
@ApiModel(value = "Transação", description = "Transação de um usuário")
public class Transaction {

    public Transaction(){}

    Transaction(String description, Long date, Integer value,Boolean duplicated){
        this.description = description;
        this.date = date;
        this.value = value;
        this.duplicated = duplicated;
    }

    @ApiModelProperty(value = "Descrição da transação", required = true)
    private String description;
    @ApiModelProperty(value = "Timestemp da data que a transação ocorreu", required = true)
    private Long date;
    @ApiModelProperty(value = "Valor da transação", required = true)
    private Integer value;
    @ApiModelProperty(value = "Flag que identifica se a transição do usuário foi duplicada em um determinado mês e ano", required = true)
    private Boolean duplicated;
}
