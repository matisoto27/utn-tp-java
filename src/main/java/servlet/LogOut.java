package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({ "/LogOut", "/logout" })
public class LogOut extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public LogOut() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mensaje = (String) request.getSession().getAttribute("mensaje");
		if (mensaje != null) {
			request.setAttribute("mensaje", mensaje);
			request.getSession().removeAttribute("mensaje");
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
}