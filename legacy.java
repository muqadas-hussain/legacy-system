import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LegacyUserSystem {

    public static Connection conn = null;
    public static Statement stmt = null;
    public static ResultSet rs = null;

    public static ArrayList users = new ArrayList(); // Raw type (no generics)

    public static void main(String[] args) {

        connectDB();

        getUsers("admin");

        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }

        if (users.size() > 0) {
            System.out.println("Users found");
        } else {
            System.out.println("No users found");
        }

        closeDB();
    }

    public static void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Deprecated driver
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/legacydb", "root", "root");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace(); // Bad exception handling
        }
    }

    public static void getUsers(String role) {

        String query = "SELECT * FROM users WHERE role = '" + role + "'"; // SQL Injection

        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeDB() {
        try {
            rs.close();   // Possible NullPointerException
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
