package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entities.Cliente;
import logic.ClienteController;

@WebServlet({ "/ClienteServlet", "/clienteservlet" })
public class ClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ClienteServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = (String) request.getSession().getAttribute("rol");
		if (!rol.equals("administrador") && !rol.equals("cliente")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		Cliente cli = new Cliente();
		ClienteController cc = new ClienteController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
				case "retrieve": {
					if (rol.equals("administrador")) {
						LinkedList<Cliente> clientes = cc.getAll();
						String errores = (String) request.getSession().getAttribute("errores");
						Cliente c = (Cliente) request.getSession().getAttribute("respuestas_correctas");
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (errores != null) {
							request.setAttribute("errores", errores);
							request.getSession().removeAttribute("errores");
						}
						if (c != null) {
							request.setAttribute("respuestas_correctas", c);
							request.getSession().removeAttribute("respuestas_correctas");
						}
						if (mensaje != null) {
							request.setAttribute("mensaje", mensaje);
							request.getSession().removeAttribute("mensaje");
						}
						request.setAttribute("clientes", clientes);
						request.getRequestDispatcher("WEB-INF/ui-cliente/crud-cliente.jsp").forward(request, response);
					}
					break;
				}
				case "update": {
					if (rol.equals("cliente")) {
						cli = (Cliente) request.getSession().getAttribute("usuario");
						request.setAttribute("cliente", cli);
						request.getRequestDispatcher("WEB-INF/ui-cliente/update-cliente.jsp").forward(request, response);
					} else {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
					}
					break;
				}
				case "delete": {
					if (rol.equals("cliente")) {
						cli = (Cliente) request.getSession().getAttribute("usuario");
						if (cc.tieneAlquiler(cli)) {
							mensaje = "No puedes eliminar tu cuenta debido a que tienes un alquiler pendiente o en curso. Por favor, cancele el alquiler y vuelva a intentarlo.";
							request.setAttribute("mensaje", mensaje);
							request.getRequestDispatcher("WEB-INF/menu-cliente.jsp").forward(request, response);
						} else {
							mensaje = "Tu cuenta ha sido eliminada.";
							cc.delete(cli);
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
		if (!rol.equals("administrador") && !rol.equals("cliente")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		Cliente cli = new Cliente();
		ClienteController cc = new ClienteController();
		String action = request.getParameter("action");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String mensaje = null;
		if (action != null) {
			switch (action) {
				case "create": {
					StringBuilder errores = new StringBuilder();
					String dni = request.getParameter("dni").trim();;
					String nombre = request.getParameter("nombre").trim();;
					String apellido = request.getParameter("apellido").trim();;
					LocalDate fecha_nac = LocalDate.parse(request.getParameter("fecha-nac"), dtf);
					String email = request.getParameter("email").trim();;
					String telefono = request.getParameter("telefono").trim();;
					String contrasena = request.getParameter("contrasena").trim();;
					
					if (dni.length() != 8 || !dni.matches("[0-9]+")) {
						errores.append("dni_invalido,");
					}
				
					if (!cc.validarDniUnico(dni)) {
						errores.append("dni_unico,");
					} else {
						cli.setDni(dni);
					}
				
					if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
						errores.append("nombre_invalido,");
					} else {
						cli.setNombre(nombre);
					}

					if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
						errores.append("apellido_invalido,");
					} else {
						cli.setApellido(apellido);
					}
				
					if (fecha_nac.isAfter(LocalDate.now().minusYears(18))) {
						errores.append("edad_invalida,");
					} else {
						cli.setFechaNac(fecha_nac);
					}
				
					if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
						errores.append("email_invalido,");
					} else {
						cli.setEmail(email);
					}
				
					if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
						errores.append("telefono_invalido,");
					} else {
						cli.setTelefono(telefono);
					}
				
					if (contrasena.length() != 8) {
						errores.append("contrasena_invalida,");
					} else {
						if (rol.equals("cliente")) {
							String contrasena2 = request.getParameter("contrasena2").trim();
							if (!contrasena.equals(contrasena2)) {
								errores.append("contrasenas_no_coinciden,");
							} else {
								cli.setContrasena(contrasena);
							}
						} else {
							cli.setContrasena(contrasena);
						}
					}
				
					if (errores.length() > 0) {
						errores.deleteCharAt(errores.length() - 1);
						request.setAttribute("respuestas", cli);
						request.setAttribute("errores", errores.toString());
						request.getRequestDispatcher("signup-cliente.jsp").forward(request, response);
						return;
					}
				
					cc.add(cli);
				
					if (rol.equals("cliente")) {
						mensaje = "Te has registrado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("http://localhost:8080/utn-tp-java/");
					} else if (rol.equals("administrador")) {
						mensaje = "Cliente registrado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("clienteservlet?action=retrieve");
					}
					break;
				}
				case "update": {
					cli = new Cliente();
					StringBuilder errores = new StringBuilder();
					String dni = null;
					if (rol.equals("cliente")) {
						dni = ((Cliente) request.getSession().getAttribute("usuario")).getDni();
					} else {
						dni = request.getParameter("dni");	
					}
					cli.setDni(dni);
					String nombre = request.getParameter("nombre").trim();
					String apellido = request.getParameter("apellido").trim();
					LocalDate fecha_nac = LocalDate.parse(request.getParameter("fecha-nac"), dtf);
					String email = request.getParameter("email").trim();
					String telefono = request.getParameter("telefono").trim();
					String contrasena = request.getParameter("contrasena").trim();
					
					if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
						errores.append("nombre_invalido,");
					} else {
						cli.setNombre(nombre);
					}

					if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
						errores.append("apellido_invalido,");
					} else {
						cli.setApellido(apellido);
					}
				
					if (fecha_nac.isAfter(LocalDate.now().minusYears(18))) {
						errores.append("edad_invalida,");
					} else {
						cli.setFechaNac(fecha_nac);
					}
				
					if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
						errores.append("email_invalido,");
					} else {
						cli.setEmail(email);
					}
				
					if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
						errores.append("telefono_invalido,");
					} else {
						cli.setTelefono(telefono);
					}
					
					if (contrasena.length() != 8) {
						errores.append("contrasena_invalida,");
					} else {
						if (rol.equals("cliente")) {
							String contrasena2 = request.getParameter("contrasena2") != null ? request.getParameter("contrasena2").trim() : null;
							if (contrasena2 != null && !contrasena.equals(contrasena2)) {
								errores.append("contrasenas_no_coinciden,");
							} else {
								cli.setContrasena(contrasena);
							}
						} else {
							cli.setContrasena(contrasena);
						}
					}
					
					if (errores.length() > 0) {
						errores.deleteCharAt(errores.length() - 1);
						if (rol.equals("cliente")) {
							request.setAttribute("respuestas_correctas", cli);
							request.setAttribute("errores", errores.toString());
							request.getRequestDispatcher("WEB-INF/ui-cliente/update-cliente.jsp").forward(request, response);
						} else {
							request.getSession().setAttribute("errores", errores.toString());
							request.getSession().setAttribute("respuestas_correctas", cli);
							response.sendRedirect("clienteservlet?action=retrieve");
						}
						return;
					}
					
					cc.update(cli);
					
					if (rol.equals("cliente")) {
						request.getSession().setAttribute("usuario", cli);
						mensaje = "Datos actualizados con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-cliente.jsp").forward(request, response);
					} else if (rol.equals("administrador")) {
						mensaje = "Cliente actualizado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("clienteservlet?action=retrieve");
					}
					break;
				}
				case "delete": {
					if (rol.equals("administrador")) {
			        	String dni = request.getParameter("dni");
						cli.setDni(dni);
						cli = cc.getByDni(cli);
						if (cc.tieneAlquiler(cli)) {
							mensaje = "No puedes eliminar este cliente debido a que tiene un alquiler pendiente o en curso. Por favor, cancele el alquiler y vuelva a intentarlo.";
						} else {
							mensaje = "Cliente eliminado con éxito.";
							cc.delete(cli);
						}
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("clienteservlet?action=retrieve");
			        } else {
			        	request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
			        }
					break;
				}
			}
		}
	}
	
}