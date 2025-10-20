package com.pluralsight;

import java.io.*;
import java.util.*;

public class TermsLibrary {

    // Load all terms from the CSV file
    public static List<String[]> loadTerms() {
        List<String[]> terms = new ArrayList<>();

        File csvFile = new File("Cafe_Java_Library_Terms.csv");

        if (!csvFile.exists()) {
            System.out.println("⚠️ File not found: " + csvFile.getAbsolutePath());
            return terms;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine(); // skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 4) { // Workbook, Term, Definition, Example
                    terms.add(parts);
                }
            }

            if (terms.isEmpty()) {
                System.out.println("⚠️ No terms loaded. Please check your CSV formatting.");
            } else {
                System.out.println("✅ Loaded " + terms.size() + " terms successfully.");
            }

        } catch (IOException e) {
            System.out.println("⚠️ Could not load terms: " + e.getMessage());
        }

        return terms;
    }

    // Save a new term to the CSV file safely
    public static void saveTerm(String workbook, String term, String definition, String example) {
        File csvFile = new File("Cafe_Java_Library_Terms.csv");
        boolean fileExists = csvFile.exists();

        try (FileWriter fw = new FileWriter(csvFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Add header if file didn't exist
            if (!fileExists) {
                out.println("Workbook,Term,Definition,Example");
            }

            // Write the term safely
            out.printf("%s,%s,%s,%s%n",
                    escapeForCSV(workbook),
                    escapeForCSV(term),
                    escapeForCSV(definition),
                    escapeForCSV(example)
            );

            System.out.println("✅ Term saved successfully to " + csvFile.getName());

        } catch (IOException e) {
            System.out.println("⚠️ Could not save term: " + e.getMessage());
        }
    }

    // --- Helper Methods ---

    // Parse a CSV line while respecting commas inside quotes
    private static String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; // toggle quote mode
            } else if (c == ',' && !inQuotes) {
                tokens.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        tokens.add(current.toString().trim()); // last field
        return tokens.toArray(new String[0]);
    }

    // Escape commas and quotes when writing CSV lines
    private static String escapeForCSV(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\""); // escape internal quotes
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            escaped = "\"" + escaped + "\""; // wrap in quotes if needed
        }
        return escaped;
    }
}
