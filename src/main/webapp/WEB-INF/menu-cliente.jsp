<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Menú Cliente</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    	String mensaje = null;
    	String mensaje_session = (String) session.getAttribute("mensaje");
    	String mensaje_request = (String) request.getAttribute("mensaje");
    	if (mensaje_session != null) {
    		mensaje = mensaje_session;
    		request.getSession().removeAttribute("mensaje");
    	} else if (mensaje_request != null) {
    		mensaje = mensaje_request;
    	} else {
    		mensaje = "";
    	}
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100">
        <div class="d-flex justify-content-center align-items-center">
            <div class="list-group text-center">
                <a href="anuncianteservlet?action=retrieve" class="list-group-item list-group-item-action mb-3">Conoce a los Anunciantes de Propiedades</a>
                <a href="propiedadservlet?action=retrieve" class="list-group-item list-group-item-action mb-3">Ver Propiedades para Alquilar</a>
                <a href="clienteservlet?action=update" class="list-group-item list-group-item-action mb-3">Actualizar mi perfil</a>
                <a href="clienteservlet?action=delete" class="list-group-item list-group-item-action mb-3" onclick="return confirm('¿Estás seguro de que deseas eliminar tu cuenta?')">Eliminar mi cuenta</a>
                <a href="logout" class="list-group-item list-group-item-action mb-3">Cerrar Sesión</a>
            </div>
        </div>
    </div>
    <div class="modal fade" id="modalId" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog" aria-labelledby="modalTitleId" aria-hidden="true">
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
        const urlParams = new URLSearchParams(window.location.search);
        const param = urlParams.get('modal');
        const modal = new bootstrap.Modal(document.getElementById('modalId'));
        const modalBody = document.getElementById('modal-body');
        if (param === 'update') {
            const message = "¡Datos actualizados con éxito!";
            modalBody.textContent = message;
            modal.show();
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