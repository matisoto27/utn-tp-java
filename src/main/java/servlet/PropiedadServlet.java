package servlet;

import java.io.IOException;
import java.util.LinkedList;

import entities.Anunciante;
import entities.Cliente;
import entities.Propiedad;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AnuncianteController;
import logic.PrecioController;
import logic.PropiedadController;

@WebServlet({ "/PropiedadServlet", "/propiedadservlet" })
public class PropiedadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PropiedadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = (String) request.getSession().getAttribute("rol");
		if (rol == null) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		PropiedadController pc = new PropiedadController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
				case "create": {
					if (rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/ui-propiedad/create-propiedad.html").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
				case "retrieve": {
					if (rol.equals("administrador")) {
						LinkedList<Anunciante> anunciantes = new AnuncianteController().getAll();
						LinkedList<Propiedad> propiedades = pc.getAll();
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (mensaje != null) {
							request.setAttribute("mensaje", mensaje);
							request.getSession().removeAttribute("mensaje");
						}
						request.setAttribute("anunciantes", anunciantes);
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-propiedad/crud-propiedad.jsp").forward(request, response);
					} else if (rol.equals("anunciante")) {
						Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
						LinkedList<Propiedad> propiedades = pc.getPropiedadesByAnunciante(anun);
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (mensaje != null) {
							request.setAttribute("mensaje", mensaje);
							request.getSession().removeAttribute("mensaje");
						}
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-propiedad/lista-propiedades-anunciante.jsp").forward(request, response);
					} else if (rol.equals("cliente")) {
						Cliente cli = (Cliente) request.getSession().getAttribute("usuario");
						LinkedList<Propiedad> propiedades = pc.getPropiedadesDisponibles();
						request.setAttribute("cliente", cli);
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-propiedad/lista-propiedades-disponibles.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
				case "update": {
					if (rol.equals("anunciante")) {
						Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
						int nro_propiedad = Integer.parseInt(request.getParameter("nro"));
						Propiedad prop = new Propiedad(anun, nro_propiedad);
						prop = pc.getByIdAnunNroProp(prop);
						request.setAttribute("propiedad", prop);
						request.getRequestDispatcher("WEB-INF/ui-propiedad/update-propiedad.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = (String) request.getSession().getAttribute("rol");
		if (rol == null || rol.equals("cliente")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		PropiedadController pc = new PropiedadController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			Anunciante anun = getAnuncianteFromSessionOrRequest(request, rol);
			switch (action) {
				case "create": {
					String direccion = request.getParameter("direccion");
					Integer piso = !request.getParameter("piso").isEmpty() ? Integer.parseInt(request.getParameter("piso")): null;
					String depto = !request.getParameter("depto").isEmpty() ? request.getParameter("depto") : null;
					Propiedad prop = new Propiedad(anun, direccion, piso, depto);
					pc.add(prop);
					mensaje = "Propiedad registrada con éxito.";
					if (rol.equals("anunciante")) {
						request.setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
					} else if (rol.equals("administrador")) {
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("propiedadservlet?action=retrieve");
					}
					break;
				}
				case "update": {
					int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
					String direccion = request.getParameter("direccion");
					Integer piso = !request.getParameter("piso").isEmpty() ? Integer.parseInt(request.getParameter("piso")): null;
					String depto = !request.getParameter("depto").isEmpty() ? request.getParameter("depto") : null;
					Propiedad prop = new Propiedad(anun, nro_propiedad, direccion, piso, depto);
					pc.update(prop);
					mensaje = "Propiedad actualizada con éxito.";
					request.getSession().setAttribute("mensaje", mensaje);
					response.sendRedirect("propiedadservlet?action=retrieve");
					break;
				}
				case "delete": {
					int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
					Propiedad prop = new Propiedad(anun, nro_propiedad);
					prop = pc.getByIdAnunNroProp(prop);
					if (prop.getEstado() != null && (prop.getEstado().equals("Pendiente") || prop.getEstado().equals("En curso"))) {
						mensaje = "No puedes eliminar esta propiedad debido a que tiene un alquiler pendiente o en curso. Por favor, cancele el alquiler y vuelva a intentarlo.";
					} else {
						mensaje = "Propiedad eliminada con éxito.";
						new PrecioController().deleteByPropiedad(prop);
						pc.delete(prop);
					}
					request.getSession().setAttribute("mensaje", mensaje);
					response.sendRedirect("propiedadservlet?action=retrieve");
					break;
				}	
			}
		}
	}
	
	private Anunciante getAnuncianteFromSessionOrRequest(HttpServletRequest request, String rol) {
        Anunciante anun = new Anunciante();
        if (rol.equals("anunciante")) {
        	anun = (Anunciante) request.getSession().getAttribute("usuario");
        } else if (rol.equals("administrador")) {
        	int id_anunciante = Integer.parseInt(request.getParameter("id-anunciante"));
			anun.setIdAnunciante(id_anunciante);
			anun = new AnuncianteController().getById(anun);
        }
        return anun;
    }
	
}