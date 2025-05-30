<%@page import="entities.*" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Actualizar mi perfil</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
        Anunciante anun = (Anunciante) request.getAttribute("respuestas_correctas") != null 
            ? (Anunciante) request.getAttribute("respuestas_correctas")
            : (Anunciante) request.getAttribute("anunciante");
        String errores = (String) request.getAttribute("errores") != null ? (String) request.getAttribute("errores") : "";
        String nombre = anun.getNombre() != null ? anun.getNombre() : "";
        String email = anun.getEmail() != null ? anun.getEmail() : "";
        String telefono = anun.getTelefono() != null ? anun.getTelefono() : "";
        String usuario = anun.getUsuario() != null ? anun.getUsuario() : "";
        String contrasena = anun.getContrasena() != null ? anun.getContrasena() : "";
        
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="d-flex justify-content-center align-items-center form-container" style="width: 500px;">
            <form method="post" action="anuncianteservlet?action=update" style="width: 400px;">
                <h2 class="text-center my-4">Actualizar mi cuenta</h2>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" name="nombre" id="nombre" value="<%= nombre %>" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="email" class="form-label">Correo Electrónico</label>
                        <input type="email" class="form-control" name="email" id="email" value="<%= email %>" required>
                    </div>
                    <div class="col-md-12 mb-3">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input type="text" class="form-control" name="telefono" id="telefono" value="<%= telefono %>" required>
                    </div>
                    <div class="col-md-12 mb-3">
                        <label for="usuario" class="form-label">Usuario</label>
                        <input type="text" class="form-control" name="usuario" id="usuario" value="<%= usuario %>" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label for="contrasena" class="form-label">Contraseña</label>
                        <input type="text" class="form-control" name="contrasena" id="contrasena" value="<%= contrasena %>" required>
                    </div>
                    <div class="col-md-6 mb-4">
                        <label for="contrasena2" class="form-label">Repetir Contraseña</label>
                        <input type="text" class="form-control" name="contrasena2" id="contrasena2" disabled>
                    </div>
                    <div class="col-12 mb-2">
                        <button type="submit" class="btn btn-primary w-100">Guardar cambios</button>
                    </div>
                    <div class="col-12 mb-4">
                        <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="login"'>Volver</button>
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
        var errores = "<%= errores %>";
        const modal = new bootstrap.Modal(document.getElementById('modalId'));
        const modalBody = document.getElementById('modal-body');
        if (errores) {
            var listaErrores = errores.split(',');
            var mensajes = {
                    "nombre_vacio": "El nombre no puede estar vacío.",
                    "nombre_invalido": "El nombre no tiene un formato válido.",
                    "nombre_unico": "Ya existe un anunciante con el nombre proporcionado.",

                    "email_vacio": "El correo electrónico no puede estar vacío.",
                    "email_invalido": "El correo electrónico no tiene un formato válido.",

                    "telefono_vacio": "El teléfono no puede estar vacío.",
                    "telefono_invalido": "El teléfono debe tener al menos 10 dígitos numéricos.",

                    "usuario_vacio": "El usuario no puede estar vacío.",
                    "usuario_invalido": "El usuario no tiene un formato válido.",
                    "usuario_unico": "Ya existe un anunciante con el usuario proporcionado.",
                    
                    "contrasena_vacia": "La contraseña no puede estar vacía.",
                    "contrasena_invalida": "La contraseña debe tener al menos 8 caracteres.",
                    "contrasenas_no_coinciden": "Las contraseñas no coinciden."
                };
            var message = listaErrores.map(function (error) {
                return mensajes[error];
            }).join('<br>');
            modalBody.innerHTML = message;
            modal.show();
        }
    };
    const inputContrasena = document.getElementById('contrasena');
    const inputRepetirContrasena = document.getElementById('contrasena2');
    function habilitarRepetirContrasena() {
        if (inputContrasena.value.trim() !== '') {
            inputRepetirContrasena.disabled = false;
            inputRepetirContrasena.setAttribute("required", "true");
        } else {
            inputRepetirContrasena.disabled = true;
            inputRepetirContrasena.setAttribute("required", "false");
        }
    }
    inputContrasena.addEventListener('input', habilitarRepetirContrasena);
    </script>
</body>

</html>