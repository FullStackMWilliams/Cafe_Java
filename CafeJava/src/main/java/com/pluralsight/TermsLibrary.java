package com.pluralsight;

import java.io.*;
import java.util.*;

public class TermsLibrary {

    private static final String FILE_NAME = "Cafe_Java_Library_Terms.csv";
    private static final String HEADER = "Workbook,Term,Definition,Example";

    /** Load all valid rows from the CSV, skipping header/blank/partial lines. */
    public static List<String[]> loadTerms() {
        List<String[]> terms = new ArrayList<>();
        File csvFile = new File(FILE_NAME);

        if (!csvFile.exists()) {
            // Create an empty file with header so the app can start cleanly.
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(csvFile, false)))) {
                out.println(HEADER);
            } catch (IOException ignored) {}
            return terms;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Skip header or any blank/whitespace lines
                if (line.isEmpty() || line.equalsIgnoreCase(HEADER) || line.toLowerCase().startsWith("workbook,term")) {
                    continue;
                }

                String[] parts = parseCSVLine(line);

                // Normalize to exactly 4 columns
                if (parts.length < 4) {
                    parts = Arrays.copyOf(parts, 4);
                }
                for (int i = 0; i < 4; i++) {
                    if (parts[i] == null) parts[i] = "";
                }

                // Skip rows missing workbook or term
                if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    continue;
                }

                terms.add(parts);
            }
        } catch (IOException e) {
            System.out.println("⚠️ Could not load terms: " + e.getMessage());
        }

        return terms;
    }

    /**
     * Save a term. Prevents duplicates (same workbook + term, case-insensitive).
     * Rewrites the whole CSV each time to guarantee a clean single header and no stray lines.
     */
    public static void saveTerm(String workbook, String term, String definition, String example) {
        // Normalize inputs
        String wb = safe(workbook);
        String tm = safe(term);
        String df = safe(definition);
        String ex = safe(example);

        // Load existing
        List<String[]> rows = loadTerms();

        // Check duplicate
        for (String[] r : rows) {
            if (r.length >= 2 &&
                    r[0].trim().equalsIgnoreCase(wb) &&
                    r[1].trim().equalsIgnoreCase(tm)) {
                System.out.println("⚠️ That term already exists in this workbook. Skipping duplicate.");
                return;
            }
        }

        // Append and rewrite
        rows.add(new String[]{wb, tm, df, ex});
        writeAll(rows);
        System.out.println("✅ Term saved successfully.");
    }

    /** Overwrite the CSV with header + all rows, safely escaped. */
    private static void writeAll(List<String[]> rows) {
        File csvFile = new File(FILE_NAME);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(csvFile, false)))) {
            out.println(HEADER);
            for (String[] r : rows) {
                String line = String.join(",",
                        escapeForCSV(get(r, 0)),
                        escapeForCSV(get(r, 1)),
                        escapeForCSV(get(r, 2)),
                        escapeForCSV(get(r, 3))
                );
                out.println(line);
            }
        } catch (IOException e) {
            System.out.println("⚠️ Could not write CSV: " + e.getMessage());
        }
    }

    // ---------- Helpers ----------

    /** Robust CSV line parser that honors quotes and commas in quoted fields. */
    private static String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // If next char is also a quote, it's an escaped quote
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++; // skip the escaped quote
                } else {
                    inQuotes = !inQuotes; // toggle
                }
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

    /** Escape field for CSV: double up quotes and wrap in quotes if needed. */
    private static String escapeForCSV(String value) {
        if (value == null) return "";
        String v = value.replace("\"", "\"\""); // escape quotes
        if (v.contains(",") || v.contains("\"") || v.contains("\n") || v.contains("\r")) {
            v = "\"" + v + "\"";
        }
        return v;
    }

    private static String safe(String s) { return s == null ? "" : s.trim(); }

    private static String get(String[] arr, int i) {
        return (arr != null && i >= 0 && i < arr.length && arr[i] != null) ? arr[i] : "";
    }
}

