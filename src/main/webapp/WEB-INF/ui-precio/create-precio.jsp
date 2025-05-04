<%@page import="entities.*" %>
<%@page import="java.time.LocalDate" %>
<%@page import="java.util.LinkedList" %>
<%@page import="logic.PrecioController" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Registrar Precio de una Propiedad</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    LinkedList<Propiedad> propiedades = (LinkedList<Propiedad>)request.getAttribute("propiedades");
    PrecioController pc = new PrecioController();
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="d-flex justify-content-center align-items-center form-container" style="width: 500px;">
            <form method="post" action="precioservlet?action=create" style="width: 300px;">
                <h2 class="text-center my-4">Registrar Precio</h2>
                <div class="mb-3">
                    <label for="nro-propiedad" class="form-label">Seleccione una Propiedad</label>
                    <select class="form-select" name="nro-propiedad" id="nro-propiedad" required>
                        <option value="" disabled selected>Seleccione una propiedad</option>
                        <%
                        	if (!propiedades.isEmpty()) {
                        		for (Propiedad prop : propiedades) {
                            		Precio pre = new Precio();
                            		pre = pc.getUltimoByPropiedad(prop);
                            		int value = prop.getNroPropiedad();
                            		String direccionPisoDepto = prop.getPiso() > 0 ? prop.getDireccion() + " " + prop.getPiso() + prop.getDepto() : prop.getDireccion();
                            		if (pre != null && !pre.getFechaDesde().equals(LocalDate.now()) || pre == null) {
                        %>
                            			<option value="<%= value %>"><%= direccionPisoDepto %></option>
                        <%
                            		}
                        		}
                        	} else {
                        %>
                        		<option selected <%= propiedades.isEmpty() ? "disabled" : "" %>>No hay propiedades disponibles</option>
                        <%
                     		}
                        %>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="valor" class="form-label">Nuevo Precio ($)</label>
                    <input type="text" class="form-control" name="valor" id="valor">
                </div>
                <div class="mb-2">
                    <button type="submit" class="btn btn-primary w-100" <%= propiedades.isEmpty() ? "disabled" : "" %>>Registrar</button>
                </div>
                <div class="mb-4">
                    <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="login"'>Volver</button>
                </div>
            </form>
        </div>
    </div>
    <!-- Bootstrap JavaScript Libraries -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>