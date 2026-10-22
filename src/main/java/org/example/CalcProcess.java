package org.example;

import java.util.List;

public class CalcProcess {
    public void process(List<String> step) {
        minusToggle(step);
        while (splitDoubleParen(step)) ;
        step.removeIf(s -> s == null || s.isBlank());
        paren(step);
    }


    private boolean splitDoubleParen(List<String> step) {
        int originSize = step.size();
        boolean isFixed = false;

        // ((
        for (int i = 0; i < originSize; i++) {
            String targetStr = step.get(i);
            int targetIndex = targetStr.indexOf("((");
            if (targetIndex < 0) continue;
            isFixed = true;

            String newValue = "(0";

            String prefix = targetStr.substring(0, targetIndex);

            String suffix = targetStr.substring(targetIndex + 1); //두번째(부터 되나? 체크해봐야함

            step.remove(i);
            step.addAll(i, List.of(prefix, newValue, "+", suffix));
        }
        // ))

        for (int i = 0; i < step.size(); i++) {
            String targetStr = step.get(i);
            int targetIndex = targetStr.indexOf("))");
            if (targetIndex < 0) continue;
            isFixed = true;

            String prefix = targetStr.substring(0, targetIndex+1);

            String newValue = "0)";

            String suffix = targetStr.substring(targetIndex + 2);

            step.remove(i);
            step.addAll(i, List.of(prefix, "+", newValue, suffix));
        }

        return isFixed;

    }

    void setupResultValue(List<String> list) {
        int result = defaultProcess(list);
        list.clear();
        list.add(result + "");
    }

    private void paren(List<String> step) {
        while (true) {
            int openIndex = -1;
            int closeIndex = -1;

            for (int i = 0; i < step.size(); i++) {
                String target = step.get(i);
                if (target.contains("(")) {
                    openIndex = i;
                }
                if (target.contains(")")) {
                    closeIndex = i;
                    break;
                }
            }

            if (openIndex < 0 && closeIndex < 0) {
                setupResultValue(step);
                return;
            }

            if (openIndex < 0 || closeIndex < 0) {
                System.out.println("이 메시지가 출력이 되면 안됩니다.");
                return;
            }

            List<String> subList = step.subList(openIndex, closeIndex + 1);
            subList.set(0, subList.getFirst().substring(1));
            int lastIndex = subList.size() - 1;
            subList.set(lastIndex, subList.getLast().substring(0, subList.getLast().length() - 1));
            setupResultValue(subList);
        }

    }

    /*
    private void paren(List<String> step) {
        // ((20 + 20)) + 20
        if (findSEWithIndex(true, step, "(") < 0) {
            setupResultValue(step);
            return;
        }

        while (step.size() > 1) {
            int oc = 0;
            int cc = 0;
            int lastOcIndex = -1;
            int firstCcIndex = -1;

            for (int i = 0; i < step.size(); i++) {
                String target = step.get(i);
                if (target.contains("(")) {
                    oc++;
                    lastOcIndex = i;
                }
                if (target.contains(")")) {
                    cc++;
                    if (firstCcIndex < 0) firstCcIndex = i;
                }
            }

            if (oc == 0) {
                setupResultValue(step);
                break;
            }

            if (oc == cc) {
                String num1 = step.get(lastOcIndex);
                String num2 = step.get(firstCcIndex);

                num1 = num1.substring(1);
                num2 = num2.substring(0, num2.length() - 1);

                List<String> subList = step.subList(lastOcIndex, firstCcIndex + 1);
                subList.removeFirst();
                subList.addFirst(num1);

                subList.removeLast();
                subList.addLast(num2);

                setupResultValue(subList);
            } else {
                // 앞에 더블 괄호 처리부분이 끝났으면 이럴일이 없다 나오면안됨.
                System.out.println("이 메시지가 출력이 되면 안됩니다.");
            }
        }
    }
       */

    public int defaultProcess(List<String> list) {
        if (list.size() == 1 && isInt(list.getFirst())) return Integer.parseInt(list.getFirst());

        multi(list);

        if (list.size() == 1 && isInt(list.getFirst())) return Integer.parseInt(list.getFirst());


        int result = 0;

        int[] temp = new int[2];
        temp[0] = Integer.parseInt(list.getFirst());

        Operator operator;
        for (int i = 0; (i + 2) < list.size(); i += 2) {
            operator = Operator.newValueOf(list.get(i + 1));
            temp[1] = Integer.parseInt(list.get(i + 2));

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

        return result;
    }


    int threeCalc(int num1, Operator op, int num2) {
        return switch (op) {
            case PLUS -> num1 + num2;
            case MINUS -> num1 - num2;
            case MULTI -> num1 * num2;
        };
    }

    private void minusToggle(List<String> step) {
        //    @DisplayName("-(10 + 5) == -15")
        List<String> subStep = step.subList(0, step.size());

        for (int i = 0; i < subStep.size(); i++) {
            int minusIndex = findSEWithIndex(true, subStep, "-");
            if (minusIndex < 0) return;

            List<String> sub = subStep.subList(minusIndex, subStep.size());
            String target = sub.getFirst();

            if (target.startsWith("-(")) {
                sub.removeFirst();
                sub.addAll(0, List.of("-1", "*", target.substring(1)));
            }
            if (minusIndex + 1 >= subStep.size()) break;
            subStep = subStep.subList(minusIndex + 1, subStep.size());
        }
    }

    boolean isInt(String str) {
        try {
            int _ = Integer.parseInt(str.trim());
            return true;
        } catch (NumberFormatException _) {
            return false;
        }
    }

    /**
     *
     * @param {boolean} isStartWith : true -> startsWith() false -> endsWith()
     * @param target
     * @param keyword
     * @return
     */
    int findSEWithIndex(boolean isStartWith, List<String> target, String keyword) {
        for (int i = 0; i < target.size(); i++) {
            if (isStartWith) {
                if (target.get(i).startsWith(keyword)) return i;
            } else {
                if (target.get(i).endsWith(keyword)) return i;
            }
        }

        return -1;
    }

    void multi(List<String> step) {
//        @DisplayName("10 + 5 * 2 == 20")
        int multiIndex = step.indexOf("*");
        while (multiIndex >= 0) {
            String before = step.get(multiIndex - 1);
            String after = step.get(multiIndex + 1);

            if (isInt(before) && isInt(after)) {
                step.remove(multiIndex - 1);
                step.remove(multiIndex - 1);
                step.remove(multiIndex - 1);

                int b = Integer.parseInt(before);
                int a = Integer.parseInt(after);
                step.add(multiIndex - 1, b * a + "");
            }
            multiIndex = step.indexOf("*");
        }
    }
}
