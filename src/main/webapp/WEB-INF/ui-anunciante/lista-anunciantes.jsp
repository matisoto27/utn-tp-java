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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%
    Cliente cli=(Cliente) session.getAttribute("usuario");
    LinkedList<Anunciante> la = (LinkedList<Anunciante>)request.getAttribute("listaAnunciantes");
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
                            <% for (Anunciante anun : la) { %>
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
                    <button type="button" class="btn btn-primary p-2" style="width: 250px;"
                        onclick='window.location.href="menu-cliente.html"'>Volver</button>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>

</html>