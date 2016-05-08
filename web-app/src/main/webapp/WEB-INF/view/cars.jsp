<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/myFixes.css"/>" rel="stylesheet"/>
</head>

<body>

<%@include file="templates/logout.template.jsp" %>

<h1><a href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car">Модели</a>/<a
        href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/producer">Производители</a>
</h1>

<div id="date">
    <!--<form>-->
        <label>C</label>
    <input id="dateBefore" name="dateBefore" type="text" placeholder="dd/MM/yyyy"/>
        <label>По</label>
    <input id="dateAfter" name="dateAfter" type="text" placeholder="dd/MM/yyyy"/>
        <button type="button" onclick="lookupCarsByDate()">Найти</button>
        <button type="button" onclick="undoLookUp()">Сброс</button>
    <!--</form>-->
</div>
<jsp:include page="templates/carstable.jsp"/>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<script>
    function undoLookUp(){
        window.location = "${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car";
    }

    function lookupCarsByDate(){
        var dateBefore=$("#dateBefore").val();
        console.log(dateBefore);
        var dateAfter=$("#dateAfter").val();
        console.log(dateAfter);
        window.location = "${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car/carsbydate" + "?dateBefore=" + dateBefore.toString() + "&dateAfter=" + dateAfter.toString();
    }

    function deleteCar(carId) {
        console.log('deleteCar' + carId);
        var url = "${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car/delete/" + carId;
        // Confirmation and Alert windows don't work in Chromium
        if(confirm('Вы действительно хотите удалить этот автомобиль?'))
            $.ajax({
                type: 'POST',
                url: url,
                success: function (data, textStatus, jqXHR) {
                    alert('Автомобиль успешно удален!');
                    console.log("success");
                    location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('Ошибка удаления автомобиля!');
                }
            });
    }
    function gotoUpdateCar(carId) {
        window.location = "${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car/update/" + carId;
    }

    $("#addCar").click(function(){
        window.location = "${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car/add";
    });
</script>
</body>
</html>