<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Alquileres cancelados</title>
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
                                <th scope="col">Fecha Renuncia</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            if (alquileres.isEmpty()) {
                            %>
                                <tr>
                                    <td colspan="8" style="text-align: center;">
                                        No existen alquileres cancelados
                                    </td>
                                </tr>
                            <%
                            } else {
                            	for (Alquiler alq : alquileres) {
                                    int id_alquiler = alq.getIdAlquiler();
                                    String cliente = alq.getCliente().getApellido() + " " + alq.getCliente().getNombre();
                                    String email = alq.getCliente().getEmail();
                                    String telefono = alq.getCliente().getTelefono();
                                    String direccion = alq.getPropiedad().getPiso() == 0 ? alq.getPropiedad().getDireccion() : alq.getPropiedad().getDireccion() + " " + alq.getPropiedad().getPiso() + alq.getPropiedad().getDepto();
                                    String fecha_solicitado = alq.getFechaSolicitado().toString();
                                    String fecha_inicio_contrato = alq.getFechaInicioContrato().toString();
                                    String fecha_fin_contrato = alq.getFechaFinContrato().toString();
                                    String precio = alq.getPrecio() > 0 ? Double.toString(alq.getPrecio()) : "-";
                                    String fecha_renuncia = alq.getFechaRenuncia().toString();
                           	%>
                                    <tr>
                                        <td>
                                            <button class="btn btn-link p-0" onclick="abrirModal('<%= cliente %>', '<%= email %>', '<%= telefono %>')">
                                                <%= cliente %>
                                            </button>
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
                                        <td>
                                            <%= fecha_renuncia %>
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
    <div class="modal fade" id="modalCliente" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false" role="dialog" aria-labelledby="modalTitleId" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalTitleId">Titulo</h5>
                </div>
                <div class="modal-body" id="modal-body">Cuerpo</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <script>
        function abrirModal(cliente, email, telefono) {
          const modal = new bootstrap.Modal(document.getElementById('modalCliente'));
          const modalTitle = document.querySelector('#modalCliente .modal-title');
          const modalBody = document.querySelector('#modalCliente #modal-body');
          modalTitle.innerHTML = 'Datos para contactar a <br>' + cliente;
          modalBody.innerHTML = '<p><strong>Email:</strong> ' + email + '</p><br><p><strong>Teléfono:</strong> ' + telefono + '</p>';
          modal.show();
        }
    </script>
</body>

</html>