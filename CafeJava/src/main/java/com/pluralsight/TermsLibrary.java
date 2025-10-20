package com.pluralsight;

import java.io.*;
import java.util.*;

public class TermsLibrary {

    private static final String FILE_NAME = "Cafe_Java_Library_Terms.csv";

    /**
     * 📚 Loads all terms from the CSV library.
     * Each row is returned as a String[]:
     *   [0] = Workbook
     *   [1] = Term
     *   [2] = Definition
     *   [3] = Example (optional)
     */
    public static List<String[]> loadTerms() {
        List<String[]> terms = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("⚠️ CSV file not found: " + FILE_NAME);
            return terms;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            br.readLine(); // skip header row

            while ((line = br.readLine()) != null) {
                // Skip empty lines or badly formatted rows
                if (line.trim().isEmpty()) continue;

                // Split by pipe and ensure at least 3 columns
                String[] parts = line.split("\\|", -1);
                if (parts.length >= 3) {
                    terms.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Could not load terms: " + e.getMessage());
        }

        return terms;
    }
}
