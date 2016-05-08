<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<div class="table-responsive">
    <table class="table table-striped">

        <thead>
        <tr>
            <th>Имя</th>
            <th>Страна</th>
            <th>Кол-во моделей</th>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <th>Удалить</th>
                <th>Изменить</th>
            </c:if>
        </tr>
        </thead>

        <tbody id="producerList">
        <c:forEach var="producer" items="${dto.producers}">
            <tr>
                <td><c:out value="${producer.producerName}"/></td>
                <td><c:out value="${producer.country}"/></td>
                <td><c:out value="${producer.countOfCars}"/></td>

                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <td>
                        <button class="mybutton" onclick="deleteProducer(${producer.producerId})">
                            <span class="glyphicon glyphicon-trash"></span></button>
                    </td>

                    <td>
                        <button id="update${producer.producerId}" class="mybutton"
                                onclick="gotoUpdateProducer(${producer.producerId})">
                            <span class="glyphicon glyphicon-pencil"></span></button>
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
        <button id="addProducer" class="addbutton">Добавить производителя</button>
    </c:if>

</div>