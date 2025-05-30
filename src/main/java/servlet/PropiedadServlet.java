package servlet;

import java.io.IOException;
import java.util.LinkedList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entities.Anunciante;
import entities.Propiedad;
import logic.AnuncianteController;
import logic.PropiedadController;

@WebServlet({ "/PropiedadServlet", "/propiedadservlet" })
public class PropiedadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PropiedadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = (String) request.getSession().getAttribute("rol");
		if (rol == null || rol.isEmpty()) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		if (!rol.equals("administrador") && !rol.equals("anunciante") && !rol.equals("cliente")) {
			request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
			return;
		}
		PropiedadController pc = new PropiedadController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
			
			
				case "create": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						return;
					}
					request.getRequestDispatcher("WEB-INF/ui-propiedad/create-propiedad.html").forward(request, response);
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
					} else {
						LinkedList<Propiedad> propiedades = pc.getPropiedadesDisponibles();
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-propiedad/lista-propiedades-disponibles.jsp").forward(request, response);
					}
					break;
				}
				
				
				case "update": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						return;
					}
					Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
					int nro_propiedad = Integer.parseInt(request.getParameter("nro"));
					Propiedad prop = new Propiedad(anun, nro_propiedad);
					prop = pc.getByIdAnunNroProp(prop);
					if (prop == null) {
						mensaje = "No se encontró una propiedad con los datos proporcionados.";
						request.setAttribute("mensaje", mensaje);
				        request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
						return;
					}
					request.setAttribute("propiedad", prop);
					request.getRequestDispatcher("WEB-INF/ui-propiedad/update-propiedad.jsp").forward(request, response);
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
		Propiedad prop = new Propiedad();
		PropiedadController pc = new PropiedadController();
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
			        String direccion = request.getParameter("direccion");
			        String piso_str = request.getParameter("piso");
			        String depto_str = request.getParameter("depto");
			        String depto = depto_str == null || depto_str.isEmpty() ? null : depto_str;
			        
			        
			        if (direccion == null || direccion.isEmpty()) {
				        mensaje = "La dirección no puede estar vacía.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
			        
			        
			        if (!direccion.matches("[\\p{L}0-9 .,-]*")) {
			            mensaje = "Por favor, ingrese una dirección válida.";
			            redirigirConMensaje(request, response, rol, mensaje);
			            return;
			        }
			        
			        
			        Integer piso = null;
			        try {
			        	if (piso_str != null && !piso_str.isEmpty()) {
					        piso = Integer.parseInt(piso_str);
					        if (piso < 0) {
					        	mensaje = "El piso debe ser un número igual o mayor que 0.";
							    redirigirConMensaje(request, response, rol, mensaje);
							    return;
					        }
					    }
			        } catch (NumberFormatException e) {
			        	mensaje = "Se ha producido un error inesperado.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
			        }
			        
			        
					if (depto != null && !depto.matches("[a-zA-Z0-9]*")) {
			        	mensaje = "El departamento solo puede contener letras y números.";
			        	redirigirConMensaje(request, response, rol, mensaje);
		                return;
			        }
					
					
			        prop = new Propiedad(anun, direccion, piso, depto);
			        try {
			        	pc.add(prop);
			        } catch (RuntimeException e) {
			        	mensaje = e.getMessage();
			        	redirigirConMensaje(request, response, rol, mensaje);
			        	return;
			        }
			        mensaje = "Propiedad registrada con éxito.";
			        redirigirConMensaje(request, response, rol, mensaje);
			        break;
				}
				
				
				case "update": {
					String nro_propiedad_str = request.getParameter("nro-propiedad");
					String direccion = request.getParameter("direccion");
			        String piso_str = request.getParameter("piso");
			        String depto_str = request.getParameter("depto");
			        String depto = depto_str == null || depto_str.isEmpty() ? null : depto_str;
			        
			        
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
					
					
					prop = new Propiedad(anun, nro_propiedad);
					prop = pc.getByIdAnunNroProp(prop);
					if (prop == null) {
						mensaje = "No se encontró una propiedad con los datos proporcionados.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (direccion == null || direccion.isEmpty()) {
				        mensaje = "La dirección no puede estar vacía.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
			        
			        
			        if (!direccion.matches("[\\p{L}0-9 .,-]*")) {
			            mensaje = "Por favor, ingrese una dirección válida.";
			            redirigirConMensaje(request, response, rol, mensaje);
			            return;
			        }
			        
			        
			        Integer piso = null;
			        try {
			        	if (piso_str != null && !piso_str.isEmpty()) {
					        piso = Integer.parseInt(piso_str);
					        if (piso < 0) {
					        	mensaje = "El piso debe ser un número igual o mayor que 0.";
							    redirigirConMensaje(request, response, rol, mensaje);
							    return;
					        }
					    }
			        } catch (NumberFormatException e) {
			        	mensaje = "Se ha producido un error inesperado.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
			        }
					
					
					if (depto != null && !depto.matches("[a-zA-Z0-9]*")) {
			        	mensaje = "El departamento solo puede contener letras y números.";
			        	redirigirConMensaje(request, response, rol, mensaje);
		                return;
			        }
					
					
					prop = new Propiedad(anun, nro_propiedad, direccion, piso, depto);
					pc.update(prop);
					mensaje = "Propiedad actualizada con éxito.";
					redirigirConMensaje(request, response, rol, mensaje);
					break;
				}
				
				
				case "delete": {
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
					
					
					prop = new Propiedad(anun, nro_propiedad);
					prop = pc.getByIdAnunNroProp(prop);
					if (prop == null) {
						mensaje = "No se encontró una propiedad con los datos proporcionados.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (prop.getEstado() != null && (prop.getEstado().equals("Pendiente") || prop.getEstado().equals("En curso"))) {
						mensaje = "No puedes eliminar esta propiedad debido a que tiene un alquiler pendiente o en curso. Por favor, cancele el alquiler y vuelva a intentarlo.";
					} else {
						mensaje = "Propiedad eliminada con éxito.";
						pc.delete(prop);
					}
					redirigirConMensaje(request, response, rol, mensaje);
					break;
				}
			}
		}
	}
	
	private void redirigirConMensaje(HttpServletRequest request, HttpServletResponse response, String rol, String mensaje) throws ServletException, IOException {
		if (rol.equals("administrador")) {
			request.getSession().setAttribute("mensaje", mensaje);
	        response.sendRedirect("propiedadservlet?action=retrieve");
	    } else {
	    	request.setAttribute("mensaje", mensaje);
	        request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
	    }
	}
	
}