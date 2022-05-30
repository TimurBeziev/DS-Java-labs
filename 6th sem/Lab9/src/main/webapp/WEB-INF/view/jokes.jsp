<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        align-items: center;
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
</style>

<div class="center">
    <c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 5) %>
    </c:set>
    <h2>${rand}</h2>
    <c:if test="${rand == 0}">
        <h2>
            У волков нет правил поведения.
            Они им не нужны.
            Волкам, чтобы быть волками, не нужны никакие правила.
        </h2>
    </c:if>
    <c:if test="${rand == 1}">
        <h2>
            Если волк молчит, то лучше его не перебивать
        </h2>
    </c:if>
    <c:if test="${rand == 2}">
        <h2>
            Волк волку человек.
        </h2>
    </c:if>
    <c:if test="${rand == 3}">
        <h2>
            Овца и волк по-разному понимают слово "свобода".
        </h2>
    </c:if>
    <c:if test="${rand == 4}">
        <h2>
            Всю жизнь овца волков боялась, а съел её пастух.
        </h2>
    </c:if><c:if test="${rand == 5}">
    <h2>
        Если не будешь с волками в стае, то станешь их кормом.
    </h2>
</c:if>


    <form method="post" action="">
        <div class="center">
            <button class="button" type="submit" name="mainPage">Спасибо, больше не надо</button>
            <button class="button" type="submit" name="readJoke">Еще</button>
        </div>
    </form>
</div>

