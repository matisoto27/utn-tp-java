package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entities.Alquiler;
import entities.Anunciante;
import entities.Cliente;
import entities.Propiedad;
import logic.AlquilerController;
import logic.AnuncianteController;
import logic.ClienteController;
import logic.PropiedadController;

@WebServlet({ "/AlquilerServlet", "/alquilerservlet" })
public class AlquilerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AlquilerServlet() {
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
		Alquiler alq = new Alquiler();
		AlquilerController ac = new AlquilerController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
			
			
				case "alquileresencursobyanunciante": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
					String filtro_str = request.getParameter("filtro");
					int filtro = 0;
					try {
					    filtro = Integer.parseInt(filtro_str);
					} catch (NumberFormatException e) {
					    filtro = 0;
					}
					LinkedList<Alquiler> alquileres = new LinkedList<>();
					switch (filtro) {
						case 0: {
							alquileres = ac.getAlquileresPendientesEnCursoByAnunciante(anun);
							break;
						}
						case 1: {
							alquileres = ac.getAlquileresPendientesByAnunciante(anun);
							break;
						}
						case 2: {
							alquileres = ac.getAlquileresEnCursoByAnunciante(anun);
							break;
						}
					}
					request.setAttribute("alquileres", alquileres);
					request.setAttribute("filtro", filtro);
					request.getRequestDispatcher("WEB-INF/ui-alquiler/lista-alquileres-en-curso.jsp").forward(request, response);
					break;
				}
				
				
				case "alquilerescanceladosbyanunciante": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
					LinkedList<Alquiler> alquileres = ac.getAlquileresCanceladosByAnunciante(anun);
					request.setAttribute("alquileres", alquileres);
					request.getRequestDispatcher("WEB-INF/ui-alquiler/lista-alquileres-cancelados.jsp").forward(request, response);
					break;
				}
				
				case "alquileresfinalizadosbyanunciante": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
					LinkedList<Alquiler> alquileres = ac.getAlquileresFinalizadosByAnunciante(anun);
					request.setAttribute("alquileres", alquileres);
					request.getRequestDispatcher("WEB-INF/ui-alquiler/lista-alquileres-finalizados.jsp").forward(request, response);
					break;
				}
				
				
				case "retrieve": {
					if (!rol.equals("administrador")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					alq = (Alquiler) request.getSession().getAttribute("respuestas_correctas");
					mensaje = (String) request.getSession().getAttribute("mensaje");
					if (alq != null) {
						request.setAttribute("respuestas_correctas", alq);
						request.getSession().removeAttribute("respuestas_correctas");
					}
					if (mensaje != null) {
						request.setAttribute("mensaje", mensaje);
						request.getSession().removeAttribute("mensaje");
					}
					LinkedList<Alquiler> alquileres = ac.getAll();
					LinkedList<Cliente> clientes = new ClienteController().getAll();
					LinkedList<Propiedad> propiedades = new PropiedadController().getAll();
					request.setAttribute("alquileres", alquileres);
					request.setAttribute("clientes", clientes);
					request.setAttribute("propiedades", propiedades);
					request.getRequestDispatcher("WEB-INF/ui-alquiler/crud-alquiler.jsp").forward(request, response);
					break;
				}
				
				
				case "update": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
					LinkedList<Alquiler> alquileres = ac.getAlquileresPendientesByAnunciante(anun);
					request.setAttribute("alquileres", alquileres);
					request.getRequestDispatcher("WEB-INF/ui-alquiler/update-alquiler.jsp").forward(request, response);
					break;
				}
				
				
				case "alquileractual": {
					if (!rol.equals("cliente")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					Cliente cli = (Cliente) request.getSession().getAttribute("usuario");
					alq.setCliente(cli);
					alq = ac.getUltimoAlquilerCliente(alq);
					request.setAttribute("alquiler", alq);
					request.getRequestDispatcher("WEB-INF/ui-alquiler/alquiler-actual.jsp").forward(request, response);
					break;
				}
				
				default: {
					request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
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
		if (!rol.equals("administrador") && !rol.equals("anunciante") && !rol.equals("cliente")) {
			request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
			return;
		}
		Alquiler alq = new Alquiler();
		AlquilerController ac = new AlquilerController();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
			
			
				case "create": {
					if (!rol.equals("administrador") && !rol.equals("cliente")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					
					
					Anunciante anun = new Anunciante();
					Cliente cli = new Cliente();
					ClienteController cc = new ClienteController();
					String id_anunciante_str = request.getParameter("id-anunciante");
					String nro_propiedad_str = request.getParameter("nro-propiedad");
					
					
			        if (rol.equals("administrador")) {
			        	String dni_cliente = request.getParameter("dni-cliente");
			        	if (dni_cliente == null || dni_cliente.isEmpty() || !dni_cliente.matches("\\d+") || dni_cliente.length() != 8) {
			        		mensaje = "El dni del cliente debe ser un número de 8 dígitos.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
			        	}
			        	
			        	
			        	cli.setDni(dni_cliente);
			        	cli = cc.getByDni(cli);
			        	if (cli == null) {
							mensaje = "No se encontró un cliente con el dni proporcionado.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
			        } else {
			        	cli = (Cliente) request.getSession().getAttribute("usuario");
			        }
			        
			        
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
		        	anun = new AnuncianteController().getById(anun);
		        	if (anun == null) {
						mensaje = "No se encontró un anunciante con el ID proporcionado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
		        	
		        	
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
					
					
					if (cc.tieneAlquiler(cli)) {
						mensaje = "Ya tienes un alquiler pendiente o en curso.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq.setCliente(cli);
					alq.setPropiedad(prop);
					
					
					if (rol.equals("administrador")) {
						String fecha_solicitado_str = request.getParameter("fecha-solicitado");
						String estado = request.getParameter("estado");
						String fecha_inicio_str = request.getParameter("fecha-inicio-contrato");
						String fecha_fin_str = request.getParameter("fecha-fin-contrato");
						String fecha_renuncia_str = request.getParameter("fecha-renuncia");
						String puntuacion_str = request.getParameter("puntuacion");
						String comentario_str = request.getParameter("comentario");
						String comentario = comentario_str == null || comentario_str.isEmpty() ? null : comentario_str;
						
						
						if (fecha_solicitado_str == null || fecha_solicitado_str.isEmpty()) {
					        mensaje = "La fecha de solicitud no puede estar vacía.";
					        redirigirConMensaje(request, response, rol, mensaje);
					        return;
					    }
						
						
						LocalDate fecha_solicitado = null;
						try {
							fecha_solicitado = LocalDate.parse(fecha_solicitado_str, dtf);
						} catch (DateTimeParseException e) {
							mensaje = "La fecha de solicitud no tiene un formato válido.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
						if (estado == null || estado.isEmpty()) {
					        mensaje = "El estado no puede estar vacío.";
					        redirigirConMensaje(request, response, rol, mensaje);
					        return;
					    }
						
						
						if (!estado.equals("Pendiente") && !estado.equals("En curso") && !estado.equals("Cancelado") && !estado.equals("Finalizado")) {
							mensaje = "El estado no es válido.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
						LocalDate fecha_inicio_contrato = null;
						try {
							if (fecha_inicio_str != null && !fecha_inicio_str.isEmpty()) {
								fecha_inicio_contrato = LocalDate.parse(fecha_inicio_str, dtf);
							}
						} catch (DateTimeParseException e) {
							mensaje = "La fecha de inicio del contrato no tiene un formato válido.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
						if (fecha_inicio_contrato != null && fecha_inicio_contrato.isBefore(fecha_solicitado)) {
						    mensaje = "La fecha de inicio del contrato no puede ser anterior a la fecha de solicitud.";
						    redirigirConMensaje(request, response, rol, mensaje);
						    return;
						}
						
						
						LocalDate fecha_fin_contrato = null;
						try {
							if (fecha_fin_str != null && !fecha_fin_str.isEmpty()) {
								fecha_fin_contrato = LocalDate.parse(fecha_fin_str, dtf);
							}
						} catch (DateTimeParseException e) {
							mensaje = "La fecha de fin del contrato no tiene un formato válido.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
						if (fecha_inicio_contrato != null && fecha_fin_contrato != null && !fecha_inicio_contrato.isBefore(fecha_fin_contrato)) {
							mensaje = "La fecha de inicio del contrato debe ser anterior a la fecha de fin.";
						    redirigirConMensaje(request, response, rol, mensaje);
						    return;
						}
						
						
						LocalDate fecha_renuncia = null;
						try {
							if (fecha_renuncia_str != null && !fecha_renuncia_str.isEmpty()) {
								fecha_renuncia = LocalDate.parse(fecha_renuncia_str, dtf);
							}
						} catch (DateTimeParseException e) {
							mensaje = "La fecha de renuncia no tiene un formato válido.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
						Integer puntuacion = null;
						try {
							if (puntuacion_str != null && !puntuacion_str.isEmpty()) {
						        puntuacion = Integer.parseInt(puntuacion_str);
						        if (puntuacion < 1 || puntuacion > 10) {
						        	mensaje = "La puntuación debe estar entre 1 y 10.";
								    redirigirConMensaje(request, response, rol, mensaje);
								    return;
						        }
						    }
						} catch (NumberFormatException e) {
							mensaje = "Se ha producido un error inesperado.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
						alq.setFechaSolicitado(fecha_solicitado);
						alq.setEstado(estado);
						alq.setFechaInicioContrato(fecha_inicio_contrato);
						alq.setFechaFinContrato(fecha_fin_contrato);
						alq.setFechaRenuncia(fecha_renuncia);
						alq.setPuntuacion(puntuacion);
						alq.setComentario(comentario);
					}
					ac.add(alq);
					
					
					if (rol.equals("administrador")) {
						mensaje = "Alquiler creado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("alquilerservlet?action=retrieve");
					} else {
						mensaje = "Propiedad reservada con éxito.";
						request.setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-cliente.jsp").forward(request, response);
					}
					break;
				}
				
				
				case "update": {
					if (!rol.equals("administrador")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					
					
					Anunciante anun = new Anunciante();
					Cliente cli = new Cliente();
					String id_alquiler_str = request.getParameter("id-alquiler");
					String dni_cliente = request.getParameter("dni-cliente");
					String id_anunciante_str = request.getParameter("id-anunciante");
					String nro_propiedad_str = request.getParameter("nro-propiedad");
					String fecha_solicitado_str = request.getParameter("fecha-solicitado");
					String estado = request.getParameter("estado");
					String fecha_inicio_str = request.getParameter("fecha-inicio-contrato");
					String fecha_fin_str = request.getParameter("fecha-fin-contrato");
					String fecha_renuncia_str = request.getParameter("fecha-renuncia");
					String puntuacion_str = request.getParameter("puntuacion");
					String comentario_str = request.getParameter("comentario");
					String comentario = comentario_str == null || comentario_str.isEmpty() ? null : comentario_str;
					
					
					if (id_alquiler_str == null || id_alquiler_str.isEmpty()) {
						mensaje = "El ID del alquiler no puede estar vacío.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
					
					
					int id_alquiler = 0;
					try {
						id_alquiler = Integer.parseInt(id_alquiler_str);
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq.setIdAlquiler(id_alquiler);
					alq = ac.getById(alq);
					if (alq == null) {
						mensaje = "No se encontró un alquiler con el ID proporcionado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (dni_cliente == null || dni_cliente.isEmpty() || !dni_cliente.matches("\\d+") || dni_cliente.length() != 8) {
						mensaje = "El dni del cliente debe ser un número de 8 dígitos.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
		        	}
					
					
					cli.setDni(dni_cliente);
		        	cli = new ClienteController().getByDni(cli);
		        	if (cli == null) {
						mensaje = "No se encontró un cliente con el dni proporcionado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
		        	
		        	
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
					anun = new AnuncianteController().getById(anun);
					if (anun == null) {
						mensaje = "No se encontró un anunciante con el ID proporcionado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
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
					
					
					if (fecha_solicitado_str == null || fecha_solicitado_str.isEmpty()) {
				        mensaje = "La fecha de solicitud no puede estar vacía.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
					
					
					LocalDate fecha_solicitado = null;
					try {
						fecha_solicitado = LocalDate.parse(fecha_solicitado_str, dtf);
					} catch (DateTimeParseException e) {
						mensaje = "La fecha de solicitud no tiene un formato válido.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (estado == null || estado.isEmpty()) {
				        mensaje = "El estado no puede estar vacío.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
					
					
					if (!estado.equals("Pendiente") && !estado.equals("En curso") && !estado.equals("Cancelado") && !estado.equals("Finalizado")) {
						mensaje = "El estado no es válido.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					LocalDate fecha_inicio_contrato = null;
					try {
						if (fecha_inicio_str != null && !fecha_inicio_str.isEmpty()) {
							fecha_inicio_contrato = LocalDate.parse(fecha_inicio_str, dtf);
						}
					} catch (DateTimeParseException e) {
						mensaje = "La fecha de inicio del contrato no tiene un formato válido.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (fecha_inicio_contrato != null && fecha_inicio_contrato.isBefore(fecha_solicitado)) {
					    mensaje = "La fecha de inicio del contrato no puede ser anterior a la fecha de solicitud.";
					    redirigirConMensaje(request, response, rol, mensaje);
					    return;
					}
					
					
					LocalDate fecha_fin_contrato = null;
					try {
						if (fecha_fin_str != null && !fecha_fin_str.isEmpty()) {
							fecha_fin_contrato = LocalDate.parse(fecha_fin_str, dtf);
						}
					} catch (DateTimeParseException e) {
						mensaje = "La fecha de fin del contrato no tiene un formato válido.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (fecha_inicio_contrato != null && fecha_fin_contrato != null && !fecha_inicio_contrato.isBefore(fecha_fin_contrato)) {
						mensaje = "La fecha de inicio del contrato debe ser anterior a la fecha de fin.";
					    redirigirConMensaje(request, response, rol, mensaje);
					    return;
					}
					
					
					LocalDate fecha_renuncia = null;
					try {
						if (fecha_renuncia_str != null && !fecha_renuncia_str.isEmpty()) {
							fecha_renuncia = LocalDate.parse(fecha_renuncia_str, dtf);
						}
					} catch (DateTimeParseException e) {
						mensaje = "La fecha de renuncia no tiene un formato válido.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					Integer puntuacion = null;
					try {
						if (puntuacion_str != null && !puntuacion_str.isEmpty()) {
					        puntuacion = Integer.parseInt(puntuacion_str);
					        if (puntuacion < 1 || puntuacion > 10) {
					        	mensaje = "La puntuación debe estar entre 1 y 10.";
							    redirigirConMensaje(request, response, rol, mensaje);
							    return;
					        }
					    }
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq = new Alquiler(id_alquiler, cli, prop, fecha_solicitado, estado, fecha_inicio_contrato, fecha_fin_contrato, fecha_renuncia, puntuacion, comentario);
					ac.update(alq);
					mensaje = "Alquiler actualizado con éxito.";
					request.getSession().setAttribute("mensaje", mensaje);
					response.sendRedirect("alquilerservlet?action=retrieve");
					break;
				}
				
				
				case "delete": {
					if (rol.equals("administrador") || rol.equals("anunciante")) {
						String id_alquiler_str = request.getParameter("id-alquiler");
						if (id_alquiler_str == null || id_alquiler_str.isEmpty()) {
							mensaje = "El ID del alquiler no puede estar vacío.";
					        redirigirConMensaje(request, response, rol, mensaje);
					        return;
					    }
						
						
						int id_alquiler = 0;
						try {
							id_alquiler = Integer.parseInt(id_alquiler_str);
						} catch (NumberFormatException e) {
							mensaje = "Se ha producido un error inesperado.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
						alq.setIdAlquiler(id_alquiler);
						alq = ac.getById(alq);
						if (alq == null) {
							mensaje = "No se encontró un alquiler con el ID proporcionado.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
						if (alq.getEstado() != null && alq.getEstado().equals("En curso")) {
							mensaje = "No puedes eliminar este alquiler debido a que está en curso. Por favor, cancele el alquiler y vuelva a intentarlo.";
						} else {
							mensaje = "Alquiler eliminado con éxito.";
							ac.delete(alq);
						}
						redirigirConMensaje(request, response, rol, mensaje);
						
						
					} else {
						Anunciante anun = new Anunciante();
						Cliente cli = (Cliente) request.getSession().getAttribute("usuario");
						String id_anunciante_str = request.getParameter("id-anunciante");
						String nro_propiedad_str = request.getParameter("nro-propiedad");
						
						
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
						anun = new AnuncianteController().getById(anun);
						if (anun == null) {
							mensaje = "No se encontró un anunciante con el ID proporcionado.";
							redirigirConMensaje(request, response, rol, mensaje);
							return;
						}
						
						
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
						
						
						alq.setCliente(cli);
						alq.setPropiedad(prop);
						ac.delete(alq);
						mensaje = "Reserva cancelada.";
						redirigirConMensaje(request, response, rol, mensaje);
					}
					break;
				}
				
				
				case "iniciarcontrato": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					
					
					String id_alquiler_str = request.getParameter("id-alquiler");
					String fecha_inicio_str = request.getParameter("fecha-inicio-contrato");
					String fecha_fin_str = request.getParameter("fecha-fin-contrato");
					
					
					if (id_alquiler_str == null || id_alquiler_str.isEmpty()) {
						mensaje = "El ID del alquiler no puede estar vacío.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
					
					
					int id_alquiler = 0;
					try {
						id_alquiler = Integer.parseInt(id_alquiler_str);
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq.setIdAlquiler(id_alquiler);
					alq = ac.getById(alq);
					if (alq == null) {
						mensaje = "No se encontró un alquiler con el ID proporcionado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					if (fecha_inicio_str == null || fecha_inicio_str.isEmpty() || fecha_fin_str == null || fecha_fin_str.isEmpty()) {
						mensaje = "Por favor, ingrese una fecha de inicio y una fecha de fin.";
						redirigirConMensaje(request, response, rol, mensaje);
				        return;
					}
					
					
					LocalDate fecha_inicio_contrato = null;
					try {
						fecha_inicio_contrato = LocalDate.parse(fecha_inicio_str, dtf);
					} catch (DateTimeParseException e) {
						mensaje = "La fecha de inicio del contrato no tiene un formato válido.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (fecha_inicio_contrato.isBefore(alq.getFechaSolicitado())) {
					    mensaje = "La fecha de inicio del contrato no puede ser anterior a la fecha de solicitud.";
					    redirigirConMensaje(request, response, rol, mensaje);
					    return;
					}
					
					LocalDate fecha_fin_contrato = null;
					try {
						fecha_fin_contrato = LocalDate.parse(fecha_fin_str, dtf);
					} catch (DateTimeParseException e) {
						mensaje = "La fecha de fin del contrato no tiene un formato válido.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (fecha_inicio_contrato.isBefore(LocalDate.now()) || fecha_fin_contrato.isBefore(LocalDate.now())) {
            			mensaje = "Las fechas no pueden ser anteriores a la fecha de hoy.";
            			redirigirConMensaje(request, response, rol, mensaje);
				        return;
            		}
					
					
            		if (!fecha_inicio_contrato.isBefore(fecha_fin_contrato)) {
            			mensaje = "La fecha de inicio del contrato debe ser anterior a la fecha de fin.";
            			redirigirConMensaje(request, response, rol, mensaje);
				        return;
            		}
            		
            		
        			alq.setEstado("En curso");
					alq.setFechaInicioContrato(fecha_inicio_contrato);
					alq.setFechaFinContrato(fecha_fin_contrato);
					alq.setPuntuacion(null);
					ac.update(alq);
					mensaje = "Contrato de alquiler registrado con éxito.";
					redirigirConMensaje(request, response, rol, mensaje);
					break;
				}
				
				
				case "cancelarcontrato": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					
					
					String id_alquiler_str = request.getParameter("id-alquiler");
					if (id_alquiler_str == null || id_alquiler_str.isEmpty()) {
						mensaje = "El ID del alquiler no puede estar vacío.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
					
					
					int id_alquiler = 0;
					try {
						id_alquiler = Integer.parseInt(id_alquiler_str);
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq.setIdAlquiler(id_alquiler);
					alq = ac.getById(alq);
					if (alq == null) {
						mensaje = "No se encontró un alquiler con el ID proporcionado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (!alq.getEstado().equals("En curso")) {
						mensaje = "No se puede cancelar el alquiler, debido a que no se encuentra en curso.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq.setEstado("Cancelado");
					alq.setFechaRenuncia(LocalDate.now());
					alq.setPuntuacion(null);
					ac.update(alq);
					mensaje = "Contrato de alquiler cancelado.";
					redirigirConMensaje(request, response, rol, mensaje);
					break;
				}
				
				
				case "puntuacioncomentario": {
					if (!rol.equals("cliente")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					
					
					String id_alquiler_str = request.getParameter("id-alquiler");
					String puntuacion_str = request.getParameter("puntuacion");
					String comentario_str = request.getParameter("comentario");
					String comentario = comentario_str == null || comentario_str.isEmpty() ? null : comentario_str;
					
					
					if (id_alquiler_str == null || id_alquiler_str.isEmpty()) {
						mensaje = "El ID del alquiler no puede estar vacío.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
					
					
					int id_alquiler = 0;
					try {
						id_alquiler = Integer.parseInt(id_alquiler_str);
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq.setIdAlquiler(id_alquiler);
					alq = ac.getById(alq);
					if (alq == null) {
						mensaje = "No se encontró un alquiler con el ID proporcionado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (alq.getPuntuacion() != 0) {
						mensaje = "El alquiler ya ha sido puntuado previamente.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					Integer puntuacion = null;
					try {
						if (puntuacion_str != null && !puntuacion_str.isEmpty()) {
					        puntuacion = Integer.parseInt(puntuacion_str);
					        if (puntuacion < 1 || puntuacion > 10) {
					        	mensaje = "La puntuación debe estar entre 1 y 10.";
							    redirigirConMensaje(request, response, rol, mensaje);
							    return;
					        }
					    }
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq.setPuntuacion(puntuacion);
					alq.setComentario(comentario);
					ac.update(alq);
					mensaje = "Tu puntuación y comentarios han sido registrados. ¡Gracias por tu opinión!";
					redirigirConMensaje(request, response, rol, mensaje);
					break;
				}
				
				
				case "finalizarcontrato": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					
					
					String id_alquiler_str = request.getParameter("id-alquiler");
					if (id_alquiler_str == null || id_alquiler_str.isEmpty()) {
						mensaje = "El ID del alquiler no puede estar vacío.";
				        redirigirConMensaje(request, response, rol, mensaje);
				        return;
				    }
					
					
					int id_alquiler = 0;
					try {
						id_alquiler = Integer.parseInt(id_alquiler_str);
					} catch (NumberFormatException e) {
						mensaje = "Se ha producido un error inesperado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					alq.setIdAlquiler(id_alquiler);
					alq = ac.getById(alq);
					if (alq == null) {
						mensaje = "No se encontró un alquiler con el ID proporcionado.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (!alq.getEstado().equals("En curso")) {
						mensaje = "No se puede finalizar el alquiler, debido a que no se encuentra en curso.";
						redirigirConMensaje(request, response, rol, mensaje);
						return;
					}
					
					
					if (alq.getPuntuacion() == 0) {
						alq.setPuntuacion(null);
					}
					
					
					alq.setEstado("Finalizado");
					ac.update(alq);
					mensaje = "Se ha finalizado el contrato de alquiler.";
					redirigirConMensaje(request, response, rol, mensaje);
					break;
				}
			}
		}
	}
	
	private void redirigirConMensaje(HttpServletRequest request, HttpServletResponse response, String rol, String mensaje) throws ServletException, IOException {
		if (rol.equals("administrador")) {
	        request.getSession().setAttribute("mensaje", mensaje);
	        response.sendRedirect("alquilerservlet?action=retrieve");
	    } else if (rol.equals("anunciante")) {
	    	request.setAttribute("mensaje", mensaje);
			request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
	    } else {
	    	request.setAttribute("mensaje", mensaje);
	        request.getRequestDispatcher("WEB-INF/menu-cliente.jsp").forward(request, response);
	    }
	}
	
}