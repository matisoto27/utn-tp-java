<%@page import="entities.*" %>
<%@page import="java.time.LocalDate" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Gestionar alquiler actual</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    Alquiler alq = (Alquiler) request.getAttribute("alquiler");
    int id_alquiler = alq.getIdAlquiler();
    Propiedad prop = alq.getPropiedad();
    Anunciante anun = prop.getAnunciante();
    String nombre = anun.getNombre();
    String email = anun.getEmail();
    String telefono = anun.getTelefono();
    String direccionPisoDepto = prop.getPiso() > 0 ? prop.getDireccion() + " " + prop.getPiso() + prop.getDepto() : prop.getDireccion();
    String fecha_inicio_contrato = alq.getFechaInicioContrato().toString();
    String fecha_fin_contrato = alq.getFechaFinContrato().toString();
    Double precio = alq.getPrecio();
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="d-flex justify-content-center align-items-center">
            <div class="card shadow">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Detalles del Contrato de Alquiler</h4>
                </div>
                    <div class="card-body">
                        <p><strong>Anunciante:</strong> <%= nombre %></p>
                        <p><strong>Correo electrónico:</strong> <%= email %></p>
                        <p><strong>Teléfono:</strong> <%= telefono %></p>
                        <p><strong>Propiedad:</strong> <%= direccionPisoDepto %></p>
                        <p><strong>Precio:</strong> $<%= precio %></p>
                        <p><strong>Fecha de inicio:</strong> <%= fecha_inicio_contrato %></p>
                        <p><strong>Fecha de finalización:</strong> <%= fecha_fin_contrato %></p>
                        <%
                        if (alq.getFechaFinContrato().isAfter(LocalDate.now())) {
                        %>
                            <div class="text-center mt-4">
                                <form method="post" action="alquilerservlet?action=cancelarcontrato">
                                    <input type="hidden" name="id-alquiler" id="id-alquiler" value="<%= id_alquiler %>">
                                    <button class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas cancelar el contrato de alquiler?')">Cancelar Contrato</button>
                                </form>
                            </div>
                        <%
                        } else {
                        %>
                            <div class="alert alert-info text-center mt-4">
                                <strong>¡El contrato ha finalizado!</strong>
                            </div>
                            <%
                            if (alq.getPuntuacion() == 0) {
                            %>
                            <div class="text-center mt-4">
                                <h5>Tu opinión nos interesa</h5>
                                <form method="post" action="alquilerservlet?action=puntuacioncomentario" onsubmit="return validarFormulario()">
                                    <input type="hidden" name="id-alquiler" id="id-alquiler" value="<%= id_alquiler %>">
                                    <div class="mb-3">
                                        <label for="puntuacion" class="form-label">Puntaje (1-10):</label>
                                        <input type="number" class="form-control" name="puntuacion" id="puntuacion" min="1" max="10" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="comentario">Comentario:</label>
                                        <textarea class="form-control" style="resize: none;" name="comentario" id="comentario" rows="4" placeholder="Cuéntanos tu experiencia con la propiedad y el contrato de alquiler. ¿Fue lo que esperabas? ¿Qué mejorarías?"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary w-100">Enviar Calificación</button>
                                </form>
                            </div>
                            <%
                            }
                        }
                        %>
                        <div class="text-center mt-4">
                            <button type="button" class="btn btn-secondary w-100" onclick='window.location.href="login"'>Volver</button>
                        </div>
                    </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap JavaScript Libraries -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>