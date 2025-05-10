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
		if (rol == null || rol.isEmpty()) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		if (!rol.equals("administrador") && !rol.equals("cliente")) {
			request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
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
						cli = (Cliente) request.getSession().getAttribute("respuestas_correctas");
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (errores != null) {
							request.setAttribute("errores", errores);
							request.getSession().removeAttribute("errores");
						}
						if (cli != null) {
							request.setAttribute("respuestas_correctas", cli);
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
					if (!rol.equals("cliente")) {
						request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
						break;
					}
					cli = (Cliente) request.getSession().getAttribute("usuario");
					request.setAttribute("cliente", cli);
					request.getRequestDispatcher("WEB-INF/ui-cliente/update-cliente.jsp").forward(request, response);
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
		if (!rol.equals("administrador") && !rol.equals("cliente")) {
			request.getRequestDispatcher("WEB-INF/acceso-no-autorizado.jsp").forward(request, response);
			return;
		}
		Cliente cli = new Cliente();
		ClienteController cc = new ClienteController();
		String action = request.getParameter("action");
		String mensaje = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		StringBuilder errores = new StringBuilder();
		if (action != null) {
			switch (action) {
			
			
				case "create": {
					String dni = request.getParameter("dni").trim();
					String nombre = request.getParameter("nombre").trim();
					String apellido = request.getParameter("apellido").trim();
					String fecha_nac_str = request.getParameter("fecha-nac");
					String email = request.getParameter("email").trim();
					String telefono = request.getParameter("telefono").trim();
					String contrasena = request.getParameter("contrasena").trim();
					
					
					if (dni == null || dni.isEmpty()) {
						errores.append("dni_vacio,");
					} else if (dni.length() != 8 || !dni.matches("[0-9]+")) {
						errores.append("dni_invalido,");
					} else if (!cc.validarDniUnico(dni)) {
						errores.append("dni_unico,");
					} else {
						cli.setDni(dni);
					}
					
					
					if (nombre == null || nombre.isEmpty()) {
				        errores.append("nombre_vacio,");
				    } else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
				    	errores.append("nombre_invalido,");
				    } else {
						cli.setNombre(nombre);
					}
					
					
					if (apellido == null || apellido.isEmpty()) {
				        errores.append("apellido_vacio,");
				    } else if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
				    	errores.append("apellido_invalido,");
				    } else {
				    	cli.setApellido(apellido);
					}
					
					
					if (fecha_nac_str == null || fecha_nac_str.isEmpty()) {
						errores.append("fecha_vacia,");
				    } else {
				    	LocalDate fecha_nac = LocalDate.parse(fecha_nac_str, dtf);
				    	if (fecha_nac.isAfter(LocalDate.now().minusYears(18))) {
							errores.append("edad_invalida,");
						} else {
							cli.setFechaNac(fecha_nac);
						}
				    }
					
					
					if (email == null || email.isEmpty()) {
				        errores.append("email_vacio,");
				    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				    	errores.append("email_invalido,");
				    } else {
				    	cli.setEmail(email);
					}
					
					
					if (telefono == null || telefono.isEmpty()) {
				        errores.append("telefono_vacio,");
				    } else if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
				    	errores.append("telefono_invalido,");
					} else {
						cli.setTelefono(telefono);
					}
					
					
					if (contrasena == null || contrasena.isEmpty()) {
				        errores.append("contrasena_vacia,");
				    } else if (contrasena.length() < 8) {
				    	errores.append("contrasena_invalida,");
				    } else {
						if (rol.equals("cliente")) {
							String contrasena2 = request.getParameter("contrasena2").trim();
							if (contrasena2 == null || contrasena2.isEmpty() || !contrasena.equals(contrasena2)) {
								errores.append("contrasenas_no_coinciden,");
							} else {
								cli.setContrasena(contrasena);
							}
						} else {
							cli.setContrasena(contrasena);
						}
					}
					
					
					if (errores.length() > 0) {
						errores.append("create");
						if (rol.equals("administrador")) {
							request.getSession().setAttribute("respuestas_correctas", cli);
							request.getSession().setAttribute("errores", errores.toString());
							response.sendRedirect("clienteservlet?action=retrieve");
						} else {
							request.setAttribute("respuestas_correctas", cli);
							request.setAttribute("errores", errores.toString());
							request.getRequestDispatcher("signup-cliente.jsp").forward(request, response);
						}
						return;
					}
					
					
					cc.add(cli);
					if (rol.equals("administrador")) {
						mensaje = "Cliente registrado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("clienteservlet?action=retrieve");
					} else {
						mensaje = "Te has registrado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("http://localhost:8080/utn-tp-java/");
					}
					break;
				}
				
				
				case "update": {
					String dni = null;
					if (rol.equals("administrador")) {
						dni = request.getParameter("dni").trim();
						if (dni == null || dni.isEmpty()) {
							errores.append("dni_vacio,");
						} else if (dni.length() != 8 || !dni.matches("[0-9]+")) {
							errores.append("dni_invalido,");
						} else {
							cli.setDni(dni);
							if (cc.getByDni(cli) == null) {
								errores.append("cliente_inexistente,");
							}
						}
					} else {
						dni = ((Cliente) request.getSession().getAttribute("usuario")).getDni();
						cli.setDni(dni);
					}
					
					
					String nombre = request.getParameter("nombre").trim();
					String apellido = request.getParameter("apellido").trim();
					String fecha_nac_str = request.getParameter("fecha-nac");
					String email = request.getParameter("email").trim();
					String telefono = request.getParameter("telefono").trim();
					String contrasena = request.getParameter("contrasena").trim();
					
					
					if (nombre == null || nombre.isEmpty()) {
				        errores.append("nombre_vacio,");
				    } else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
				    	errores.append("nombre_invalido,");
				    } else {
						cli.setNombre(nombre);
					}
					
					
					if (apellido == null || apellido.isEmpty()) {
				        errores.append("apellido_vacio,");
				    } else if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
				    	errores.append("apellido_invalido,");
				    } else {
				    	cli.setApellido(apellido);
					}
					
					
					if (fecha_nac_str == null || fecha_nac_str.isEmpty()) {
						errores.append("fecha_vacia,");
				    } else {
				    	LocalDate fecha_nac = LocalDate.parse(fecha_nac_str, dtf);
				    	if (fecha_nac.isAfter(LocalDate.now().minusYears(18))) {
							errores.append("edad_invalida,");
						} else {
							cli.setFechaNac(fecha_nac);
						}
				    }
					
					
					if (email == null || email.isEmpty()) {
				        errores.append("email_vacio,");
				    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				    	errores.append("email_invalido,");
				    } else {
				    	cli.setEmail(email);
					}
					
					
					if (telefono == null || telefono.isEmpty()) {
				        errores.append("telefono_vacio,");
				    } else if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
				    	errores.append("telefono_invalido,");
					} else {
						cli.setTelefono(telefono);
					}
					
					
					if (contrasena == null || contrasena.isEmpty()) {
				        errores.append("contrasena_vacia,");
				    } else if (contrasena.length() < 8) {
				    	errores.append("contrasena_invalida,");
				    } else {
						if (rol.equals("cliente")) {
							String contrasena2 = request.getParameter("contrasena2").trim();
							if (contrasena2 == null || contrasena2.isEmpty() || !contrasena.equals(contrasena2)) {
								errores.append("contrasenas_no_coinciden,");
							} else {
								cli.setContrasena(contrasena);
							}
						} else {
							cli.setContrasena(contrasena);
						}
					}
					
					
					if (errores.length() > 0) {
						errores.append("update");
						if (rol.equals("administrador")) {
							request.getSession().setAttribute("respuestas_correctas", cli);
							request.getSession().setAttribute("errores", errores.toString());
							response.sendRedirect("clienteservlet?action=retrieve");
						} else {
							request.setAttribute("respuestas_correctas", cli);
							request.setAttribute("errores", errores.toString());
							request.getRequestDispatcher("signup-cliente.jsp").forward(request, response);
						}
						return;
					}
					
					
					cc.update(cli);
					if (rol.equals("administrador")) {
						mensaje = "Cliente actualizado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("clienteservlet?action=retrieve");
					} else {
						request.getSession().setAttribute("usuario", cli);
						mensaje = "Datos actualizados con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-cliente.jsp").forward(request, response);
					}
					break;
				}
				
				
				case "delete": {
					String dni = null;
					if (rol.equals("administrador")) {
						dni = request.getParameter("dni").trim();
						if (dni == null || dni.isEmpty()) {
							errores.append("dni_vacio,");
						} else if (dni.length() != 8 || !dni.matches("[0-9]+")) {
							errores.append("dni_invalido,");
						} else {
							cli.setDni(dni);
							cli = cc.getByDni(cli);
							if (cli == null) {
								errores.append("cliente_inexistente,");
							}
						}
						
						
						if (errores.length() > 0) {
							errores.deleteCharAt(errores.length() - 1);
							request.getSession().setAttribute("errores", errores.toString());
							response.sendRedirect("clienteservlet?action=retrieve");
							return;
						}
					} else {
						cli = (Cliente) request.getSession().getAttribute("usuario");
					}
					
					
					if (rol.equals("administrador")) {
						if (cc.tieneAlquiler(cli)) {
							mensaje = "No puedes eliminar este cliente debido a que tiene un alquiler pendiente o en curso. Por favor, cancele el alquiler y vuelva a intentarlo.";
						} else {
							mensaje = "Cliente eliminado con éxito.";
							cc.delete(cli);
						}
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("clienteservlet?action=retrieve");
					} else {
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
					}
					break;
				}
			}
		}
	}
	
}