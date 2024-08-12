package com.example.tdd.chap03;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * 테스트 검증 조건 1. 서비스를 사용하려면 매달 1만원을 선불로 납부한다. 납부일 기준으로 한 달 뒤가 서비스 만료일이 된다. 2. 2개월 이상 요금을 납부할 수 있다. 3.
 * 10만원을 납부하면 서비스를 1년 제공한다.
 */

public class ExpirationDateCalculatorTest {

    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨() {
        LocalDate billingDate = LocalDate.of(2019, 3, 1); // 납부일
        int payAmount = 10_000; // 서비스 사용료 만원을 납부한다.

        ExpirationDateCalculator cal = new ExpirationDateCalculator();
        LocalDate expirationDate = cal.calculatorExpirationDate(billingDate, payAmount);

        // 3월 1일에 사용료를 납부하였으므로, 한달 뒤인 4월 1일이 만료일이 된다.
        assertEquals(LocalDate.of(2019, 4, 1), expirationDate);

        LocalDate billingDate2 = LocalDate.of(2019, 5, 5);
        int payAmount2 = 10_000;

        ExpirationDateCalculator cal2 = new ExpirationDateCalculator();
        LocalDate expirationDate2 = cal2.calculatorExpirationDate(billingDate2, payAmount2);
        assertEquals(LocalDate.of(2019, 6, 5), expirationDate2);
    }

    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨_refactor() {
        assertExpirationDate(LocalDate.of(2019, 3, 1), 10_000, LocalDate.of(2019, 4, 1));
        assertExpirationDate(LocalDate.of(2019, 5, 5), 10_000, LocalDate.of(2019, 6, 5));
    }

    private void assertExpirationDate(LocalDate billingDate, int payAmount,
        LocalDate expectedExpirationDate) {
        ExpirationDateCalculator cal = new ExpirationDateCalculator();
        LocalDate result = cal.calculatorExpirationDate(billingDate, payAmount);
        assertEquals(expectedExpirationDate, result);
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음() {
        // 놀랍게도 아래의 세 개의 테스트가 모두 통과한다.
        // LocalDate#plusMonths()가 알아서 한 달 추가를 처리한다.
        assertExpirationDate(LocalDate.of(2019, 1, 31), 10_000, LocalDate.of(2019, 2, 28));
        assertExpirationDate(LocalDate.of(2019, 5, 31), 10_000, LocalDate.of(2019, 6, 30));
        assertExpirationDate(LocalDate.of(2020, 1, 31), 10_000, LocalDate.of(2020, 2, 29));
    }

    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨_refactor2() {
        assertExpirationDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2019, 4, 1)
        );
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음_refactor() {
        assertExpirationDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 1, 31))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2019, 2, 28)
        );

        assertExpirationDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 5, 31))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2019, 6, 30)
        );

        assertExpirationDate(
            PayData.builder()
                .billingDate(LocalDate.of(2020, 1, 31))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2020, 2, 29)
        );
    }

    // 기존의 assertExpirationDate()는 3개의 파라미터를 받았지만, 코드의 가독성 및 유지보수를 위해 PayData 객체를 생성하였다.
    private void assertExpirationDate(PayData payData, LocalDate expectedExpirationDate) {
        ExpirationDateCalculator cal = new ExpirationDateCalculator();
        LocalDate result = cal.calculatorExpirationDate_refactor(payData);
        assertEquals(expectedExpirationDate, result);
    }

    @Test
    void 첫_납부일과_만료일의_일자가_같지_않을때_첫_납부일을_기준으로_다음_만료일을_정함() { // 메서드 이름이 너무 길다면 다른 이름을 사용하는 것도 좋다. 여기서는 좀더 명확하게 표현하고자 길게 사용한다.
        PayData payData = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 1, 31)) // 첫 번째 납부일
            .billingDate(LocalDate.of(2019, 2, 28)) // 두 번째 납부일
            .payAmount(10_000) // 납부 금액
            .build();

        assertExpirationDate(payData, LocalDate.of(2019, 3, 31));

        PayData payData2 = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 1, 30))
            .billingDate(LocalDate.of(2019, 2, 28))
            .payAmount(10_000)
            .build();
        assertExpirationDate(payData2, LocalDate.of(2019, 3, 30));

        PayData payData3 = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 5, 31))
            .billingDate(LocalDate.of(2019, 6, 30))
            .payAmount(10_000)
            .build();
        assertExpirationDate(payData3, LocalDate.of(2019, 7, 31));
    }

    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산() {
        PayData payData = PayData.builder()
            .billingDate(LocalDate.of(2019, 3, 1))
            .payAmount(20_000)
            .build();
        assertExpirationDate(payData, LocalDate.of(2019, 5, 1));

        PayData payData2 = PayData.builder()
            .billingDate(LocalDate.of(2019, 3, 1))
            .payAmount(30_000)
            .build();
        assertExpirationDate(payData2, LocalDate.of(2019, 6, 1));
    }

    @Test
    void 첫_납부일과_만료일이_다를때_이만원_이상_납부() {
        PayData payData = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 1, 31))
            .billingDate(LocalDate.of(2019, 2, 28))
            .payAmount(20_000)
            .build();
        assertExpirationDate(payData, LocalDate.of(2019, 4, 30));
    }

    @Test
    void 십만원을_납부하면_1년_제공() {
        PayData payData = PayData.builder()
            .billingDate(LocalDate.of(2019, 1, 28))
            .payAmount(100_000)
            .build();
        assertExpirationDate(payData, LocalDate.of(2020, 1, 28));
    }
}
