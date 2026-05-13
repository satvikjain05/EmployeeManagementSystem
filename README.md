# 🏢 Employee Management System
**Built with:** Java Swing + SQLite JDBC  
**Level:** Beginner-friendly | College Project

---

## 📁 Project Structure

```
EmployeeManagementSystem/
│
├── src/
│   └── com/
│       └── ems/
│           ├── Main.java                  ← Entry point (run this)
│           │
│           ├── model/
│           │   └── Employee.java          ← Employee class (OOP model)
│           │
│           ├── db/
│           │   ├── DBConnection.java      ← JDBC database connection
│           │   └── EmployeeDAO.java       ← All SQL operations (CRUD)
│           │
│           ├── gui/
│           │   ├── LoginFrame.java        ← Login screen
│           │   ├── MainFrame.java         ← Main dashboard with table
│           │   └── EmployeeFormDialog.java← Add/Update form dialog
│           │
│           └── utils/
│               └── Validator.java         ← Input validation helper
│
├── lib/
│   └── sqlite-jdbc-3.x.x.jar             ← ⚠ YOU MUST DOWNLOAD THIS (see below)
│
├── sql/
│   └── schema.sql                         ← SQL reference (table + sample data)
│
├── .vscode/
│   └── launch.json                        ← VS Code run config
│
└── README.md                              ← This file
```

> **Note:** `ems.db` (the SQLite database file) is auto-created when you first run the app.

---

## 🔧 Step-by-Step Setup in VS Code

### Step 1: Install Required Software
- **Java JDK 17+** → https://adoptium.net/
- **VS Code** → https://code.visualstudio.com/
- **Extension Pack for Java** (in VS Code):
  1. Open VS Code → Press `Ctrl+Shift+X`
  2. Search: `Extension Pack for Java`
  3. Click Install (by Microsoft)

---

### Step 2: Download the SQLite JDBC Driver (Required!)
1. Go to: https://github.com/xerial/sqlite-jdbc/releases
2. Download the latest `.jar` file (e.g., `sqlite-jdbc-3.46.0.0.jar`)
3. Rename it to something simple or leave as-is
4. Copy it into the `lib/` folder of this project

---

### Step 3: Add the JAR to VS Code Classpath
1. Open the project folder in VS Code: `File → Open Folder`
2. Look for `JAVA PROJECTS` in the left Explorer panel
3. Expand your project → Click `Referenced Libraries`
4. Click the `+` button → Navigate to `lib/` → Select the `.jar` file

   **Alternative:** Edit `.vscode/launch.json` and update the jar filename in `classPaths`.

---

### Step 4: Run the Project
1. Open `src/com/ems/Main.java`
2. Click the ▶ **Run** button that appears above `public static void main`
   — OR press `F5`
   — OR right-click → `Run Java`

---

### Step 5: Login
```
Username: admin
Password: admin123
```

---

## 🗄️ Database Info
- **Type:** SQLite (no installation needed!)
- **File:** `ems.db` (created automatically in the project root)
- **Table:** `employees` (created automatically on first run)
- You can open `ems.db` with **DB Browser for SQLite** (free tool) to see data visually

---

## ✨ Features
| Feature | Description |
|--------|-------------|
| 🔐 Login | Username/Password authentication |
| ➕ Add Employee | Add with ID, Name, Salary, Department, Designation |
| 👁 View All | See all employees in a sortable table |
| 🔍 Search | Find employee by ID |
| ✏ Update | Edit any field of an existing employee |
| 🗑 Delete | Remove employee with confirmation |
| ✅ Validation | All inputs are validated before saving |
| 💬 Messages | Success/Error dialogs for all actions |

---

## 🐛 Common Errors & Fixes

| Error | Fix |
|-------|-----|
| `ClassNotFoundException: org.sqlite.JDBC` | JAR not added to classpath — see Step 3 |
| Cannot find `Main` class | Make sure you opened the right folder in VS Code |
| Table not shown | Click 🔄 Refresh button on dashboard |
| Login fails | Use exactly: `admin` / `admin123` |

---

## 📚 Concepts Used
- **OOP:** Classes, Objects, Encapsulation (Employee model)
- **Swing GUI:** JFrame, JTable, JDialog, JMenuBar, JButton
- **JDBC:** PreparedStatement, ResultSet, Connection
- **Exception Handling:** try-catch in all DB operations
- **Validation:** Separate Validator utility class
- **Design Pattern:** DAO (Data Access Object) pattern for database operations
