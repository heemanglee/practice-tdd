package com.example.tdd.chap02;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String password) {

        // 비밀번호에 아무 값도 들어오지 않는 경우 INVALID를 반환한다.
        if (password == null || password.isEmpty()) {
            return PasswordStrength.INVALID;
        }

        /*
        // 비밀번호의 길이가 8글자 이하이면 보통 수준이다.
        if (password.length() < 8) {
            return PasswordStrength.NORMAL;
        }
        */

        boolean lengthEnough = password.length() >= 8;

        boolean containsNumber = false; // 비밀번호에 숫자가 포함되어 있는지 확인하는 flag
        containsNumber = meetsContainingNumberCriteria(password, containsNumber);

        /* 아래에서 "약함" 수준임을 검증하기 위해 아래로 이동한다.
        if (!containsNumber) { // 비밀번호가 숫자가 포함되어 있지 않으면 "보통" 수준이다.
            return PasswordStrength.NORMAL;
        }
         */

        boolean containsUpperWord = false;
        containsUpperWord = meetsContainingUppercaseCriteria(password, containsUpperWord);

        /* 아래에서 "약함" 수준임을 검증하기 위해 아래로 이동한다.
        if (!containsUpperWord) { // 비밀번호에 대문자가 포함되어 있지 않으면 "보통" 수준이다.
            return PasswordStrength.NORMAL;
        }
         */

        // 비밀번호가 8글자 이상이지만, 나머지 조건을 만족하지 않는다면 "약함" 수준이다.
        if (lengthEnough && !containsNumber && !containsUpperWord) {
            return PasswordStrength.WEAK;
        }

        // 숫자를 포함하지만, 나머지 조건을 만족하지 않으면 "약함" 수준이다.
        if (!lengthEnough && containsNumber && !containsUpperWord) {
            return PasswordStrength.WEAK;
        }

        // 대문자를 포함하지만, 나머지 조건을 만족하지 않는 경우 "약함" 수준이다.
        if (!lengthEnough && !containsNumber && containsUpperWord) {
            return PasswordStrength.WEAK;
        }

        if (!lengthEnough) {
            return PasswordStrength.NORMAL;
        }

        if (!containsNumber || !containsUpperWord) {
            return PasswordStrength.NORMAL;
        }

        // 모든 조건에 부합하지 않으면 "강함" 수준이다.
        return PasswordStrength.STRONG;
    }

    public PasswordStrength meterRefactor(String password) {
        // 비밀번호에 아무 값도 들어오지 않는 경우 INVALID를 반환한다.
        if (password == null || password.isEmpty()) {
            return PasswordStrength.INVALID;
        }

        /**
         * 비밀번호가 만족하는 조건의 개수를 저장하는 변수
         * 1 : 약함
         * 2 : 보통
         * 3 : 강함
         */
        int metCount = getMetCriteriaCounts(password);

        if (metCount <= 1) {
            return PasswordStrength.WEAK;
        }
        if (metCount == 2) {
            return PasswordStrength.NORMAL;
        }

        return PasswordStrength.STRONG;
    }

    // 비밀번호가 만족하는 조건의 개수를 반환하는 메서드
    private int getMetCriteriaCounts(String password) {
        int metCount = 0;

        boolean lengthEnough = password.length() >= 8;
        if (lengthEnough) {
            metCount++;
        }

        boolean containsNumber = false; // 비밀번호에 숫자가 포함되어 있는지 확인하는 flag
        containsNumber = meetsContainingNumberCriteria(password, containsNumber);
        if (containsNumber) {
            metCount++;
        }

        boolean containsUpperWord = false;
        containsUpperWord = meetsContainingUppercaseCriteria(password, containsUpperWord);
        if (containsUpperWord) {
            metCount++;
        }

        return metCount;
    }

    // 비밀번호에 "대문자"가 포함되지 않는 경우 "보통" 수준이다.
    private static boolean meetsContainingUppercaseCriteria(String password,
        boolean containsNumber) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsNumber = true;
                break;
            }
        }
        return containsNumber;
    }

    // 비밀번호에 "숫자"가 포함되어 있는지 여부를 반환하는 메서드
    private static boolean meetsContainingNumberCriteria(String password, boolean containsNumber) {
        for (char c : password.toCharArray()) {
            if (c >= '0' && c <= '9') {
                containsNumber = true;
                break;
            }
        }
        return containsNumber;
    }
}
