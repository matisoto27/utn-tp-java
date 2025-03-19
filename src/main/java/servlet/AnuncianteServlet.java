package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

import entities.Anunciante;
import logic.AnuncianteController;

@WebServlet({ "/AnuncianteServlet", "/anuncianteservlet" })
public class AnuncianteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public AnuncianteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AnuncianteController ac = new AnuncianteController();
		LinkedList<Anunciante> la = ac.getAll();
		request.setAttribute("listaAnunciantes", la);
		request.getRequestDispatcher("WEB-INF/ui-anunciante/lista-anunciantes.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}