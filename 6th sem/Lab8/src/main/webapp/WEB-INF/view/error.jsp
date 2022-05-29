<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Error</title>
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
        align-items: center;
        appearance: none;
        background-color: #FCFCFD;
        border-radius: 4px;
        border-width: 0;
        box-shadow: rgb(45 35 66 / 10%) 0 20px 40px, rgb(45 35 66 / 10%) 0 0px 26px 0px, #d6d6e9 0 -2px 0 inset;
        box-sizing: border-box;
    }

    .button {
        align-items: center;
        appearance: none;
        background-color: #FCFCFD;
        border-radius: 4px;
        border-width: 0;
        box-shadow: rgba(45, 35, 66, 0.4) 0 2px 4px, rgba(45, 35, 66, 0.3) 0 7px 13px -3px, #D6D6E7 0 -3px 0 inset;
        box-sizing: border-box;
        color: #ff0000;
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

<form method="post" action="">
    <div class="center">
        <h3>BAAAMS AN ERROR OCCURED!</h3>
        <h2>
            Exception is:
            <br>
            <%= exception %>
        </h2>

        <button class="button" type="submit" name="prevStage">Извиняюсь, пойду исправлю</button>
    </div>
</form>



</body>
</html>
