package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import entities.Anunciante;
import logic.AnuncianteController;

@WebServlet({ "/SignUpAnunciante", "/signupanunciante" })
public class SignUpAnunciante extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public SignUpAnunciante() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Anunciante anun = new Anunciante();
		AnuncianteController ac = new AnuncianteController();
		
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		String telefono = request.getParameter("telefono");
		String usuario = request.getParameter("usuario");
		String contrasena = request.getParameter("contrasena");
		String contrasena2 = request.getParameter("contrasena2");
		
		anun.setNombre(nombre);
		anun.setEmail(email);
		anun.setTelefono(telefono);
		anun.setUsuario(usuario);
		anun.setContrasena(contrasena);
		
		StringBuilder errores = new StringBuilder("error=");
		
		if (!ac.validarNombreUnico(anun)) {
			errores.append("nombre_unico,");
		}
		
		if (!ac.validarUsuarioUnico(anun)) {
			errores.append("usuario_unico,");
		}
		
		if (!contrasena.equals(contrasena2)) {
			errores.append("contrasenas_no_coinciden,");
		}
		
		if (errores.length() > 6) {
			errores.deleteCharAt(errores.length() - 1);
	        response.sendRedirect("signup-anunciante.html?" + errores.toString());
	        return;
	    }
		
		// Falta manejar errores del lado del html
		
		ac.add(anun);
		response.sendRedirect("index.html");
	}
	
}