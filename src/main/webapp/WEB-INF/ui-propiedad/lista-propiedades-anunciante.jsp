<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
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
    	String mensaje = (String) request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
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
                                <th scope="col">Direccion</th>
                                <th scope="col">Piso</th>
                                <th scope="col">Departamento</th>
                                <th scope="col">Precio Actual</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Actualizar</th>
                                <th scope="col">Eliminar</th>
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
                            		int nro_propiedad = prop.getNroPropiedad();
                   		%>
                                    <tr>
                                        <td>
                                            <%= prop.getDireccion() %>
                                        </td>
                                        <td>
                                            <%= prop.getPiso() > 0 ? prop.getPiso() : "-" %>
                                        </td>
                                        <td>
                                            <%= prop.getDepto() != null ? prop.getDepto() : "-" %>
                                        </td>
                                        <td>
                                            <%= prop.getPrecioActual() != 0 ? prop.getPrecioActual() : "Sin datos" %>
                                        </td>
                                  	<%
                                    	if (prop.getEstado() != null && prop.getEstado().equals("Confirmado")) {
                                   	%>
                                    		<td>Alquilada</td>
                                  	<%
                                    	} else if (prop.getEstado() != null && prop.getEstado().equals("Pendiente")) {
                                  	%>
                                    		<td>Pendiente</td>
                                 	<%
                                   		} else {
                                	%>
                                        	<td>Disponible</td>
                                	<%
                                   		}
                               		%>
                                       <td>
                                    		<a href="propiedadservlet?action=update&nro=<%= nro_propiedad %>" class="btn btn-primary">Actualizar datos</a>
                                       </td>
                                       <td>
                                            <form method="POST" action="propiedadservlet?action=delete">
                                                <input type="hidden" name="nro-propiedad" value="<%= prop.getNroPropiedad() %>"></input>
                                                <button class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar esta propiedad?')">Eliminar</button>
                                            </form>
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
                    <button type="button" class="btn btn-primary p-2" style="width: 250px;" onclick='window.location.href="menu-anunciante.html"'>Volver</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="modalId" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog"
        aria-labelledby="modalTitleId" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalTitleId">Mensaje</h5>
                </div>
                <div class="modal-body" id="modal-body">Body</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap JavaScript Libraries -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <script>
        window.onload = function () {
        	var mensaje = "<%= mensaje %>";
            const modal = new bootstrap.Modal(document.getElementById('modalId'));
            const modalBody = document.getElementById('modal-body');
            if (mensaje) {
                modalBody.innerHTML = mensaje;
                modal.show();
            }
        };
    </script>
</body>

</html>