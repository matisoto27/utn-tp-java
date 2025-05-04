<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Registrar contrato de alquiler</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    LinkedList<Alquiler> alquileres = (LinkedList<Alquiler>) request.getAttribute("alquileres");
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="d-flex justify-content-center align-items-center form-container" style="width: 500px;">
            <form method="post" action="alquilerservlet?action=iniciarcontrato" style="width: 300px;">
                <h2 class="text-center my-4">Registrar Contrato</h2>
                <div class="mb-3">
                    <label for="id-alquiler" class="form-label">Seleccione una Propiedad</label>
                    <select class="form-select" name="id-alquiler" id="id-alquiler" required>
                        <option value="" disabled selected>Seleccione una propiedad</option>
                        <%
                        	if (!alquileres.isEmpty()) {
                        		for (Alquiler alq : alquileres) {
                                    int id_alquiler = alq.getIdAlquiler();
                                    String dni_cliente = alq.getCliente().getDni();
                                    String apellido_nombre = alq.getCliente().getApellido() + " " + alq.getCliente().getNombre();
                                    String fecha_solicitado = alq.getFechaSolicitado().toString();
                                    String direccionPisoDepto = alq.getPropiedad().getPiso() > 0 ? alq.getPropiedad().getDireccion() + " " + alq.getPropiedad().getPiso() + alq.getPropiedad().getDepto() : alq.getPropiedad().getDireccion();
                        %>
                            			<option value="<%= id_alquiler %>" data-dni="<%= dni_cliente %>" data-cliente="<%= apellido_nombre %>" data-fecha="<%= fecha_solicitado %>"><%= direccionPisoDepto %></option>
                        <%
                            	}
                        	} else {
                        %>
                        		<option selected <%= alquileres.isEmpty() ? "disabled" : "" %>>No hay alquileres pendientes</option>
                        <%
                     		}
                        %>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="cliente" class="form-label">Cliente</label>
                    <input type="text" class="form-control" name="cliente" id="cliente" disabled>
                </div>
                <div class="mb-3">
                    <label for="fecha-solicitado" class="form-label">Fecha Solicitado</label>
                    <input type="date" class="form-control" name="fecha-solicitado" id="fecha-solicitado" disabled>
                </div>
                <div class="mb-3">
                    <label for="fecha-inicio-contrato" class="form-label">Fecha Inicio Contrato</label>
                    <input type="date" class="form-control" name="fecha-inicio-contrato" id="fecha-inicio-contrato" required>
                </div>
                <div class="mb-3">
                    <label for="fecha-fin-contrato" class="form-label">Fecha Fin Contrato</label>
                    <input type="date" class="form-control" name="fecha-fin-contrato" id="fecha-fin-contrato" required>
                </div>
                <div class="mb-2">
                    <button type="submit" class="btn btn-primary w-100" <%= alquileres.isEmpty() ? "disabled" : "" %>>Registrar</button>
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
    <script>
        function actualizarCampos() {
            var propiedadSeleccionada = document.getElementById("id-alquiler").value;
            if (propiedadSeleccionada) {
                var opcionSeleccionada = document.querySelector("#id-alquiler option[value='" + propiedadSeleccionada + "']");
                var cliente = opcionSeleccionada.getAttribute("data-cliente");
                var fechaSolicitado = opcionSeleccionada.getAttribute("data-fecha");
                document.getElementById("cliente").value = cliente;
                document.getElementById("fecha-solicitado").value = fechaSolicitado;
            } else {
                document.getElementById("cliente").value = "";
                document.getElementById("fecha-solicitado").value = "";
            }
        }
        document.getElementById("id-alquiler").addEventListener("change", actualizarCampos);
    </script>
    
</body>

</html>