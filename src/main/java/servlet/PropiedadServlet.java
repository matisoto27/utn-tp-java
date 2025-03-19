package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

import entities.Propiedad;
import logic.PropiedadController;

@WebServlet({ "/PropiedadServlet", "/propiedadservlet" })
public class PropiedadServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public PropiedadServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PropiedadController pc = new PropiedadController();
		LinkedList<Propiedad> propiedades = pc.getPropiedadesDisponibles();
		request.setAttribute("listaPropiedades", propiedades);
		request.getRequestDispatcher("WEB-INF/ui-propiedad/lista-propiedades.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}