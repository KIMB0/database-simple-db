package com.company;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to BankBox!");

        while (true) {
            System.out.println("Type 'get' for seeing what's in the bankbox, or type 'set' to put things in the bankbox:");
            Scanner scan = new Scanner(System.in);
            String entered = scan.next();
            switch (entered) {
                case "get":
                    String item;
                    System.out.println("Get-Mode. Enter an item to find, and see its value:");
                    item = scan.next();
                    getItem(item);
                    break;
                case "set":
                    String setItem;
                    System.out.println("Set-Mode: Enter an item to set into the bankbox:");
                    setItem = scan.next();
                    int value;
                    System.out.println("Set-Mode: Enter the value of the item:");
                    value = scan.nextInt();
                    storeItem(setItem, value);
                default:
                    break;
            }
        }

    }

    public static void storeItem(String item, int value) throws IOException {
        HashMap<String, Integer> simple_db = new HashMap<String, Integer>();
        simple_db.put(item, value);

        byte[]data = simple_db.toString().getBytes();

        try (FileOutputStream fos = new FileOutputStream("simple_db", true)) {
            fos.write(data);
            System.out.println("It's safe in the bankbox!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getItem(String item) throws IOException {
        HashMap<String, Integer> simple_db = new HashMap<String, Integer>();

        Path path = Paths.get("simple_db");
        byte[] data = Files.readAllBytes(path);
        String dataString = new String(data);
        String result = dataString.replaceAll("/}/b", "");
        String[] pairs = result.split(",");
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split("=");
            simple_db.put(keyValue[0], Integer.valueOf(keyValue[1]));
        }

        System.out.println(simple_db.get(item));
    }

}
