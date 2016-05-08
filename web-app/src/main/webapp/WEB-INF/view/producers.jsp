<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"/>
  <link href="<c:url value="/resources/css/myFixes.css"/>" rel="stylesheet"/>
</head>
<body>

<h1><a href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/car">Модели</a>/<a
        href="${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/producer">Производители</a>
</h1>

<jsp:include page="templates/producerstable.jsp"/>

</body>

<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>

<script>
  function deleteProducer(producerId) {
    console.log('deleteProducer' + producerId);
    var url = "${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/producer/delete/" + producerId;
    if (confirm('Вы действительно хотите удалить этого производителя?'))
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
  function gotoUpdateProducer(producerId) {
    window.location = "${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/producer/update/" + producerId;
  }
  $("#addProducer").click(function () {
    window.location = "${pageContext.request.contextPath}/${pageContext.request.userPrincipal.name}/producer/add";
  });
</script>

</html>