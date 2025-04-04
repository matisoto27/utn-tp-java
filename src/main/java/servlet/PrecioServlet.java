package servlet;

import java.io.IOException;
import java.util.LinkedList;

import entities.Anunciante;
import entities.Precio;
import entities.Propiedad;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.PrecioController;
import logic.PropiedadController;

@WebServlet({ "/PrecioServlet", "/precioservlet" })
public class PrecioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PrecioServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {

			case "create": {
				PropiedadController pc = new PropiedadController();
				Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
				LinkedList<Propiedad> propiedades = pc.getPropiedadesByAnunciante(anun);
				request.setAttribute("propiedades", propiedades);
				request.getRequestDispatcher("WEB-INF/ui-precio/create-precio.jsp").forward(request, response);
				break;
			}

			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Precio p = new Precio();
		PrecioController pc = new PrecioController();

		Anunciante anun = (Anunciante) request.getSession().getAttribute("usuario");
		int nro_propiedad = Integer.parseInt(request.getParameter("nro-propiedad"));
		double valor = Double.parseDouble(request.getParameter("valor"));

		p.setPropiedad(new Propiedad());
		p.getPropiedad().setNroPropiedad(nro_propiedad);
		p.getPropiedad().setAnunciante(anun);
		p.setValor(valor);

		pc.add(p);
		request.getRequestDispatcher("WEB-INF/menu-anunciante.jsp").forward(request, response);
	}

}