/**
 * Created by antonsavitsky on 07.12.15.
 */
// The root URL for the RESTful services
$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var PRODUCER_URL = "/producer";
var PRODUCERDTO_URL = "/producersdto";

findAll();

function gotoAddProducer() {
    window.location = "addproducer.html";
}

function gotoUpdateProducer(producerId,producerName,country)
{
    sessionStorage.setItem('producerId', producerId);
    sessionStorage.setItem('name', producerName);
    sessionStorage.setItem('country', country);
    window.location="updateproducer.html";
}

function deleteProducer(producerId, producerName, countOfCars) {
    var mes="Вы уверены, что хотите удалить производителя "+producerName+"?";
    if (countOfCars>0) mes="Существуют автомобили принадлежащие данному производителю! Они будут удалены!\n"+mes;
    if (confirm(mes))
    {
        console.log('deleteProducer id=' + producerId);
        var url = PREFIX_URL + PRODUCER_URL + "?id=" + producerId;
        $.ajax({
            type: 'DELETE',
            url: url,
            async: false,
            success: function (data, textStatus, jqXHR) {
                alert('Производитель успешно удален!');
                findAll();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Ошибка удаления!');
            }
        });
    }
}

function findAll() {
    console.log('findAll');
    var url = PREFIX_URL + PRODUCERDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json",
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('Не удалось получить данные из БД!');
        }
    });
}

function drawRow(producer) {
    var row = $("<tr />")
    $("#producerList").append(row);
    row.append($("<td>" + producer.producerName+"</td>"));
    row.append($("<td>" + producer.country+"</td>"));
    row.append($("<td>" + producer.countOfCars + "</td>"));
    row.append($("<td>" + '<button id="delete'+producer.producerId+'" class="mybutton"><span class="glyphicon glyphicon-trash"></span></button>' + "</td>"));
    $("#delete"+producer.producerId).click(function(){
        deleteProducer(producer.producerId, producer.producerName, producer.countOfCars);
    });
    row.append($("<td>"+'<button id="update'+producer.producerId+'" class="mybutton" type="button"><span class="glyphicon glyphicon-pencil"></span></button>' + "</td>"));
    $("#update"+producer.producerId).click(function(){
        gotoUpdateProducer(producer.producerId, producer.producerName, producer.country);
    });
}

function renderList(data) {
    cleanOldData();
    var dto = data.producers == null ? [] : (data.producers instanceof Array ? data.producers : [data.producers]);
    var total = data.total;
    $.each(dto, function (index, producer) {
        drawRow(producer);
    });
    $('#producerTotal').append($("<p>Всего производителей: " + total + "</p>"));
}

function cleanOldData(){
    $("#producerList tr").remove();
    $('#producerTotal p').remove();
}
