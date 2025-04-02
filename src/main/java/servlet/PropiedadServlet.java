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
import logic.PrecioController;
import logic.PropiedadController;

@WebServlet({ "/PropiedadServlet", "/propiedadservlet" })
public class PropiedadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PropiedadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PropiedadController pc = new PropiedadController();
		String rol = (String) request.getSession().getAttribute("rol");
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			
				case "propiedadesdisponibles": {
					if (rol.equals("cliente")) {
						Cliente cli = new Cliente();
						LinkedList<Propiedad> propiedades = new LinkedList<>();
						cli = (Cliente) request.getSession().getAttribute("usuario");
						propiedades = pc.getPropiedadesDisponibles();
						request.setAttribute("cliente", cli);
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-propiedad/lista-propiedades-disponibles.jsp").forward(request, response);
					} else {
						// Acceso no autorizado
					}
					break;
				}
				
				case "propiedadesbyanunciante": {
					if (rol.equals("anunciante")) {
						Anunciante anun = new Anunciante();
						LinkedList<Propiedad> propiedades = new LinkedList<>();
						String mensaje = null;
						anun = (Anunciante) request.getSession().getAttribute("usuario");
						propiedades = pc.getPropiedadesByAnunciante(anun);
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (mensaje != null) {
							request.setAttribute("mensaje", mensaje);
							request.getSession().removeAttribute("mensaje");
						}
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-propiedad/lista-propiedades-anunciante.jsp").forward(request, response);
					} else {
						// Acceso no autorizado
					}
					break;
				}
				
				case "create": {
					if (rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/ui-propiedad/create-propiedad.html").forward(request, response);
					} else {
						// Acceso no autorizado
					}
					break;
				}
			
				case "update": {
					if (rol.equals("anunciante")) {
						Propiedad prop = new Propiedad();
						Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
						int nro_propiedad = Integer.parseInt(request.getParameter("nro"));
						prop.setAnunciante(anun);
						prop.setNroPropiedad(nro_propiedad);
						prop = pc.getByIdAnunNroProp(prop);
						request.setAttribute("propiedad", prop);
						request.getRequestDispatcher("WEB-INF/ui-propiedad/update-propiedad.jsp").forward(request, response);
					} else {
					// Admin
					}
					break;
				}
			
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Propiedad prop = new Propiedad();
		PropiedadController pc = new PropiedadController();
		String rol = (String) request.getSession().getAttribute("rol");
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			
				case "create": {
					if (rol.equals("anunciante")) {
						Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
						String direccion = request.getParameter("direccion");
						Integer piso = !request.getParameter("piso").isEmpty() ? Integer.parseInt(request.getParameter("piso")): null;
						String depto = !request.getParameter("depto").isEmpty() ? request.getParameter("depto") : null;
						prop.setAnunciante(anun);
						prop.setDireccion(direccion);
						prop.setPiso(piso);
						prop.setDepto(depto);
						pc.add(prop);
						String mensaje = "Propiedad registrada con éxito.";
						request.setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
					} else {
					
					}
					break;
				}
			
				case "update": {
					if (rol.equals("anunciante")) {
						Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
						int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
						String direccion = request.getParameter("direccion");
						Integer piso = !request.getParameter("piso").isEmpty() ? Integer.parseInt(request.getParameter("piso")): null;
						String depto = !request.getParameter("depto").isEmpty() ? request.getParameter("depto") : null;
						prop.setAnunciante(anun);
						prop.setNroPropiedad(nro_propiedad);
						prop.setDireccion(direccion);
						prop.setPiso(piso);
						prop.setDepto(depto);
						pc.update(prop);
						String mensaje = "Propiedad actualizada con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("propiedadservlet?action=propiedadesbyanunciante");
					} else {
						// Admin
					}
					break;
				}
				
				case "delete": {
					if (rol.equals("anunciante")) {
						Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
						int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
						String mensaje = null;
						prop.setAnunciante(anun);
						prop.setNroPropiedad(nro_propiedad);
						prop = pc.getByIdAnunNroProp(prop);
						if (prop.getEstado() != null && prop.getEstado().equals("En curso")) {
							mensaje = "No puedes eliminar esta propiedad, ya que tiene un alquiler en curso. Por favor, cancela el alquiler antes de intentar eliminarla.";
						} else {
							mensaje = "Propiedad eliminada con éxito.";
							PrecioController prec = new PrecioController();
							prec.deleteByPropiedad(prop);
							pc.delete(prop);
						}
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("propiedadservlet?action=propiedadesbyanunciante");
					} else {
						// Admin
					}
					break;
				}
				
			}
		}
	}

}