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
    Cliente cli = (Cliente) session.getAttribute("usuario");
    String dni = cli.getDni();
    String nombre = cli.getNombre();
    String apellido = cli.getApellido();
	String fecha_nac = cli.getFechaNac().toString();
    String email = cli.getEmail();
    String telefono = cli.getTelefono();
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="d-flex justify-content-center align-items-center form-container" style="width: 500px;">
            <form method="post" action="clienteservlet" style="width: 400px;">
                <h2 class="text-center my-4">Actualizar mi perfil</h2>
                <div class="row">
                	<input type="hidden" value="<%= dni %>" name="dni">
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
                        <button type="submit" class="btn btn-primary w-100">Guardar cambios</button>
                    </div>
                    <div class="col-12 mb-4">
                        <button type="button" class="btn btn-secondary w-100"
                            onclick='window.location.href="menu-cliente.html"'>Volver</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- Bootstrap JavaScript Libraries -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>