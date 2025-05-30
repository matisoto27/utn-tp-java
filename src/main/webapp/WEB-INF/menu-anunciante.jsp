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
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="row">
            <div class="col-6 mb-3">
                <a href="alquilerservlet?action=alquileresencursobyanunciante" class="btn-custom btn-color-1 w-100">Alquileres pendientes o en curso</a>
            </div>
            <div class="col-6 mb-3">
                <a href="alquilerservlet?action=alquilerescanceladosbyanunciante" class="btn-custom btn-color-2 w-100">Alquileres cancelados</a>
            </div>
            <div class="col-6 mb-3">
                <a href="alquilerservlet?action=alquileresfinalizadosbyanunciante" class="btn-custom btn-color-2 w-100">Alquileres finalizados</a>
            </div>
            <div class="col-6 mb-3">
                <a href="alquilerservlet?action=update" class="btn-custom btn-color-1 w-100">Registrar contrato de alquiler</a>
            </div>
            <div class="col-6 mb-3">
                <a href="propiedadservlet?action=create" class="btn-custom btn-color-1 w-100">Registrar propiedad</a>
            </div>
            <div class="col-6 mb-3">
                <a href="propiedadservlet?action=retrieve" class="btn-custom btn-color-2 w-100">Ver mis propiedades</a>
            </div>
            <div class="col-6 mb-3">
                <a href="precioservlet?action=create" class="btn-custom btn-color-2 w-100">Registrar precio de una propiedad</a>
            </div>
            <div class="col-6 mb-3">
                <a href="precioservlet?action=retrieve" class="btn-custom btn-color-1 w-100">Ver histórico de precios</a>
            </div>
            <div class="col-6 mb-3">
                <a href="anuncianteservlet?action=update" class="btn-custom btn-color-1 w-100">Actualizar datos de mi cuenta</a>
            </div>
            <div class="col-6 mb-3">
                <form action="anuncianteservlet?action=delete" method="POST" onsubmit="return confirm('¿Estás seguro de que deseas eliminar tu cuenta?')">
                    <button type="submit" class="w-100" id="eliminar-cuenta">Eliminar mi cuenta</button>
                </form>
            </div>
            <div class="col-12 mb-3">
                <a href="logout" class="btn-custom w-100" id="cerrar-sesion">Cerrar sesión</a>
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