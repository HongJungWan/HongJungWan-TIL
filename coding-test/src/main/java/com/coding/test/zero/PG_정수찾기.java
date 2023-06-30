package com.coding.test.zero;

import java.util.stream.IntStream;

/**
 * 정수 리스트 num_list와 찾으려는 정수 n이 주어질 때,
 * num_list안에 n이 있으면 1을 없으면 0을 return 하도록
 * solution 함수를 완성해주세요.
 */
public class PG_정수찾기 {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        System.out.println(solution(arr, 3));
    }

    public static int solution(int[] num_list, int n) {
        return IntStream.of(num_list)
                .anyMatch(e -> e == n) ? 1 : 0;
    }
}

//    private static final int ANSWER_CONDITION_ZERO = 0;
//    private static final int ANSWER_CONDITION_ONE = 1;

//    public static int solution(int[] num_list, int n) {
//        for (int temp : num_list) {
//            if (temp == n) {
//                return ANSWER_CONDITION_ONE;
//            }
//        }
//        return ANSWER_CONDITION_ZERO;
//    }