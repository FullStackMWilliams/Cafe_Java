# ☕ Café Java  
*A Java CLI learning tool built by Markus, a Year Up United students.*

---

## 🧭 Overview

**Café Java** is an interactive command-line application that helps students learn and quiz themselves on key Java terms.  
It uses a CSV file as a shared **term library**, allowing everyone in the Year Up United program to add new terms, definitions, and examples to grow the knowledge base together.

---

# 💡 Features

- ✅ **Search by Term** – Instantly look up any Java term.  
- ✅ **Learn a New Term** – Get a random Java concept to review.  
- ✅ **Add a New Term** – Contribute to the shared CSV library.  
- ✅ **Display Workbooks** – View organized “workbooks” of topics.  
- ✅ **True/False Quiz** – Test your Java knowledge interactively.  
- ✅ **Persistent CSV Storage** – All terms are saved in `Cafe_Java_Library_Terms.csv`.  

---
## 🧠 How the Quiz Works

When you start a True/False quiz, Café Java:

1. Randomly selects a term and its definition.

2. Either shows the real definition or swaps in a fake one from another term.

3. You decide whether it’s True or False.

After each question, your score updates in real time — great for self-testing before Year Up assessments!

---

# 🚀 How to Run the Program
## 🧩 Requirements

Java 17 or newer

IntelliJ IDEA (Community Edition or Ultimate)

## ▶️ Steps

1. Clone this repository

```java
git clone https://github.com/FullStackMWilliams/Cafe_Java.git
````

2. Open the project in IntelliJ IDEA.

3. Wait for IntelliJ to finish indexing (progress bar at the bottom).

4. Open src/main/java/com/pluralsight/CafeJava.java.

5. Click Run ▶️ (top-right corner). The Café Java menu will appear in your terminal.

---
# 👨🏾‍💻 Getting Started (For First-Time GitHub Users)

If you’re new to Git or IntelliJ, follow these steps to collaborate smoothly.

## 1️⃣ Set Up GitHub & IntelliJ

- Create a GitHub account: https://github.com/join

- Install Git: https://git-scm.com/downloads

- Install IntelliJ IDEA (Community): https://www.jetbrains.com/idea/download

## 2️⃣ Clone the Project (from IntelliJ)

- IntelliJ → Get from VCS → paste:
```java
https://github.com/FullStackMWilliams/Cafe_Java.git
```

- Click Clone (project opens automatically).

## 3️⃣ Make Your Own Branch
```java
git checkout -b yourname-update
```

Keep your changes separate and easy to review.

## 4️⃣ Add or Update Terms

Run the app → choose 3 ➕ Add a New Term.
Your data is saved to Cafe_Java_Library_Terms.csv.

## 5️⃣ Save Your Changes
```java
git add .
git commit -m "Added new term: Polymorphism"
```

## 6️⃣ Push to GitHub
```java
git push origin yourname-update
```

Then open GitHub → click “Compare & Pull Request” to submit your update to the main repository.

---

# 👷🏾‍♂️ Why I Built This 🛠️

Café Java was created as part of the Year Up United Software Development track to help students:

Learn core Java concepts interactively

Collaborate using Git & version control

Practice file I/O and data persistence

Strengthen teamwork through shared code contributions

This project reflects both technical learning and professional collaboration, just like real-world software teams.

---

# 📂 Project Structure

```plaintext
Cafe_Java/
├── src/
│   └── main/java/com/pluralsight/
│       ├── CafeJava.java          # Main application (menu & logic)
│       └── TermsLibrary.java      # Handles reading/writing to CSV
├── Cafe_Java_Library_Terms.csv    # Shared term database
└── README.md                      # Project documentation
````
---

# 🧱 Technologies Used

| Component | Description |
|------------|--------------|
| **Java 17+** | Main programming language |
| **CSV File I/O** | Persistent data storage |
| **Collections API** | Manages lists, sets, and randomization |
| **OOP Principles** | Clean separation between UI and data logic |

---

# 🧩 Troubleshooting

| Problem                   | Cause                   | Solution                                                      |
| ------------------------- | ----------------------- | ------------------------------------------------------------- |
| Blank line in workbooks   | Extra empty line in CSV | Delete the blank line or re-save via the app                  |
| Duplicate term not saving | Already exists          | App blocks duplicates intentionally (same workbook + term)    |
| Quiz missing new terms    | Using old data          | Re-enter the quiz — the app reloads the CSV each time         |
| “File not found”          | CSV missing             | The app auto-creates `Cafe_Java_Library_Terms.csv` if missing |

---

# 🏁 Future Improvements

 Edit and delete existing terms

 Track quiz history or user progress

 Export stats to JSON or CSV

 Build a GUI (JavaFX version)

 Add difficulty levels for quizzes

## 💬 “Keep your code brewing — every bug fixed is one sip closer to mastery!”
— Team Café Java ☕

---
## Screenshots 

<img width="1919" height="1126" alt="Screenshot 2025-10-20 172253" src="https://github.com/user-attachments/assets/992340a6-1e2b-4b8d-9a48-9df6b9a81f0f" />
<img width="1916" height="1126" alt="Screenshot 2025-10-20 170245" src="https://github.com/user-attachments/assets/fb42234a-8f35-44e4-be94-e5d48a6e789d" />
<img width="1919" height="1127" alt="Screenshot 2025-10-20 170042" src="https://github.com/user-attachments/assets/31de5473-0081-4018-a658-a9976b9f2e0d" />
<img width="1914" height="1130" alt="Screenshot 2025-10-20 145800" src="https://github.com/user-attachments/assets/7f96f45c-2d80-4c55-bd16-5c38edfb19d7" />
<img width="1919" height="1129" alt="Screenshot 2025-10-20 165438" src="https://github.com/user-attachments/assets/ef4a45c6-4397-425e-9d71-08794f361cfc" />
<img width="1919" height="1130" alt="Screenshot 2025-10-20 165800" src="https://github.com/user-attachments/assets/eb7e084a-3adb-4351-a9ec-93038f963b08" />
<img width="1918" height="1129" alt="Screenshot 2025-10-20 165625" src="https://github.com/user-attachments/assets/cee0954c-9efb-4dd8-b43e-3ae190270fa9" />
<img width="1919" height="1128" alt="Screenshot 2025-10-20 173151" src="https://github.com/user-attachments/assets/65e0af55-f07d-4b9e-a05e-a24a4f28f509" />
