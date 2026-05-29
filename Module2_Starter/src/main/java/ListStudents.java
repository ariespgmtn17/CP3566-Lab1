import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListStudents {

    public static void main(String[] args) {

        final String URL      = "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
        final String USER     = "sa";
        final String PASSWORD = "secret";

        DriverManager.setLoginTimeout(5);

        long start = System.nanoTime();

        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement   = connection.createStatement()
        ) {
            statement.setQueryTimeout(10);

            try (ResultSet resultSet = statement.executeQuery(
                    "SELECT id, name, program, gpa FROM student ORDER BY id")) {

                // Header
                System.out.println(" id  \u2502 name             \u2502 prog \u2502  gpa");
                // Separator
                System.out.println("\u2500\u2500\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2500\u2500\u2500\u253c\u2500\u2500\u2500\u2500\u2500\u2500");

                int count = 0;

                while (resultSet.next()) {
                    int    id      = resultSet.getInt("id");
                    String name    = resultSet.getString("name");
                    String program = resultSet.getString("program");
                    double gpa     = resultSet.getDouble("gpa");

                    System.out.printf(" %3d \u2502 %-16s \u2502 %-4s \u2502 %5.2f%n",
                            id, name, program, gpa);
                    count++;
                }

                long elapsed = (System.nanoTime() - start) / 1_000_000;

                // Blank line + count line
                System.out.println();
                System.out.printf("%d student(s) listed in %d ms.%n", count, elapsed);
            }

            System.exit(0);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(3);
        }

    }

}