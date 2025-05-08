<%@page import="entities.Anunciante" %>
<%@page import="entities.Propiedad" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>CRUD Propiedad</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
        String mensaje = (String) request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
        LinkedList<Propiedad> propiedades = (LinkedList<Propiedad>) request.getAttribute("propiedades");
        LinkedList<Anunciante> anunciantes = (LinkedList<Anunciante>) request.getAttribute("anunciantes");
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100 bg-dark">
        <div class="container bg-white">
            <div class="row">
                <div class="col-4 border-end">
                    <form class="my-4" method="post" action="propiedadservlet?action=create">
                        <h2 class="text-center mb-4">Registrar Propiedad</h2>
                        <div class="mb-3">
                            <label for="nro-propiedad" class="form-label">Nro. de Propiedad</label>
                            <input type="number" class="form-control" style="background-color: #e9ecef;" name="nro-propiedad" id="nro-propiedad" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="id-anunciante" class="form-label">Anunciante</label>
                            <select class="form-select" name="id-anunciante" id="id-anunciante" required>
                                <%
                                    for (Anunciante anun : anunciantes) {
                                        int id_anunciante = anun.getIdAnunciante();
                                        String nombre = anun.getNombre();
                                %>
                                            <option value="<%= id_anunciante %>"><%= nombre %></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="direccion" class="form-label">Dirección</label>
                            <input type="text" class="form-control" name="direccion" id="direccion" required>
                        </div>
                        <div class="mb-3">
                            <label for="piso" class="form-label">Piso</label>
                            <input type="number" class="form-control" name="piso" id="piso" min="0">
                        </div>
                        <div class="mb-3">
                            <label for="depto" class="form-label">Departamento</label>
                            <input type="text" class="form-control" name="depto" id="depto">
                        </div>
                        <button type="submit" class="btn btn-success w-100 mb-2">Registrar Propiedad</button>
                        <button type="button" class="btn btn-primary w-100 mb-2" onclick='window.location.href="propiedadservlet?action=retrieve"'>Limpiar formulario</button>
                        <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="login"'>Volver</button>
                    </form>
                </div>
                <div class="col-8">
                    <div class="crud-table crud-table-anun">
                        <table class="table table-bordered table-hover text-center mb-0">
                            <thead>
                                <tr>
                                    <th scope="col">Anunciante</th>
                                    <th scope="col">Nro. de Propiedad</th>
                                    <th scope="col">Dirección</th>
                                    <th scope="col">Piso</th>
                                    <th scope="col">Departamento</th>
                                    <th scope="col">Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (Propiedad prop : propiedades) {
                                        int nro_propiedad = prop.getNroPropiedad();
                                    	int id_anunciante = prop.getAnunciante().getIdAnunciante();
                                        String nombre_anunciante = prop.getAnunciante().getNombre();
                                        String direccion = prop.getDireccion();
                                        String piso = prop.getPiso() > 0 ? Integer.toString(prop.getPiso()) : "-";
                                        String piso_param = prop.getPiso() > 0 ? Integer.toString(prop.getPiso()) : "";
                                        String depto = prop.getDepto() != null ? prop.getDepto() : "-";
                                        String depto_param = prop.getDepto() != null ? prop.getDepto() : "";
                                %>
                                    <tr onclick="loadPropiedadData('<%= nro_propiedad %>', '<%= id_anunciante %>', '<%= direccion %>', '<%= piso_param %>', '<%= depto_param %>')">
                                        <td><%= nombre_anunciante %></td>
                                        <td><%= nro_propiedad %></td>
                                        <td><%= direccion %></td>
                                        <td><%= piso %></td>
                                        <td><%= depto %></td>
                                        <td>
                                <%          
                                        if (prop.getEstado() == null || !prop.getEstado().equals("En curso")) {
                             	%>
                                            <form method="POST" action="propiedadservlet?action=delete">
                                                <input type="hidden" name="id-anunciante" value="<%= id_anunciante %>"></input>
                                                <input type="hidden" name="nro-propiedad" value="<%= nro_propiedad %>"></input>
                                                <button class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar esta propiedad?')">Eliminar</button>
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
        function loadPropiedadData(nroPropiedad, idAnunciante, direccion, piso, depto) {
            document.getElementById('nro-propiedad').value = nroPropiedad;
            document.getElementById('id-anunciante').value = idAnunciante;
            document.getElementById('direccion').value = direccion;
            document.getElementById('piso').value = piso;
            document.getElementById('depto').value = depto;
            var form = document.querySelector('form');
            var h2 = form.querySelector('h2');
            var submitButton = form.querySelector('button[type="submit"]');
            form.action = 'propiedadservlet?action=update';
            h2.textContent = 'Actualizar Propiedad';
            selectAnunciante = document.getElementById('id-anunciante');
            selectAnunciante.style.pointerEvents = "none";
            selectAnunciante.style.backgroundColor = "#e9ecef";
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
    </script>
</body>

</html>