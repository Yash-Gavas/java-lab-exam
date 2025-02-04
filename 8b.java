package a;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/vote")
public class vote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        
	        Cookie cookie1 = new Cookie("Cookie1", "Value1");
	        Cookie cookie2 = new Cookie("Cookie2", "Value2");
	        Cookie cookie3 = new Cookie("Cookie3", "Value3");
	        Cookie cookie4 = new Cookie("Cookie4", "Value4");
	        
	        cookie1.setMaxAge(60); 
	        cookie2.setMaxAge(60);
	        
	        response.addCookie(cookie1);
	        response.addCookie(cookie2);
	        response.addCookie(cookie3);
	        response.addCookie(cookie4);
	        
	        Cookie[] cookies = request.getCookies();
	
	        out.println("<h2>Cookies Status</h2>");
	
	        if (cookies != null) 
	        {
	            for (Cookie cookie : cookies) 
	            {
	                out.println("<p>" + cookie.getName() + ": " + cookie.getValue() + "</p>");
	            }
	        } 
	        
	        else 
	        {
	            out.println("<p>No cookies found. Some cookies might have expired.</p>");
	        }
	
	        out.println("<p>Refresh the page to see the effect of cookie expiration.</p>");
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




