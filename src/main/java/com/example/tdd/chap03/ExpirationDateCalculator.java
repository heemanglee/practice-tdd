package com.example.tdd.chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpirationDateCalculator {

    public LocalDate calculatorExpirationDate(LocalDate billingDate, int payAmount) {
//        return LocalDate.of(2019, 4, 1); // expirationDate를 단순히 테스트 통과시키기 위해 사용한 코드.

        // expirationDate, expirationDate2를 테스트 통과시키기 위해 사용한 코드
        // 10_000원을 지불하면 서비스 만료일이 납부일로부터 1달 뒤이므로, (납부일 + 1달)로 단순화할 수 있다.
        return billingDate.plusMonths(1);
    }

    public LocalDate calculatorExpirationDate(PayData payData) { // 메서드의 파라미터 개수가 3개 이상이라면 하나의 객체로 줄이는 것이 가독성과 유지보수에 유리하다.
        int addMonth = payData.getPayAmount() / 10_000;
        if (payData.getFirstBillingDate() != null) {
            LocalDate candidateExpiration = payData.getBillingDate().plusMonths(addMonth); // 후보 만료일
            // 첫 납부일의 일자와 후보 만료일의 일자가 다른 경우
            // ex) 첫 납부일: 2019-01-31, 두 번째 납부일: 2019-02-28인 경우
            if(payData.getFirstBillingDate().getDayOfMonth() != candidateExpiration.getDayOfMonth()) {

                // 첫 납부일의 날짜가 후보 만료일의 월에는 존재하지 않을 경우 예외 처리
                // ex) 첫 납부일: 2019-1-31, 후보 만료일: 2019-4-31 -> 2019년 4월에는 30일이 마지막 일이다.
                if(YearMonth.from(candidateExpiration).lengthOfMonth() < payData.getFirstBillingDate().getDayOfMonth()) { // 후보 만료일의 월의 마지막 일자 < 첫 납부일의 마지막 일자
                    return candidateExpiration.withDayOfMonth(YearMonth.from(candidateExpiration).lengthOfMonth());
                }

                // 후보 만료일의 날짜를 첫 납부일의 날짜로 설정한다.
                // ex) 기존의 후보 만료일: 2019-03-28, 변경된 후보 만료일: 2019-03-31
                return candidateExpiration.withDayOfMonth(
                    payData.getFirstBillingDate().getDayOfMonth()
                );
            }
        }
        return payData.getBillingDate().plusMonths(addMonth);
    }

    public LocalDate calculatorExpirationDate_refactor(PayData payData) { // 메서드의 파라미터 개수가 3개 이상이라면 하나의 객체로 줄이는 것이 가독성과 유지보수에 유리하다.
        int addMonth = payData.getPayAmount() == 100_000 ? 12 : payData.getPayAmount() / 10_000;
        if (payData.getFirstBillingDate() != null) {
            LocalDate candidateExpiration = payData.getBillingDate().plusMonths(addMonth); // 후보 만료일
            // 첫 납부일의 일자와 후보 만료일의 일자가 다른 경우
            // ex) 첫 납부일: 2019-01-31, 두 번째 납부일: 2019-02-28인 경우
            final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
            if(dayOfFirstBilling != candidateExpiration.getDayOfMonth()) {

                // 첫 납부일의 날짜가 후보 만료일의 월에는 존재하지 않을 경우 예외 처리
                // ex) 첫 납부일: 2019-1-31, 후보 만료일: 2019-4-31 -> 2019년 4월에는 30일이 마지막 일이다.
                final int dayLengthOfCandidateMonth = YearMonth.from(candidateExpiration).lengthOfMonth();
                if(dayLengthOfCandidateMonth < payData.getFirstBillingDate().getDayOfMonth()) { // 후보 만료일의 월의 마지막 일자 < 첫 납부일의 마지막 일자
                    return candidateExpiration.withDayOfMonth(dayLengthOfCandidateMonth);
                }

                // 후보 만료일의 날짜를 첫 납부일의 날짜로 설정한다.
                // ex) 기존의 후보 만료일: 2019-03-28, 변경된 후보 만료일: 2019-03-31
                return candidateExpiration.withDayOfMonth(
                    dayOfFirstBilling
                );
            }
        }
        return payData.getBillingDate().plusMonths(addMonth);
    }
}
