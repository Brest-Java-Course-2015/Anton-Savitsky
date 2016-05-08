<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<head>
  <title>Производитель</title>
  <link href="<c:url value="/resources/css/myFixes.css"/>" rel="stylesheet"/>
</head>


<body>
<h1><a href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car">Модели</a>/<a
        href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/producer">Производители</a>
</h1>
<div>
  <sf:form id="producerForm" method="POST" modelAttribute="producer"><!-- Связать форму -->
    <fieldset>                                <!-- с атрибутом модели -->
      <table cellspacing="10">
        <tr>
          <th><label for="producerName">Имя модели:</label></th>
          <td><span><sf:input path="producerName" size="30" id="producerName"/></span></td>
        </tr>
        <tr hidden>
          <td><span><sf:input path="producerId" size="30" id="producerId"/></span></td>
        </tr>
        <tr>
          <th><label for="country">Страна:</label></th>
          <td><span><sf:input path="country" size="30" id="country"/></span></td>
        </tr>
      </table>
      <input type="submit" value="Сохранить"/>
    </fieldset>
  </sf:form>
</div>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/validateProducerForm.js"/>"></script>
</body>

</html>
