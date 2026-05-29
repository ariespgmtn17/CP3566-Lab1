import java.sql.DriverManager;

/**
 * Smoke test for CP3566 Module 2.
 *
 * Asks DriverManager whether the H2 driver was auto-registered by the
 * JDBC 4.0 ServiceLoader mechanism. Does NOT call Class.forName —
 * that's the 2006-era idiom; in CP3566 it loses marks (see lecture
 * §1 grading rule). The driver JAR (h2-2.3.232.jar) ships with a
 * META-INF/services/java.sql.Driver entry which the JVM reads at
 * startup. Putting the JAR on the classpath is enough.
 *
 * Does NOT open any database file. That's the next step
 * (BasicConnection.java).
 */
public class HelloJdbc {
    public static void main(String[] args) {
        boolean h2Registered = DriverManager.drivers()
                .anyMatch(d -> d.getClass().getName().equals("org.h2.Driver"));

        if (h2Registered) {
            System.out.println("Driver registered: org.h2.Driver");
            System.out.println("Java version    : " + System.getProperty("java.version"));
            System.out.println("H2 ready        : YES — proceed to BasicConnection.java");
        } else {
            System.err.println("H2 driver NOT auto-registered.");
            System.err.println("Maven import may be incomplete — open the Maven tool window in");
            System.err.println("IntelliJ and click the Reload icon, then re-run.");
        }
    }
}
