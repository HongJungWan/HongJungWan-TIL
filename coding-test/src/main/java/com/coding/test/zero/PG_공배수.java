package com.coding.test.zero;

/**
 * 정수 number와 n, m이 주어집니다.
 * number가 n의 배수이면서 m의 배수이면 1을 아니라면 0을 return 하도록
 * solution 함수를 완성해주세요.
 */
public class PG_공배수 {
    public static void main(String[] args) {
        System.out.println(solution(60, 2, 3));
    }

    public static int solution(int number, int n, int m) {
        int answer = 0;
        if (n < 2 || m > 10) {
            return answer;
        }
        if (number % n == 0 && number % m == 0) {
            return ++answer;
        }
        return answer;
    }
}