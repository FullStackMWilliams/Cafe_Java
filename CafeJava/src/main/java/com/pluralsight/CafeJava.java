package com.pluralsight;

import java.util.*;

public class CafeJava {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n=======================================");
        System.out.println("        ☕ Welcome to Cafe Java!");
        System.out.println("=======================================\n");

        while (true) {
            printMainMenu();
            System.out.print("👉 Choose option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> searchByTerm(TermsLibrary.loadTerms());
                case "2" -> learnNewTerm(TermsLibrary.loadTerms());
                case "3" -> addNewTerm();
                case "4" -> displayWorkbooks(TermsLibrary.loadTerms());
                case "5" -> startTrueFalseQuiz(TermsLibrary.loadTerms());
                case "6" -> {
                    System.out.println("\n👋 Thanks for visiting Cafe Java! Keep coding strong!\n");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option. Please try again.\n");
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
        if (terms.isEmpty()) {
            System.out.println("⚠️ No terms available to search. Add some first.");
            return;
        }

        System.out.print("\n🔎 Enter a term to search: ");
        String search = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        for (String[] row : terms) {
            if (row.length >= 3 && row[1].toLowerCase().contains(search)) {
                System.out.println("\n📖 Workbook: " + row[0]);
                System.out.println("📘 Term: " + row[1]);
                System.out.println("💡 Definition: " + row[2]);
                System.out.println("🧠 Example: " + safe(row, 3) + "\n");
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
        String[] t = terms.get(rand.nextInt(terms.size()));

        System.out.println("\n🎓 Random Term from " + t[0] + ":");
        System.out.println("📘 " + t[1]);
        System.out.println("💡 " + t[2]);
        System.out.println("🧠 Example: " + safe(t, 3) + "\n");
    }

    // --- ADD TERM ---
    private static void addNewTerm() {
        System.out.println("\n➕ Add a New Term");

        System.out.print("📚 Workbook name: ");
        String workbook = scanner.nextLine();

        System.out.print("📘 Term: ");
        String term = scanner.nextLine();

        System.out.print("💡 Definition: ");
        String definition = scanner.nextLine();

        System.out.print("🧠 Example: ");
        String example = scanner.nextLine();

        TermsLibrary.saveTerm(workbook, term, definition, example);
    }

    // --- DISPLAY WORKBOOKS ---
    private static void displayWorkbooks(List<String[]> terms) {
        if (terms.isEmpty()) {
            System.out.println("⚠️ No workbooks to display.");
            return;
        }

        // Build normalized set of workbook names
        Set<String> workbooks = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String[] r : terms) {
            String wb = r[0] == null ? "" : r[0].trim();
            if (!wb.isEmpty()) {
                workbooks.add(wb);
            }
        }

        System.out.println("\n📂 Workbooks Available:");
        for (String wb : workbooks) {
            System.out.println(" - " + wb);
        }
        System.out.println();
    }

    // --- TRUE/FALSE QUIZ ---
    private static void startTrueFalseQuiz(List<String[]> terms) {
        if (terms.isEmpty()) {
            System.out.println("⚠️ No terms available for quiz!");
            return;
        }

        // Build workbook list (normalized, unique)
        Set<String> wbSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String[] r : terms) {
            String wb = r[0] == null ? "" : r[0].trim();
            if (!wb.isEmpty()) wbSet.add(wb);
        }
        List<String> workbooks = new ArrayList<>(wbSet);

        System.out.println("\n🧠 Choose a workbook to quiz from:");
        for (int i = 0; i < workbooks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + workbooks.get(i));
        }

        System.out.print("📘 Enter number: ");
        int idx = readInt(1, workbooks.size()) - 1;
        String selectedWB = workbooks.get(idx);

        // Filter terms by selected workbook
        List<String[]> wbTerms = new ArrayList<>();
        for (String[] r : terms) {
            if (r[0] != null && r[0].trim().equalsIgnoreCase(selectedWB) && r.length >= 3) {
                // trim fields defensively
                String term  = (r[1] == null ? "" : r[1].trim());
                String defin = (r[2] == null ? "" : r[2].trim());
                if (!term.isEmpty() && !defin.isEmpty()) {
                    wbTerms.add(new String[]{ r[0].trim(), term, defin, (r.length>3 && r[3]!=null)? r[3].trim() : "" });
                }
            }
        }

        if (wbTerms.isEmpty()) {
            System.out.println("⚠️ No terms in that workbook yet.");
            return;
        }

        System.out.print("🧮 How many questions? (1–" + wbTerms.size() + "): ");
        int questions = readInt(1, wbTerms.size());

        Random rand = new Random();
        int score = 0;

        System.out.println("\n=== 🧠 TRUE / FALSE QUIZ START ===");

        for (int q = 1; q <= questions; q++) {
            String[] correct = wbTerms.get(rand.nextInt(wbTerms.size()));
            String term       = correct[1];
            String correctDef = correct[2];

            // Decide what to show
            String shownDef;

            if (wbTerms.size() == 1) {
                // With one term we cannot build a false statement — always show the real one
                shownDef = correctDef;
            } else {
                boolean showReal = rand.nextBoolean();
                if (showReal) {
                    shownDef = correctDef;
                } else {
                    // pick a different definition
                    String[] fake;
                    do {
                        fake = wbTerms.get(rand.nextInt(wbTerms.size()));
                    } while (fake == correct || fake[2].equalsIgnoreCase(correctDef));
                    shownDef = fake[2];
                }
            }

            // Ask
            System.out.println("\n" + q + ". " + term);
            System.out.println("💭 Definition: " + shownDef);
            System.out.print("👉 True or False? ");
            String answer = scanner.nextLine().trim().toLowerCase();

            // ✅ Grade by comparing WHAT WE SHOWED vs the CORRECT definition
            boolean statementIsTrue = shownDef.equalsIgnoreCase(correctDef);
            boolean userSaysTrue    = answer.startsWith("t"); // t/true/TRUE, etc.

            if (userSaysTrue == statementIsTrue) {
                System.out.println("✅ Correct!\n");
                score++;
            } else {
                System.out.println("❌ Incorrect. Correct definition: " + correctDef + "\n");
            }
        }

        System.out.println("🏆 Final Score: " + score + "/" + questions + "\n");
    }


    private static String safe(String[] row, int idx) {
        return (row != null && idx >= 0 && idx < row.length && row[idx] != null) ? row[idx] : "";
    }
    // --- HELPER: Read integer input safely ---
    private static int readInt(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.print("⚠️ Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Invalid number. Try again: ");
            }
        }
    }
}
