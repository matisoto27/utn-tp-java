package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
		if (rol == null || rol.isEmpty()) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		if (!rol.equals("administrador") && !rol.equals("anunciante")) {
			request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
			return;
		}
		PrecioController pc = new PrecioController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
			
			
				case "create": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						return;
					}
					
					
					Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
					LinkedList<Propiedad> propiedades = new PropiedadController().getPropiedadesByAnunciante(anun);
					request.setAttribute("propiedades", propiedades);
					request.getRequestDispatcher("WEB-INF/ui-precio/create-precio.jsp").forward(request, response);
					break;
				}
				
				
				case "retrieve": {
					if (rol.equals("administrador")) {
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (mensaje != null) {
							request.setAttribute("mensaje", mensaje);
							request.getSession().removeAttribute("mensaje");
						}
						LinkedList<Precio> precios = pc.getAll();
						LinkedList<Propiedad> propiedades = new PropiedadController().getAll();
						request.setAttribute("precios", precios);
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-precio/crud-precio.jsp").forward(request, response);
					} else {
						Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
						LinkedList<Precio> precios = pc.getAllByAnunciante(anun);
						request.setAttribute("precios", precios);
						request.getRequestDispatcher("WEB-INF/ui-precio/lista-precios-anunciante.jsp").forward(request, response);
					}
					break;
				}
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = (String) request.getSession().getAttribute("rol");
		if (rol == null || rol.isEmpty()) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		if (!rol.equals("administrador") && !rol.equals("anunciante")) {
			request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
			return;
		}
		Anunciante anun = new Anunciante();
		AnuncianteController ac = new AnuncianteController();
		Precio pre = new Precio();
		PrecioController pc = new PrecioController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			
			
			if (rol.equals("administrador")) {
	        	String id_anunciante_str = request.getParameter("id-anunciante");
	        	if (id_anunciante_str == null || id_anunciante_str.isEmpty()) {
	        		mensaje = "El ID del anunciante no puede estar vacío.";
					redirigirConMensaje(request, response, rol, mensaje);
					return;
	        	}
	        	
	        	
	        	int id_anunciante = 0;
				try {
					id_anunciante = Integer.parseInt(id_anunciante_str);
				} catch (NumberFormatException e) {
					mensaje = "Se ha producido un error inesperado.";
					redirigirConMensaje(request, response, rol, mensaje);
					return;
				}
				
				
	        	anun.setIdAnunciante(id_anunciante);
	        	anun = ac.getById(anun);
	        	if (anun == null) {
					mensaje = "No se encontró un anunciante con el ID proporcionado.";
					redirigirConMensaje(request, response, rol, mensaje);
					return;
				}
	        } else {
	        	anun = (Anunciante) request.getSession().getAttribute("usuario");
	        }
			
			
			switch (action) {
			
			
				case "create": {
					String nro_propiedad_str = request.getParameter("nro-propiedad");
					String valor_str = request.getParameter("valor");
					
					
					if (nro_propiedad_str == null || nro_propiedad_str.isEmpty()) {
						mensaje = "El número de propiedad no puede estar vacío.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					int nro_propiedad = 0;
					try {
					    nro_propiedad = Integer.parseInt(nro_propiedad_str);
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (valor_str == null || valor_str.isEmpty()) {
						mensaje = "El valor del precio no puede estar vacío.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					double valor = 0;
					try {
						valor = Double.parseDouble(valor_str);
			        } catch (NumberFormatException e) {
			        	mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
			        }
					
					
					Propiedad prop = new Propiedad(anun, nro_propiedad);
					prop = new PropiedadController().getByIdAnunNroProp(prop);
					if (prop == null) {
						mensaje = "No se encontró una propiedad con los datos proporcionados.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (pc.precioAsignadoHoy(prop)) {
						mensaje = "La propiedad ya tiene un precio asignado para el día de hoy.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					pre.setPropiedad(prop);
					pre.setValor(valor);
					pc.add(pre);
					mensaje = "Precio registrado con éxito.";
					redirigirConMensaje(request, response, rol, mensaje);
					break;
				}
				
				
				case "update": {
					if (!rol.equals("administrador")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						return;
					}
					
					
					String nro_propiedad_str = request.getParameter("nro-propiedad");
					String fecha_desde_str = request.getParameter("fecha-desde");
					String valor_str = request.getParameter("valor");
					
					
					if (nro_propiedad_str == null || nro_propiedad_str.isEmpty()) {
						mensaje = "El número de propiedad no puede estar vacío.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					int nro_propiedad = 0;
					try {
					    nro_propiedad = Integer.parseInt(nro_propiedad_str);
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					Propiedad prop = new Propiedad(anun, nro_propiedad);
					prop = new PropiedadController().getByIdAnunNroProp(prop);
					if (prop == null) {
						mensaje = "No se encontró una propiedad con los datos proporcionados.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (fecha_desde_str == null || fecha_desde_str.isEmpty()) {
						mensaje = "La fecha no puede estar vacía.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					LocalDate fecha_desde = null;
					try {
						fecha_desde = LocalDate.parse(fecha_desde_str);
			        } catch (DateTimeParseException e) {
			        	mensaje = "La fecha no tiene un formato válido.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
			        }
					
					
					pre.setPropiedad(prop);
					pre.setFechaDesde(fecha_desde);
					pre = pc.getByPropiedadFechaDesde(pre);
					if (pre == null) {
						mensaje = "No se encontró un precio con los datos proporcionados.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (valor_str == null || valor_str.isEmpty()) {
						mensaje = "El valor del precio no puede estar vacío.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					double valor = 0;
					try {
						valor = Double.parseDouble(valor_str);
			        } catch (NumberFormatException e) {
			        	mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
			        }
					
					
					pre.setValor(valor);
					pc.update(pre);
					mensaje = "Precio actualizado con éxito.";
					request.getSession().setAttribute("mensaje", mensaje);
					response.sendRedirect("precioservlet?action=retrieve");
					break;
				}
				
				
				case "delete": {
					if (!rol.equals("administrador")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						return;
					}
					
					
					String nro_propiedad_str = request.getParameter("nro-propiedad");
					if (nro_propiedad_str == null || nro_propiedad_str.isEmpty()) {
						mensaje = "El número de propiedad no puede estar vacío.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					int nro_propiedad = 0;
					try {
					    nro_propiedad = Integer.parseInt(nro_propiedad_str);
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					Propiedad prop = new Propiedad(anun, nro_propiedad);
					prop = new PropiedadController().getByIdAnunNroProp(prop);
					if (prop == null) {
						mensaje = "No se encontró una propiedad con los datos proporcionados.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					pre.setPropiedad(prop);
					pc.delete(pre);
					mensaje = "Precio eliminado con éxito.";
					request.getSession().setAttribute("mensaje", mensaje);
					response.sendRedirect("precioservlet?action=retrieve");
					break;
				}
			}
		}
	}
	
	private void redirigirConMensaje(HttpServletRequest request, HttpServletResponse response, String rol, String mensaje) throws ServletException, IOException {
		if (rol.equals("administrador")) {
			request.getSession().setAttribute("mensaje", mensaje);
			response.sendRedirect("precioservlet?action=retrieve");
	    } else {
	    	request.setAttribute("mensaje", mensaje);
			request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
	    }
	}
	
}