<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Menú Administrador</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100">
        <div class="d-flex justify-content-center align-items-center">
            <div class="list-group text-center">
                <a href="anuncianteservlet?action=retrieve" class="list-group-item list-group-item-action mb-3">CRUD Anunciante</a>
                <a href="clienteservlet?action=retrieve" class="list-group-item list-group-item-action mb-3">CRUD Cliente</a>
                <a href="propiedadservlet?action=retrieve" class="list-group-item list-group-item-action mb-3">CRUD Propiedad</a>
                <!-- Falta cerrar la sesion -->
                <a href="index.html" class="list-group-item list-group-item-action mb-3">Cerrar Sesión</a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>