package com.sample.ams.proxy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResultApiCall {
    private boolean success;
    private Long timestamp;
    private String base;
    private String date;
    private ErrorApi error;
    private Rates rates;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public class Rates {
        private String USD;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public class ErrorApi {
        private int code;
        private String info;
    }


}

