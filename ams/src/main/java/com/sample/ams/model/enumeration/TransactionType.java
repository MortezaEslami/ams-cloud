package com.sample.ams.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    Withdraw,
    Deposit,
    Transfer;

}
