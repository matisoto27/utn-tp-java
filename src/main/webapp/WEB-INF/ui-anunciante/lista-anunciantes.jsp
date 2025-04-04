<%@page import="entities.Anunciante" %>
<%@page import="entities.Cliente" %>
<%@page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!doctype html>
<html lang="es">

<head>
    <title>Listado de Anunciantes</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    Cliente cli = (Cliente) session.getAttribute("usuario");
    LinkedList<Anunciante> anunciantes = (LinkedList<Anunciante>)request.getAttribute("anunciantes");
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
                                <th scope="col">Nombre</th>
                                <th scope="col">Correo Electrónico</th>
                                <th scope="col">Teléfono</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Anunciante anun : anunciantes) { %>
                                <tr>
                                    <td>
                                        <%=anun.getNombre()%>
                                    </td>
                                    <td>
                                        <%=anun.getEmail()%>
                                    </td>
                                    <td>
                                        <%=anun.getTelefono()%>
                                    </td>
                                </tr>
                            <% } %>
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