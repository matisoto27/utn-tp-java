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
    	String errores = (String) request.getAttribute("errores") != null ? (String) request.getAttribute("errores") : "";
        Cliente cli = (Cliente) request.getAttribute("respuestas_correctas") != null ? (Cliente) request.getAttribute("respuestas_correctas") : null;
    	String mensaje = (String) request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
        LinkedList<Cliente> clientes = (LinkedList<Cliente>) request.getAttribute("clientes");
        String dni = "";
        String nombre = "";
        String apellido = "";
        String fecha_nac = "";
        String email = "";
        String telefono = "";
        String contrasena = "";
        if (cli != null) {
            dni = cli.getDni() != null ? cli.getDni() : "";
            nombre = cli.getNombre() != null ? cli.getNombre() : "";
            apellido = cli.getApellido() != null ? cli.getApellido() : "";
	        fecha_nac = cli.getFechaNac() != null ? cli.getFechaNac().toString() : "";
            email = cli.getEmail() != null ? cli.getEmail() : "";
            telefono = cli.getTelefono() != null ? cli.getTelefono() : "";
            contrasena = cli.getContrasena() != null ? cli.getContrasena() : "";
        }
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
                            <input type="text" class="form-control" name="dni" id="dni" value="<%= dni %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" name="nombre" id="nombre" value="<%= nombre %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="apellido" class="form-label">Apellido</label>
                            <input type="text" class="form-control" name="apellido" id="apellido" value="<%= apellido %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="fecha-nac" class="form-label">Fecha de Nacimiento</label>
                            <input type="date" class="form-control" name="fecha-nac" id="fecha-nac" value="<%= fecha_nac %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Correo Electrónico</label>
                            <input type="email" class="form-control" name="email" id="email" value="<%= email %>" required>
                        </div>
                        <div class="mb-3">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="text" class="form-control" name="telefono" id="telefono" value="<%= telefono %>" required>
                        </div>
                        <div class="mb-4">
                            <label for="contrasena" class="form-label">Contraseña</label>
                            <input type="text" class="form-control" name="contrasena" id="contrasena" value="<%= contrasena %>" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100 mb-2">Registrar Cliente</button>
                        <button type="button" class="btn btn-primary w-100 mb-2" onclick='window.location.href="clienteservlet?action=retrieve"'>Limpiar formulario</button>
                        <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="login"'>Volver</button>
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
                                    for (Cliente c : clientes) {
                                        dni = c.getDni();
                                        nombre = c.getNombre();
                                        apellido = c.getApellido();
                                        fecha_nac = c.getFechaNac().toString();
                                        email = c.getEmail();
                                        telefono = c.getTelefono();
                                        contrasena = c.getContrasena();
                                %>
                                    <tr onclick="loadClienteData('<%= dni %>', '<%= nombre %>', '<%= apellido %>', '<%= fecha_nac %>', '<%= email %>', '<%= telefono %>', '<%= contrasena %>')">
                                        <td><%= dni %></td>
                                        <td><%= nombre %></td>
                                        <td><%= apellido %></td>
                                        <td><%= fecha_nac %></td>
                                        <td><%= email %></td>
                                        <td><%= telefono %></td>
                                        <td>
                                            <form method="POST" action="clienteservlet?action=delete">
                                                <input type="hidden" name="dni" value="<%= dni %>">
                                                <button class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar este cliente?')">Eliminar</button>
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
        	if (errores) {
            	var listaErrores = errores.split(',');
            	var mensajes = {
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
            	var form = document.querySelector('form');
            	var h2 = form.querySelector('h2');
            	var submitButton = form.querySelector('button[type="submit"]');
                form.action = 'clienteservlet?action=update';
                h2.textContent = 'Actualizar Cliente';
                document.getElementById('dni').setAttribute('readonly', true);
                document.getElementById('dni').style.backgroundColor = "#e9ecef";
                submitButton.textContent = "Guardar cambios";
        	}
        	if (mensaje) {
                modalBody.innerHTML = mensaje;
                modal.show();
            }
        };
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
    </script>
</body>

</html>