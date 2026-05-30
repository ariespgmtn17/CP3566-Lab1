import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class SearchSafe {

    private static final String URL      = "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER     = "sa";
    private static final String PASSWORD = "secret";

    private static final String SQL =
            "SELECT id, name, program, gpa FROM student WHERE name LIKE ?";

    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        if (args.length == 0) {
            System.err.printf("%s | ERROR | SearchSafe | message=expected 1 argument: name fragment%n",
                    Instant.now());
            System.exit(1);
        }

        // Wildcards go in the bound value, NOT in the SQL
        String searchValue = "%" + args[0] + "%";

        DriverManager.setLoginTimeout(5);

        try (
                Connection connection       = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(SQL)
        ) {
            statement.setQueryTimeout(10);
            statement.setString(1, searchValue);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int    id      = resultSet.getInt("id");
                    String name    = resultSet.getString("name");
                    String program = resultSet.getString("program");
                    double gpa     = resultSet.getDouble("gpa");

                    System.out.printf("Student #%d · %s · program %s · GPA %.2f%n",
                            id, name, program, gpa);
                }
            }

            System.exit(0);

        } catch (SQLException e) {
            System.err.printf("%s | ERROR | SearchSafe | SQLState=%s errorCode=%d message=%s%n",
                    Instant.now(), e.getSQLState(), e.getErrorCode(), e.getMessage());
            System.exit(2);
        }
    }
}