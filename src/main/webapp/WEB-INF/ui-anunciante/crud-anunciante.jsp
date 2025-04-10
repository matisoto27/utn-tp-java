<%@page import="entities.Anunciante" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>CRUD Anunciante</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
        Anunciante anun = (Anunciante) request.getAttribute("respuestas_correctas");
        String errores = (String) request.getAttribute("errores") != null ? (String) request.getAttribute("errores") : "";
    	String mensaje = (String) request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
        LinkedList<Anunciante> anunciantes = (LinkedList<Anunciante>) request.getAttribute("anunciantes");
        String id_anunciante = "";
        String nombre = "";
        String email = "";
        String telefono = "";
        String usuario = "";
        String contrasena = "";
        if (anun != null) {
            id_anunciante = anun.getIdAnunciante() > 0 ? Integer.toString(anun.getIdAnunciante()) : "";
            nombre = anun.getNombre() != null ? anun.getNombre() : "";
            email = anun.getEmail() != null ? anun.getEmail() : "";
            telefono = anun.getTelefono() != null ? anun.getTelefono() : "";
            usuario = anun.getUsuario() != null ? anun.getUsuario() : "";
            contrasena = anun.getContrasena() != null ? anun.getContrasena() : "";
        }
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100 bg-dark">
        <div class="container bg-white">
            <div class="row">
                <div class="col-4 border-end">
                    <form class="my-4" method="post" action="anuncianteservlet?action=create">
                        <h2 class="text-center mb-4">Registrar Anunciante</h2>
                        <div class="mb-3">
                            <label for="id-anunciante" class="form-label">ID</label>
                            <input type="number" class="form-control" style="background-color: #e9ecef;" name="id-anunciante" id="id-anunciante" value="<%= id_anunciante %>" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" name="nombre" id="nombre" value="<%= nombre %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Correo Electrónico</label>
                            <input type="email" class="form-control" name="email" id="email" value="<%= email %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="text" class="form-control" name="telefono" id="telefono" value="<%= telefono %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="usuario" class="form-label">Usuario</label>
                            <input type="text" class="form-control" name="usuario" id="usuario" value="<%= usuario %>" required>
                        </div>
                        <div class="mb-4">
                            <label for="contrasena" class="form-label">Contraseña</label>
                            <input type="text" class="form-control" name="contrasena" id="contrasena" value="<%= contrasena %>" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100 mb-2">Registrar Anunciante</button>
                        <button type="button" class="btn btn-primary w-100 mb-2" onclick='window.location.href="anuncianteservlet?action=retrieve"'>Limpiar formulario</button>
                        <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="login"'>Volver</button>
                    </form>
                </div>
                <div class="col-8">
                    <div class="crud-table crud-table-anun">
                        <table class="table table-bordered table-hover text-center mb-0">
                            <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Correo Electrónico</th>
                                    <th scope="col">Teléfono</th>
                                    <th scope="col">Usuario</th>
                                    <th scope="col">Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (Anunciante a : anunciantes) {
                                       	id_anunciante = Integer.toString(a.getIdAnunciante());
                                        nombre = a.getNombre();
                                        email = a.getEmail();
                                        telefono = a.getTelefono();
                                        usuario = a.getUsuario();
                                        contrasena = a.getContrasena();
                                %>
                                    <tr onclick="loadAnuncianteData('<%= id_anunciante %>', '<%= nombre %>', '<%= email %>', '<%= telefono %>', '<%= usuario %>', '<%= contrasena %>')">
                                        <td><%= id_anunciante %></td>
                                        <td><%= nombre %></td>
                                        <td><%= email %></td>
                                        <td><%= telefono %></td>
                                        <td><%= usuario %></td>
                                        <td>
                                            <form method="POST" action="anuncianteservlet?action=delete">
                                                <input type="hidden" name="id-anunciante" value="<%= id_anunciante %>"></input>
                                                <button class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar este anunciante?')">Eliminar</button>
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
        window.onload = function () {
        	var mensaje = "<%= mensaje %>";
        	var errores = "<%= errores %>";
       	 	const modal = new bootstrap.Modal(document.getElementById('modalId'));
        	const modalBody = document.getElementById('modal-body');
        	var form = document.querySelector('form');
            var h2 = form.querySelector('h2');
            var submitButton = form.querySelector('button[type="submit"]');
            if (<%= anun != null && anun.getIdAnunciante() > 0 %>) {
                form.action = 'anuncianteservlet?action=update';
                h2.textContent = 'Actualizar Anunciante';
                submitButton.textContent = "Guardar cambios";
            }
            if (errores) {
            	var listaErrores = errores.split(',');
            	var mensajes = {
                    "nombre_unico": "Ya existe un anunciante con el nombre proporcionado.",
                    "email_invalido": "El correo electrónico no tiene un formato válido.",
                    "telefono_invalido": "El teléfono debe tener al menos 10 dígitos numéricos.",
                    "usuario_unico": "Ya existe un anunciante con el usuario proporcionado.",
                    "contrasena_invalida": "La contraseña debe tener 8 caracteres.",
                    "contrasenas_no_coinciden": "Las contraseñas no coinciden."
                };
            	var message = listaErrores.map(function (error) {
                	return mensajes[error];
            	}).join('<br>');
            	modalBody.innerHTML = message;
            	modal.show();
        	}
        	if (mensaje) {
                modalBody.innerHTML = mensaje;
                modal.show();
            }
        };
        function loadAnuncianteData(idAnunciante, nombre, email, telefono, usuario, contrasena) {
            document.getElementById('id-anunciante').value = idAnunciante;
            document.getElementById('nombre').value = nombre;
            document.getElementById('email').value = email;
            document.getElementById('telefono').value = telefono;
            document.getElementById('usuario').value = usuario;
            document.getElementById('contrasena').value = contrasena;
            var form = document.querySelector('form');
            var h2 = form.querySelector('h2');
            var submitButton = form.querySelector('button[type="submit"]');
            form.action = 'anuncianteservlet?action=update';
            h2.textContent = 'Actualizar Anunciante';
            submitButton.textContent = "Guardar cambios";
        }
    </script>
</body>

</html>