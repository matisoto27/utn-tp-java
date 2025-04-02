<%@page import="entities.*" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Registrar Cliente</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    Cliente cliente = (Cliente) request.getAttribute("respuestas");
    String dni = (cliente != null && cliente.getDni() != null) ? cliente.getDni() : "";
    String nombre = (cliente != null && cliente.getNombre() != null) ? cliente.getNombre() : "";
    String apellido = (cliente != null && cliente.getApellido() != null) ? cliente.getApellido() : "";
    String fecha_nac = (cliente != null && cliente.getFechaNac() != null) ? cliente.getFechaNac().toString() : "";
    String email = (cliente != null && cliente.getEmail() != null) ? cliente.getEmail() : "";
    String telefono = (cliente != null && cliente.getTelefono() != null) ? cliente.getTelefono() : "";
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="d-flex justify-content-center align-items-center form-container" style="width: 500px;">
            <form method="post" action="signupcliente" style="width: 400px;">
                <h2 class="text-center my-4">Registrar Cliente</h2>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="dni" class="form-label">DNI</label>
                        <input type="text" class="form-control" name="dni" id="dni" value="<%= dni %>" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" name="nombre" id="nombre" value="<%= nombre %>" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="apellido" class="form-label">Apellido</label>
                        <input type="text" class="form-control" name="apellido" id="apellido" value="<%= apellido %>" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="fecha_nac" class="form-label">Fecha de Nacimiento</label>
                        <input type="date" class="form-control" name="fecha_nac" id="fecha_nac" value="<%= fecha_nac %>" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="email" class="form-label">Correo Electrónico</label>
                        <input type="email" class="form-control" name="email" id="email" value="<%= email %>" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input type="text" class="form-control" name="telefono" id="telefono" value="<%= telefono %>" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label for="contrasena" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" name="contrasena" id="contrasena" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label for="contrasena2" class="form-label">Repetir Contraseña</label>
                        <input type="password" class="form-control" name="contrasena2" id="contrasena2" required>
                    </div>
                    <div class="col-12 mb-2">
                        <button type="submit" class="btn btn-primary w-100">Registrar</button>
                    </div>
                    <div class="col-12 mb-4">
                        <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="signup.html"'>Volver</button>
                    </div>
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
            var errores = "<%= request.getAttribute("errores") != null ? request.getAttribute("errores") : "" %>";
            const modal = new bootstrap.Modal(document.getElementById('modalId'));
            const modalBody = document.getElementById('modal-body');
            if (errores) {
                var listaErrores = errores.split(',');
                var mensajes = {
                    "dni_invalido": "El DNI debe tener 8 dígitos numéricos.",
                    "dni_unico": "Ya existe un cliente con ese DNI.",
                    "nombre_invalido": "El nombre solo puede contener letras.",
                    "apellido_invalido": "El apellido solo puede contener letras.",
                    "edad_invalida": "Debe tener al menos 18 años para registrarse.",
                    "email_invalido": "El correo electrónico no tiene un formato válido.",
                    "telefono_invalido": "El teléfono debe tener al menos 10 dígitos numéricos.",
                    "contrasena_invalida": "La contraseña debe tener 8 caracteres.",
                    "contrasenas_no_coinciden": "Las contraseñas no coinciden."
                };
                var message = listaErrores.map(function (error) {
                    return mensajes[error];
                }).join('<br>');
                modalBody.innerHTML = message;
                modal.show();
            }
        };
    </script>
</body>

</html>