<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="mapping" value="${pageContext.request.requestURI}"/>

<!DOCTYPE html>
<html lang="ru">
<head>
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"/>
  <link href="<c:url value="/resources/css/myFixes.css"/>" rel="stylesheet"/>
</head>
<body>
<h1><a href="<c:url value="/car"/>">Модели</a>/<a href="<c:url value="/producer"/>">Производители</a></h1>
<div class="table-responsive">
  <table class="table table-striped">
    <thead>
    <tr>
      <th>Имя</th>
      <th>Страна</th>
      <th>Количество моделей</th>
      <th>Удалить</th>
      <th>Изменить</th>
    </tr>
    </thead>
    <tbody id="producerList">
    <c:forEach var="producer" items="${dto.producers}">
      <tr>
        <td><c:out value="${producer.producerName}"/></td>
        <td><c:out value="${producer.country}"/> </td>
        <td><c:out value="${producer.countOfCars}"/></td>
        <td><button id="delete${producer.producerId}" class="mybutton" onclick="deleteProducer(${producer.producerId})"><span class="glyphicon glyphicon-trash"></span></button></td>
        <td><button id="update${producer.producerId}" class="mybutton" onclick="gotoUpdateProducer(${producer.producerId})"><span class="glyphicon glyphicon-pencil"></span></button></td>
      </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
      <td>Всего: <c:out value="${dto.total}"/></td>
    </tr>
    </tfoot>
  </table>
  <button id="addProducer" class="addbutton">Добавить производителя</button>
</div>
</body>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<script>
  function deleteProducer(producerId) {
    console.log('deleteProducer' + producerId);
    var url = '<c:url value="/producer/delete/"/>' + producerId;
    // Confirmation and Alert windows don't work in Chromium
    if(confirm('Вы действительно хотите удалить этого производителя?'))
      $.ajax({
        type: 'POST',
        url: url,
        success: function (data, textStatus, jqXHR) {
          alert('Производитель успешно удален!');
          console.log("success");
          location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
          alert('Ошибка удаления производителя!');
        }
      });
  }
  function gotoUpdateProducer(carId) {
    window.location='<c:url value="/producer/update/"/>' + carId;
  }
  $("#addProducer").click(function(){
    window.location='<c:url value="/producer/add"/>';
  });
</script>

</html>