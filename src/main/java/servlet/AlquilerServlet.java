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

import entities.Alquiler;
import entities.Anunciante;
import entities.Cliente;
import entities.Propiedad;
import logic.AlquilerController;
import logic.ClienteController;
import logic.PropiedadController;

@WebServlet({ "/AlquilerServlet", "/alquilerservlet" })
public class AlquilerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AlquilerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rol = (String) request.getSession().getAttribute("rol");
		if (!rol.equals("administrador") && !rol.equals("anunciante") && !rol.equals("cliente")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		Alquiler alq = new Alquiler();
		AlquilerController ac = new AlquilerController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
				case "alquileresencursobyanunciante": {
					Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
					LinkedList<Alquiler> alquileres = ac.getAlquileresPendientesEnCursoByAnunciante(anun);
					request.setAttribute("alquileres", alquileres);
					request.getRequestDispatcher("WEB-INF/ui-alquiler/lista-alquileres-en-curso.jsp").forward(request, response);
				}
				case "retrieve": {
					if (rol.equals("administrador")) {
						alq = (Alquiler) request.getSession().getAttribute("respuestas_correctas");
						String errores = (String) request.getSession().getAttribute("errores");
						mensaje = (String) request.getSession().getAttribute("mensaje");
						if (alq != null) {
							request.setAttribute("respuestas_correctas", alq);
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
						LinkedList<Alquiler> alquileres = ac.getAll();
						ClienteController cc = new ClienteController();
						LinkedList<Cliente> clientes = cc.getAll();
						PropiedadController pc = new PropiedadController();
						LinkedList<Propiedad> propiedades = pc.getAll();
						request.setAttribute("alquileres", alquileres);
						request.setAttribute("clientes", clientes);
						request.setAttribute("propiedades", propiedades);
						request.getRequestDispatcher("WEB-INF/ui-alquiler/crud-alquiler.jsp").forward(request, response);
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
		if (!rol.equals("administrador") && !rol.equals("anunciante") && !rol.equals("cliente")) {
			response.sendRedirect("http://localhost:8080/utn-tp-java/");
			return;
		}
		Alquiler alq = new Alquiler();
		AlquilerController ac = new AlquilerController();
		String action = request.getParameter("action");
		String mensaje = null;
		if (action != null) {
			switch (action) {
				case "create": {
					String dni_cliente = request.getParameter("dni-cliente");
					int id_anunciante = Integer.parseInt(request.getParameter("id-anunciante"));
					int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
					alq.setCliente(new Cliente());
					alq.getCliente().setDni(dni_cliente);
					alq.setPropiedad(new Propiedad());
					alq.getPropiedad().setNroPropiedad(nro_propiedad);
					alq.getPropiedad().setAnunciante(new Anunciante());
					alq.getPropiedad().getAnunciante().setIdAnunciante(id_anunciante);
					ac.add(alq);
					if (rol.equals("cliente")) {
						mensaje = "Propiedad reservada con éxito.";
						request.setAttribute("mensaje", mensaje);
						request.getRequestDispatcher("WEB-INF/menu-cliente.jsp").forward(request, response);
					} else if (rol.equals("anunciante")) {
						// Falta alquiler de parte del anunciante
					} else {
						mensaje = "Alquiler creado con éxito.";
						request.getSession().setAttribute("mensaje", mensaje);
						response.sendRedirect("alquilerservlet?action=retrieve");
					}
					break;
				}
				case "update": {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					int id_alquiler = Integer.parseInt(request.getParameter("id-alquiler"));
					String dni_cliente = request.getParameter("dni-cliente");
					int id_anunciante = Integer.parseInt(request.getParameter("id-anunciante"));
					int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
					LocalDate fecha_solicitado = LocalDate.parse(request.getParameter("fecha-solicitado"), dtf);
					String estado = request.getParameter("estado");
					LocalDate fecha_inicio_contrato = !request.getParameter("fecha-inicio-contrato").isEmpty() ?
							LocalDate.parse(request.getParameter("fecha-inicio-contrato"), dtf) 
							: null;
					LocalDate fecha_fin_contrato = !request.getParameter("fecha-fin-contrato").isEmpty() ?
							LocalDate.parse(request.getParameter("fecha-fin-contrato"), dtf)
							: null;
					LocalDate fecha_renuncia = !request.getParameter("fecha-renuncia").isEmpty() ?
							LocalDate.parse(request.getParameter("fecha-renuncia"), dtf)
							: null;
					Integer puntuacion = request.getParameter("puntuacion") != null && !request.getParameter("puntuacion").isEmpty() ?
							Integer.parseInt(request.getParameter("puntuacion"))
							: null;
					String comentario = !request.getParameter("comentario").isEmpty() ?
							request.getParameter("comentario")
							: null;
					Cliente cli = new Cliente();
					cli.setDni(dni_cliente);
					Propiedad prop = new Propiedad();
					prop.setAnunciante(new Anunciante());
					prop.getAnunciante().setIdAnunciante(id_anunciante);
					prop.setNroPropiedad(nro_propiedad);
					alq = new Alquiler(id_alquiler, cli, prop, fecha_solicitado, estado, fecha_inicio_contrato, fecha_fin_contrato, fecha_renuncia, puntuacion, comentario);
					ac.update(alq);
					mensaje = "Alquiler actualizado con éxito.";
					request.getSession().setAttribute("mensaje", mensaje);
					response.sendRedirect("alquilerservlet?action=retrieve");
					break;
				}
				case "delete": {
					int id_alquiler = Integer.parseInt(request.getParameter("id-alquiler"));
					alq.setIdAlquiler(id_alquiler);
					alq = ac.getById(alq);
					if (alq.getEstado() != null && (alq.getEstado().equals("Pendiente") || alq.getEstado().equals("En curso"))) {
						mensaje = "No puedes eliminar este alquiler debido a que está pendiente o en curso. Por favor, cancele el alquiler y vuelva a intentarlo.";
					} else {
						mensaje = "Alquiler eliminado con éxito.";
						ac.delete(alq);
					}
					request.getSession().setAttribute("mensaje", mensaje);
					response.sendRedirect("alquilerservlet?action=retrieve");
					break;
				}
			}
		}
	}

}