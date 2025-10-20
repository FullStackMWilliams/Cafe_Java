package com.pluralsight;

import java.util.*;
import java.io.*;

/**
 * ☕ Cafe Java (Refactored)
 * ---------------------------------------------------------
 * A CLI learning tool for Java terms sourced from a CSV file.
 * Uses TermsLibrary to load terms consistently.
 * Features:
 *  1. Search for term definitions
 *  2. Learn a random term
 *  3. Add new term
 *  4. Display workbook contents
 *  5. Start a quiz (True/False)
 */
public class CafeJava {

    private static final Scanner in = new Scanner(System.in);
    private static final String FILE_NAME = "Cafe_Java_Library_Terms.csv";
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
            System.out.print("👉 Choose an option: ");
            String choice = in.nextLine();

            switch (choice) {
                case "1" -> searchTerm();
                case "2" -> learnTerm();
                case "3" -> addTerm();
                case "4" -> displayWorkbook();
                case "5" -> startQuiz();
                case "6" -> {
                    System.out.println("👋 Goodbye and happy coding!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
    }

    // ✅ Ensure CSV exists
    private static void ensureFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println("Workbook|Term|Definition|Example");
            } catch (IOException e) {
                System.out.println("❌ Error creating CSV: " + e.getMessage());
            }
        }
    }

    // 🔍 Search by term
    private static void searchTerm() {
        List<String[]> terms = TermsLibrary.loadTerms();
        System.out.print("🔎 Enter term to search: ");
        String query = in.nextLine().trim();

        boolean found = false;
        for (String[] t : terms) {
            if (t[1].equalsIgnoreCase(query)) {
                System.out.println("✅ " + t[1] + " → " + t[2]);
                if (t.length > 3 && !t[3].isEmpty()) {
                    System.out.println("📌 Example: " + t[3]);
                }
                found = true;
            }
        }
        if (!found) System.out.println("⚠️ Term not found.");
    }

    // 📖 Learn a random term
    private static void learnTerm() {
        List<String[]> terms = TermsLibrary.loadTerms();
        if (terms.isEmpty()) {
            System.out.println("⚠️ No terms found in the library.");
            return;
        }

        String[] randomTerm = terms.get(random.nextInt(terms.size()));
        System.out.println("\n📘 From " + randomTerm[0] + ": " + randomTerm[1] + " → " + randomTerm[2]);
        if (randomTerm.length > 3 && !randomTerm[3].isEmpty()) {
            System.out.println("📌 Example: " + randomTerm[3]);
        }
    }

    // ➕ Add a new term
    private static void addTerm() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            System.out.print("📚 Workbook: ");
            String workbook = in.nextLine().trim();

            System.out.print("📖 Term: ");
            String term = in.nextLine().trim();

            System.out.print("✏️ Definition: ");
            String def = in.nextLine().trim();

            System.out.print("📌 Example (optional): ");
            String example = in.nextLine().trim();

            pw.println(workbook + "|" + term + "|" + def + "|" + example);
            System.out.println("✅ Term added successfully!");
        } catch (IOException e) {
            System.out.println("❌ Could not write term: " + e.getMessage());
        }
    }

    // 📂 Display terms by workbook
    private static void displayWorkbook() {
        List<String[]> terms = TermsLibrary.loadTerms();
        System.out.print("📚 Enter workbook name: ");
        String workbook = in.nextLine().trim();

        System.out.println("\n📂 Terms from " + workbook + ":");
        boolean found = false;
        for (String[] t : terms) {
            if (t[0].equalsIgnoreCase(workbook)) {
                System.out.println(" - " + t[1] + " → " + t[2]);
                if (t.length > 3 && !t[3].isEmpty()) {
                    System.out.println("   📌 Example: " + t[3]);
                }
                found = true;
            }
        }
        if (!found) System.out.println("⚠️ No terms found in this workbook.");
    }

    // 🧠 Quiz: True/False based on random definitions
    private static void startQuiz() {
        List<String[]> terms = TermsLibrary.loadTerms();

        if (terms.size() < 2) {
            System.out.println("⚠️ Not enough terms for a quiz. Add more terms first.");
            return;
        }

        int questions = 0;
        while (questions <= 0 || questions > terms.size()) {
            System.out.print("🧠 How many questions? (1-" + terms.size() + "): ");
            try {
                questions = Integer.parseInt(in.nextLine());
                if (questions <= 0 || questions > terms.size()) {
                    System.out.println("⚠️ Enter a valid number between 1 and " + terms.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a valid number.");
            }
        }

        int score = 0;
        for (int i = 0; i < questions; i++) {
            String[] term = terms.get(random.nextInt(terms.size()));
            String correctDef = term[2];

            String[] randomDefTerm = terms.get(random.nextInt(terms.size()));
            String randomDef = randomDefTerm[2];

            boolean showCorrect = random.nextBoolean();
            String shownDef = showCorrect ? correctDef : randomDef;

            System.out.println("\nQ" + (i + 1) + ": Does this definition match the term \"" + term[1] + "\"?");
            System.out.println("👉 " + shownDef);
            System.out.print("(true/false): ");
            String answer = in.nextLine().trim().toLowerCase();

            boolean correct = (showCorrect && answer.equals("true")) || (!showCorrect && answer.equals("false"));
            if (correct) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Incorrect. Correct definition: " + correctDef);
            }
        }

        System.out.println("\n🏆 Final Score: " + score + "/" + questions);
    }
}

