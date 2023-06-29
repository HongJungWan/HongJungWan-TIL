package com.coding.test.zero;

/**
 * 정수 n과 k가 주어졌을 때,
 * 1 이상 n이하의 정수 중에서 k의 배수를 오름차순으로 저장한 배열을 return 하는
 * solution 함수를 완성해 주세요.
 */
public class PG_배열만들기1 {
    public static void main(String[] args) {
        for (int print : solution(10, 3)) {
            System.out.print(print + " ");
        }
    }

    public static int[] solution(int n, int k) {
        int[] answer = {};
        int temp = 0, value = 0;
        for (int i = k; i <= n; i += k) {
            temp++;
        }
        answer = new int[temp];
        value = k;
        for (int i = 0; i < temp; i++) {
            answer[i] = k;
            k += value;
        }
        return answer;
    }
}