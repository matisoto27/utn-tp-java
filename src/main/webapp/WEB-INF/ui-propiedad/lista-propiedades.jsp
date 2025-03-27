<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page import="logic.AlquilerController" %>
<%@page import="logic.PrecioController" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Listado de Propiedades</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    Cliente cli = (Cliente) session.getAttribute("usuario");
    LinkedList<Propiedad> lp = (LinkedList<Propiedad>)request.getAttribute("listaPropiedades");
    AlquilerController ac = new AlquilerController();
    PrecioController pc = new PrecioController();
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100">
        <div class="container">
            <div class="row mb-4">
                <div class="col">
                    <table class="table text-center table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Anunciante</th>
                                <th scope="col">Nro. de Propiedad</th>
                                <th scope="col">Direccion</th>
                                <th scope="col">Piso</th>
                                <th scope="col">Departamento</th>
                                <th scope="col">Precio</th>
                                <th scope="col">Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (lp.isEmpty()) { %>
                                <tr>
                                    <td colspan="6" style="text-align: center;">
                                        No hay propiedades disponibles
                                    </td>
                                </tr>
                            <%
                            } else {
                            	for (Propiedad prop : lp) {
                           	%>
                                    <tr>
                                        <td>
                                            <%= prop.getAnunciante().getNombre() %>
                                        </td>
                                        <td>
                                            <%= prop.getNroPropiedad() %>
                                        </td>
                                        <td>
                                            <%= prop.getDireccion() %>
                                        </td>
                                        <td>
                                            <%= prop.getPiso() != 0 ? prop.getPiso() : "-" %>
                                        </td>
                                        <td>
                                            <%= prop.getDepto() != null ? prop.getDepto() : "-" %>
                                        </td>
                                        <td>
                                            <%= pc.getUltimoByPropiedad(prop) != 0 ? pc.getUltimoByPropiedad(prop) : "Sin datos" %>
                                        </td>
                                        <td>
                                        	<%
                                        		Alquiler alq = new Alquiler();
                                        		alq.setPropiedad(prop);
                                        		alq = ac.getUltimoByPropiedad(alq);
                                        		if (alq == null) {
                                        	%>
                                        			<form method="POST" action="alquilerservlet">
                                                		<input type="hidden" name="dni-cliente" value="<%= cli.getDni() %>"></input>
                                                		<input type="hidden" name="nro-propiedad" value="<%= prop.getNroPropiedad() %>"></input>
                                                		<input type="hidden" name="id-anunciante" value="<%= prop.getAnunciante().getIdAnunciante() %>"></input>
                                                		<button class="btn btn-success">Solicitar visita</button>
                                            		</form>
                                        	<%
                                        		} else {
                                        	%>
                                        			<button class="btn btn-secondary disabled">Reservado</button>
                                            <%
                                        		}
                                        	%>
                                        </td>
                                    </tr>
                                <% } %>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col text-center">
                    <button type="button" class="btn btn-primary p-2" style="width: 250px;" onclick='window.location.href="menu-cliente.html"'>Volver</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>

</html>