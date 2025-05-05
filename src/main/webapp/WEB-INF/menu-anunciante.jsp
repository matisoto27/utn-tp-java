<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Menú Anunciante</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    	String mensaje = (String) request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100">
        <div class="d-flex justify-content-center align-items-center">
            <div class="list-group text-center">
                <a href="alquilerservlet?action=alquileresencursobyanunciante" class="list-group-item list-group-item-action mb-3">Alquileres pendientes o en curso</a>
                <a href="alquilerservlet?action=alquileresfinalizadosbyanunciante" class="list-group-item list-group-item-action mb-3">Alquileres finalizados</a>
                <a href="alquilerservlet?action=update" class="list-group-item list-group-item-action mb-3">Registrar contrato de alquiler</a>
                <a href="propiedadservlet?action=create" class="list-group-item list-group-item-action mb-3">Registrar propiedad</a>
                <a href="propiedadservlet?action=retrieve" class="list-group-item list-group-item-action mb-3">Ver mis propiedades</a>
                <a href="precioservlet?action=create" class="list-group-item list-group-item-action mb-3">Registrar precio de una propiedad</a>
                <a href="anuncianteservlet?action=update" class="list-group-item list-group-item-action mb-3">Actualizar datos de mi cuenta</a>
                <a href="anuncianteservlet?action=delete" class="list-group-item list-group-item-action mb-3" onclick="return confirm('¿Estás seguro de que deseas eliminar tu cuenta?')">Eliminar mi cuenta</a>
                <a href="logout" class="list-group-item list-group-item-action mb-3">Cerrar sesión</a>
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