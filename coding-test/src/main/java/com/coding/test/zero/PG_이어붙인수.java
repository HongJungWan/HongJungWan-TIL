package com.coding.test.zero;

/**
 * 홀수만 이어 붙인 수는 351이고 짝수만 이어 붙인 수는 42입니다.
 * 두 수의 합은 393
 */
public class PG_이어붙인수 {
    public static void main(String[] args) {
        int[] array = {5, 7, 8, 3};
        System.out.println(solution(array));
    }

    public static int solution(int[] num_list) {
        StringBuilder sbOne = new StringBuilder();
        StringBuilder sbTwo = new StringBuilder();

        for (int i = 0; i < num_list.length; i++) {
            if (num_list[i] % 2 != 0) {
                sbOne.append(num_list[i]);
            } else if (num_list[i] % 2 == 0) {
                sbTwo.append(num_list[i]);
            }
        }
        return Integer.parseInt(sbOne.toString()) + Integer.parseInt(sbTwo.toString());
    }
}