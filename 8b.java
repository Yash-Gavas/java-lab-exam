package a1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/first")
public class first extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve cookies from the request
        Cookie[] cookies = request.getCookies();

        out.println("<html>");
        out.println("<head><title>Cookie Display</title></head>");
        out.println("<body>");
        out.println("<h1>Cookie Display</h1>");

        if (cookies != null && cookies.length > 0) {
            out.println("<h3>All Available Cookies:</h3>");
            out.println("<ul>");
            for (Cookie cookie : cookies) {
                out.println("<li>" + cookie.getName() + ": " + cookie.getValue() + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>No cookies found. They may have expired.</p>");
        }

        out.println("<h3>Refresh the page to see which cookies remain.</h3>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Create 4 cookies
        Cookie cookie1 = new Cookie("shortLivedCookie1", "value1");
        cookie1.setMaxAge(60); // 1 minute expiration
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("shortLivedCookie2", "value2");
        cookie2.setMaxAge(60); // 1 minute expiration
        response.addCookie(cookie2);

        Cookie cookie3 = new Cookie("longLivedCookie1", "value3");
        cookie3.setMaxAge(3600); // 1 hour expiration
        response.addCookie(cookie3);

        Cookie cookie4 = new Cookie("longLivedCookie2", "value4");
        cookie4.setMaxAge(3600); // 1 hour expiration
        response.addCookie(cookie4);

        // Redirect to the same servlet to display cookies
        response.sendRedirect("first");
    }
}



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cookie Example</title>
</head>
<body>
    <h1>Cookie Management</h1>
    <form action="first" method="post">
        <p>Click the button below to set cookies:</p>
        <button type="submit">Set Cookies</button>
    </form>
    <h3>Once cookies are set, refresh the page to see the remaining cookies.</h3>
</body>
</html>




