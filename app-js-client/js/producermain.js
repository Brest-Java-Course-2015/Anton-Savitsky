/**
 * Created by antonsavitsky on 07.12.15.
 */
// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT";
var PRODUCER_URL = "/producer";
var PRODUCERDTO_URL = "/producersdto";
var CARSDTO_URL="/carsdto";

findAll();

function gotoAddProducer() {
    window.location = "addproducer.html";
}

function gotoUpdateProducer(producerId)
{
    sessionStorage.setItem('producerId', producerId);
    window.location="updateproducer.html";
}

function goHome() {
    window.location="index.html";
}

function deleteProducer(producerId) {
    if (confirm("Вы уверены, что хотите удалить производителя с id=" + producerId + "?"))
    {
        console.log('deleteProducer' + producerId);
        var url = PREFIX_URL + PRODUCER_URL + "?id=" + producerId;
        $.ajax({
            type: 'DELETE',
            url: url,
            success: function (data, textStatus, jqXHR) {
                alert('Производитель успешно удален');
                findAll();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Ошибка удаления!' +'\n'+textStatus);
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
        dataType: "json", // data type of response
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('findAll: ' + textStatus);
        }
    });
}

function getCountOfCarsByProducer(id) {
    console.log('getCountOfCarsByProducer');
    var i=0;
    var url = PREFIX_URL + CARSDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        async: false,
        dataType: "json", // data type of response
        success: function (data){
            var dto = data.cars == null ? [] : (data.cars instanceof Array ? data.cars : [data.cars]);
            $.each(dto, function (index, car) {
                if(car.producerId==id) i++;
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('getCountOfCarsByProducer: ' + textStatus);
        }
    });
    return i;
}

function drawRow(producer) {
    var row = $("<tr />")
    $("#producerList").append(row);
    row.append($("<td>" + producer.producerId + "</td>"));
    row.append($("<td>" + producer.producerName+"</td>"));
    row.append($("<td>" + producer.country+"</td>"));
    row.append($("<td>" + getCountOfCarsByProducer(producer.producerId) + "</td>"));
    row.append($("<td>" + '<button class="mybutton" onclick="deleteProducer('+ producer.producerId +')"><span class="glyphicon glyphicon-trash"></span></button>' + "</td>"));
    row.append($("<td>"+'<button id="updateButton" class="mybutton" type="button" onclick="gotoUpdateProducer('+ producer.producerId +')"><span class="glyphicon glyphicon-pencil"></span></button>' + "</td>"));
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

/*
 function updateCar() {
 console.log('updateCar');
 var url = PREFIX_URL + CAR_URL + "";
 $.ajax({
 type: 'PUT',
 contentType: 'application/json',
 url: url,
 data: formToJSON(),
 success: function (data, textStatus, jqXHR) {
 alert('User updated successfully');
 findAll();
 },
 error: function (jqXHR, textStatus, errorThrown) {
 alert('updateUser error: ' + textStatus);
 }
 });
 }*/
/*
function formToJSON() {
    return JSON.stringify({
        "carId": $('#carId').val(),
        "carName": $('#carName').val(),
        "producerId": $('#producerId').val(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}
*/