package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.Date;

import entities.Cliente;
import logic.ClienteController;

@WebServlet({ "/SignUpCliente", "/signupcliente" })
public class SignUpCliente extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public SignUpCliente() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cliente cli = new Cliente();
		ClienteController cc = new ClienteController();
		
		String dni = request.getParameter("dni");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String fecha_nac_str = request.getParameter("fecha_nac");
		Date fecha_nac = java.sql.Date.valueOf(fecha_nac_str);
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
		
		StringBuilder errores = new StringBuilder("error=");
		
		if (!cc.validarDniUnico(cli)) {
			errores.append("dni_unico,");
		}
		
		if (!contrasena.equals(contrasena2)) {
			errores.append("contrasenas_no_coinciden,");
		}
		
		if (errores.length() > 6) {
			errores.deleteCharAt(errores.length() - 1);
	        response.sendRedirect("signup-cliente.html?" + errores.toString());
	        return;
	    }
		
		// Falta manejar errores del lado del html
		
		cc.add(cli);
		response.sendRedirect("index.html");
	}
	
}