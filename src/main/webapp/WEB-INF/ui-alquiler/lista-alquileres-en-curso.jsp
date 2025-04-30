<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Alquileres en curso</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    LinkedList<Alquiler> alquileres = (LinkedList<Alquiler>) request.getAttribute("alquileres");
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100">
        <div class="container">
            <div class="row mb-4">
                <div class="col">
                    <table class="table text-center table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Cliente</th>
                                <th scope="col">Direccion</th>
                                <th scope="col">Fecha Solicitado</th>
                                <th scope="col">Fecha Inicio Contrato</th>
                                <th scope="col">Fecha Fin Contrato</th>
                                <th scope="col">Precio</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            if (alquileres.isEmpty()) {
                            %>
                                <tr>
                                    <td colspan="8" style="text-align: center;">
                                        No existe ningún alquiler en curso
                                    </td>
                                </tr>
                            <%
                            } else {
                            	for (Alquiler alq : alquileres) {
                                    String cliente = alq.getCliente().getApellido() + " " + alq.getCliente().getNombre();
                                    String direccion = alq.getPropiedad().getPiso() == 0 ? alq.getPropiedad().getDireccion() : alq.getPropiedad().getDireccion() + " " + alq.getPropiedad().getPiso() + alq.getPropiedad().getDepto();
                                    String fecha_solicitado = alq.getFechaSolicitado().toString();
                                    String fecha_inicio_contrato = alq.getFechaInicioContrato() != null ? alq.getFechaInicioContrato().toString() : "-";
                                    String fecha_fin_contrato = alq.getFechaFinContrato() != null ? alq.getFechaFinContrato().toString() : "-";
                                    String precio = alq.getPrecio() > 0 ? Double.toString(alq.getPrecio()) : "Sin datos";
                           	%>
                                    <tr>
                                        <td>
                                            <%= cliente %>
                                        </td>
                                        <td>
                                            <%= direccion %>
                                        </td>
                                        <td>
                                            <%= fecha_solicitado %>
                                        </td>
                                        <td>
                                            <%= fecha_inicio_contrato %>
                                        </td>
                                        <td>
                                            <%= fecha_fin_contrato %>
                                        </td>
                                        <td>
                                            <%= precio %>
                                        </td>
                                    </tr>
                             	<%
                                }
                           	}
                         	%>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col text-center">
                    <button type="button" class="btn btn-primary p-2" style="width: 250px;" onclick='window.location.href="login"'>Volver</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>