import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class FindStudent {

    private static final String URL      = "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER     = "sa";
    private static final String PASSWORD = "secret";

    private static final String SQL =
            "SELECT id, name, program, gpa FROM student WHERE id = ?";

    public static void main(String[] args) throws Exception {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        // Validate argument
        if (args.length == 0) {
            printError("id must be a positive integer, got no argument");
            System.exit(1);
        }

        int theId;
        try {
            theId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            printError("id must be a positive integer, got \"" + args[0] + "\"");
            System.exit(1);
            return;
        }

        if (theId <= 0) {
            printError("id must be a positive integer, got " + theId);
            System.exit(1);
        }

        // Database lookup
        DriverManager.setLoginTimeout(5);

        try (
                Connection connection       = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(SQL)
        ) {
            statement.setQueryTimeout(10);
            statement.setInt(1, theId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int    id      = resultSet.getInt("id");
                    String name    = resultSet.getString("name");
                    String program = resultSet.getString("program");
                    double gpa     = resultSet.getDouble("gpa");

                    System.out.printf("Student #%d · %s · program %s · GPA %.2f%n",
                            id, name, program, gpa);
                } else {
                    System.out.println("No student with id " + theId + ".");
                }
            }

            System.exit(0);

        } catch (SQLException e) {
            System.err.printf("%s | ERROR | FindStudent | SQLState=%s errorCode=%d message=%s%n",
                    Instant.now(), e.getSQLState(), e.getErrorCode(), e.getMessage());
            System.exit(2);
        }
    }

    private static void printError(String message) {
        System.err.printf("%s | ERROR | FindStudent | message=%s%n",
                Instant.now(), message);
    }
}