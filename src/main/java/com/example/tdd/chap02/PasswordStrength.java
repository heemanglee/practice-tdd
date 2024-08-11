package com.example.tdd.chap02;

public enum PasswordStrength {

    STRONG, // 비밀번호가 "강함"인지만 테스트할 것이기 때문에, "약함", "보통"은 생성하지 않는다.
    NORMAL, // "보통"인지도 검증하기 위해서 추가한다.
    INVALID, // 비밀번호에 아무 값도 들어오지 않는 경우 반환한다.
    WEAK, // 한가지 조건만 만족하거나 모두 만족하지 않는 경우 "약함"수준이다.
    ;
}
