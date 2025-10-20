package com.pluralsight;

import java.io.*;
import java.util.*;

public class TermsLibrary {

    private static final String FILE_NAME = "Cafe_Java_Library_Terms.csv";

    // --- Load all terms safely ---
    public static List<String[]> loadTerms() {
        List<String[]> terms = new ArrayList<>();
        File csvFile = new File(FILE_NAME);

        if (!csvFile.exists()) {
            System.out.println("⚠️ File not found: " + csvFile.getAbsolutePath());
            return terms;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Skip blank lines or header
                if (line.isEmpty() || line.startsWith("Workbook,")) {
                    continue;
                }

                // Skip header only once
                if (isFirstLine && line.toLowerCase().contains("workbook,term")) {
                    isFirstLine = false;
                    continue;
                }
                isFirstLine = false;

                String[] parts = parseCSVLine(line);
                if (parts.length < 4) {
                    parts = Arrays.copyOf(parts, 4);
                }

                // Skip rows with missing workbook or term names
                if (parts[0] == null || parts[0].trim().isEmpty() ||
                        parts[1] == null || parts[1].trim().isEmpty()) {
                    continue;
                }

                terms.add(parts);
            }

            System.out.println("✅ Loaded " + terms.size() + " terms.");

        } catch (IOException e) {
            System.out.println("⚠️ Could not load terms: " + e.getMessage());
        }

        return terms;
    }

    // --- Save new term safely ---
    public static void saveTerm(String workbook, String term, String definition, String example) {
        File csvFile = new File(FILE_NAME);
        boolean fileExists = csvFile.exists();

        try (FileWriter fw = new FileWriter(csvFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Add header if new file
            if (!fileExists) {
                out.println("Workbook,Term,Definition,Example");
            }

            // Always write 4 fields in the same order
            String csvLine = String.join(",",
                    escapeForCSV(workbook),
                    escapeForCSV(term),
                    escapeForCSV(definition),
                    escapeForCSV(example)
            );

            out.println(csvLine);
            out.flush();

            System.out.println("✅ Term saved successfully.");

        } catch (IOException e) {
            System.out.println("⚠️ Could not save term: " + e.getMessage());
        }
    }

    // --- Parse line safely ---
    private static String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        tokens.add(current.toString());
        return tokens.toArray(new String[0]);
    }

    // --- Escape commas and quotes ---
    private static String escapeForCSV(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\""); // escape internal quotes
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            escaped = "\"" + escaped + "\"";
        }
        return escaped;
    }
}
