<%@page import="entities.*" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Actualizar Propiedad</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    Propiedad prop = (Propiedad) request.getAttribute("propiedad");
    String nro_propiedad = Integer.toString(prop.getNroPropiedad());
    String direccion = prop.getDireccion();
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="d-flex justify-content-center align-items-center form-container" style="width: 500px;">
            <form class="my-4" method="post" action="propiedadservlet?action=update">
                <input type="hidden" value="<%= nro_propiedad %>" name="nro-propiedad">
                <h2 class="text-center mb-4">Actualizar Propiedad</h2>
                <div class="mb-3">
                    <label for="direccion" class="form-label">Dirección</label>
                    <input type="text" class="form-control" name="direccion" id="direccion" value="<%= direccion %>" required>
                </div>
                <div class="mb-3">
                    <label for="piso" class="form-label">Piso</label>
                    <input type="number" class="form-control" name="piso" id="piso" <%= prop.getPiso() > 0 ? "value='" + prop.getPiso() + "'" : "" %>>
                </div>
                <div class="mb-4">
                    <label for="depto" class="form-label">Departamento</label>
                    <input type="text" class="form-control" name="depto" id="depto" <%= prop.getDepto() != null ? "value='" + prop.getDepto() + "'" : "" %>>
                </div>
                <button type="submit" class="btn btn-primary w-100 mb-2">Guardar cambios</button>
                <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="propiedadservlet?action=retrieve"'>Volver</button>
            </form>
        </div>
    </div>
    <!-- Bootstrap JavaScript Libraries -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>