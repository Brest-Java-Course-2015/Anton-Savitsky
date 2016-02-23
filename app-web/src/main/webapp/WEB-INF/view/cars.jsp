<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<c:set var="mapping" value="${pageContext.request.requestURI}"/>

<!DOCTYPE html>
<html lang="ru">
<h1>${mapping}</h1>
<head>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/myFixes.css"/>" rel="stylesheet"/>
</head>
<body>
<div class="table-responsive">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Модель</th>
                <th>Производитель</th>
                <th>Дата выпуска</th>
                <th>Удалить</th>
                <th>Изменить</th>
            </tr>
        </thead>
        <tbody id="carList">
            <c:forEach var="car" items="${dto.cars}">
                <tr>
                    <td><c:out value="${car.carName}"/></td>
                    <td><c:out value="${car.producerName}"/> </td>
                    <td><c:out value="${car.dateOfCreation}"/></td>
                    <td><button id="delete${car.carId}" class="mybutton" onclick="deleteCar(${car.carId})"><span class="glyphicon glyphicon-trash"></span></button></td>
                    <td><button id="update${car.carId}" class="mybutton" onclick="gotoUpdateCar(${car.carId})"><span class="glyphicon glyphicon-pencil"></span></button></td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <td>Всего: <c:out value="${dto.total}"/></td>
            </tr>
        </tfoot>
    </table>
    <button id="addCar" class="addbutton">Добавить автомобиль</button>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<script src="<c:url value="/resources/js/cars.js" />"></script>
</body>
</html>