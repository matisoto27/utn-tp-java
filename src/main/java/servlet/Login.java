package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import entities.Anunciante;
import entities.Cliente;
import logic.AnuncianteController;
import logic.ClienteController;

@WebServlet({ "/Login", "/login" })
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public Login() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String tipo = request.getParameter("tipo");
		String contrasena = request.getParameter("contrasena");
		
		if (tipo.equals("cliente")) {
			Cliente cli = new Cliente();
			ClienteController cc = new ClienteController();
			
			String dni = request.getParameter("dni");
			
			cli.setDni(dni);
			cli.setContrasena(contrasena);
			
			cli = cc.validarCredenciales(cli);
			
			if (cli != null) {
				request.getSession().setAttribute("usuario", cli);
				response.sendRedirect("menu-cliente.html");
			} else {
				// Manejar error cliente no existe
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
				response.sendRedirect("menu-anunciante.html");
			} else {
				// Manejar error anunciante no existe
			}
		}
	}
	
}