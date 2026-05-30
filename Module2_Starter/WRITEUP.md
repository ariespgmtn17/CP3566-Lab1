Question 1. In Task 5, the unsafe version returned four rows for the malicious input. In your own words — not
Module 2's words — explain what the database actually executed in that run. What did the SQL string look
like just before being sent? Why did the WHERE clause match every row?

When the input ' OR '1'='1 was entered into SearchUnsafe, it was added directly to the SQL query without any protection. This changed the query so that it contained OR '1'='1', 
which is always true. As a result, the database returned all student records instead of only the students whose names matched the search term. This is an example of an SQL injection attack.

Question 2. In Task 4 the row count was either 1 or 0 depending on whether the id existed. Pick one specific
bug that ignoring that return value would let through. Explain why that bug would not be caught by manual
testing of "does the program print something sensible."

In UpdateGpa, if the program ignores the result of executeUpdate() and always prints "Updated successfully", it can give a false success message. For example, 
if you try to update student ID 99 and that student does not exist, no records will be changed, but the program will still say the update was successful. 
This mistake may go unnoticed because the program runs normally and shows no errors. Checking the row count returned by executeUpdate() is the only way to confirm that a record was actually updated.

Question 3. Task 3 used Statement.RETURN_GENERATED_KEYS  to read back the new student's id. In one
sentence, without metaphors or the word "magic": who assigned that id, when did they assign it, and how did
your Java code get the number back?

The H2 database automatically created a new student ID when the INSERT statement was executed because the id column uses AUTO_INCREMENT. 
After the record was added, the Java program used getGeneratedKeys() to get the newly created ID and read it with rs.getInt(1).

Question 4. Pick one prompt from your COPILOT_PROMPTS.md  where the AI's suggestion looked correct at
first reading but would have lost marks under §9. Explain what looked right and what was actually wrong. If
you didn't use AI, pick a Stack Overflow snippet you considered.

When I asked Claude to create ListStudents.java, its first version used try-with-resources correctly, but it placed the ResultSet in the same try block as the Connection and Statement. 
Although the code compiled and worked, the lab required the ResultSet to be in a separate inner try-with-resources block. At first glance, the code looked correct because all resources were closed properly,
but it did not fully follow the lab instructions and could have resulted in lost marks.