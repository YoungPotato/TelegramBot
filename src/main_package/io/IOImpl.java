package main_package.io;

import java.util.Scanner;

public class IOImpl implements IO {
    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
}
