package servlet;

import java.io.IOException;
import java.util.LinkedList;

import entities.Alquiler;
import entities.Anunciante;
import entities.Cliente;
import entities.Propiedad;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.AlquilerController;

@WebServlet({ "/AlquilerServlet", "/alquilerservlet" })
public class AlquilerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AlquilerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AlquilerController ac = new AlquilerController();
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			case "alquileresencursobyanunciante": {
				Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
				LinkedList<Alquiler> alquileres = ac.getAlquileresPendientesEnCursoByAnunciante(anun);
				request.setAttribute("alquileres", alquileres);
				request.getRequestDispatcher("WEB-INF/ui-alquiler/lista-alquileres-en-curso.jsp").forward(request,
						response);
			}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Alquiler alq = new Alquiler();
		AlquilerController ac = new AlquilerController();

		String dni = request.getParameter("dni-cliente");
		int nroPropiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
		int idAnunciante = Integer.parseInt(request.getParameter("id-anunciante"));

		alq.setCliente(new Cliente());
		alq.getCliente().setDni(dni);
		alq.setPropiedad(new Propiedad());
		alq.getPropiedad().setNroPropiedad(nroPropiedad);
		alq.getPropiedad().setAnunciante(new Anunciante());
		alq.getPropiedad().getAnunciante().setIdAnunciante(idAnunciante);

		ac.add(alq);
		response.sendRedirect("menu-cliente.html");
	}

}