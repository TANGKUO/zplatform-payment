package com.zlebank.zplatform.payment.enums;


public enum OrderType {
    /**
     * recharge
     */
    RECHARGE("0002"),
    /**
     * consume
     */
    CONSUME("0001"), 
    /**授信消费类**/
    CREDIT_CONSUME("0003"),
    /**授信充值类**/
    CREDIT_RECHARGE("0004"),
    /**行业消费类**/
    INDUSTRY_CONSUME("0005"),	
    /**行业充值类**/
    INDUSTRY_RECHARGE("0006"),
    /**保证金充值类**/
    BAIL_RECHARGE("0007"),
    /**
     * consume
     */
    WITHDRAW("1001"),
    /**
     * unknow
     */
    REFUND("0000"),
    UNKNOW("");
    private String code;
    private OrderType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static OrderType fromValue(String code) {
        for (OrderType orderType : OrderType.values()) {
            if (code != null && orderType.getCode().equals(code)) {
                return orderType;
            }
        }
        return UNKNOW;
    }
}
