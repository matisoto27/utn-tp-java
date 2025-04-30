<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page import="java.util.List" %>
<%@page import="java.util.HashMap" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>CRUD Precio</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    	String errores = (String) request.getAttribute("errores") != null ? (String) request.getAttribute("errores") : "";
        Precio pre = (Precio) request.getAttribute("respuestas_correctas") != null ? (Precio) request.getAttribute("respuestas_correctas") : null;
    	String mensaje = (String) request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
        LinkedList<Precio> precios = (LinkedList<Precio>) request.getAttribute("precios");
        LinkedList<Propiedad> propiedades = (LinkedList<Propiedad>) request.getAttribute("propiedades");
        String id_anunciante = "";
        String nro_propiedad = "";
        String fecha_desde = "";
        String valor = "";
        if (pre != null) {
            id_anunciante = pre.getPropiedad().getAnunciante().getIdAnunciante() > 0 ? Integer.toString(pre.getPropiedad().getAnunciante().getIdAnunciante()) : "";
            nro_propiedad = pre.getPropiedad().getNroPropiedad() > 0 ? Integer.toString(pre.getPropiedad().getNroPropiedad()) : "";
            fecha_desde = pre.getFechaDesde() != null ? pre.getFechaDesde().toString() : "";
            valor = pre.getValor() > 0 ? Double.toString(pre.getValor()) : "";
        }
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100 bg-dark">
        <div class="container bg-white">
            <div class="row">
                <div class="col-4 border-end">
                    <form class="my-4" method="post" action="precioservlet?action=create">
                        <h2 class="text-center mb-4">Registrar Precio</h2>
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
                            <label for="fecha-desde" class="form-label">Vigente desde</label>
                            <input type="date" class="form-control" name="fecha-desde" id="fecha-desde" value="<%= fecha_desde %>">
                        </div>
                        <div class="mb-3">
                            <label for="valor" class="form-label">Valor</label>
                            <input type="text" class="form-control" name="valor" id="valor" value="<%= valor %>" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100 mb-2">Registrar Precio</button>
                        <button type="button" class="btn btn-primary w-100 mb-2" onclick='window.location.href="precioservlet?action=retrieve"'>Limpiar formulario</button>
                        <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="login"'>Volver</button>
                    </form>
                </div>
                <div class="col-8">
                    <div class="crud-table crud-table-cli">
                        <table class="table table-bordered table-hover text-center mb-0">
                            <thead>
                                <tr>
                                    <th scope="col">ID Anunciante</th>
                                    <th scope="col">Nro. Propiedad</th>
                                    <th scope="col">Vigente desde</th>
                                    <th scope="col">Valor</th>
                                    <th scope="col">Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (Precio p : precios) {
                                        id_anunciante = Integer.toString(p.getPropiedad().getAnunciante().getIdAnunciante());
                                        nro_propiedad = Integer.toString(p.getPropiedad().getNroPropiedad());
                                        fecha_desde = p.getFechaDesde().toString();
                                        valor = Double.toString(p.getValor());
                                %>
                                    <tr onclick="loadPrecioData('<%= id_anunciante %>', '<%= nro_propiedad %>', '<%= fecha_desde %>', '<%= valor %>')">
                                        <td><%= id_anunciante %></td>
                                        <td><%= nro_propiedad %></td>
                                        <td><%= fecha_desde %></td>
                                        <td><%= valor %></td>
                                        <td>
                                            <form method="POST" action="precioservlet?action=delete">
                                                <input type="hidden" name="id-anunciante" value="<%= id_anunciante %>">
                                                <input type="hidden" name="nro-propiedad" value="<%= nro_propiedad %>">
                                                <button class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar este precio?')">Eliminar</button>
                                            </form>
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
        inputFechaDesde = document.getElementById('fecha-desde');
        function loadPrecioData(idAnunciante, nroPropiedad, fechaDesde, valor) {
            document.getElementById('id-anunciante').value = idAnunciante;
            document.getElementById('nro-propiedad').value = nroPropiedad;
            document.getElementById('fecha-desde').value = fechaDesde;
            document.getElementById('valor').value = valor;
            var form = document.querySelector('form');
            var h2 = form.querySelector('h2');
            var submitButton = form.querySelector('button[type="submit"]');
            form.action = 'precioservlet?action=update';
            h2.textContent = 'Actualizar Precio';
            selectAnunciante = document.getElementById('id-anunciante');
            selectAnunciante.style.pointerEvents = "none";
            selectAnunciante.style.backgroundColor = "#e9ecef";
            selectPropiedad = document.getElementById('nro-propiedad');
            selectPropiedad.style.pointerEvents = "none";
            selectPropiedad.style.backgroundColor = "#e9ecef";
            inputFechaDesde.required = true;
            submitButton.textContent = "Guardar cambios";
        }
        window.onload = function () {
            inputFechaDesde.style.pointerEvents = "none";
            inputFechaDesde.style.backgroundColor = "#e9ecef";

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