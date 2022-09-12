package com.parkingvision.host;

import java.io.*;
import java.util.Scanner;

public class ReadCSV {

    public static void main(String[] args) {
        System.out.println("Hello Read CSV file!");

        try {
            Scanner sc = new Scanner(new File("grades.csv"));
            sc.useDelimiter(",");   // Sets the delimiter pattern

            // Returns a boolean value
            while (sc.hasNext()) {
                // Finds and returns the next complete token from this scanner
                System.out.print(sc.next());
            }
            sc.close(); // Closes the scanner

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
