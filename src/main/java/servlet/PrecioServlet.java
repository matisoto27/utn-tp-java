package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entities.Anunciante;
import entities.Precio;
import entities.Propiedad;
import logic.AnuncianteController;
import logic.PrecioController;
import logic.PropiedadController;

@WebServlet({ "/PrecioServlet", "/precioservlet" })
public class PrecioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public PrecioServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String rol = (String) request.getSession().getAttribute("rol");
		if (!rol.equals("administrador") && !rol.equals("anunciante")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		Precio pre = new Precio();
		PrecioController pc = new PrecioController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
				case "create": {
					if (rol.equals("anunciante")) {
						PropiedadController propc = new PropiedadController();
						Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
						LinkedList<Propiedad> propiedades = propc.getPropiedadesByAnunciante(anun);
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-precio/create-precio.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
				case "retrieve": {
					if (rol.equals("administrador")) {
						pre = (Precio) request.getSession().getAttribute("respuestas_correctas");
						String errores = (String) request.getSession().getAttribute("errores");
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (pre != null) {
							request.setAttribute("respuestas_correctas", pre);
							request.getSession().removeAttribute("respuestas_correctas");
						}
						if (errores != null) {
							request.setAttribute("errores", errores);
							request.getSession().removeAttribute("errores");
						}
						if (mensaje != null) {
							request.setAttribute("mensaje", mensaje);
							request.getSession().removeAttribute("mensaje");
						}
						LinkedList<Precio> precios = pc.getAll();
						PropiedadController propc = new PropiedadController();
						LinkedList<Propiedad> propiedades = propc.getAll();
						request.setAttribute("precios", precios);
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-precio/crud-precio.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						// FALTA EL HISTORICO DE PRECIOS PARA EL ANUNCIANTE
					}
					break;
				}
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = (String) request.getSession().getAttribute("rol");
		if (!rol.equals("administrador") && !rol.equals("anunciante")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		Precio pre = new Precio();
		PrecioController pc = new PrecioController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
				case "create": {
					int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
					double valor = Double.parseDouble(request.getParameter("valor"));
					Anunciante anun = getAnuncianteFromSessionOrRequest(request, rol);
					Propiedad prop = new Propiedad();
					prop.setAnunciante(anun);
					prop.setNroPropiedad(nro_propiedad);
					if (pc.precioAsignadoHoy(prop)) {
						mensaje = "Esta propiedad ya tiene un precio asignado el dia de hoy.";
					} else {
						pre.setPropiedad(prop);
						pre.setValor(valor);
						pc.add(pre);
						mensaje = "Precio registrado con éxito.";
					}
					if (rol.equals("anunciante")) {
						request.setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
					} else {
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("precioservlet?action=retrieve");
					}
					break;
				}
				case "update": {
					if (rol.equals("administrador")) {
						int id_anunciante = Integer.parseInt(request.getParameter("id-anunciante"));
						int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
						LocalDate fecha_desde = LocalDate.parse(request.getParameter("fecha-desde"));
						Double valor = Double.parseDouble(request.getParameter("valor"));
						pre.setPropiedad(new Propiedad());
						pre.getPropiedad().setAnunciante(new Anunciante());
						pre.getPropiedad().getAnunciante().setIdAnunciante(id_anunciante);
						pre.getPropiedad().setNroPropiedad(nro_propiedad);
						pre.setFechaDesde(fecha_desde);
						pre.setValor(valor);
						pc.update(pre);
						mensaje = "Precio actualizado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("precioservlet?action=retrieve");
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
				case "delete": {
					if (rol.equals("administrador")) {
						int id_anunciante = Integer.parseInt(request.getParameter("id-anunciante"));
						int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
						pre.setPropiedad(new Propiedad());
						pre.getPropiedad().setAnunciante(new Anunciante());
						pre.getPropiedad().getAnunciante().setIdAnunciante(id_anunciante);
						pre.getPropiedad().setNroPropiedad(nro_propiedad);
						pc.delete(pre);
						mensaje = "Precio eliminado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("precioservlet?action=retrieve");
			        } else {
			        	request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
			        }
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