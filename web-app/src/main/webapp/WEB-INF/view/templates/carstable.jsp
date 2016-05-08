<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<div class="table-responsive">
  <table class="table table-striped">

    <thead>
    <tr>
      <th>Модель</th>
      <th>Производитель</th>
      <th>Дата выпуска</th>
      <c:if test="${pageContext.request.userPrincipal.name != null}">
        <th>Удалить</th>
        <th>Изменить</th>
      </c:if>
    </tr>
    </thead>

    <tbody id="carList">
    <c:forEach var="car" items="${dto.cars}">
      <tr>
        <td><c:out value="${car.carName}"/></td>
        <td><c:out value="${car.producer.producerName}"/> </td>
        <td><tags:localDate date="${car.dateOfCreation}" pattern="dd/MM/yyyy"/></td>
        <c:if test="${pageContext.request.userPrincipal.name != null}">
          <td>
            <button id="delete${car.carId}" class="mybutton" onclick="deleteCar(${car.carId})"><span
                    class="glyphicon glyphicon-trash"></span></button>
          </td>
          <td>
            <button id="update${car.carId}" class="mybutton" onclick="gotoUpdateCar(${car.carId})"><span
                    class="glyphicon glyphicon-pencil"></span></button>
          </td>
        </c:if>
      </tr>
    </c:forEach>
    </tbody>

    <tfoot>
    <tr>
      <td>Всего: <c:out value="${dto.total}"/></td>
    </tr>
    </tfoot>

  </table>

  <c:if test="${pageContext.request.userPrincipal.name != null}">
    <button id="addCar" class="addbutton">Добавить автомобиль</button>
  </c:if>
</div>