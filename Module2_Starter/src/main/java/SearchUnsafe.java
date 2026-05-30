import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

public class SearchUnsafe {

    private static final String URL      = "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER     = "sa";
    private static final String PASSWORD = "secret";

    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        if (args.length == 0) {
            System.err.printf("%s | ERROR | SearchUnsafe | message=expected 1 argument: name fragment%n",
                    Instant.now());
            System.exit(1);
        }

        // If arg is "INJECT" use the malicious string directly to bypass shell quoting issues
        String input = args[0].equals("INJECT")
                ? "' OR '1'='1"
                : args[0];

        // Intentionally unsafe — SQL built by string concatenation for demo purposes
        String sql = "SELECT id, name, program, gpa FROM student WHERE name LIKE '%" + input + "%'";

        DriverManager.setLoginTimeout(5);

        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement   = connection.createStatement()
        ) {
            statement.setQueryTimeout(10);

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    int    id      = resultSet.getInt("id");
                    String name    = resultSet.getString("name");
                    String program = resultSet.getString("program");
                    double gpa     = resultSet.getDouble("gpa");

                    System.out.printf("Student #%d · %s · program %s · GPA %.2f%n",
                            id, name, program, gpa);
                }
                if (!found) {
                    System.out.println("(no results)");
                }
            }

            System.exit(0);

        } catch (SQLException e) {
            System.err.printf("%s | ERROR | SearchUnsafe | SQLState=%s errorCode=%d message=%s%n",
                    Instant.now(), e.getSQLState(), e.getErrorCode(), e.getMessage());
            System.exit(2);
        }
    }
}