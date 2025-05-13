package servlet;

import java.io.IOException;

import entities.Administrador;
import entities.Anunciante;
import entities.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AdministradorController;
import logic.AnuncianteController;
import logic.ClienteController;

@WebServlet({ "/Login", "/login" })
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object usuario = request.getSession().getAttribute("usuario");
		String rol = (String) request.getSession().getAttribute("rol");
		if (usuario != null && !rol.isEmpty()) {
			switch (rol) {
				case "cliente": {
					request.getRequestDispatcher("WEB-INF/menu-cliente.jsp").forward(request, response);
					break;
				}
				case "anunciante": {
					request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
					break;
				}
				case "administrador": {
					request.getRequestDispatcher("WEB-INF/menu-administrador.jsp").forward(request, response);
					break;
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipo = request.getParameter("tipo");
		String contrasena = request.getParameter("contrasena");
		String mensaje = null;
		
		if (tipo.equals("administrador")) {
			Administrador adm = new Administrador();
			AdministradorController ac = new AdministradorController();

			String usuario = request.getParameter("usuario");

			adm.setUsuario(usuario);
			adm.setContrasena(contrasena);

			adm = ac.getByUsuarioContrasena(adm);

			if (adm != null) {
				request.getSession().setAttribute("usuario", adm);
				request.getSession().setAttribute("rol", "administrador");
				request.getRequestDispatcher("WEB-INF/menu-administrador.jsp").forward(request, response);
			} else {
				mensaje = "Credenciales incorrectas.";
				request.setAttribute("mensaje", mensaje);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		} else if (tipo.equals("anunciante")) {
			Anunciante anun = new Anunciante();
			AnuncianteController ac = new AnuncianteController();

			String usuario = request.getParameter("usuario");

			anun.setUsuario(usuario);
			anun.setContrasena(contrasena);

			anun = ac.validarCredenciales(anun);

			if (anun != null) {
				request.getSession().setAttribute("usuario", anun);
				request.getSession().setAttribute("rol", "anunciante");
				request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
			} else {
				mensaje = "Credenciales incorrectas.";
				request.setAttribute("mensaje", mensaje);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		} else {
			Cliente cli = new Cliente();
			ClienteController cc = new ClienteController();

			String dni = request.getParameter("dni");

			cli.setDni(dni);
			cli.setContrasena(contrasena);

			cli = cc.validarCredenciales(cli);

			if (cli != null) {
				request.getSession().setAttribute("usuario", cli);
				request.getSession().setAttribute("rol", "cliente");
				request.getRequestDispatcher("WEB-INF/menu-cliente.jsp").forward(request, response);
			} else {
				mensaje = "Credenciales incorrectas.";
				request.setAttribute("mensaje", mensaje);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}
	}

}