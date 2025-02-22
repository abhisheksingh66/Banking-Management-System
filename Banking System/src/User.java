import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;




public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


//    public  void register() throws SQLException {
//        scanner.nextLine();
//        System.out.print("Full Name : ");
//        String full_name = scanner.nextLine();
//        System.out.print("Email : ");
//        String email = scanner.nextLine();
//        System.out.print("Password : ");
//        String password = scanner.nextLine();
//        if (user_exist(email)){
//            System.out.println("User Already Exist for this Email Address!!");
//            return;
//        }
//        String register_query ="insert into user(full_name,email,password) values(?,?,?)";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
//            preparedStatement.setString(1,full_name);
//            preparedStatement.setString(2,email);
//            preparedStatement.setString(3,password);
//            int effectedRows = preparedStatement.executeUpdate();
//            if (effectedRows>0){
//                System.out.println("Registration successfully");
//            }else {
//                System.out.println("Registration Failed");
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }




    public  void register() throws SQLException {
        scanner.nextLine();
        System.out.print("Full Name : ");
        String full_name = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();
        if (user_exist(email)){
            System.out.println("User Already Exist for this Email Address!!");
            return;
        }
        String register_query ="insert into user(full_name,email,password) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            int effectedRows = preparedStatement.executeUpdate();
            if (effectedRows>0){
                System.out.println("Registration successfully");
            }else {
                System.out.println("Registration Failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

//    // Utility function for password hashing
//    private String hashPassword(String password) throws NoSuchAlgorithmException {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hash = md.digest(password.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                hexString.append(String.format("%02x", b));
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error hashing password", e);
//        }
//    }

    public String login() throws SQLException {
        scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();

        String loginQuery = "select * from user where email = ? and password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(loginQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return email;
            } else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean user_exist(String email) throws SQLException {
String query = "select * from user where email = ?";

try {
    PreparedStatement preparedStatement = connection.prepareStatement(query);
    preparedStatement.setString(1, email);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()){
         return true;
    }else {
        return false;
    }
}catch (SQLException e){
    e.printStackTrace();
}

    return false;
}

}
