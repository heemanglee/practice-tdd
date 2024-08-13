package com.example.tdd.chap05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 각 테스트 메서드는 반드시 독립적을 동작해야 한다. 아래는 테스트 메서드가 독립적이지 않은 예이다. -> 잘못된 테스트 클래스 만약 calculateNumTest()가 먼저
 * 호출되면 테스트를 실패하게 된다.
 */
public class BadTest {

    private static Calculator calculator;

    static class Calculator {

        public int add(int num1, int num2) {
            return num1 + num2;
        }
    }

    @Test
    @DisplayName("계산기 인스턴스 생성 테스트")
    void createCalculatorInstanceTest() {
        calculator = new Calculator();
        assertNotNull(calculator);
    }

    @Test
    @DisplayName("두 수를 더하는 테스트")
    @Disabled
        // 테스트를 비활성화한다.
    void calculateNumTest() {
        int num1 = 10;
        int num2 = 20;
        int result = calculator.add(num1, num2);
        assertEquals(30, result);
    }
}
