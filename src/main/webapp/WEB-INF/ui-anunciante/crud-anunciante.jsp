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
        LinkedList<Anunciante> anunciantes = (LinkedList<Anunciante>) request.getAttribute("anunciantes");
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
                            <input type="number" class="form-control" style="background-color: #e9ecef;" name="id-anunciante" id="id-anunciante" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" name="nombre" id="nombre" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Correo Electrónico</label>
                            <input type="email" class="form-control" name="email" id="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="text" class="form-control" name="telefono" id="telefono" required>
                        </div>
                        <div class="mb-3">
                            <label for="usuario" class="form-label">Usuario</label>
                            <input type="text" class="form-control" name="usuario" id="usuario" required>
                        </div>
                        <div class="mb-4">
                            <label for="contrasena" class="form-label">Contraseña</label>
                            <input type="text" class="form-control" name="contrasena" id="contrasena" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100 mb-2">Registrar</button>
                        <button type="button" class="btn btn-primary w-100 mb-2" onclick="resetForm()">Limpiar formulario</button>
                        <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="menu-administrador.html"'>Volver</button>
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
                                    for (Anunciante anun : anunciantes) {
                                        int id_anunciante = anun.getIdAnunciante();
                                        String nombre = anun.getNombre();
                                        String email = anun.getEmail();
                                        String telefono = anun.getTelefono();
                                        String usuario = anun.getUsuario();
                                        String contrasena = anun.getContrasena();
                                %>
                                    <tr onclick="loadAnuncianteData('<%= id_anunciante %>', '<%= nombre %>', '<%= email %>', '<%= telefono %>', '<%= usuario %>', '<%= contrasena %>')">
                                        <td><%= id_anunciante %></td>
                                        <td><%= nombre %></td>
                                        <td><%= email %></td>
                                        <td><%= telefono %></td>
                                        <td><%= usuario %></td>
                                        <td><a href="anuncianteservlet?action=delete&id=<%= id_anunciante %>" class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar este anunciante?')">Eliminar</a></td>
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
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <script>
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
            if (idAnunciante) {
                form.action = 'anuncianteservlet?action=update';
                h2.textContent = 'Actualizar Anunciante';
                submitButton.textContent = "Guardar cambios";
            } else {
                form.action = 'anuncianteservlet?action=create';
                h2.textContent = 'Registrar Anunciante';
                submitButton.textContent = "Registrar";
            }
        }
        function resetForm() {
            document.getElementById('id-anunciante').value = '';
            document.getElementById('nombre').value = '';
            document.getElementById('email').value = '';
            document.getElementById('telefono').value = '';
            document.getElementById('usuario').value = '';
            document.getElementById('contrasena').value = '';
            var form = document.querySelector('form');
            var h2 = form.querySelector('h2');
            var submitButton = form.querySelector('button[type="submit"]');
            form.action = 'anuncianteservlet?action=create';
            h2.textContent = 'Registrar Anunciante';
            submitButton.textContent = "Registrar";
        }
    </script>
</body>

</html>