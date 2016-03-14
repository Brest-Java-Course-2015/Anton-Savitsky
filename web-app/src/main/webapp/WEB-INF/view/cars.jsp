<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="mapping" value="${pageContext.request.requestURI}"/>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/myFixes.css"/>" rel="stylesheet"/>
</head>
<body>
<h1><a href="<c:url value="/car"/>">Модели</a>/<a href="<c:url value="/producer"/>">Производители</a></h1>
<div>
    <form>
        <label>C</label>
        <input id="dateBefore" name="dateBefore" type="date" placeholder="dd/MM/yyyy"/>
        <label>По</label>
        <input id="dateAfter" name="dateAfter" type="date" placeholder="dd/MM/yyyy"/>
        <button type="button" onclick="lookupCarsByDate()">Найти</button>
        <button type="button" onclick="undoLookUp()">Сброс</button>
    </form>
</div>
<jsp:include page="templates/carstable.jsp"/>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<script src="<c:url value="/resources/js/cars.js" />"></script>
<script>
    function undoLookUp(){
        window.location='<c:url value="/car"/>';
        /*
         $("#dateBefore").val("");
         $("#dateAfter").val("");*/
    }
    function lookupCarsByDate(){
        var dateBefore=$("#dateBefore").val();
        var dateAfter=$("#dateAfter").val();
        window.location='<c:url value="/car/carsbydate"/>'+"?dateBefore="+dateBefore.toString()+"&dateAfter="+dateAfter.toString();
    }

    function deleteCar(carId) {
        console.log('deleteCar' + carId);
        var url = '<c:url value="/car/delete/"/>' + carId;
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
        window.location='<c:url value="/car/update/"/>' + carId;
    }

    $("#addCar").click(function(){
        window.location='<c:url value="/car/add"/>';
    });
</script>
</body>
</html>