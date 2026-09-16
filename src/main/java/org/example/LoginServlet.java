package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                out.println("<h2>Login Successful!</h2>");
                out.println("<p>Welcome, " + username + "</p>");

            } else {

                out.println("<h2>Invalid username or password</h2>");
                out.println("<a href='login.html'>Try Again</a>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h2>Database Error</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }
}