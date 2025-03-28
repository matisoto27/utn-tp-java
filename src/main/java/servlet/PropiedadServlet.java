package servlet;

import java.io.IOException;
import java.util.LinkedList;

import entities.Anunciante;
import entities.Propiedad;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.PropiedadController;

@WebServlet({ "/PropiedadServlet", "/propiedadservlet" })
public class PropiedadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PropiedadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PropiedadController pc = new PropiedadController();
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {

			case "propiedadesdisponibles": {
				LinkedList<Propiedad> propiedades = pc.getPropiedadesDisponibles();
				request.setAttribute("propiedades", propiedades);
				request.getRequestDispatcher("WEB-INF/ui-propiedad/lista-propiedades-disponibles.jsp").forward(request,
						response);
				break;
			}

			case "create": {
				request.getRequestDispatcher("WEB-INF/ui-propiedad/create-propiedad.html").forward(request, response);
				break;
			}

			case "propiedadesbyanunciante": {
				Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
				LinkedList<Propiedad> propiedades = pc.getPropiedadesByAnunciante(anun);
				request.setAttribute("propiedades", propiedades);
				request.getRequestDispatcher("WEB-INF/ui-propiedad/lista-propiedades-anunciante.jsp").forward(request,
						response);
				break;
			}

			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Propiedad p = new Propiedad();
		PropiedadController pc = new PropiedadController();

		Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
		String direccion = request.getParameter("direccion");
		int piso = Integer.parseInt(request.getParameter("piso"));
		String depto = request.getParameter("depto");

		p.setAnunciante(anun);
		p.setDireccion(direccion);
		p.setPiso(piso);
		p.setDepto(depto);

		pc.add(p);
		response.sendRedirect("menu-anunciante.html");
	}

}