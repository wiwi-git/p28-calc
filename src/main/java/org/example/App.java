package org.example;

import java.util.Scanner;

public class App {
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String cmd = sc.nextLine();

            if (cmd.startsWith("exit")) break;
            else Calc.run(cmd);
        }
    }
}
