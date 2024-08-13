package com.example.tdd.chap05;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLifeCycle {

    /**
     * 실행하고자 하는 모든 테스트에 대하여 딱 한 번만 실행된다.
     *
     * 테스트 클래스 생성
     * 테스트 메서드 실행 전
     * A
     * 테스트 메서드 실행 후
     *
     * 테스트 클래스 생성
     * 테스트 메서드 실행 전
     * B
     * 테스트 메서드 실행 후
     *
     * 테스트 클래스 생성
     * 테스트 메서드 실행 전
     * C
     * 테스트 메서드 실행 후
     *
     * 실행하고자 하는 모든 테스트에 대하여 딱 한 번만 실행된다.
     */

    public TestLifeCycle() {
        System.out.println("테스트 클래스 생성");
    }

    @BeforeEach
    void setUp() {
        System.out.println("테스트 메서드 실행 전");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("실행하고자 하는 모든 테스트에 대하여 딱 한 번만 실행된다. \n");
    }

    @AfterEach
    void tearDown() {
        System.out.println("테스트 메서드 실행 후 \n");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("실행하고자 하는 모든 테스트에 대하여 딱 한 번만 실행된다.");
    }

    @Test
    void testA() {
        System.out.println("A");
    }

    @Test
    void testB() {
        System.out.println("B");
    }

    @Test
    void testC() {
        System.out.println("C");
    }
}
