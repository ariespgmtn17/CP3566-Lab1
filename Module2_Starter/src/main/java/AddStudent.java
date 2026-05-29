import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

public class AddStudent {

    private static final String URL      = "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER     = "sa";
    private static final String PASSWORD = "secret";

    private static final String SQL =
            "INSERT INTO student (name, program, gpa) VALUES (?, ?, ?)";

    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        // Validate argument count
        if (args.length < 3) {
            printError("expected 3 arguments: name program gpa, got " + args.length);
            System.exit(1);
        }

        // Validate name
        String name = args[0].trim();
        if (name.isEmpty() || name.length() > 80) {
            printError("name must be 1 to 80 characters, got \"" + args[0] + "\"");
            System.exit(1);
        }

        // Validate program code
        String program = args[1];
        if (!program.matches("^[A-Z0-9]{2,12}$")) {
            printError("program must be 2-12 uppercase letters or digits, got \"" + program + "\"");
            System.exit(1);
        }

        // Validate GPA
        double gpa;
        try {
            gpa = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            printError("gpa must be a decimal 0.00 to 4.00, got \"" + args[2] + "\"");
            System.exit(1);
            return;
        }

        if (gpa < 0.00 || gpa > 4.00) {
            printError("gpa must be in range 0.00 to 4.00, got " + args[2]);
            System.exit(1);
        }

        // All validated — open connection
        DriverManager.setLoginTimeout(5);

        try (
                Connection connection       = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setQueryTimeout(10);
            statement.setString(1, name);
            statement.setString(2, program);
            statement.setDouble(3, gpa);

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    System.out.printf("Inserted student #%d · %s · program %s · GPA %.2f%n",
                            newId, name, program, gpa);
                }
            }

            System.exit(0);

        } catch (SQLException e) {
            System.err.printf("%s | ERROR | AddStudent | SQLState=%s errorCode=%d message=%s%n",
                    Instant.now(), e.getSQLState(), e.getErrorCode(), e.getMessage());
            System.exit(2);
        }
    }

    private static void printError(String message) {
        System.err.printf("%s | ERROR | AddStudent | message=%s%n",
                Instant.now(), message);
    }
}