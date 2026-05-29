# CP3566 Module 2 — JDBC Starter Project

A truly turn-key Maven project for the JDBC lab of CP3566 (Applied Java
Programming). You install two things on your computer, open the folder
in IntelliJ IDEA, hit Run. That's the whole setup.

**No database server to install.** The database engine (H2) is shipped
inside the project as a JAR — same idea as the MySQL Connector/J JAR,
except H2 is *also* the database. Pure-Java, runs inside your JVM, data
stored in a single file in the project folder. Identical JDBC API to
MySQL, PostgreSQL, or any other relational database.

## What's inside

| Path                                                       | What it is |
|------------------------------------------------------------|------------|
| `pom.xml`                                                  | Maven project definition — Java 21 + H2 Database Engine 2.3.232 |
| `mvnw`, `mvnw.cmd`                                         | Maven Wrapper — `./mvnw` (or `mvnw.cmd` on Windows) auto-installs Maven 3.9.9 on first use; no manual Maven install on any OS |
| `.mvn/wrapper/`                                            | Wrapper bootstrap JAR + properties |
| `.mvn/repo/com/h2database/h2/2.3.232/`                     | Bundled local Maven repo — the H2 JAR + a minimal POM |
| `src/main/java/HelloJdbc.java`                             | Smoke test — confirms the driver is on the classpath |
| `src/main/java/BasicConnection.java`                       | Lab 1 — opens a connection and prints database metadata |
| `src/main/java/SchemaSetup.java`                           | Lab 2 — creates the `student` table and seeds four rows |
| `sql/create_database.sql`                                  | Schema + four seed rows — paste into the H2 web console for manual resets (H2 auto-creates the database file itself; no `CREATE DATABASE` needed) |
| `data/studentdb.mv.db`                                     | Created automatically on first run — your actual database file |

## What you need to install

| Tool                       | Why                                       | Tested version |
|----------------------------|-------------------------------------------|----------------|
| JDK 21 (LTS)               | Compiles and runs the Java code           | OpenJDK 21.0.x |
| IntelliJ IDEA Community    | The editor/IDE you'll work in (free)      | 2024.3 or newer |

That's it. **No MySQL.** **No password setup.** **No port to keep
running.** **No `mysql_secure_installation` ritual.** **No separate
Maven install.** On Windows, macOS, and Linux the steps below are
identical.

> ⚠️  **Do not mix up Community and Ultimate** for IntelliJ. The free
> **Community** edition is everything you need. Ultimate is fine too if
> you already have it via the JetBrains student plan.

### How Maven is handled (you don't need to install it)

Two ways, both work out of the box:

1. **IntelliJ users (the recommended path)** — IntelliJ ships a bundled
   Maven runtime. When you click *Load Maven Project* on first open,
   that bundled Maven imports the dependencies from `.mvn/repo/`.
   You never see Maven on the command line.

2. **Command-line users** — the project ships **Maven Wrapper** at the
   root: `mvnw` (macOS/Linux) and `mvnw.cmd` (Windows). The first time
   you run it, the wrapper downloads Apache Maven 3.9.9 to
   `~/.m2/wrapper/dists/` (one-time, ~10 MB, then cached). After that:
   ```bash
   ./mvnw -q compile exec:java -Dexec.mainClass=HelloJdbc   # macOS / Linux
   mvnw.cmd -q compile exec:java -Dexec.mainClass=HelloJdbc # Windows PowerShell
   ```
   No `apt install maven`, no `brew install maven`, no Chocolatey
   package — the wrapper is the standard Apache mechanism for
   distributing a Maven-pinned project.

---

## 1 · Install JDK 21

### Windows 10 / 11
1. Open <https://adoptium.net/temurin/releases/?version=21&package=jdk>.
2. **OS = Windows**, **Architecture = x64**, **Package Type = JDK**,
   **Version = 21 - LTS**.
3. Download the **`.msi`** installer.
4. Run it. On *Custom Setup*, expand *Set JAVA_HOME variable* and *Add
   to PATH* and switch both to *Will be installed on local hard drive*.
5. Open a **new** PowerShell window, run `java -version`. Expect
   `openjdk version "21.0.x"`.

### macOS (Apple Silicon and Intel)
Install Homebrew if you don't have it:
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
Then:
```bash
brew install --cask temurin@21
java -version
```
If `java` isn't found:
```bash
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

### Linux (Ubuntu / Debian)
```bash
sudo apt update
sudo apt install -y openjdk-21-jdk
java -version
```

---

## 2 · Install IntelliJ IDEA Community

### Windows
1. <https://www.jetbrains.com/idea/download/?section=windows>. Scroll to
   **IntelliJ IDEA Community Edition**, click the **`.exe`** button
   (NOT the *Ultimate* one at the top).
2. Run the installer. Tick *Add "bin" folder to the PATH* and *Create
   Desktop Shortcut*.

### macOS
```bash
brew install --cask intellij-idea-ce
```

### Linux
Use the **JetBrains Toolbox App**:
1. <https://www.jetbrains.com/toolbox-app/> — Linux `.tar.gz`.
2. Extract, run `./jetbrains-toolbox`.
3. In the Toolbox UI, click **Install** next to *IntelliJ IDEA Community
   Edition*.

Alternative: `sudo snap install intellij-idea-community --classic`.

---

## 3 · Open the project in IntelliJ

1. Unzip `Module2_Starter.zip` somewhere stable. Avoid paths with
   spaces or non-ASCII characters — `C:\dev\cp3566\` or `~/dev/cp3566/`
   are good choices.
2. Launch IntelliJ. From the welcome screen click **Open** and select
   the unzipped `Module2_Starter` **folder** (NOT a file inside it).
3. *"Trust and Open Project?"* — click **Trust Project**.
4. *"Maven projects need to be imported"* — click **Load Maven Project**
   (recent IntelliJ versions auto-load — wait for the progress bar).

That's it. IntelliJ reads `pom.xml`, sees the bundled `.mvn/repo`
repository, resolves the H2 dependency from that bundled repo (no
internet needed), and wires the classpath. When the import finishes,
expand **External Libraries** in the Project panel — you should see
`Maven: com.h2database:h2:2.3.232`.

> If IntelliJ asks *"No SDK"* in the bottom-right, click **Setup SDK**
> and pick the JDK 21 you installed. Default install locations:
> - Windows: `C:\Program Files\Eclipse Adoptium\jdk-21.x.x-hotspot`
> - macOS: `/Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home`
> - Linux (apt): `/usr/lib/jvm/java-21-openjdk-amd64`

---

## 4 · First run — HelloJdbc

A 5-second sanity check. Doesn't touch any database file yet.

1. In the **Project** panel, expand `src/main/java/` and double-click
   `HelloJdbc.java`.
2. Click the green ▶ in the gutter next to `public class HelloJdbc`.
3. Expected output:
   ```
   Driver registered: org.h2.Driver
   Java version    : 21.0.x
   H2 ready        : YES — proceed to BasicConnection.java
   ```

---

## 5 · Lab 1 — BasicConnection

1. Open `src/main/java/BasicConnection.java`. Click ▶.
2. The first run creates the database file `data/studentdb.mv.db` in
   your project folder.
3. Expected output:
   ```
   Status     : Connected
   Driver     : H2 JDBC Driver v2.3.232 (2024-08-11)
   URL        : jdbc:h2:./data/studentdb
   AutoCommit : true
   ```

---

## 6 · Lab 2 — SchemaSetup

1. Open `src/main/java/SchemaSetup.java`. Run it.
2. Expected output (one line):
   ```
   Schema OK · rows seeded: 4
   ```

Re-run `SchemaSetup` any time you want a clean slate — it drops and
re-creates the `student` table every run.

---

## 7 · Inspect the data (optional)

H2 ships a small web console for browsing the database in a browser.
From inside IntelliJ's **Terminal** tab (bottom edge):

```bash
java -cp .mvn/repo/com/h2database/h2/2.3.232/h2-2.3.232.jar org.h2.tools.Console
```

Your browser will open at `http://localhost:8082`. In the login form:
- **JDBC URL**: `jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE` &nbsp;(must include `DATABASE_TO_LOWER=TRUE` so the Console finds the same lowercased schema the Java code creates)
- **User Name**: `sa`
- **Password**: `secret`

Click **Connect**, then run `SELECT * FROM student;` in the SQL pane.

Press `Ctrl+C` in the terminal to stop the console when you're done.

---

## 8 · Troubleshooting

### IntelliJ never offers to "Load Maven Project"

You may have opened the wrong folder, or IntelliJ doesn't see `pom.xml`.

- In the **Project** panel, locate `pom.xml` at the top level.
- Right-click `pom.xml` → **Add as Maven Project**.
- Wait for the import progress bar.

### "H2 driver NOT auto-registered" (from HelloJdbc)

Maven hasn't finished importing yet, or it failed.
1. Open the **Maven** tool window (right edge of IntelliJ, or
   *View → Tool Windows → Maven*).
2. Click the 🔄 **Reload All Maven Projects** icon.
3. Wait for the progress bar, then re-run.

If it still fails:
- **File → Invalidate Caches…** → tick *Clear file system cache and
  Local History* → **Invalidate and Restart**.

### IntelliJ shows red squiggles under `import java.sql.…`

Almost always **"No SDK selected"**. Click the red lightbulb or
**File → Project Structure → Project**, set **Project SDK** to your
JDK 21, **Project language level** = **21**.

### "Database may be already in use" (org.h2.jdbc.JdbcSQLNonTransientConnectionException)

You ran the program twice and the first JVM is still hanging around.
H2 embedded mode lets only one JVM open the file at a time. Stop the
older Run (red ⏹ in the Run panel), then start the new one.

If you ran the H2 web console alongside, stop that too.

### "Wrong user name or password" (when using the H2 console)

The console treats `studentdb` as a brand-new database if you give it
a different URL than the Java code used. Make sure the **JDBC URL**
includes the full path the Java code uses — see section 7.

### Wipe everything and start fresh

Delete the `data/` folder. The next run of `BasicConnection` or
`SchemaSetup` will create a brand-new `studentdb.mv.db`.

---

## Where to next

* **The JDBC API you learn here is portable.** Every `Connection`,
  `Statement`, `PreparedStatement`, and `ResultSet` call works
  identically against MySQL, PostgreSQL, Oracle, SQL Server. To switch
  databases you change exactly one thing: the JDBC URL in the
  `.java` file (plus the driver dependency in `pom.xml`).

* **Module 5 (Spring Boot) reuses this same H2 setup** as Spring Boot's
  default dev database. Everything you learn here transfers directly.

* **The MySQL mode (`MODE=MySQL` in the URL) is doing real work**:
  H2 accepts MySQL-flavoured SQL — `AUTO_INCREMENT`, `LIMIT n OFFSET m`,
  etc. So when you read MySQL examples online or in the Deitel
  textbook, the SQL you copy will work here too.

---

Questions? Bring them to lab or post on the course forum. Have fun.
