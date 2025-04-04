<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Listado de Propiedades</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    Cliente cli = (Cliente) request.getAttribute("cliente");
    LinkedList<Propiedad> propiedades = (LinkedList<Propiedad>) request.getAttribute("propiedades");
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
                                <th scope="col">Direccion</th>
                                <th scope="col">Piso</th>
                                <th scope="col">Departamento</th>
                                <th scope="col">Precio</th>
                                <th scope="col">Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            if (propiedades.isEmpty()) {
                            %>
                                <tr>
                                    <td colspan="6" style="text-align: center;">
                                        No hay propiedades disponibles
                                    </td>
                                </tr>
                            <%
                            } else {
                            	for (Propiedad prop : propiedades) {
                                    int id_anunciante = prop.getAnunciante().getIdAnunciante();
                                    int nro_propiedad = prop.getNroPropiedad();
                                    String nombre = prop.getAnunciante().getNombre();
                                    String direccion = prop.getDireccion();
                                    String piso = prop.getPiso() > 0 ? Integer.toString(prop.getPiso()) : "-";
                                    String depto = prop.getDepto() != null ? prop.getDepto() : "-";
                                    String precio = prop.getPrecioActual() != 0 ? Double.toString(prop.getPrecioActual()) : "Sin datos";
                                    String estado = prop.getEstado();
                                    String dni_cliente = cli.getDni();
                           	%>
                                    <tr>
                                        <td>
                                            <%= nombre %>
                                        </td>
                                        <td>
                                            <%= direccion %>
                                        </td>
                                        <td>
                                            <%= piso %>
                                        </td>
                                        <td>
                                            <%= depto %>
                                        </td>
                                        <td>
                                            <%= precio %>
                                        </td>
                                        <td>
                                        	<%
                                        		if (estado != null && estado.equals("Pendiente")) {
                                        	%>
                                        			<button class="btn btn-secondary disabled">Reservado</button>
                                        	<%
                                        		} else {
                                        	%>
                                        			<form method="POST" action="alquilerservlet">
                                                		<input type="hidden" name="dni-cliente" value="<%= dni_cliente %>"></input>
                                                		<input type="hidden" name="nro-propiedad" value="<%= nro_propiedad %>"></input>
                                                		<input type="hidden" name="id-anunciante" value="<%= id_anunciante %>"></input>
                                                		<button class="btn btn-success">Solicitar visita</button>
                                            		</form>
                                            <%
                                        		}
                                        	%>
                                        </td>
                                    </tr>
                            	<%
                           		}
                         	}
                           	%>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col text-center">
                    <button type="button" class="btn btn-primary p-2" style="width: 250px;" onclick='window.location.href="login"'>Volver</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>