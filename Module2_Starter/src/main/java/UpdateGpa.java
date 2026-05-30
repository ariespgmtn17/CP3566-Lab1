import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

public class UpdateGpa {

    private static final String URL      = "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER     = "sa";
    private static final String PASSWORD = "secret";

    private static final String SQL =
            "UPDATE student SET gpa = ? WHERE id = ?";

    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        // Validate argument count
        if (args.length < 2) {
            printError("expected 2 arguments: id gpa, got " + args.length);
            System.exit(1);
        }

        // Validate id
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

        // Validate GPA
        double gpa;
        try {
            gpa = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            printError("gpa must be a decimal 0.00 to 4.00, got \"" + args[1] + "\"");
            System.exit(1);
            return;
        }

        if (gpa < 0.00 || gpa > 4.00) {
            printError("gpa must be in range 0.00 to 4.00, got " + args[1]);
            System.exit(1);
        }

        // All validated — open connection
        DriverManager.setLoginTimeout(5);

        try (
                Connection connection       = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(SQL)
        ) {
            statement.setQueryTimeout(10);

            // Bind GPA first, then id — matches the SQL order
            statement.setDouble(1, gpa);
            statement.setInt(2, theId);

            int rowsChanged = statement.executeUpdate();

            if (rowsChanged == 1) {
                System.out.printf("Updated student #%d · GPA set to %.2f · 1 row changed%n",
                        theId, gpa);
            } else {
                System.out.printf("No update · no student with id %d · 0 rows changed%n",
                        theId);
            }

            System.exit(0);

        } catch (SQLException e) {
            System.err.printf("%s | ERROR | UpdateGpa | SQLState=%s errorCode=%d message=%s%n",
                    Instant.now(), e.getSQLState(), e.getErrorCode(), e.getMessage());
            System.exit(2);
        }
    }

    private static void printError(String message) {
        System.err.printf("%s | ERROR | UpdateGpa | message=%s%n",
                Instant.now(), message);
    }
}