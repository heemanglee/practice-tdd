package com.example.tdd.chap03;

import java.time.LocalDate;

public class PayData {

    private LocalDate firstBillingDate; // 첫 납부일
    private LocalDate billingDate; // 납부일
    private int payAmount; // 납부 금액

    public PayData() {
    }

    public PayData(LocalDate firstBillingDate, LocalDate billingDate, int payAmount) {
        this.firstBillingDate = firstBillingDate;
        this.billingDate = billingDate;
        this.payAmount = payAmount;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public LocalDate getFirstBillingDate() {
        return firstBillingDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private PayData payData = new PayData();

        public Builder firstBillingDate(LocalDate firstBillingDate) {
            payData.firstBillingDate = firstBillingDate;
            return this;
        }

        public Builder billingDate(LocalDate billingDate) {
            payData.billingDate = billingDate;
            return this;
        }

        public Builder payAmount(int payAmount) {
            payData.payAmount = payAmount;
            return this;
        }

        public PayData build() {
            return payData;
        }
    }
}
