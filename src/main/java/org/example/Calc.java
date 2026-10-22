package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 문제: 문자열 식을 받아 계산하는 Calc.run(String)을 TDD로 만듭니다. 이번에는 덧셈·뺄셈·곱셈이 들어간 식(t1~t14)까지 통과시킵니다.
 * +, - *
 * 나눗셈은 없이!
 * 괄호도 없어!
 * 그냥 순서대로 짤라서 진행하면될듯?
 *
 * 문제: 다항식 계산기 ①에 이어 연산자 우선순위와 **괄호(중첩·음수 괄호 포함)**까지 처리해 t1~t28 을 모두 통과시킵니다.
 * 망했다 연산순서 생각안하고 만든거라 다 뒤집어질거다
 *
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
        List<String> step = new ArrayList<>(Arrays.stream(target.split(" ")).toList());

        new CalcProcess().process(step);

        return Integer.parseInt(step.getFirst());
    }
}


//public class RealCalc {
//    public int calc(String str) {
//        String target = str.trim();
//        List<String> step = new ArrayList<>(Arrays.stream(target.split(" ")).toList());
//
//        // TODO: 정규식 검사를 해보려는데 뭔가 안된다 규칙을 잘 모르겠음 이후에 다시 추가하자
/// /        String regex = "^[0-9]\\+\\-\\(\\)\\s]+$";
/// /        target.matches(regex);
//
//        minusToggle(step);
//
//        try {
//            paren(step);
//        } catch (IOException e) {
//            return -Integer.MAX_VALUE;
//        }
//
//        multi(step);
//        return defaultProcess(step);
//    }
//
//    private void minusToggle(List<String> step) {
//        //    @DisplayName("-(10 + 5) == -15")
//        List<String> subStep = step.subList(0, step.size());
//
//        for (int i = 0; i < step.size(); i++) {
//            int minusIndex = findSEWithIndex(true, subStep, "-");
//            if (minusIndex < 0) return;
//
//            List<String> sub = subStep.subList(minusIndex, subStep.size());
//            String target = sub.getFirst();
//
//            if (target.startsWith("-(")) {
//                sub.removeFirst();
//                sub.add(0, "-1");
//                sub.add(1, "*");
//                sub.add(2, target.substring(1));
//            }
//
//            subStep = subStep.subList(minusIndex, subStep.size());
//        }
//    }
////
////    private void paren(List<String> step) throws IOException {
//////        @DisplayName("(10 + 20) == 30")
//////        @DisplayName("(((10 + 20))) == 30")
//////        @DisplayName("-(8 + 2) * -(7 + 3) + 5 == 105")
////
//////        int openParenIndex = findStartWithIndex(step, "(");
////        int openParenIndex = findSEWithIndex(true, step, "(");
////        if (openParenIndex < 0) return;
////
////        String openParen = step.get(openParenIndex);
////        // open
////        int sOpenParenIndex = openParen.indexOf('(');
////        boolean isDobuleOpenParen = false;
////        if (sOpenParenIndex + 1 < openParen.length()) {
////            char[] sArray =  openParen.toCharArray();
////            if (sArray[sOpenParenIndex + 1] == '(') isDobuleOpenParen = true;
////        }
////
////        if (isDobuleOpenParen) {
////            step.remove(openParenIndex);
////            String prefix = openParen.substring(0, sOpenParenIndex + 1);
////            String suffix = openParen.substring(sOpenParenIndex + 1);
////            step.addAll(openParenIndex, new ArrayList<>(List.of(prefix, suffix)));
////            paren(step);
////        }
////
////        int closeParenIndex = findSEWithIndex(true, step, ")");
////        if (closeParenIndex < 0) {
////            System.out.println("괄호를 찾는 부분에서 문제가 발생했습니다.");
////            throw new IOException();
//////                return -Integer.MAX_VALUE;
////        }
////        // close
////        String closeParen = step.get(closeParenIndex);
////        int sCloseParenIndex = closeParen.indexOf('(');
////        boolean isDobuleCloseParen = false;
////        if (sCloseParenIndex + 1 < closeParen.length()) {
////            char[] sArray =  closeParen.toCharArray();
////            if (sArray[sCloseParenIndex + 1] == '(') isDobuleCloseParen = true;
////        }
////
////        if (isDobuleCloseParen) {
////            step.remove(sCloseParenIndex);
////            String prefix = closeParen.substring(0, sCloseParenIndex + 1);
////            String suffix = closeParen.substring(sCloseParenIndex + 1);
////            step.addAll(closeParenIndex, new ArrayList<>(List.of(prefix, suffix)));
////            paren(step);
////        }
////
////
////
////        // 본처리
////        int openParenIndex2 = findSEWithIndex(
////                true,
////                step.subList(openParenIndex + 1, step.size()),
////                "("
////        );
////
////        // 서브
////        if (openParenIndex2 >= 0 && openParenIndex2 < closeParenIndex) {
////            List<String> sub = step.subList(openParenIndex2, step.size());
////            paren(sub);
////            return;
////        }
////
////        List<String> subStr = step.subList(openParenIndex, closeParenIndex + 1);
////        String startStep = subStr.getFirst().substring(1);
////        subStr.removeFirst();
////        subStr.addFirst(startStep);
////
////        String endStep = subStr.getLast();
////        endStep = endStep.substring(0, endStep.length() - 1);
////        subStr.removeLast();
////        subStr.addLast(endStep);
////
//
//    /// /        StringBuilder sb = new StringBuilder();
//    /// /        for (String sub: subStr) sb.append(sub).append(" ");
////
////        int subResult = defaultProcess(subStr);
////
////        subStr.clear();
////        subStr.add(subResult + "");
////    }
//    private void paren(List<String> step) throws IOException {
//        // ((20 + 20)) + 20
//        int openParenIndex = findSEWithIndex(true, step, "(");
//        int closeParenIndex = findSEWithIndex(false, step, ")");
//
//        String part1 = step.get(openParenIndex);
//        String part2 = step.get(closeParenIndex);
//
//        String part1_1 = part1.substring(1);
//        String part2_1 = part2.substring(0,part2.length() - 1);
//
//        if (isInt(part1_1) && isInt(part2_1)) {
//            defaultProcess()
//        }
//
//    }
//
//    void multi(List<String> step) {
////        @DisplayName("10 + 5 * 2 == 20")
//        int multiIndex = step.indexOf("*");
//        while (multiIndex >= 0) {
//            String before = step.get(multiIndex - 1);
//            String after = step.get(multiIndex + 1);
//
//            if (isInt(before) && isInt(after)) {
//                step.remove(multiIndex - 1);
//                step.remove(multiIndex - 1);
//                step.remove(multiIndex - 1);
//
//                int b = Integer.parseInt(before);
//                int a = Integer.parseInt(after);
//                step.add(multiIndex - 1, b * a + "");
//            }
//            multiIndex = step.indexOf("*");
//        }
//    }
//
//    boolean isInt(String str) {
//        try {
//            int _ = Integer.parseInt(str.trim());
//            return true;
//        } catch (NumberFormatException _) {
//            return false;
//        }
//    }
//
//    /**
//     *
//     * @param {boolean} isStartWith : true -> startsWith() false -> endsWith()
//     * @param target
//     * @param keyword
//     * @return
//     */
//    int findSEWithIndex(boolean isStartWith, List<String> target, String keyword) {
//        for (int i = 0; i < target.size(); i++) {
//            if (isStartWith) {
//                if (target.get(i).startsWith(keyword)) return i;
//            } else {
//                if (target.get(i).endsWith(keyword)) return i;
//            }
//        }
//
//        return -1;
//    }
//
//
//    int findEndWithLastIndex(List<String> target, String suffix) {
//        final int lastIndex = target.size() - 1;
//        for (int i = lastIndex; i >= 0; i--) {
//            if (target.get(i).endsWith(suffix)) return i;
//        }
//
//        return -1;
//    }
//
//
//    public int defaultProcess(List<String> step) {
//        if (step.size() == 1 && isInt(step.getFirst())) return Integer.parseInt(step.getFirst());
//
//        int result = 0;
//
//        int[] temp = new int[2];
//        temp[0] = Integer.parseInt(step.getFirst());
//
//        Operator operator;
//        for (int i = 0; (i + 2) < step.size(); i += 2) {
//            operator = Operator.newValueOf(step.get(i + 1));
//            temp[1] = Integer.parseInt(step.get(i + 2));
//
//            switch (operator) {
//                case PLUS -> {
//                    temp[0] = temp[0] + temp[1];
//                }
//                case MINUS -> {
//                    temp[0] = temp[0] - temp[1];
//                }
//                case MULTI -> {
//                    temp[0] = temp[0] * temp[1];
//                }
//            }
//
//            result = temp[0];
//        }
//
//        return result;
//    }
//}
