<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="infosRepository" type="kr.repositories.InfosRepository" scope="session"/>


<style>
    .container {
        margin: auto;
        width: 50%;
    }

    table {
        width: 800px;
        border-collapse: collapse;
        overflow: hidden;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
    }

    th, td {
        padding: 15px;
        background-color: rgba(255, 255, 255, 0.2);
        color: #000;
    }

    th {
        text-align: left;
    }

    thead th {
        background-color: #cfcfcf;
    }

</style>

<div class="container">
    <h1>Infos</h1>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Product Name</th>
            <th>Stock Name</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${infosRepository.items}" var="element">
            <tr>
                <td>${element.uniqueID}</td>
                <td>${element.product.name}</td>
                <td>${element.stock.name}</td>
                <td>${element.price}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
