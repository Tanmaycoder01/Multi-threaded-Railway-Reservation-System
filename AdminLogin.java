import java.util.Scanner;

public class AdminLogin {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    public static boolean login(Scanner sc) {
        System.out.println("--- Admin Login ---");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (USERNAME.equals(user) && PASSWORD.equals(pass)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }
}
