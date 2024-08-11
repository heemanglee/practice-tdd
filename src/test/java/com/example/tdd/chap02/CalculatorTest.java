package com.example.tdd.chap02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CalculatorTest {

    /**
     * TDD는 테스트부터 시작한다. 테스트 코드를 먼저 작성하고 이에 맞추어 구현한다.
     * 1. 기능을 검증하는 테스트 코드를 작성한다.
     * 2. 테스트를 통과하기 위한 코드르 작성한다.
     */

    @Test
    void plus() {
        // 1. 기능을 검증하기 위한 테스트 코드를 작성한다.
        // 이 시점에는 Calculator 클래스가 존재하지 않으므로 컴파일 에러가 발생한다.
        int result = Calculator.plus(1, 2);
        assertEquals(3, result);
    }
}
