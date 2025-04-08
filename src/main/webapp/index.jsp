<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Iniciar Sesión</title>
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
        <div class="d-flex justify-content-center align-items-center form-container">
            <form method="post" action="login" id="login-form">
                <div class="my-4">
                    <h3 class="text-center">Iniciar Sesión</h3>
                </div>
                <div class="mb-3">
                    <label for="tipo" class="form-label">Seleccionar Tipo</label>
                    <select class="form-select" name="tipo" id="tipo">
                        <option value="anunciante">Soy Anunciante</option>
                        <option value="cliente">Soy Cliente</option>
                        <option value="administrador">Soy Administrador</option>
                    </select>
                </div>
                <div class="mb-3" id="campo-cliente">
                    <label for="dni" class="form-label">DNI</label>
                    <input type="text" class="form-control" name="dni" id="dni">
                </div>
                <div class="mb-3" id="campo-anunciante-adm" style="display: none;">
                    <label for="usuario" class="form-label">Usuario</label>
                    <input type="text" class="form-control" name="usuario" id="usuario">
                </div>
                <div class="mb-4">
                    <label for="contrasena" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" name="contrasena" id="contrasena" required>
                </div>
                <div class="mb-2">
                    <button type="submit" class="btn btn-primary w-100">Ingresar</button>
                </div>
                <div class="mb-4 text-center">
                    <a href="signup.html">Registrarse</a>
                </div>
            </form>
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

        // Actualizar campo del formulario dependiendo del tipo
        document.getElementById('tipo').addEventListener('change', function () {
            const tipoSeleccionado = this.value;
            const campoCliente = document.getElementById('campo-cliente');
            const campoAnunciante = document.getElementById('campo-anunciante-adm');
            if (tipoSeleccionado === 'cliente') {
                campoCliente.style.display = 'block';
                campoAnunciante.style.display = 'none';
            } else {
                campoCliente.style.display = 'none';
                campoAnunciante.style.display = 'block';
            }
        });

        // Validar antes de enviar el formulario
        document.getElementById('login-form').addEventListener('submit', function (e) {
            const tipoSeleccionado = document.getElementById('tipo').value;
            const dni = document.getElementById('dni').value.trim();
            const usuario = document.getElementById('usuario').value.trim();
            if ((tipoSeleccionado === 'cliente' && !dni) || (tipoSeleccionado === 'anunciante' && !usuario)) {
                // Evitar el envío del formulario
                e.preventDefault();
                alert('Por favor, ingrese su ' + (tipoSeleccionado === 'cliente' ? 'DNI' : 'nombre de usuario') + '.');
            }
        });

        // Disparar el evento de cambio para asegurar que se muestre el campo correcto inicialmente
        document.getElementById('tipo').dispatchEvent(new Event('change'));        
    </script>
</body>

</html>