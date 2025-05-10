package servlet;

import java.io.IOException;
import java.util.LinkedList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entities.Anunciante;
import logic.AlquilerController;
import logic.AnuncianteController;

@WebServlet({ "/AnuncianteServlet", "/anuncianteservlet" })
public class AnuncianteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AnuncianteServlet() {
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
		Anunciante anun = new Anunciante();
		AnuncianteController ac = new AnuncianteController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
			
			
				case "retrieve": {
					if (rol.equals("administrador")) {
						anun = (Anunciante) request.getSession().getAttribute("respuestas_correctas");
						String errores = (String) request.getSession().getAttribute("errores");
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (anun != null) {
							request.setAttribute("respuestas_correctas", anun);
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
						LinkedList<Anunciante> anunciantes = ac.getAll();
						request.setAttribute("anunciantes", anunciantes);
						request.getRequestDispatcher("WEB-INF/ui-anunciante/crud-anunciante.jsp").forward(request, response);
					} else if (rol.equals("cliente")) {
						LinkedList<Anunciante> anunciantes = ac.getAll();
						request.setAttribute("anunciantes", anunciantes);
						request.getRequestDispatcher("WEB-INF/ui-anunciante/lista-anunciantes.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
				
				
				case "update": {
					if (!rol.equals("anunciante")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					anun = (Anunciante) request.getSession().getAttribute("usuario");
					request.setAttribute("anunciante", anun);
					request.getRequestDispatcher("WEB-INF/ui-anunciante/update-anunciante.jsp").forward(request, response);
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
		String action = request.getParameter("action");
		String mensaje = null;
		StringBuilder errores = new StringBuilder();
		if (action != null) {
			switch (action) {
			
			
				case "create": {
					String nombre = request.getParameter("nombre").trim();
					String email = request.getParameter("email").trim();
					String telefono = request.getParameter("telefono").trim();
					String usuario = request.getParameter("usuario").trim();
					String contrasena = request.getParameter("contrasena").trim();
					
					
					if (nombre == null || nombre.isEmpty()) {
				        errores.append("nombre_vacio,");
				    } else if (!nombre.matches("[\\p{L}0-9 .,-]*")) {
				    	errores.append("nombre_invalido,");
				    } else if (!ac.validarNombreUnico(nombre)) {
						errores.append("nombre_unico,");
					} else {
						anun.setNombre(nombre);
					}
					
					
					if (email == null || email.isEmpty()) {
				        errores.append("email_vacio,");
				    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				    	errores.append("email_invalido,");
				    } else {
				    	anun.setEmail(email);
					}
					
					
					if (telefono == null || telefono.isEmpty()) {
				        errores.append("telefono_vacio,");
				    } else if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
				    	errores.append("telefono_invalido,");
					} else {
						anun.setTelefono(telefono);
					}
					
					
					if (usuario == null || usuario.isEmpty()) {
				        errores.append("usuario_vacio,");
				    } else if (usuario.length() < 4 || usuario.length() > 15 || !usuario.matches("^[a-zA-Z0-9_]+$")) {
				    	errores.append("usuario_invalido,");
				    } else if (!ac.validarUsuarioUnico(usuario)) {
				    	errores.append("usuario_unico,");
					} else {
						anun.setUsuario(usuario);
					}
					
					
					if (contrasena == null || contrasena.isEmpty()) {
				        errores.append("contrasena_vacia,");
				    } else if (contrasena.length() < 8) {
				    	errores.append("contrasena_invalida,");
				    } else {
						if (rol.equals("anunciante")) {
							String contrasena2 = request.getParameter("contrasena2").trim();
							if (contrasena2 == null || contrasena2.isEmpty() || !contrasena.equals(contrasena2)) {
								errores.append("contrasenas_no_coinciden,");
							} else {
								anun.setContrasena(contrasena);
							}
						} else {
							anun.setContrasena(contrasena);
						}
					}
					
					
					if (errores.length() > 0) {
						errores.deleteCharAt(errores.length() - 1);
						if (rol.equals("administrador")) {
							request.getSession().setAttribute("respuestas_correctas", anun);
							request.getSession().setAttribute("errores", errores.toString());
							response.sendRedirect("anuncianteservlet?action=retrieve");
						} else {
							request.setAttribute("respuestas_correctas", anun);
							request.setAttribute("errores", errores.toString());
							request.getRequestDispatcher("signup-anunciante.jsp").forward(request, response);
						}
						return;
					}
					
					
					ac.add(anun);
					if (rol.equals("administrador")) {
						mensaje = "Anunciante registrado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("anuncianteservlet?action=retrieve");
					} else {
						mensaje = "Te has registrado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("http://localhost:8080/utn-tp-java/");
					}
					break;
				}
				
				
				case "update": {
					int id_anunciante = 0;
					if (rol.equals("administrador")) {
						String id_anunciante_str = request.getParameter("id-anunciante");
						if (id_anunciante_str == null || id_anunciante_str.isEmpty()) {
							errores.append("anunciante_vacio,");
						} else if (!id_anunciante_str.matches("\\d+")) {
							errores.append("anunciante_invalido,");
						} else {
							id_anunciante = Integer.parseInt(id_anunciante_str);
							anun.setIdAnunciante(id_anunciante);
							if (ac.getById(anun) == null) {
								errores.append("anunciante_inexistente,");
							}
						}
					} else {
						id_anunciante = ((Anunciante) request.getSession().getAttribute("usuario")).getIdAnunciante();
					}
					
					
					Anunciante aux = new Anunciante();
					aux.setIdAnunciante(id_anunciante);
					aux = ac.getById(aux);
					
					
					String nombre = request.getParameter("nombre").trim();
					String email = request.getParameter("email").trim();
					String telefono = request.getParameter("telefono").trim();
					String usuario = request.getParameter("usuario").trim();
					String contrasena = request.getParameter("contrasena").trim();
					
					
					if (nombre == null || nombre.isEmpty()) {
				        errores.append("nombre_vacio,");
				    } else if (!nombre.matches("[\\p{L}0-9 .,-]*")) {
				    	errores.append("nombre_invalido,");
				    } else if (aux != null && !nombre.equals(aux.getNombre()) && !ac.validarNombreUnico(nombre)) {
						errores.append("nombre_unico,");
					} else {
						anun.setNombre(nombre);
					}
					
					
					if (email == null || email.isEmpty()) {
				        errores.append("email_vacio,");
				    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				    	errores.append("email_invalido,");
				    } else {
				    	anun.setEmail(email);
					}
					
					
					if (telefono == null || telefono.isEmpty()) {
				        errores.append("telefono_vacio,");
				    } else if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
				    	errores.append("telefono_invalido,");
					} else {
						anun.setTelefono(telefono);
					}
					
					
					if (usuario == null || usuario.isEmpty()) {
				        errores.append("usuario_vacio,");
				    } else if (usuario.length() < 4 || usuario.length() > 15 || !usuario.matches("^[a-zA-Z0-9_]+$")) {
				    	errores.append("usuario_invalido,");
				    } else if (aux != null && !usuario.equals(aux.getUsuario()) && !ac.validarUsuarioUnico(usuario)) {
						errores.append("usuario_unico,");
					} else {
						anun.setUsuario(usuario);
					}
					
					
					if (contrasena == null || contrasena.isEmpty()) {
				        errores.append("contrasena_vacia,");
				    } else if (contrasena.length() < 8) {
				    	errores.append("contrasena_invalida,");
				    } else {
						if (rol.equals("anunciante")) {
							String contrasena2 = request.getParameter("contrasena2").trim();
							if (contrasena2 == null || contrasena2.isEmpty() || !contrasena.equals(contrasena2)) {
								errores.append("contrasenas_no_coinciden,");
							} else {
								anun.setContrasena(contrasena);
							}
						} else {
							anun.setContrasena(contrasena);
						}
					}
					
					
					if (errores.length() > 0) {
						errores.deleteCharAt(errores.length() - 1);
						if (rol.equals("administrador")) {
							request.getSession().setAttribute("respuestas_correctas", anun);
							request.getSession().setAttribute("errores", errores.toString());
							response.sendRedirect("anuncianteservlet?action=retrieve");
						} else {
							request.setAttribute("respuestas_correctas", anun);
							request.setAttribute("errores", errores.toString());
							request.getRequestDispatcher("WEB-INF/ui-anunciante/update-anunciante.jsp").forward(request, response);
						}
						return;
					}
					
					
					ac.update(anun);
					if (rol.equals("administrador")) {
						mensaje = "Anunciante actualizado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("anuncianteservlet?action=retrieve");
					} else {
						request.getSession().setAttribute("usuario", anun);
						mensaje = "Datos actualizados con éxito.";
						request.setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
					}
					break;
				}
				
				
				case "delete": {
					AlquilerController alqc = new AlquilerController();
					int id_anunciante = 0;
					if (rol.equals("administrador")) {
						String id_anunciante_str = request.getParameter("id-anunciante");
						if (id_anunciante_str == null || id_anunciante_str.isEmpty()) {
							errores.append("anunciante_vacio,");
						} else if (!id_anunciante_str.matches("\\d+")) {
							errores.append("anunciante_invalido,");
						} else {
							id_anunciante = Integer.parseInt(id_anunciante_str);
							anun.setIdAnunciante(id_anunciante);
							if (ac.getById(anun) == null) {
								errores.append("anunciante_inexistente,");
							}
						}
						
						
						if (errores.length() > 0) {
							errores.deleteCharAt(errores.length() - 1);
							request.getSession().setAttribute("errores", errores.toString());
							response.sendRedirect("anuncianteservlet?action=retrieve");
							return;
						}
					} else {
						anun = (Anunciante) request.getSession().getAttribute("usuario");
					}
					
					
					if (rol.equals("administrador")) {
						if (!alqc.getAlquileresPendientesEnCursoByAnunciante(anun).isEmpty()) {
							mensaje = "No puedes eliminar este anunciante debido a que tiene alquileres pendientes o en curso. Por favor, cancele los alquileres y vuelva a intentarlo.";
						} else {
							mensaje = "Anunciante eliminado con éxito.";
							ac.delete(anun);
						}
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("anuncianteservlet?action=retrieve");
					} else {
						if (!alqc.getAlquileresPendientesEnCursoByAnunciante(anun).isEmpty()) {
							mensaje = "No puedes eliminar tu cuenta debido a que tienes alquileres pendientes o en curso. Por favor, cancele los alquileres y vuelva a intentarlo.";
							request.setAttribute("mensaje", mensaje);
							request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
						} else {
							mensaje = "Tu cuenta ha sido eliminada.";
							ac.delete(anun);
							request.getSession().setAttribute("mensaje", mensaje);
							response.sendRedirect("logout");
						}
					}
					break;
				}
			}
		}
	}

}