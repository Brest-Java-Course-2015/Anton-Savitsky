<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<head>
  <title>Обновить модель</title>
  <link href="<c:url value="/resources/css/myFixes.css"/>" rel="stylesheet"/>
</head>
<body>
<div>
  <h2>Обновить модель автомобиля</h2>
  <sf:form id="carForm" method="POST" modelAttribute="car">   <!-- Связать форму -->
    <fieldset>                                <!-- с атрибутом модели -->
      <table cellspacing="10">
        <tr>
          <th><label for="carName">Имя модели:</label></th>
          <td><span><sf:input path="carName" size="30" id="carName"/></span></td>
        </tr>
        <tr hidden>
          <td><span><sf:input path="carId" size="30" id="carId"/></span></td>
        </tr>
        <tr>
          <th><label for="dateOfCreation">Дата создания:</label></th>
          <td><span><sf:input path="dateOfCreation" size="30" id="dateOfCreation"/></span></td>
        </tr>
        <tr>
          <th><label for="producerId">Имя производителя:</label></th>
          <td><span><sf:select path="producerId" id="producerId">
            <c:forEach var="producer" items="${producersdto.producers}">
              <sf:option value="${producer.producerId}">${producer.producerName}</sf:option>
            </c:forEach>
          </sf:select></span></td>
        </tr>
      </table>
      <input type="submit" value="Сохранить"/>
    </fieldset>
  </sf:form>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/validateCarForm.js"/>"></script>
</body>
</html>