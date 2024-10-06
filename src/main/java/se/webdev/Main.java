package se.webdev;
import se.webdev.entity.UserEntity;
import se.webdev.service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            UserEntity loggedInUser = userService.getLoggedInUser();
            StringBuilder isLoggedIn = new StringBuilder();

            if (loggedInUser != null) {
                isLoggedIn.append(" ").append(loggedInUser.getUsername());


            }

            System.out.println("\nVälkommen" + isLoggedIn + "!");
            System.out.println("Välj ett alternativ:");
            System.out.println("****************************************");
            System.out.println("1. Registrera ny användare");
            System.out.println("2. Logga in");
            System.out.println("3. Logga out");
            System.out.println("4. Visa/uppdatera mina uppgifter");
            System.out.println("5. Radera mitt konto");
            System.out.println("6. Avsluta");
            System.out.println("****************************************");


            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("1. Registrera ny användare");
                    System.out.print("Ange användarnamn: ");
                    String username = scanner.nextLine();
                    System.out.print("Ange lösenord: ");
                    String password = scanner.nextLine();
                    System.out.println("Ange e-post: ");
                    String email = scanner.nextLine();
                    System.out.println("Ange adress: ");
                    String address = scanner.nextLine();

                    userService.registerUser(username, password, email, address);
                    break;

                case 2:
                    System.out.println("2. Logga in");
                    System.out.println("Ange användarnamn: ");
                    String loginUsername = scanner.nextLine();
                    System.out.println("Ange lösenord: ");
                    String loginPassword = scanner.nextLine();

                    userService.loginUser(loginUsername, loginPassword);
                    break;
                case 3:
                    System.out.println("3. Logga out");
                    System.out.println("Loggar ut" + userService.getLoggedInUser().getUsername()) ;
                    userService.logoutUser();
                    break;
                case 4:
                    System.out.println("4. Visa/uppdatera mina uppgiftery");
                    userService.viewLoggedInUserDetails();

                    // Fråga om användaren vill uppdatera sina uppgifter
                    System.out.println("Vill du uppdatera dina uppgifter? (y/n)");
                    String updateChoice = scanner.nextLine();

                    if (updateChoice.equalsIgnoreCase("y")) {
                        System.out.println("Ange ny e-post (lämna tomt för att behålla nuvarande):");
                        String newEmail = scanner.nextLine();

                        System.out.println("Ange ny adress (lämna tomt för att behålla nuvarande):");
                        String newAddress = scanner.nextLine();

                        System.out.println("Ange nytt lösenord (lämna tomt för att behålla nuvarande):");
                        String newPassword = scanner.nextLine();

                        userService.updateLoggedInUser(newEmail, newAddress, newPassword);
                    }
                    break;

                case 5:
                    if (loggedInUser != null) {
                        System.out.println("Radera mitt konto:");
                        System.out.println("Raderar " + loggedInUser.getUsername() + " konto:");

                        userService.deleteLoggedInUser();
                    } else {
                        System.out.println("Du måste vara inloggad för att kunna radera ditt konto.");
                    }

                    break;

                case 6:
                    System.out.println("Avslutar programmet.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.err.println("Ogiltigt val.");
                    break;
            }
        }
    }
}
