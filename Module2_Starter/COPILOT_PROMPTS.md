Prompt 1:
create a program ListStudents.java in C:\Users\Aries\Desktop\Accelerated software dev2\java\Assignment #1\Module2_Starter\src\main\java. that has public class ListStudents with public static void main(String[] args)

WHEN — Starting Task 1 and creating ListStudents.java
ACCEPTED — Yes, added to ListStudents.java
VERDICT — Correct. It created the basic class structure with the correct class name and main method.
Prompt 2:
next is Open a connection to H2 using the URL jdbc:h2:./data/studentdb;MODE-MySQL;DATABASE_TO_LOWER=TRUE, user sa, password secret. set the login timeout to 5 seconds before calling DriverManager.getConnection. 
use try-with-resources for the connection, the statement and the result set.

WHEN — Task 1: Added the H2 JDBC connection.
ACCEPTED — Partially; the URL needed a fix from MODE-MySQL to MODE=MySQL.
VERDICT — Mostly correct. The URL contained a typo (using - instead of = for the MODE parameter), which would have caused a connection error.
Prompt 3:
next is Set setQueryTimeout(10) on the statement before executing. run the SQL SELECT id, name , program, gpa FROM student ORDER by  id. 
Loop with rs.next(). read the four columns. print one line per row using the format below- header, separator, four rows, blank line, count line, Exit 0 on success, 2 on ane SQLException, 3 if the driver isn't found

WHEN — Task 1, adding query execution, loop, and exit codes
ACCEPTED — yes, went into ListStudents.java
VERDICT — Correct, query timeout, SQL, loop, and all three exit
codes were implemented properly.
Prompt 4:
this is the format. the box-drawing characters are Unicode - Column widths: id padded to 3 characters right-aligned (%3d), name padded to 16 left-aligned (%-16s), 
program padded to 4 left-aligned (%-4s), GPA two decimals right- aligned in 5 characters (%5.2f). the final N is the wall-clock elapsed milliseconds from just before 
getConnection to just after the last rrow is read measure with the System.nanoTime(), divide by 1_000_000, print as an integer. expect a number under 200 on a warm JVM. I'll upload the screenshot for the expected output format.

WHEN — Task 1: Fixed the output format to match the required screenshot.
ACCEPTED — Yes; updated ListStudents.java.
VERDICT — Correct. The table formatting matched the required output, and the execution time was under 200 ms.
Prompt 5:
create a program FIndStudent.java that read an integer id from args[0] with argument: one positive integer, the student id. validate against input rules -- reject zero, negatives, non-integers, 
or no argument with the appropriate stderr diagnostic and exit code 1 this is the format timestamp | ERROR | FindStudent | message=id. use prepared statement. SQL: SELECT id, name program, 
gpa FROM student WHERE id = ?. bind the integer with ps.setInt(1, theId). Set setQueryTimeout(10). execute, read the at-most-one row.

WHEN — Starting Task 2, creating FindStudent.java
ACCEPTED — yes, went into FindStudent.java
VERDICT — Correct, all three cases worked: found exits 0, not found
exits 0, and bad input prints stderr in the required timestamp format
and exits 1.
Prompt 6:
Create a program AddStudent.java that read three command-line arguments: name program, GPS. Three Arguments. Name (1-80 characters, trimmed, possibly containing spaces if 
quoted on the command line and non-ASCII characters like Erik Skarsgard). Program code (regex ^[A-Z0-9{2m12}$). GPA (decimal 0.00 to 4.00). validate all three before opening the connection. 
if any fails, print one stderr line in the format exit 1.

WHEN — Started Task 3: Created AddStudent.java.
ACCEPTED — Yes; implemented in AddStudent.java.
VERDICT — Correct. All validations ran before opening the database connection, and the regex was corrected to ^[A-Z0-9]{2,12}$ to avoid validation errors.
Prompt 7:
then the id column is INT AUTO_INCREMENT PRIMARY KEY. use conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS). after ps.executeUpdate(), call ps.getGenerateKeys() to get a one-row ResultSet containing the new id. 
put it in its own try-with-resources block. read rs.getInt(1). SQL: INSERT INTO student (name, program, gpa) VALUES (?, ?, ?). bind with setString, setString, setDouble. set setQueryTimeout(10)

WHEN — Task 3, adding RETURN_GENERATED_KEYS to AddStudent.java
ACCEPTED — yes, went into AddStudent.java
VERDICT — Correct, the generated keys ResultSet was placed in its own
inner try-with-resources block and rs.getInt(1) retrieved the new id
assigned by the database.
Prompt 8:
Create a program UpdateGpa.java. Read an id and new GPA from args. reject bad input with exit 1. SQL: UPDATE student SET  gpa = ? WHERE id ? . bind GPA first, then id-  the order matches the SQL, not your intuition. 
ps.executeUpdate() returns the number of rows modified. if the id existed, it returns 1. if not, it returns 0. the 0 is not an error - it is the answer to the question "did anything match?"

WHEN — Starting Task 4, creating UpdateGpa.java
ACCEPTED — yes, went into UpdateGpa.java
VERDICT — Correct, GPA was bound first then id to match the SQL order,
and both 0 rows and 1 row cases printed the right message and exited 0.
Prompt 9:
Create a program SearchUnsafe.java that takes one command-line argument as a name fragment. use LIKE with % wildcards on either side. Build the SQL by string concatenation: 
"SELECT id, name, program, gpa FROM student WHERE name LIKE '%" + args[0] + "%'". use a plain Statement, not PreparedStatement. execute with stmt.executeQuery(sql). 
print every matching row."Student # · Name · program · gpa n.nn. exit 0 on success

WHEN — Started Task 5: Created SearchUnsafe.java.
ACCEPTED — Yes; implemented in SearchUnsafe.java.
VERDICT — Correct. The test input was adjusted internally to avoid IntelliJ and PowerShell quoting issues while still demonstrating the intended SQL injection behavior.
Prompt 10:
Then create a SearchSafe.java - same thing but use PreparedStatement withplaceholder SQL: "Select id, name, program, gpa FROM student WHERE name LIKE ?". the bound value is "%" + args[0] + "%" - wildcards go in the bound string, 
not in the SQL itself

WHEN — Task 5: Created SearchSafe.java.
ACCEPTED — Yes; implemented in SearchSafe.java.
VERDICT — Correct. SearchSafe returned no results for the malicious input, while SearchUnsafe returned all rows, demonstrating that PreparedStatement prevents SQL injection by treating user input as data rather than SQL code.