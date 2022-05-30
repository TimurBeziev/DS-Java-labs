<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="./error.jsp" %>


<html>
<head>
    <title>Master</title>
</head>
<body>

<style>
    * {
        font-family: Helvetica, Verdana, sans-serif;
        font-weight: lighter;
    }

    .header {
        margin: auto;
        width: 60%;
        padding: 10px;
        text-align: center;
        align-items: center;
    }

    .center {
        margin: auto;
        width: 60%;
        padding: 10px;
        text-align: center;
        align-items: center;
    }

    .input {
        margin: 5px;
        align-items: center;
        appearance: none;
        background-color: #FCFCFD;
        border-radius: 4px;
        border-width: 0;
        box-shadow: rgba(45, 35, 66, 0.4) 0 2px 4px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;
        box-sizing: border-box;
        color: #36395A;
        cursor: pointer;
        display: inline-flex;
        font-family: "JetBrains Mono", monospace;
        height: 48px;
        justify-content: center;
        line-height: 1;
        list-style: none;
        overflow: hidden;
        padding-left: 16px;
        padding-right: 16px;
        position: relative;
        text-align: left;
        text-decoration: none;
        transition: box-shadow .15s, transform .15s;
        user-select: none;
        -webkit-user-select: none;
        touch-action: manipulation;
        white-space: nowrap;
        will-change: box-shadow, transform;
        font-size: 18px;
    }

    .button {
        align-items: center;
        appearance: none;
        background-color: #FCFCFD;
        border-radius: 4px;
        border-width: 0;
        box-shadow: rgba(45, 35, 66, 0.4) 0 2px 4px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;
        box-sizing: border-box;
        color: #36395A;
        cursor: pointer;
        display: inline-flex;
        font-family: "JetBrains Mono", monospace;
        height: 48px;
        justify-content: center;
        line-height: 1;
        list-style: none;
        overflow: hidden;
        padding-left: 16px;
        padding-right: 16px;
        position: relative;
        text-align: left;
        text-decoration: none;
        transition: box-shadow .15s, transform .15s;
        user-select: none;
        -webkit-user-select: none;
        touch-action: manipulation;
        white-space: nowrap;
        will-change: box-shadow, transform;
        font-size: 18px;
    }

    .button:focus {
        box-shadow: #D6D6E7 0 0 0 1.5px inset, rgba(45, 35, 66, 0.4) 0 2px 4px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;
    }

    .button:hover {
        box-shadow: rgba(45, 35, 66, 0.4) 0 4px 8px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;
        transform: translateY(-2px);
    }

    .button:active {
        box-shadow: #D6D6E7 0 3px 7px inset;
        transform: translateY(2px);
    }

    ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
    }

    li {
        font: 200 20px/1.5 Helvetica, Verdana, sans-serif;
        border-bottom: 1px solid #ccc;
    }

</style>

<jsp:include page="header.jsp"/>

<form method="post" action="">
    <div class="center">
        <label><input class="input" type="text" name="productName" placeholder="product name"></label>
        <label><input class="input" type="text" name="stockName" placeholder="stock name"></label>
        <label><input class="input" type="number" step="0.01" name="price" placeholder="price"></label><br>

        <button class="button" type="submit" name="addProduct">Добавить продукт</button>
        <button class="button" type="submit" name="deleteProduct">Удалить продукт</button>

    </div>
</form>

<jsp:include page="infosTable.jsp"/>
<jsp:include page="productsTable.jsp"/>
<jsp:include page="stockTable.jsp"/>

<form method="post" action="">
    <div class="center">
        <button class="button" type="submit" name="readJoke">Цитаты про волков</button>
    </div>
</form>

</body>
</html>
