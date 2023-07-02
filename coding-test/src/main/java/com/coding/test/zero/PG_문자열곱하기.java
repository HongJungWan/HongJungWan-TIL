package com.coding.test.zero;

public class PG_문자열곱하기 {
    public static void main(String[] args) {
        System.out.println(solution("string", 3));
    }

    public static String solution(String my_string, int k) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < k; i++) {
            stringBuilder.append(my_string);
        }
        return stringBuilder.toString();
    }
}