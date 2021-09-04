package com.sample.ams.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.ams.model.enumeration.CurrencyType;
import com.sample.ams.model.enumeration.TransactionStatus;
import com.sample.ams.model.enumeration.TransactionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankTransactionDTO {

    @ApiModelProperty
    private String originAccountNumber;

    @ApiModelProperty
    private String destinationAccountNumber;

    @NotEmpty
    @ApiModelProperty(required = true)
    private BigDecimal amount;

    private BigDecimal convertedAmount;

    @NotNull
    @ApiModelProperty(required = true)
    private CurrencyType currencyType;

    @ApiModelProperty
    private String comment;

    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private String trackingCode;
    private Date transactionTime;

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankTransactionInfo")
    public static class Info extends BankTransactionDTO {
        @ApiModelProperty(hidden = true)
        private Long id;
        private Date createdDate;
        private Date lastModifiedDate;
    }
    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankTransactionCreateRq")
    public static class Create extends BankTransactionDTO {

    }

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankTransactionUpdateRq")
    public static class Update extends BankTransactionDTO {
        @NotNull
        @ApiModelProperty(required = true)
        private Integer version;
    }

}

