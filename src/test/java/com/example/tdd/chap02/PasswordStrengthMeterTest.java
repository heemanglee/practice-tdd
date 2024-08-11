package com.example.tdd.chap02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 1. 길이가 8글자 이상이다.
 * 2. 0~9 사이의 숫자를 포함한다.
 * 3. 대문자를 포함한다.
 * 4. 위 세 규칙을 만족하면 암호 수준이 "강함"이다.
 * 5. 위 두 규칙을 만족하면 암호 수준이 "보통"이다.
 * 6. 한 개 이하의 규칙을 만족하면 암호 수준이 "약함"이다.
 */


// 패스워드를 검증하는 테스트 클래스
public class PasswordStrengthMeterTest {

    // "중복"하여 사용되는 코드는 필드에서 생성하도록 한다.
    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    // "중복" 메서드를 분리한다.
    private void assertStrength(String password, PasswordStrength expStr) {
//        PasswordStrength result = meter.meter(password);
        PasswordStrength result = meter.meterRefactor(password); // 리팩토링된 코드 사용
        assertEquals(expStr, result);
    }

    @Test
    void 비밀번호의_수준이_강함인지_검증하는_테스트() {
        // "강함" 수준인지만 검증할 것이기 때문에 ,PasswordStrength.STRONG만 생성한다.
//        PasswordStrength result = meter.meter("ab12!@AB");
//        assertEquals(PasswordStrength.STRONG, result);

        assertStrength("ab12!@AB", PasswordStrength.STRONG);
    }

    @Test
    void 비밀번호가_8글자_이하이면_보통수준임을_테스트() {
        // 이어서 "보통" 수준인지 검증할 것이기 때문에, PasswordStrength.NORMAL을 추가로 생성한다.
//        PasswordStrength result = meter.meter( "Ab12!c");
//        assertEquals(PasswordStrength.NORMAL, result);

        assertStrength("Ab12!c", PasswordStrength.NORMAL);
    }

    @Test
    void 숫자를_포함하지_않고_나머지_조건을_충족하는_경우_보통수준임을_테스트() {
//        PasswordStrength result = meter.meter("ab!@ABqwer");
//        assertEquals(PasswordStrength.NORMAL, result);

        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    @Test
    void 비밀번호에_아무값도_들어오지_않는경우_INVALID임을_테스트() {
        assertStrength("", PasswordStrength.INVALID);
    }

    @Test
    void 비밀번호에_대문자가_포함되지_않을경우_보통수준임을_테스트() {
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }

    @Test
    void 비밀번호가_한가지_조건만_만족하거나_모두_만족하지_않는경우_약함수준임을_테스트() {
        // 8글자 이상이지만, 나머지 조건을 만족하지 않으므로 "약함" 수준이다.
        assertStrength("abcdefgh", PasswordStrength.WEAK);
    }

    @Test
    void 비밀번호에_숫자만_포함하는경우_약함수준임을_테스트() {
        assertStrength("12345", PasswordStrength.WEAK);
    }

    @Test
    void 비밀번호에_대문자만_포함하는경우_약함수준임을_테스트() {
        assertStrength("ABCDE", PasswordStrength.WEAK);
    }

    @Test
    void 비밀번호가_아무조건도_만족하지_않는경우_약함수준임을_테스트() {
        assertStrength("abc", PasswordStrength.WEAK);
    }
}
