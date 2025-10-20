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
                    terms = TermsLibrary.loadTerms();
                    break;
                case "4":
                    displayWorkbooks(terms);
                    break;
                case "5":
                    startTrueFalseQuiz(terms);
                    break;
                case "6":
                    System.out.println("\n👋 Thanks for visiting Cafe Java! Keep coding strong!\n");
                    return;
                default:
                    System.out.println("⚠️ Invalid option. Please try again.\n");
            }
        }
    }

    // --- MENU ---
    private static void printMainMenu() {
        System.out.println("""
                ===  📚 Cafe Java Menu  ===
                1️⃣  🔍 Search by Term
                2️⃣  📘 Learn New Term
                3️⃣  ➕ Add a New Term
                4️⃣  📂 Display Workbooks
                5️⃣  🧠 Start True/False Quiz
                6️⃣  🚪 Exit
                """);
    }

    // --- SEARCH ---
    private static void searchByTerm(List<String[]> terms) {
        System.out.print("\n🔎 Enter a term to search: ");
        String search = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        for (String[] row : terms) {
            if (row[1].toLowerCase().contains(search)) {
                System.out.println("\n📖 Workbook: " + row[0]);
                System.out.println("📘 Term: " + row[1]);
                System.out.println("💡 Definition: " + row[2]);
                System.out.println("🧠 Example: " + row[3] + "\n");
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠️ No matches found for '" + search + "'.");
        }
    }

    // --- LEARN RANDOM TERM ---
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

    // --- ADD TERM ---
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

    // --- TRUE/FALSE QUIZ ---
    private static void startTrueFalseQuiz(List<String[]> terms) {
        if (terms.isEmpty()) {
            System.out.println("⚠️ No terms available for quiz!");
            return;
        }

        // Collect workbooks
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

        Random rand = new Random();
        int score = 0;

        System.out.println("\n=== 🧠 TRUE / FALSE QUIZ START ===");

        for (int q = 0; q < numQuestions; q++) {
            String[] correctTerm = selectedTerms.get(rand.nextInt(selectedTerms.size()));
            String displayedTerm = correctTerm[1];
            String correctDef = correctTerm[2];

            // Randomly decide whether to show correct or fake definition
            boolean isTrue = rand.nextBoolean();
            String displayedDef;

            if (isTrue) {
                displayedDef = correctDef;
            } else {
                // Pick a random fake definition
                String[] fakeTerm = selectedTerms.get(rand.nextInt(selectedTerms.size()));
                while (fakeTerm[1].equals(displayedTerm)) {
                    fakeTerm = selectedTerms.get(rand.nextInt(selectedTerms.size()));
                }
                displayedDef = fakeTerm[2];
            }

            System.out.println("\n" + (q + 1) + ". " + displayedTerm);
            System.out.println("💭 Definition: " + displayedDef);
            System.out.print("👉 True or False? ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if ((answer.equals("true") && isTrue) || (answer.equals("false") && !isTrue)) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Incorrect! The correct definition was:");
                System.out.println("💡 " + correctDef);
            }
        }

        System.out.println("\n🏁 Quiz complete!");
        System.out.println("⭐ Final Score: " + score + "/" + numQuestions + "\n");
    }

    // --- Utility for integer input ---
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
