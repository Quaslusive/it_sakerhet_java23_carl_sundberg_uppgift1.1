package se.webdev.service;
import se.webdev.database.DatabaseConnection;
import se.webdev.entity.UserEntity;
import  org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private UserEntity loggedInUser = null;  // Spara inloggad användare

    // Registrera ny användare
    public boolean registerUser(String username, String password, String email, String address) {
        if (existsByUsername(username)) {
            System.out.println("Användarnamnet finns redan.");
            return false;
        }

        String hashedPassword = hashPassword(password);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, email, address) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setString(3, email);
            statement.setString(4, address);
         //   statement.setString(5, test);
            statement.executeUpdate();
            System.out.println("Användare registrerad.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public boolean loginUser(String username, String password) {
       try (Connection conn = DatabaseConnection.getConnection()) {
           String sql = "SELECT * FROM users WHERE username = ?";
           PreparedStatement statement = conn.prepareStatement(sql);
           statement.setString(1, username);
           ResultSet resultSet = statement.executeQuery();

           if (resultSet.next()) {
               String storedPassword = resultSet.getString("password");

               if (checkPassword(password, storedPassword)) {

                   loggedInUser = new UserEntity(
                           resultSet.getLong("id"),
                           resultSet.getString("username"),
                           resultSet.getString("email"),
                           resultSet.getString("password"), // kunde inte ta bort detta, annars blir email null
                           // null,
                           resultSet.getString("address")
                   );
                   System.out.println("Inloggningen lyckades.");
                   return true;
               } else {
                   System.out.println("Felaktigt lösenord.");
               }
           } else {
               System.out.println("Användaren hittades inte.");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
   }

    public void logoutUser() {
        if (loggedInUser != null) {
            System.out.println("Du är nu utloggad.");
            loggedInUser = null;
        } else {
            System.out.println("Ingen användare är inloggad.");
        }
    }

    public UserEntity getLoggedInUser() {
        return loggedInUser;
    }

    public void viewLoggedInUserDetails() {
        if (loggedInUser != null) {
            System.out.println("Användaruppgifter:");
            System.out.println("__________________");
            System.out.println("Användarnamn: " + loggedInUser.getUsername());
            System.out.println("__________________");
            System.out.println("E-post: " + loggedInUser.getEmail());
            System.out.println("__________________");
            System.out.println("Lösenord: " + (loggedInUser.getPassword() != null ? "[PROTECTED]" : "null")); // Detta är väl ok?
            System.out.println("__________________");
            System.out.println("Adress: " + loggedInUser.getAddress());
            System.out.println("__________________");
        } else {
            System.out.println("Ingen användare inloggad.");
        }
    }

    public void updateLoggedInUser(String email, String address, String password) {
        if (loggedInUser != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE users SET email = ?, address = ?, password = ? WHERE username = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, email.isEmpty() ? loggedInUser.getEmail() : email);
                statement.setString(2, address.isEmpty() ? loggedInUser.getAddress() : address);
                statement.setString(3, password.isEmpty() ? loggedInUser.getPassword() : hashPassword(password));
                statement.setString(4, loggedInUser.getUsername());
                statement.executeUpdate();
                System.out.println("Dina uppgifter har uppdaterats.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ingen användare är inloggad.");
        }
    }


    public void deleteLoggedInUser() {
        if (loggedInUser != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM users WHERE username = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, loggedInUser.getUsername());
                statement.executeUpdate();
                System.out.println("Användaren har raderats.");
                loggedInUser = null;  // Logga ut användaren
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ingen användare är inloggad.");
        }
    }

    private boolean existsByUsername(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hasha lösenordet
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Kontrollera lösenord
    private boolean checkPassword(String rawPassword, String hashedPassword) {
       return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
