package com.example.backend4f;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class LoginServlet extends HttpServlet {

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String Utente = "";
        int flag = 0;
        String User = "";
        String Password = "";
        String Username = (request.getParameter("Username"));
        String pass = (request.getParameter("pass"));
        BufferedReader br = new BufferedReader(new FileReader("login.txt"));
        String riga = br.readLine();
        boolean Corretto = true;
        while(riga != null){
            flag = 0;
            User = "";
            Password = "";
            for(int i=0; i< riga.length(); i++){
                if( riga.charAt(i) != ' '){
                    if (flag == 0){
                        User = User + riga.charAt(i);
                    }else{
                        Password = Password + riga.charAt(i);
                    }
                }else{
                    flag = 1;
                }
            }

            if(User.equals(Username) && (Password.equals(pass))){
                Corretto = true;
                Utente = Username;
            }
        }

        if (Corretto == true){
            HttpSession session = request.getSession(true);
            session.setAttribute("messaggio",Utente);
            response.sendRedirect("/voti");
        }else{
            HttpSession session = request.getSession(true);
            String messaggio = "Username o Password sbagliate";
            session.setAttribute("error",messaggio);
            response.sendRedirect("/Errori");
        }
    }

    public void destroy() {
    }
}