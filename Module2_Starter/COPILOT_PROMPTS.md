Prompt 1:
create a program ListStudents.java in C:\Users\Aries\Desktop\Accelerated software dev2\java\Assignment #1\Module2_Starter\src\main\java. that has public class ListStudents with public static void main(String[] args)
Prompt 2:
next is Open a connection to H2 using the URL jdbc:h2:./data/studentdb;MODE-MySQL;DATABASE_TO_LOWER=TRUE, user sa, password secret. set the login timeout to 5 seconds before calling DriverManager.getConnection. 
use try-with-resources for the connection, the statement and the result set.
Prompt 3:
next is Set setQueryTimeout(10) on the statement before executing. run the SQL SELECT id, name , program, gpa FROM student ORDER by  id. 
Loop with rs.next(). read the four columns. print one line per row using the format below- header, separator, four rows, blank line, count line, Exit 0 on success, 2 on ane SQLException, 3 if the driver isn't found
Prompt 4:
this is the format. the box-drawing characters are Unicode - Column widths: id padded to 3 characters right-aligned (%3d), name padded to 16 left-aligned (%-16s), 
program padded to 4 left-aligned (%-4s), GPA two decimals right- aligned in 5 characters (%5.2f). the final N is the wall-clock elapsed milliseconds from just before 
getConnection to just after the last rrow is read measure with the System.nanoTime(), divide by 1_000_000, print as an integer. expect a number under 200 on a warm JVM. I'll upload the screenshot for the expected output format.
Prompt 5:
Create a program AddStudent.java that read three command-line arguments: name program, GPS. Three Arguments. Name (1-80 characters, trimmed, possibly containing spaces if 
quoted on the command line and non-ASCII characters like Erik Skarsgard). Program code (regex ^[A-Z0-9{2m12}$). GPA (decimal 0.00 to 4.00). validate all three before opening the connection. 
if any fails, print one stderr line in the format exit 1.