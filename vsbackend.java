import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    // Set up the database connection
    String jdbcUrl = "jdbc:mysql://localhost:3306/mydatabase";
    String jdbcUsername = "myusername";
    String jdbcPassword = "mypassword";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the email and password parameters from the request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Set up the database connection
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            
            // Query the database for user with matching email and password
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            
            // Check if user exists
            if (resultSet.next()) {
                // Login successful
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                response.sendRedirect("dashboard.jsp");
            } else {
                // Login failed
                request.setAttribute("error", "Invalid email or password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
