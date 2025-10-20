# ☕ Café Java  
*A Java CLI learning tool built by Year Up United students.*

---

## 🧭 Overview

**Café Java** is an interactive command-line application that helps students learn and quiz themselves on key Java terms.  
It uses a CSV file as a shared **term library**, allowing everyone in the Year Up United program to add new terms, definitions, and examples to grow the knowledge base together.

---

## 💡 Features

- ✅ **Search by Term** – Instantly look up any Java term.  
- ✅ **Learn a New Term** – Get a random Java concept to review.  
- ✅ **Add a New Term** – Contribute to the shared CSV library.  
- ✅ **Display Workbooks** – View organized “workbooks” of topics.  
- ✅ **True/False Quiz** – Test your Java knowledge interactively.  
- ✅ **Persistent CSV Storage** – All terms are saved in `Cafe_Java_Library_Terms.csv`.  

---

## 🧱 Technologies Used

| Component | Description |
|------------|--------------|
| **Java 17+** | Main programming language |
| **CSV File I/O** | Persistent data storage |
| **Collections API** | Manages lists, sets, and randomization |
| **OOP Principles** | Clean separation between UI and data logic |

---

## 📂 Project Structure

```plaintext
Cafe_Java/
├── src/
│   └── main/java/com/pluralsight/
│       ├── CafeJava.java          # Main application (menu & logic)
│       └── TermsLibrary.java      # Handles reading/writing to CSV
├── Cafe_Java_Library_Terms.csv    # Shared term database
└── README.md                      # Project documentation

