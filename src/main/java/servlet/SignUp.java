package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({ "/SignUp", "/signup" })
public class SignUp extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public SignUp() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = request.getParameter("rol");
		if (rol.equals("anunciante")) {
			request.getSession().setAttribute("rol", "anunciante");
            request.getRequestDispatcher("/signup-anunciante.jsp").forward(request, response);
		} else if (rol.equals("cliente")) {
        	request.getSession().setAttribute("rol", "cliente");
            request.getRequestDispatcher("/signup-cliente.jsp").forward(request, response);
        } else {
        	response.sendRedirect("signup.html");
        }
	}
	
}