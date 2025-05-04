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
		if (!rol.equals("administrador") && !rol.equals("anunciante") && !rol.equals("cliente")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
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
					} if (rol.equals("cliente")) {
						LinkedList<Anunciante> anunciantes = ac.getAll();
						request.setAttribute("anunciantes", anunciantes);
						request.getRequestDispatcher("WEB-INF/ui-anunciante/lista-anunciantes.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
				case "update": {
					if (rol.equals("anunciante")) {
						anun = (Anunciante) request.getSession().getAttribute("usuario");
						request.setAttribute("anunciante", anun);
						request.getRequestDispatcher("WEB-INF/ui-anunciante/update-anunciante.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
				case "delete": {
					if (rol.equals("anunciante")) {
						AlquilerController alqc = new AlquilerController();
						anun = (Anunciante) request.getSession().getAttribute("usuario");
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
		if (!rol.equals("administrador") && !rol.equals("anunciante")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		Anunciante anun = new Anunciante();
		AnuncianteController ac = new AnuncianteController();
		StringBuilder errores = new StringBuilder();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
				case "create": {
					String nombre = request.getParameter("nombre").trim();
					String email = request.getParameter("email").trim();
					String telefono = request.getParameter("telefono").trim();
					String usuario = request.getParameter("usuario").trim();
					String contrasena = request.getParameter("contrasena").trim();
					if (!ac.validarNombreUnico(nombre)) {
						errores.append("nombre_unico,");
					} else {
						anun.setNombre(nombre);
					}
					if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
						errores.append("email_invalido,");
					} else {
						anun.setEmail(email);
					}
					if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
						errores.append("telefono_invalido,");
					} else {
						anun.setTelefono(telefono);
					}
					
					if (!ac.validarUsuarioUnico(usuario)) {
						errores.append("usuario_unico,");
					} else {
						anun.setUsuario(usuario);
					}
					if (contrasena.length() != 8) {
						errores.append("contrasena_invalida,");
					} else {
						if (rol.equals("anunciante")) {
							String contrasena2 = request.getParameter("contrasena2").trim();
							if (!contrasena.equals(contrasena2)) {
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
						if (rol.equals("anunciante")) {
							request.setAttribute("respuestas_correctas", anun);
							request.setAttribute("errores", errores.toString());
							request.getRequestDispatcher("signup-anunciante.jsp").forward(request, response);
						} else {
							request.getSession().setAttribute("respuestas_correctas", anun);
							request.getSession().setAttribute("errores", errores.toString());
							response.sendRedirect("anuncianteservlet?action=retrieve");
						}
						return;
					}
					ac.add(anun);
					if (rol.equals("anunciante")) {
						mensaje = "Te has registrado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("http://localhost:8080/utn-tp-java/");
					} else {
						mensaje = "Anunciante registrado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("anuncianteservlet?action=retrieve");
					}
					break;
				}
				case "update": {
					int id_anunciante = 0;
					if (rol.equals("anunciante")) {
						id_anunciante = ((Anunciante) request.getSession().getAttribute("usuario")).getIdAnunciante();
					} else {
						id_anunciante = Integer.parseInt(request.getParameter("id-anunciante").trim());
					}
					anun.setIdAnunciante(id_anunciante);
					Anunciante aux = new Anunciante();
					aux.setIdAnunciante(id_anunciante);
					aux = ac.getById(anun);
					String nombre = request.getParameter("nombre").trim();
					String email = request.getParameter("email").trim();
					String telefono = request.getParameter("telefono").trim();
					String usuario = request.getParameter("usuario").trim();
					String contrasena = request.getParameter("contrasena").trim();
					if (!nombre.equals(aux.getNombre()) && !ac.validarNombreUnico(nombre)) {
						errores.append("nombre_unico,");
					} else {
						anun.setNombre(nombre);
					}
					if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
						errores.append("email_invalido,");
					} else {
						anun.setEmail(email);
					}
					if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
						errores.append("telefono_invalido,");
					} else {
						anun.setTelefono(telefono);
					}
					if (!usuario.equals(aux.getUsuario()) && !ac.validarUsuarioUnico(usuario)) {
						errores.append("usuario_unico,");
					} else {
						anun.setUsuario(usuario);
					}
					if (contrasena.length() != 8) {
						errores.append("contrasena_invalida,");
					} else {
						if (rol.equals("anunciante")) {
							String contrasena2 = request.getParameter("contrasena2").trim();
							if (!contrasena.equals(contrasena2)) {
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
						if (rol.equals("anunciante")) {
							request.setAttribute("respuestas_correctas", anun);
							request.setAttribute("errores", errores.toString());
							request.getRequestDispatcher("update-anunciante.jsp").forward(request, response);
						} else {
							request.getSession().setAttribute("respuestas_correctas", anun);
							request.getSession().setAttribute("errores", errores.toString());
							response.sendRedirect("anuncianteservlet?action=retrieve");
						}
						return;
					}
					ac.update(anun);
					if (rol.equals("anunciante")) {
						request.getSession().setAttribute("usuario", anun);
						mensaje = "Datos actualizados con éxito.";
						request.setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
					} else if (rol.equals("administrador")) {
						mensaje = "Anunciante actualizado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("anuncianteservlet?action=retrieve");
					}
					break;
				}
				case "delete": {
					if (rol.equals("administrador")) {
						AlquilerController alqc = new AlquilerController();
			        	int id_anunciante = Integer.parseInt(request.getParameter("id-anunciante").trim());
						anun.setIdAnunciante(id_anunciante);
						anun = ac.getById(anun);
						if (!alqc.getAlquileresPendientesEnCursoByAnunciante(anun).isEmpty()) {
							mensaje = "No puedes eliminar este anunciante debido a que tiene alquileres pendientes o en curso. Por favor, cancele los alquileres y vuelva a intentarlo.";
						} else {
							mensaje = "Anunciante eliminado con éxito.";
							ac.delete(anun);
						}
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("anuncianteservlet?action=retrieve");
			        } else {
			        	request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
			        }
					break;
				}
			}
		}
	}

}