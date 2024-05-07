package com.example.backend4f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet {

    private static final Map<String, String> credentials = new HashMap<>();

    /**
     * Carica le credenziali da un file di testo.
     *
     * @param filePath Il percorso del file contenente le credenziali.
     */
    public static void loadCredentials(String filePath) {
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ", 2); // Supponiamo che username e password siano separati da uno spazio
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Verifica se la coppia di username e password esiste nel file.
     *
     * @param user L'username da verificare.
     * @param pass La password da verificare.
     * @return true se la coppia esiste, altrimenti false.
     */
    public static boolean authenticate(String user, String pass) {
        return credentials.getOrDefault(user, "").equals(pass);
    }

    public static void main(String[] args) {
        // Carica le credenziali dal file
        System.out.println(System.getProperty("user.dir"));
        loadCredentials("login.txt");

        // Test del metodo authenticate
        System.out.println(authenticate("alice.morgan", "strongP@ssw0rd!")); // true
        System.out.println(authenticate("bob.smith", "wrongpassword")); // false
        System.out.println(authenticate("nonexistentuser", "password")); // false
    }
}