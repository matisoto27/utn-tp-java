package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import entities.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ClienteController;

@WebServlet({ "/ClienteServlet", "/clienteservlet" })
public class ClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cliente cli = new Cliente();
		ClienteController cc = new ClienteController();
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			case "delete": {
				String dni = request.getParameter("dni");
				cli.setDni(dni);
				cc.delete(cli);
				break;
			}
			}
		}
		LinkedList<Cliente> clientes = cc.getAll();
		request.setAttribute("clientes", clientes);
		request.getRequestDispatcher("WEB-INF/ui-cliente/crud-cliente.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cliente cli = new Cliente();
		ClienteController cc = new ClienteController();
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			case "create": {
				String dni = request.getParameter("dni");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String fecha_nac_str = request.getParameter("fecha-nac");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate fecha_nac = LocalDate.parse(fecha_nac_str, dtf);
				String email = request.getParameter("email");
				String telefono = request.getParameter("telefono");
				String contrasena = request.getParameter("contrasena");
				cli.setDni(dni);
				cli.setNombre(nombre);
				cli.setApellido(apellido);
				cli.setFechaNac(fecha_nac);
				cli.setEmail(email);
				cli.setTelefono(telefono);
				cli.setContrasena(contrasena);
				cc.add(cli);
				break;
			}
			case "update": {
				String dni = request.getParameter("dni");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String fecha_nac_str = request.getParameter("fecha-nac");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate fecha_nac = LocalDate.parse(fecha_nac_str, dtf);
				String email = request.getParameter("email");
				String telefono = request.getParameter("telefono");
				String contrasena = request.getParameter("contrasena");
				cli.setDni(dni);
				cli.setNombre(nombre);
				cli.setApellido(apellido);
				cli.setFechaNac(fecha_nac);
				cli.setEmail(email);
				cli.setTelefono(telefono);
				cli.setContrasena(contrasena);
				cc.update(cli);
				break;
			}
			}
		}
		LinkedList<Cliente> clientes = cc.getAll();
		request.setAttribute("clientes", clientes);
		request.getRequestDispatcher("WEB-INF/ui-cliente/crud-cliente.jsp").forward(request, response);
	}

}