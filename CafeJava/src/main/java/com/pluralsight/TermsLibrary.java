package com.pluralsight;

import java.io.*;
import java.util.*;

public class TermsLibrary {

    public static List<String[]> loadTerms() {
        List<String[]> terms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Cafe_Java_Library_Terms.csv"))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                terms.add(parts);
            }
        } catch (IOException e) {
            System.out.println("⚠️ Could not load terms: " + e.getMessage());
        }
        return terms;
    }
}
