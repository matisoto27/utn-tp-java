<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page import="logic.AlquilerController" %>
<%@page import="logic.PrecioController" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Mis Propiedades</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    AlquilerController ac = new AlquilerController();
    PrecioController pc = new PrecioController();
    LinkedList<Propiedad> propiedades = (LinkedList<Propiedad>)request.getAttribute("propiedades");
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
                                <th scope="col">Nro. de Propiedad</th>
                                <th scope="col">Direccion</th>
                                <th scope="col">Piso</th>
                                <th scope="col">Departamento</th>
                                <th scope="col">Precio Actual</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Cliente</th>
                                <th scope="col">Fecha Fin Contrato</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            if (propiedades.isEmpty()) {
                            %>
                                <tr>
                                    <td colspan="8" style="text-align: center;">
                                        Todavía no has registrado ninguna propiedad
                                    </td>
                                </tr>
                            <%
                            } else {
                            	for (Propiedad prop : propiedades) {
                            		Precio p = new Precio();
                            		p = pc.getUltimoByPropiedad(prop);
                           	%>
                                    <tr>
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
                                            <%= p != null ? p.getValor() : "Error al cargar" %>
                                        </td>
                                        <%
                                        Alquiler alq = new Alquiler();
                                    	alq.setPropiedad(prop);
                                    	alq = ac.getUltimoByPropiedad(alq);
                                    	if (alq != null) {
                                    	%>
                                    		<td>
                                    			<%= alq.getEstado() %>
                                    		</td>
                                    		<td>
                                    			<%= alq.getCliente().getApellido() + " " + alq.getCliente().getNombre() %>
                                    		</td>
                                    		<%
                                    		if (alq.getEstado().equals("Confirmado")) {
                                    		%>
                                    			<td>
                                    				<%= alq.getFechaFinContrato() %>
                                    			</td>
                                    		<%
                                    		} else {
                                    		%>
                                    			<td>-</td>
                                    		<%
                                    		}
                                    		%>
                                    	<%
                                    	} else {
                                    	%>
                                			<td>Disponible</td>
                                			<td>-</td>
                                			<td>-</td>
                                    	<%
                                   		}
                                        %>
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
                    <button type="button" class="btn btn-primary p-2" style="width: 250px;" onclick='window.location.href="menu-anunciante.html"'>Volver</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>