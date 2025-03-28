package servlet;

import java.io.IOException;
import java.util.LinkedList;

import entities.Anunciante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AnuncianteController;

@WebServlet({ "/AnuncianteServlet", "/anuncianteservlet" })
public class AnuncianteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AnuncianteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Anunciante anun = new Anunciante();
		AnuncianteController ac = new AnuncianteController();
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			case "delete": {
				int id_anunciante = Integer.parseInt(request.getParameter("id"));
				anun.setIdAnunciante(id_anunciante);
				ac.delete(anun);
				break;
			}
			}
		}
		LinkedList<Anunciante> anunciantes = ac.getAll();
		request.setAttribute("anunciantes", anunciantes);
		request.getRequestDispatcher("WEB-INF/ui-anunciante/crud-anunciante.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Anunciante anun = new Anunciante();
		AnuncianteController ac = new AnuncianteController();
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			case "create": {
				String nombre = request.getParameter("nombre");
				String email = request.getParameter("email");
				String telefono = request.getParameter("telefono");
				String usuario = request.getParameter("usuario");
				String contrasena = request.getParameter("contrasena");
				anun.setNombre(nombre);
				anun.setEmail(email);
				anun.setTelefono(telefono);
				anun.setUsuario(usuario);
				anun.setContrasena(contrasena);
				ac.add(anun);
				break;
			}
			case "update": {
				int id_anunciante = Integer.parseInt(request.getParameter("id-anunciante"));
				String nombre = request.getParameter("nombre");
				String email = request.getParameter("email");
				String telefono = request.getParameter("telefono");
				String usuario = request.getParameter("usuario");
				String contrasena = request.getParameter("contrasena");
				anun.setIdAnunciante(id_anunciante);
				anun.setNombre(nombre);
				anun.setEmail(email);
				anun.setTelefono(telefono);
				anun.setUsuario(usuario);
				anun.setContrasena(contrasena);
				ac.update(anun);
				break;
			}
			}
		}
		LinkedList<Anunciante> anunciantes = ac.getAll();
		request.setAttribute("anunciantes", anunciantes);
		request.getRequestDispatcher("WEB-INF/ui-anunciante/crud-anunciante.jsp").forward(request, response);
	}

}