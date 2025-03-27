package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import entities.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClienteController;

@WebServlet({ "/SignUpCliente", "/signupcliente" })
public class SignUpCliente extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SignUpCliente() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cliente cli = new Cliente();
		ClienteController cc = new ClienteController();

		String dni = request.getParameter("dni");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String fecha_nac_str = request.getParameter("fecha_nac");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha_nac = LocalDate.parse(fecha_nac_str, dtf);
		String email = request.getParameter("email");
		String telefono = request.getParameter("telefono");
		String contrasena = request.getParameter("contrasena");
		String contrasena2 = request.getParameter("contrasena2");

		cli.setDni(dni);
		cli.setNombre(nombre);
		cli.setApellido(apellido);
		cli.setFechaNac(fecha_nac);
		cli.setEmail(email);
		cli.setTelefono(telefono);
		cli.setContrasena(contrasena);

		StringBuilder errores = new StringBuilder();

		Cliente cliAux = new Cliente();

		if (dni.length() != 8 || !dni.matches("[0-9]+")) {
			errores.append("dni_invalido,");
		}

		if (!cc.validarDniUnico(cli)) {
			errores.append("dni_unico,");
		} else {
			cliAux.setDni(dni);
		}

		if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
			errores.append("nombre_invalido,");
		} else {
			cliAux.setNombre(nombre);
		}

		if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
			errores.append("apellido_invalido,");
		} else {
			cliAux.setApellido(apellido);
		}

		if (fecha_nac.isAfter(LocalDate.now().minusYears(18))) {
			errores.append("edad_invalida,");
		} else {
			cliAux.setFechaNac(fecha_nac);
		}

		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			errores.append("email_invalido,");
		} else {
			cliAux.setEmail(email);
		}

		if (telefono.length() < 10 || !telefono.matches("[0-9]+")) {
			errores.append("telefono_invalido,");
		} else {
			cliAux.setTelefono(telefono);
		}

		if (contrasena.length() != 8) {
			errores.append("contrasena_invalida,");
		}

		if (!contrasena.equals(contrasena2)) {
			errores.append("contrasenas_no_coinciden,");
		}

		if (errores.length() > 0) {
			errores.deleteCharAt(errores.length() - 1);
			request.setAttribute("respuestas", cliAux);
		    request.setAttribute("errores", errores.toString());
		    request.getRequestDispatcher("signup-cliente.jsp").forward(request, response);
			return;
		}

		cc.add(cli);
		response.sendRedirect("index.html");
	}

}