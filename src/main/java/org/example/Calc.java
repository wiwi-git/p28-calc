package org.example;


import java.io.IOException;

/**
 * 문제: 문자열 식을 받아 계산하는 Calc.run(String)을 TDD로 만듭니다. 이번에는 덧셈·뺄셈·곱셈이 들어간 식(t1~t14)까지 통과시킵니다.
 * +, - *
 * 나눗셈은 없이!
 * 괄호도 없어!
 * 그냥 순서대로 짤라서 진행하면될듯?
 */

enum Operator {
    PLUS, MINUS, MULTI;

    static Operator newValueOf(String str) {
        return switch (str.trim()) {
            case "+" -> Operator.PLUS;
            case "-" -> Operator.MINUS;
            case "*" -> Operator.MULTI;
            default -> throw new IllegalArgumentException();
        };
    }
}

public class Calc {

    public static int run(String str) {
        String target = str.trim();

        String [] step = target.split(" ");

        // check values
        if (step.length <= 2) {
            System.out.println("잘못된 입력이 들어왔습니다. > " + target);
//            throw new IOException();// 문제에서 try해줄수가 없어서 그냥 보냄
            return -Integer.MAX_VALUE;
        }

        // 숫자, 연산자, 숫자 순으로 쭉쭉쭉 그러면 숫자는 항상 짝수 인덱스에(0,2,4 ...) 있네?

        for (int i = 0; i < step.length; i++) {
            step[i] = step[i].trim();

            if (i % 2 == 0) {
                try {
                    int _ = Integer.parseInt(step[i]);
                } catch (NumberFormatException _) {
                    System.out.println("잘못된 입력이 들어왔습니다. > " + step[i]);
                    return -Integer.MAX_VALUE;
                }
            } else {
                try {
                    Operator _ = Operator.newValueOf(step[i]);
                } catch (IllegalArgumentException _) {
                    System.out.println("잘못된 입력이 들어왔습니다. > " + step[i]);
                    return -Integer.MAX_VALUE;
                }
            }
        }

        // 잘못된 입력이 없다고 가정
        int result = 0;

        int[] temp = new int[2];
        temp[0] = Integer.parseInt(step[0]);

        Operator operator;

//        10 + 20 + 30;
//        0 1 2;
//        2 3 4


        for (int i = 0; (i + 2) < step.length; i += 2) {
            operator = Operator.newValueOf(step[i+1]);
            temp[1] = Integer.parseInt(step[i+2]);

            switch (operator) {
                case PLUS -> {
                    temp[0] = temp[0] + temp[1];
                }
                case MINUS -> {
                    temp[0] = temp[0] - temp[1];
                }
                case MULTI -> {
                    temp[0] = temp[0] * temp[1];
                }
            }

            result = temp[0];
        }

        // 출력
//        System.out.println(result);
        return result;
    }
}
