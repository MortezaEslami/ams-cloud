package com.sample.ams.model;

import com.sample.ams.model.enumeration.CurrencyType;
import com.sample.ams.model.enumeration.TransactionStatus;
import com.sample.ams.model.enumeration.TransactionType;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@Table(name = "tbl_bank_transaction")
public class BankTransaction extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "C_ORIGIN_ACCOUNT_NUMBER", referencedColumnName = "C_ACCOUNT_NUMBER", nullable = false, insertable = false, updatable = false)
    private BankAccount originAccount;

    @Column(name = "C_ORIGIN_ACCOUNT_NUMBER", nullable = false)
    private String originAccountNumber;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "C_DESTINATION_ACCOUNT_NUMBER", referencedColumnName = "C_ACCOUNT_NUMBER", nullable = false, insertable = false, updatable = false)
    private BankAccount destinationAccount;

    @Column(name = "C_DESTINATION_ACCOUNT_NUMBER", nullable = false)
    private String destinationAccountNumber;

    @Column(name = "E_TRANSACTION_TYPE")
    private TransactionType transactionType;

    @Column(name = "N_AMOUNT")
    private BigDecimal amount;

    @Column(name = "N_CONVERTED_AMOUNT")
    private BigDecimal convertedAmount;

    @Column(name = "E_STATUS")
    private TransactionStatus transactionStatus;

    @Column(name = "E_CURRENCY_TYPE")
    private CurrencyType currencyType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_TRANSACTION_TIME")
    private Date transactionTime;

    @Version
    @Column(name = "N_VERSION", nullable = false)
    private Integer version;

    @Column(name = "C_COMMENT")
    private String comment;

    @Column(name = "C_tracking_code")
    private String trackingCode;
}
