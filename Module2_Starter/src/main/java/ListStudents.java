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

        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement   = connection.createStatement();
                ResultSet resultSet   = statement.executeQuery("SELECT * FROM students")
        ) {

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}