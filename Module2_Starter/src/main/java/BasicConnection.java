import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lab 1 of CP3566 Module 2.
 *
 * Opens a JDBC connection to an embedded H2 database, prints basic
 * metadata, and closes the connection (try-with-resources handles
 * the close automatically).
 *
 * H2 is a pure-Java relational database that runs INSIDE this JVM.
 * The first run of this program creates ./data/studentdb.mv.db in
 * the project folder; subsequent runs reuse it.
 *
 * The JDBC URL switches:
 *   MODE=MySQL            -- accept MySQL-flavoured SQL (so the same
 *                            code you'll write here works against
 *                            real MySQL in industry with no changes)
 *   DATABASE_TO_LOWER=TRUE -- treat table/column names case-insensitively
 *                            (matches MySQL's default behaviour)
 *
 * No database server, no port, no password to install. The JAR on
 * the classpath IS the database.
 */
public class BasicConnection {
    private static final String URL  =
            "jdbc:h2:./data/studentdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "secret";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("Status     : Connected");
            System.out.println("Driver     : " + meta.getDriverName()
                                  + " v" + meta.getDriverVersion());
            System.out.println("URL        : " + meta.getURL());
            System.out.println("AutoCommit : " + conn.getAutoCommit());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
