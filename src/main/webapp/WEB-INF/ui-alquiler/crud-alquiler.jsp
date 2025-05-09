<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>CRUD Alquiler</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    	// String errores = (String) request.getAttribute("errores") != null ? (String) request.getAttribute("errores") : "";
    	Alquiler alq = (Alquiler) request.getAttribute("respuestas_correctas") != null ? (Alquiler) request.getAttribute("respuestas_correctas") : null;
        String mensaje = (String) request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
        LinkedList<Alquiler> alquileres = (LinkedList<Alquiler>) request.getAttribute("alquileres");
        LinkedList<Cliente> clientes = (LinkedList<Cliente>) request.getAttribute("clientes");
        LinkedList<Propiedad> propiedades = (LinkedList<Propiedad>) request.getAttribute("propiedades");
        String id_alquiler = "";
        String fecha_solicitado = "";
        String estado = "";
        String fecha_inicio_contrato = "";
        String fecha_fin_contrato = "";
        String fecha_renuncia = "";
        String puntuacion = "";
        String comentario = "";
        if (alq != null) {
        	id_alquiler = Integer.toString(alq.getIdAlquiler());
        	fecha_solicitado = alq.getFechaSolicitado().toString();
        	estado = alq.getEstado();
        	fecha_inicio_contrato = alq.getFechaInicioContrato().toString();
        	fecha_fin_contrato = alq.getFechaFinContrato().toString();
        	fecha_renuncia = alq.getFechaRenuncia().toString();
        	puntuacion = Integer.toString(alq.getPuntuacion());
        	comentario = alq.getComentario();
        }
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center bg-dark">
        <div class="container-fluid bg-white">
            <div class="row">
                <div class="col-4 border-end">
                    <form class="my-4" method="post" action="alquilerservlet?action=create">
                        <h2 class="text-center mb-4">Registrar Alquiler</h2>
                        <div class="mb-3">
                            <label for="id-alquiler" class="form-label">ID</label>
                            <input type="number" class="form-control" style="background-color: #e9ecef;" name="id-alquiler" id="id-alquiler" value="<%= id_alquiler %>" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="dni-cliente" class="form-label">Cliente</label>
                            <select class="form-select" name="dni-cliente" id="dni-cliente" required>
                                <option value="" disabled selected>Seleccione un cliente</option>
                                <%
                                    for (Cliente cli : clientes) {
                                        String dni_cliente = cli.getDni();
                                        String apellido_nombre = cli.getApellido() + " " + cli.getNombre();
                                %>
                                            <option value="<%= dni_cliente %>"><%= apellido_nombre %></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="nro-propiedad" class="form-label">Propiedad</label>
                            <input type="hidden" name="id-anunciante" id="id-anunciante">
                            <select class="form-select" name="nro-propiedad" id="nro-propiedad" required>
                                <option value="" disabled selected>Seleccione una propiedad</option>
                                <%
                                    for (Propiedad prop : propiedades) {
                                        int id = prop.getAnunciante().getIdAnunciante();
                                        int nro = prop.getNroPropiedad();
                                        String direccion = prop.getAnunciante().getNombre() + " - " + prop.getDireccion() + (prop.getPiso() > 0 ? " " + prop.getPiso() + prop.getDepto() : "");
                                %>
                                        <option value="<%= nro %>" data-anunciante="<%= id %>">
                                            <%= direccion %>
                                        </option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="fecha-solicitado" class="form-label">Fecha Solicitado</label>
                            <input type="date" class="form-control" name="fecha-solicitado" id="fecha-solicitado" value="<%= fecha_solicitado %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="estado" class="form-label">Estado</label>
                            <select class="form-select" name="estado" id="estado" required>
                                <option value="" disabled selected>Seleccione un estado</option>
                                <option value="Pendiente" <%= estado.equals("Pendiente") ? "selected" : "" %>>Pendiente</option>
                                <option value="En curso" <%= estado.equals("En curso") ? "selected" : "" %>>En curso</option>
                                <option value="Cancelado" <%= estado.equals("Cancelado") ? "selected" : "" %>>Cancelado</option>
                                <option value="Finalizado" <%= estado.equals("Finalizado") ? "selected" : "" %>>Finalizado</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="fecha-inicio-contrato" class="form-label">Fecha Inicio Contrato</label>
                            <input type="date" class="form-control" name="fecha-inicio-contrato" id="fecha-inicio-contrato" value="<%= fecha_inicio_contrato %>">
                        </div>
                        <div class="mb-3">
                            <label for="fecha-fin-contrato" class="form-label">Fecha Fin Contrato</label>
                            <input type="date" class="form-control" name="fecha-fin-contrato" id="fecha-fin-contrato" value="<%= fecha_fin_contrato %>">
                        </div>
                        <div class="mb-3">
                            <label for="fecha-renuncia" class="form-label">Fecha Renuncia</label>
                            <input type="date" class="form-control" name="fecha-renuncia" id="fecha-renuncia" value="<%= fecha_renuncia %>">
                        </div>
                        <div class="mb-3">
                            <label for="puntuacion" class="form-label">Puntuación</label>
                            <input type="number" class="form-control" name="puntuacion" id="puntuacion" min="1" max="10" value="<%= puntuacion %>">
                        </div>
                        <div class="mb-3">
                            <label for="comentario" class="form-label">Comentario</label>
                            <input type="text" class="form-control" name="comentario" id="comentario" value="<%= comentario %>">
                        </div>
                        <button type="submit" class="btn btn-success w-100 mb-2">Registrar Alquiler</button>
                        <button type="button" class="btn btn-primary w-100 mb-2" onclick='window.location.href="alquilerservlet?action=retrieve"'>Limpiar formulario</button>
                        <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="login"'>Volver</button>
                    </form>
                </div>
                <div class="col-8">
                    <div class="crud-table crud-table-anun">
                        <table class="table table-bordered table-hover text-center mb-0">
                            <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Cliente</th>
                                    <th scope="col">Propiedad</th>
                                    <th scope="col">Fecha Solicitado</th>
                                    <th scope="col">Estado</th>
                                    <th scope="col">Fecha Inicio Contrato</th>
                                    <th scope="col">Fecha Fin Contrato</th>
                                    <th scope="col">Fecha Renuncia</th>
                                    <th scope="col">Puntuación</th>
                                    <th scope="col">Comentario</th>
                                    <th scope="col">Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (Alquiler a : alquileres) {
                                        id_alquiler = Integer.toString(a.getIdAlquiler());
                                        String cliente = a.getCliente().getApellido() + " " + a.getCliente().getNombre();
                                        String dni_cliente = a.getCliente().getDni();
                                        String propiedad = a.getPropiedad().getAnunciante().getNombre() + "<br>" + a.getPropiedad().getDireccion() + (a.getPropiedad().getPiso() > 0 ? " " + a.getPropiedad().getPiso() + a.getPropiedad().getDepto() : "");
                                        int id_anunciante = a.getPropiedad().getAnunciante().getIdAnunciante();
                                        int nro_propiedad = a.getPropiedad().getNroPropiedad();
                                        fecha_solicitado = a.getFechaSolicitado().toString();
                                        estado = a.getEstado();
                                        fecha_inicio_contrato = a.getFechaInicioContrato() != null ? a.getFechaInicioContrato().toString() : null;
                                        fecha_fin_contrato = a.getFechaFinContrato() != null ? a.getFechaFinContrato().toString() : null;
                                        fecha_renuncia = a.getFechaRenuncia() != null ? a.getFechaRenuncia().toString() : null;
                                        puntuacion = a.getPuntuacion() > 0 ? Integer.toString(a.getPuntuacion()) : "";
                                        comentario = a.getComentario() != null ? a.getComentario() : "";
                                %>
                                    <tr onclick="loadAlquilerData('<%= id_alquiler %>', '<%= dni_cliente %>', '<%= id_anunciante %>', '<%= nro_propiedad %>', '<%= fecha_solicitado %>', '<%= estado %>', '<%= fecha_inicio_contrato %>', '<%= fecha_fin_contrato %>', '<%= fecha_renuncia %>', '<%= puntuacion %>', '<%= comentario %>')">
                                        <td><%= id_alquiler %></td>
                                        <td><%= cliente %></td>
                                        <td><%= propiedad %></td>
                                        <td><%= fecha_solicitado %></td>
                                        <td><%= estado %></td>
                                        <td><%= fecha_inicio_contrato != null ? fecha_inicio_contrato : "-" %></td>
                                        <td><%= fecha_fin_contrato != null ? fecha_fin_contrato : "-" %></td>
                                        <td><%= fecha_renuncia != null ? fecha_renuncia : "-" %></td>
                                        <td><%= !puntuacion.isEmpty() ? puntuacion : "-" %></td>
                                        <td><%= !comentario.isEmpty() ? comentario : "-" %></td>
                                        <td>
                                <%          
                                        if (estado == null || !estado.equals("En curso")) {
                             	%>
                                            <form method="POST" action="alquilerservlet?action=delete">
                                                <input type="hidden" name="id-alquiler" value="<%= id_alquiler %>"></input>
                                                <button class="btn btn-danger" onclick="event.stopPropagation(); return confirm('¿Estás seguro de que deseas eliminar este alquiler?')">Eliminar</button>
                                            </form>
                             	<%
                                        }
                                %>
                                        </td>
                                    </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
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
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <script>
        function loadAlquilerData(idAlquiler, dniCliente, idAnunciante, nroPropiedad, fechaSolicitado, estado, fechaInicioContrato, fechaFinContrato, fechaRenuncia, puntuacion, comentario) {
            document.getElementById('id-alquiler').value = idAlquiler;
            document.getElementById('dni-cliente').value = dniCliente;
            document.getElementById('id-anunciante').value = idAnunciante;
            document.getElementById('nro-propiedad').value = nroPropiedad;
            document.getElementById('fecha-solicitado').value = fechaSolicitado;
            document.getElementById('estado').value = estado;
            document.getElementById('fecha-inicio-contrato').value = fechaInicioContrato;
            document.getElementById('fecha-fin-contrato').value = fechaFinContrato;
            document.getElementById('fecha-renuncia').value = fechaRenuncia;
            document.getElementById('puntuacion').value = puntuacion > 0 ? puntuacion : "";
            document.getElementById('comentario').value = comentario;
            var form = document.querySelector('form');
            var h2 = form.querySelector('h2');
            var submitButton = form.querySelector('button[type="submit"]');
            form.action = 'alquilerservlet?action=update';
            h2.textContent = 'Actualizar Alquiler';
            submitButton.textContent = "Guardar cambios";
        }
        window.onload = function () {
        	var mensaje = "<%= mensaje %>";
            const modal = new bootstrap.Modal(document.getElementById('modalId'));
            const modalBody = document.getElementById('modal-body');
            if (mensaje) {
                modalBody.innerHTML = mensaje;
                modal.show();
            }
        };
        document.getElementById('nro-propiedad').addEventListener('change', function() {
            var select = this;
            var selectedOption = select.options[select.selectedIndex];
            var idAnunciante = selectedOption.getAttribute('data-anunciante');
            document.getElementById('id-anunciante').value = idAnunciante;
        });
    </script>
</body>

</html>