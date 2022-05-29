<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<style>
    * {
        font-family: Helvetica, Verdana, sans-serif;
        font-weight: lighter;
    }
    .center {
        margin: auto;
        width: 60%;
        padding: 10px;
        text-align: center;
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

<div class="center">
    <h1><b>Beziev Timur LLC</b></h1>
    <h2>Мастер создания студента</h2>
</div>

<form method="post" action="">
    <div class="center">
        <c:if test="${sessionScope.currentStage == 1}">
            <label>
                <h1>Имя студента</h1>
                <input type="text" name="input" value="${sessionScope.name}">
            </label>
        </c:if>
        <c:if test="${sessionScope.currentStage == 2}">
            <label>
                <h1>Гендер студента</h1>
                <input type="text" name="input" value="${sessionScope.gender}">
            </label>
        </c:if>
        <c:if test="${sessionScope.currentStage == 3}">
            <label>
                <h1>Любимый предмет</h1>
                <input type="text" name="input" value="${sessionScope.bestSubject}">
            </label>
        </c:if>
        <c:if test="${sessionScope.currentStage == 4}">
            <label>
                <h2>Поздравляем, вы создали студента со следующими характеристиками:</h2>
                <ul>
                    <li>${sessionScope.name}</li>
                    <li>${sessionScope.gender}</li>
                    <li>${sessionScope.bestSubject}</li>
                </ul>
            </label>
        </c:if>
        <br>
        <c:if test="${sessionScope.currentStage == 0}">
            <button class="button" type="submit" name="nextStage">СТАРТУЕМ!</button>
        </c:if>
        <c:if test="${sessionScope.currentStage >= 1 && sessionScope.currentStage <= 3}">
            <button class="button" type="submit" name="prevStage">Назад</button>
            <button class="button" type="submit" name="nextStage">Далее</button>
        </c:if>
        <c:if test="${sessionScope.currentStage == 4}">
            <button class="button" type="submit" name="prevStage">Я передумал</button>
        </c:if>
    </div>
</form>

</body>
</html>
