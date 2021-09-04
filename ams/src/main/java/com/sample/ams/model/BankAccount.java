package com.sample.ams.model;

import com.sample.ams.model.enumeration.AccountStatus;
import com.sample.ams.model.enumeration.AccountType;
import com.sample.ams.model.enumeration.CurrencyType;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Audited
@Data
@Entity
@Table(name = "tbl_bank_account")
public class BankAccount extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "N_CUSTOMER_ID", nullable = false, insertable = false, updatable = false)
    @NotAudited
    private Customer customer;

    @Column(name = "N_CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "N_BANK_ID", nullable = false, insertable = false, updatable = false)
    @NotAudited
    private Bank bank;

    @Column(name = "N_BANK_ID", nullable = false)
    private Long bankId;

    @Column(name = "C_ACCOUNT_NUMBER", length = 20, unique = true)
    private String accountNumber;

    @Column(name = "C_CARD_NUMBER", length = 16, unique = true)
    private String cardNumber;

    @Column(name = "N_BALANCE", length = 10)
    private BigDecimal balance;

    @Column(name = "E_ACCOUNT_STATUS")
    private AccountStatus accountStatus;

    @Column(name = "E_ACCOUNT_TYPE")
    private AccountType accountType;

    @Column(name = "E_CURRENCY_TYPE")
    private CurrencyType currencyType;

    @Version
    @Column(name = "N_VERSION", nullable = false)
    @NotAudited
    private Integer version;

    @Column(name = "C_COMMENT")
    private String comment;
}
