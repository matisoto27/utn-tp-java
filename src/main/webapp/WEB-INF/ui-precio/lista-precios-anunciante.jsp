<%@page import="entities.*" %>
<%@page import="java.util.LinkedList" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Histórico de precios</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    	LinkedList<Precio> precios = (LinkedList<Precio>) request.getAttribute("precios");
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
                                <th scope="col">Dirección</th>
                                <th scope="col">Fecha de inicio de vigencia</th>
                                <th scope="col">Valor</th>
                            </tr>
                        </thead>
                        <tbody>
            			<%
                            if (precios.isEmpty()) {
                     	%>
                                <tr>
                                    <td colspan="3" style="text-align: center;">
                                        Todavía no has registrado ningún precio
                                    </td>
                                </tr>
                      	<%
                            } else {
                            	for (Precio pre : precios) {
                                    String direccion = pre.getPropiedad().getPiso() == 0 ? pre.getPropiedad().getDireccion() : pre.getPropiedad().getDireccion() + " " + pre.getPropiedad().getPiso() + pre.getPropiedad().getDepto();
                                    String fecha_desde = pre.getFechaDesde().toString();
                                    String valor = Double.toString(pre.getValor());
                   		%>
                                    <tr>
                                        <td>
                                            <%= direccion %>
                                        </td>
                                        <td>
                                            <%= fecha_desde %>
                                        </td>
                                        <td>
                                            <%= valor %>
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
    <!-- Bootstrap JavaScript Libraries -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
</body>

</html>