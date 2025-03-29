<%@page import="entities.Cliente" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>CRUD Cliente</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
        LinkedList<Cliente> clientes = (LinkedList<Cliente>) request.getAttribute("clientes");
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100 bg-dark">
        <div class="container bg-white">
            <div class="row">
                <div class="col-4 border-end">
                    <form class="my-4" method="post" action="clienteservlet?action=create">
                        <h2 class="text-center mb-4">Registrar Cliente</h2>
                        <div class="mb-3">
                            <label for="dni" class="form-label">DNI</label>
                            <input type="text" class="form-control" name="dni" id="dni" required>
                        </div>
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" name="nombre" id="nombre" required>
                        </div>
                        <div class="mb-3">
                            <label for="apellido" class="form-label">Apellido</label>
                            <input type="text" class="form-control" name="apellido" id="apellido" required>
                        </div>
                        <div class="mb-3">
                            <label for="fecha-nac" class="form-label">Fecha de Nacimiento</label>
                            <input type="date" class="form-control" name="fecha-nac" id="fecha-nac" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Correo Electrónico</label>
                            <input type="email" class="form-control" name="email" id="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="text" class="form-control" name="telefono" id="telefono" required>
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
                    <div class="crud-table crud-table-cli">
                        <table class="table table-bordered table-hover text-center mb-0">
                            <thead>
                                <tr>
                                    <th scope="col">DNI</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Apellido</th>
                                    <th scope="col">Fecha de Nacimiento</th>
                                    <th scope="col">Correo Electrónico</th>
                                    <th scope="col">Teléfono</th>
                                    <th scope="col">Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (Cliente cli : clientes) {
                                        String dni = cli.getDni();
                                        String nombre = cli.getNombre();
                                        String apellido = cli.getApellido();
                                        String fecha_nac = cli.getFechaNac().toString();
                                        String email = cli.getEmail();
                                        String telefono = cli.getTelefono();
                                        String contrasena = cli.getContrasena();
                                %>
                                    <tr onclick="loadClienteData('<%= dni %>', '<%= nombre %>', '<%= apellido %>', '<%= fecha_nac %>', '<%= email %>', '<%= telefono %>', '<%= contrasena %>')">
                                        <td><%= dni %></td>
                                        <td><%= nombre %></td>
                                        <td><%= apellido %></td>
                                        <td><%= fecha_nac %></td>
                                        <td><%= email %></td>
                                        <td><%= telefono %></td>
                                        <td><a href="clienteservlet?action=delete&dni=<%= dni %>" class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar este cliente?')">Eliminar</a></td>
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
        function loadClienteData(dni, nombre, apellido, fechaNac, email, telefono, contrasena) {
            document.getElementById('dni').value = dni;
            document.getElementById('nombre').value = nombre;
            document.getElementById('apellido').value = apellido;
            document.getElementById('fecha-nac').value = fechaNac;
            document.getElementById('email').value = email;
            document.getElementById('telefono').value = telefono;
            document.getElementById('contrasena').value = contrasena;
            var form = document.querySelector('form');
            var h2 = form.querySelector('h2');
            var submitButton = form.querySelector('button[type="submit"]');
            form.action = 'clienteservlet?action=update';
            h2.textContent = 'Actualizar Cliente';
            document.getElementById('dni').setAttribute('readonly', true);
            document.getElementById('dni').style.backgroundColor = "#e9ecef";
            submitButton.textContent = "Guardar cambios";
        }
        function resetForm() {
            document.getElementById('dni').value = '';
            document.getElementById('nombre').value = '';
            document.getElementById('apellido').value = '';
            document.getElementById('fecha-nac').value = '';
            document.getElementById('email').value = '';
            document.getElementById('telefono').value = '';
            document.getElementById('contrasena').value = '';
            var form = document.querySelector('form');
            var h2 = form.querySelector('h2');
            var submitButton = form.querySelector('button[type="submit"]');
            form.action = 'clienteservlet?action=create';
            h2.textContent = 'Registrar Cliente';
            document.getElementById('dni').setAttribute('readonly', false);
            document.getElementById('dni').style.backgroundColor = "";
            submitButton.textContent = "Registrar";
        }
    </script>
</body>

</html>