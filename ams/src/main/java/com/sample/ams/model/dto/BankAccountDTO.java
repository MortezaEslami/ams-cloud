package com.sample.ams.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sample.ams.model.enumeration.AccountStatus;
import com.sample.ams.model.enumeration.AccountType;
import com.sample.ams.model.enumeration.CurrencyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountDTO {

    @NotNull
    @ApiModelProperty(required = true)
    private Long customerId;

    @NotNull
    @ApiModelProperty(required = true , example = "1")
    private Long bankId;

    @NotEmpty
    @ApiModelProperty(required = true , example = "5464-5456")
    private String accountNumber;

    @NotEmpty
    @ApiModelProperty(required = true, example = "5500000000000004")
    private String cardNumber;

    @NotNull
    @ApiModelProperty(required = true, example = "10.00")
    private BigDecimal balance;

    @NotNull
    @ApiModelProperty(required = true)
    private AccountType accountType;

    @ApiModelProperty(example = " This is a comment.")
    private String comment;

    @NotNull
    @ApiModelProperty(required = true)
    private CurrencyType currencyType;

    @ApiModelProperty(hidden = true)
    private AccountStatus accountStatus;


    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankAccountInfo")
    public static class Info extends BankAccountDTO {
        private Long id;
        private BigDecimal balance;
        private AccountStatus accountStatus;
        private CustomerDTO.Info customer;
    }
    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankAccountCreateRq")
    public static class Create extends BankAccountDTO {

    }

    // ------------------------------

    @Getter
    @Setter
    @Accessors(chain = true)
    @ApiModel("BankAccountUpdateRq")
    public static class Update extends BankAccountDTO {
        @NotNull
        @ApiModelProperty(required = true)
        private Integer version;
    }
}
