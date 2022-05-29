<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="productsRepository" type="kr.repositories.ProductsRepository" scope="session"/>

<div class="container">
    <h1>Products</h1>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Product Name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productsRepository.products}" var="element">
            <tr>
                <td>${element.uniqueID}</td>
                <td>${element.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
