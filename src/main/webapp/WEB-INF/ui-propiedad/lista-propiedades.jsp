<%@page import="entities.Propiedad" %>
<%@page import="entities.Cliente" %>
<%@page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Listado de Propiedades</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    Cliente cli=(Cliente) session.getAttribute("usuario");
    LinkedList<Propiedad> lp = (LinkedList<Propiedad>)request.getAttribute("listaPropiedades");
    %>
</head>

<body>
    <div class="d-flex justify-content-center align-items-center vh-100">
        <div class="container">
            <div class="row mb-4">
                <div class="col">
                    <table class="table text-center table-bordered table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Anunciante</th>
                                <th scope="col">Nro. de Propiedad</th>
                                <th scope="col">Direccion</th>
                                <th scope="col">Piso</th>
                                <th scope="col">Departamento</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (lp.isEmpty()) { %>
                                <tr>
                                    <td colspan="5" style="text-align: center;">
                                        Error al cargar las Propiedades
                                    </td>
                                </tr>
                            <% } else { %>
                                <% for (Propiedad prop : lp) { %>
                                    <tr>
                                        <td>
                                            <%=prop.getAnunciante().getNombre()%>
                                        </td>
                                        <td>
                                            <%=prop.getNroPropiedad()%>
                                        </td>
                                        <td>
                                            <%=prop.getDireccion()%>
                                        </td>
                                        <td>
                                            <%= prop.getPiso() != 0 ? prop.getPiso() : "-" %>
                                        </td>
                                        <td>
                                            <%= prop.getDepto() != null ? prop.getDepto() : "-" %>
                                        </td>
                                    </tr>
                                <% } %>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col text-center">
                    <button type="button" class="btn btn-primary p-2" style="width: 250px;" onclick='window.location.href="menu-cliente.html"'>Volver</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>

</html>