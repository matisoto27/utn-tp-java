<%@page import="entities.*" %>
<%@page import="logic.AlquilerController" %>
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
    Cliente cli = (Cliente) request.getSession().getAttribute("usuario");
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
                                <th scope="col">Puntuación</th>
                                <th scope="col">Ver comentarios</th>
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
                                    String puntuacion = null;
                                    LinkedList<Alquiler> alquileres = new AlquilerController().getAlquileresByPropiedad(prop);
                                    int sumatoria = 0;
                                    int contador = 0;
                                    StringBuilder comentarios = new StringBuilder();
                                    if (!alquileres.isEmpty()) {
                                        for (Alquiler alq : alquileres) {
                                            if (alq.getPuntuacion() != 0) {
                                                sumatoria += alq.getPuntuacion();
                                                contador++;
                                            }
                                            if (alq.getComentario() != null && !alq.getComentario().trim().isEmpty()) {
                                                comentarios.append("<strong>")
                                                .append(alq.getCliente().getApellido() + " " + alq.getCliente().getNombre())
                                                .append(":</strong> ")
                                                .append(alq.getComentario().replace("\"", "&quot;")) // Evitar romper atributos HTML
                                                .append("<br>");
                                            }
                                        }
                                        puntuacion = contador > 0 ? Integer.toString(sumatoria / contador) : "-";
                                    } else {
                                        puntuacion = "-";
                                    }
                                    String estado = prop.getEstado();
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
                                            <%= puntuacion %>
                                        </td>
                                        <td><%
                                                if (comentarios.toString().isEmpty()) {
                                            %>
                                                    No hay comentarios disponibles para esta propiedad.
                                            <%
                                                } else {
                                                    String comentariosHTML = comentarios.toString();
                                            %>
                                                    <button class="btn btn-link p-0" onclick="abrirModal('<%= comentariosHTML %>')">Mostrar</button>
                                            <%
                                                }
                                            %>
                                            
                                        </td>
                                        <td>
                                        	<%
                                        		if (estado != null && estado.equals("Pendiente")) {
                                                    Alquiler alq = new Alquiler();
                                                    alq.setPropiedad(new Propiedad());
                                                    alq.getPropiedad().setAnunciante(new Anunciante());
                                                    alq.getPropiedad().getAnunciante().setIdAnunciante(id_anunciante);
                                                    alq.getPropiedad().setNroPropiedad(nro_propiedad);
                                                    alq = new AlquilerController().getUltimoByPropiedad(alq);
                                                    if (alq != null && alq.getCliente().getDni().equals(cli.getDni())) {
                                        	%>
                                                        <form method="POST" action="alquilerservlet?action=delete" onsubmit="return confirm('¿Estás seguro de que quieres cancelar la reserva?');">
                                                            <input type="hidden" name="id-anunciante" value="<%= id_anunciante %>"></input>
                                                            <input type="hidden" name="nro-propiedad" value="<%= nro_propiedad %>"></input>
                                                            <button class="btn btn-danger">Cancelar reserva</button>
                                                        </form>
                                        	<%
                                                    } else {
                                            %>
                                                        <button class="btn btn-secondary">Reservado</button>
                                            <%
                                                    }
                                        		} else {
                                        	%>
                                        			<form method="POST" action="alquilerservlet?action=create" onsubmit="return confirm('¿Estás seguro de que quieres reservar esta propiedad?');">
                                                		<input type="hidden" name="id-anunciante" value="<%= id_anunciante %>"></input>
                                                		<input type="hidden" name="nro-propiedad" value="<%= nro_propiedad %>"></input>
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
    <div class="modal fade" id="comentariosModal" tabindex="-1" aria-labelledby="comentariosModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="comentariosModalLabel">Comentarios</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body" id="comentariosContent"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <script>
        function abrirModal(comentariosHTML) {
            const contenido = document.getElementById("comentariosContent");
            contenido.innerHTML = comentariosHTML;
            const modal = new bootstrap.Modal(document.getElementById('comentariosModal'));
            modal.show();
        }
    </script>
</body>

</html>