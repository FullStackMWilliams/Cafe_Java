package com.pluralsight;

import java.io.*;
import java.util.*;

/**
 * ☕ Cafe Java
 * ---------------------------------------------------------
 * CLI application for mastering Java terms and definitions.
 * Works with a CSV file containing workbook-tagged terms.
 * Features:
 *  1. Search for terms
 *  2. Learn random term
 *  3. Add a new term
 *  4. Display workbook contents
 *  5. Start a quiz (True/False)
 */
public class CafeJava {
    private static final Scanner in = new Scanner(System.in);
    private static final String FILE_NAME = "Cafe_Java_Library_Terms.csv"; // capitalized name
    private static final Random random = new Random();

    public static void main(String[] args) {
        ensureFile();

        while (true) {
            System.out.println("\n=== ☕ Cafe Java Menu ===");
            System.out.println("1. 🔍 Search by Term");
            System.out.println("2. 📖 Learn New Term");
            System.out.println("3. ➕ Add a New Term");
            System.out.println("4. 📂 Display Workbook");
            System.out.println("5. 📝 Start Quiz");
            System.out.println("6. 🚪 Exit");

            System.out.print("Choose option: ");
            String choice = in.nextLine();

            switch (choice) {
                case "1" -> searchTerm();
                case "2" -> learnTerm();
                case "3" -> addTerm();
                case "4" -> displayWorkbook();
                case "5" -> startQuiz();
                case "6" -> {
                    System.out.println("👋 Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
    }

    // ✅ Ensure CSV file exists
    private static void ensureFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
                    pw.println("Workbook|Term|Definition");
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error ensuring file: " + e.getMessage());
        }
    }

    // 🔍 Search term by name
    private static void searchTerm() {
        System.out.print("Enter term: ");
        String term = in.nextLine().trim();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3 && parts[1].equalsIgnoreCase(term)) {
                    System.out.println("✅ " + parts[1] + " → " + parts[2] + " (" + parts[0] + ")");
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file.");
        }

        if (!found) System.out.println("⚠️ Term not found.");
    }

    // 📖 Learn a random term
    private static void learnTerm() {
        List<String[]> entries = loadEntries();
        if (entries.isEmpty()) {
            System.out.println("⚠️ No terms available.");
            return;
        }
        String[] entry = entries.get(random.nextInt(entries.size()));
        System.out.println("📘 [" + entry[0] + "] " + entry[1] + " → " + entry[2]);
    }

    // ➕ Add a new term
    private static void addTerm() {
        System.out.print("Workbook name: ");
        String workbook = in.nextLine().trim();
        System.out.print("New term: ");
        String term = in.nextLine().trim();
        System.out.print("Definition (1-2 sentences): ");
        String def = in.nextLine().trim();

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            pw.println(workbook + "|" + term + "|" + def);
            System.out.println("✅ Term added successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error writing to file.");
        }
    }

    // 📂 Display all terms in a workbook
    private static void displayWorkbook() {
        System.out.print("Enter workbook name: ");
        String wb = in.nextLine().trim();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n📂 Workbook: " + wb);
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3 && parts[0].equalsIgnoreCase(wb)) {
                    System.out.println(" - " + parts[1] + " → " + parts[2]);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file.");
        }

        if (!found) System.out.println("⚠️ No terms found in this workbook.");
    }

    // 📝 Quiz Mode
    private static void startQuiz() {
        List<String[]> entries = loadEntries();
        if (entries.size() < 2) {
            System.out.println("⚠️ Not enough terms to start a quiz.");
            return;
        }

        System.out.print("How many questions do you want? ");
        int q;
        try {
            q = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid number. Defaulting to 5.");
            q = 5;
        }

        q = Math.min(q, entries.size()); // prevent asking for more questions than exist

        int score = 0;
        for (int i = 0; i < q; i++) {
            String[] entry = entries.get(random.nextInt(entries.size()));
            String[] other = entries.get(random.nextInt(entries.size()));

            String realDef = entry[2];
            String fakeDef = other[2];
            boolean showReal = random.nextBoolean();

            System.out.println("\nQ" + (i + 1) + ": Does this definition match '" + entry[1] + "'?");
            System.out.println("👉 " + (showReal ? realDef : fakeDef));
            System.out.print("(true/false): ");
            String ans = in.nextLine();

            boolean correct = (showReal && ans.equalsIgnoreCase("true")) ||
                    (!showReal && ans.equalsIgnoreCase("false"));

            if (correct) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Incorrect. The correct definition was: " + realDef);
            }
        }

        System.out.println("\n🏆 Final Score: " + score + "/" + q + " (" + (score * 100 / q) + "%)");
    }

    // 📚 Utility: load all terms from CSV
    private static List<String[]> loadEntries() {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3 && !parts[0].equalsIgnoreCase("Workbook")) list.add(parts);
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading entries.");
        }
        return list;
    }
}
