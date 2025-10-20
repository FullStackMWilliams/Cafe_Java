package com.pluralsight;

import java.util.*;

public class CafeJava {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n=======================================");
        System.out.println("        ☕ Welcome to Cafe Java!");
        System.out.println("=======================================\n");

        List<String[]> terms = TermsLibrary.loadTerms();

        while (true) {
            printMainMenu();
            System.out.print("👉 Choose option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    searchByTerm(terms);
                    break;
                case "2":
                    learnNewTerm(terms);
                    break;
                case "3":
                    addNewTerm();
                    terms = TermsLibrary.loadTerms(); // reload after adding
                    break;
                case "4":
                    displayWorkbooks(terms);
                    break;
                case "5":
                    startQuiz(terms);
                    break;
                case "6":
                    System.out.println("\n👋 Thanks for visiting Cafe Java! See you next study session!\n");
                    return;
                default:
                    System.out.println("⚠️ Invalid option. Please try again.\n");
            }
        }
    }

    // --- MENU DESIGN ---
    private static void printMainMenu() {
        System.out.println("""
                ===  📚 Cafe Java Menu  ===
                1️⃣  🔍 Search by Term
                2️⃣  📘 Learn New Term
                3️⃣  ➕ Add a New Term
                4️⃣  📂 Display Workbooks
                5️⃣  🧠 Start Quiz
                6️⃣  🚪 Exit
                """);
    }

    // --- SEARCH BY TERM ---
    private static void searchByTerm(List<String[]> terms) {
        System.out.print("\n🔎 Enter a term to search: ");
        String search = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        for (String[] row : terms) {
            if (row[1].toLowerCase().contains(search)) {
                System.out.println("\n📖 Workbook: " + row[0]);
                System.out.println("📌 Term: " + row[1]);
                System.out.println("💡 Definition: " + row[2]);
                System.out.println("🧠 Example: " + row[3] + "\n");
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠️ No matches found for '" + search + "'.");
        }
    }

    // --- LEARN NEW TERM ---
    private static void learnNewTerm(List<String[]> terms) {
        if (terms.isEmpty()) {
            System.out.println("⚠️ No terms available yet! Add some first.");
            return;
        }

        Random rand = new Random();
        String[] randomTerm = terms.get(rand.nextInt(terms.size()));

        System.out.println("\n🎓 Random Term from " + randomTerm[0] + ":");
        System.out.println("📘 " + randomTerm[1]);
        System.out.println("💡 " + randomTerm[2]);
        System.out.println("🧠 Example: " + randomTerm[3] + "\n");
    }

    // --- ADD NEW TERM ---
    private static void addNewTerm() {
        System.out.println("\n➕ Add a New Term");

        System.out.print("📚 Workbook name: ");
        String workbook = scanner.nextLine().trim();

        System.out.print("📘 Term: ");
        String term = scanner.nextLine().trim();

        System.out.print("💡 Definition: ");
        String definition = scanner.nextLine().trim();

        System.out.print("🧠 Example: ");
        String example = scanner.nextLine().trim();

        TermsLibrary.saveTerm(workbook, term, definition, example);
    }

    // --- DISPLAY WORKBOOKS ---
    private static void displayWorkbooks(List<String[]> terms) {
        if (terms.isEmpty()) {
            System.out.println("⚠️ No workbooks to display.");
            return;
        }

        Set<String> workbooks = new TreeSet<>();
        for (String[] row : terms) {
            workbooks.add(row[0]);
        }

        System.out.println("\n📂 Workbooks Available:");
        for (String workbook : workbooks) {
            System.out.println("   - " + workbook);
        }
        System.out.println();
    }

    // --- START QUIZ ---
    private static void startQuiz(List<String[]> terms) {
        if (terms.isEmpty()) {
            System.out.println("⚠️ No terms available to quiz on!");
            return;
        }

        // Get workbook list
        Set<String> workbooks = new TreeSet<>();
        for (String[] row : terms) {
            workbooks.add(row[0]);
        }

        System.out.println("\n🧠 Choose a workbook to quiz from:");
        int i = 1;
        List<String> workbookList = new ArrayList<>(workbooks);
        for (String wb : workbookList) {
            System.out.println(" " + i++ + ". " + wb);
        }

        System.out.print("📘 Enter number: ");
        int choice = readInt(1, workbookList.size());
        String selectedWorkbook = workbookList.get(choice - 1);

        // Filter terms
        List<String[]> selectedTerms = new ArrayList<>();
        for (String[] row : terms) {
            if (row[0].equalsIgnoreCase(selectedWorkbook)) {
                selectedTerms.add(row);
            }
        }

        if (selectedTerms.isEmpty()) {
            System.out.println("⚠️ No terms in that workbook yet.");
            return;
        }

        System.out.print("🧮 How many questions? (1–" + selectedTerms.size() + "): ");
        int numQuestions = readInt(1, selectedTerms.size());

        Collections.shuffle(selectedTerms);
        int score = 0;

        for (int q = 0; q < numQuestions; q++) {
            String[] t = selectedTerms.get(q);
            String term = t[1];
            String def = t[2];
            String ex = t[3];

            System.out.println("\n❓ " + (q + 1) + ". What does \"" + term + "\" mean?");
            System.out.print("💭 Your answer: ");
            scanner.nextLine(); // user input ignored for simplicity

            System.out.println("💡 Definition: " + def);
            System.out.println("🧠 Example: " + ex);

            System.out.print("✅ Did you get it right? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) score++;
        }

        System.out.println("\n🏁 Quiz Complete!");
        System.out.println("⭐ Score: " + score + "/" + numQuestions + "\n");
    }

    // --- Utility: Safe integer input ---
    private static int readInt(int min, int max) {
        while (true) {
            try {
                int num = Integer.parseInt(scanner.nextLine().trim());
                if (num >= min && num <= max) return num;
            } catch (Exception ignored) {}
            System.out.print("⚠️ Enter a valid number (" + min + "–" + max + "): ");
        }
    }
}
