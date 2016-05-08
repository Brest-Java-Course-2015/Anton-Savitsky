<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<head>
  <title>Модель</title>
  <link href="<c:url value="/resources/css/myFixes.css"/>" rel="stylesheet"/>
</head>
<body>
<h1><a href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car">Модели</a>/<a
        href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/producer">Производители</a>
</h1>
<div>
  <form:form id="carForm" method="POST" commandName="car"> <!-- Связать форму -->
    <fieldset>                                <!-- с атрибутом модели -->
      <table cellspacing="10">
        <tr>
          <th><label for="carName">Имя модели:</label></th>
          <td><span><form:input path="carName" size="30" id="carName"/></span></td>
        </tr>
        <tr hidden>
          <td><span><form:input path="carId" size="30" id="carId"/></span></td>
        </tr>
        <tr>
          <th><label for="dateOfCreation">Дата создания:</label></th>
          <td><span><form:input size="30" id="dateOfCreation" value="${date}" path="dateOfCreation"/></span></td>
        </tr>
        <tr>
          <th><label for="producerId">Имя производителя:</label></th>
          <td><span><form:select path="producer.producerId" id="producerId">
            <c:forEach var="producer" items="${producersdto.producers}">
              <form:option value="${producer.producerId}">${producer.producerName}</form:option>
            </c:forEach>
          </form:select></span></td>
        </tr>
      </table>
      <input type="submit" value="Сохранить"/>
    </fieldset>
  </form:form>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/validateCarForm.js"/>"></script>
</body>
</html>