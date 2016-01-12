// The root URL for the RESTful services
console.log(location.hostname);
console.log(location.port);
var PREFIX_URL = "http://"+ location.hostname+ ":"+location.port +"/app-rest-1.0.0-SNAPSHOT";
var CAR_URL = "/car";
var CARDTO_URL = "/carsdto";
var CARDTOBYDATE_URL="/carsdtobydate";
var PRODUCERDTO_URL="/producersdto";
var producerdto=null;

getProducerDto();
findAll();

function gotoAddCar() {
    window.location = "addCar.html";
}

function gotoUpdateCar(carId,carName,producerId,dateOfCreation, producerName)
{
    sessionStorage.setItem('carId', carId);
    sessionStorage.setItem('carName', carName);
    sessionStorage.setItem('producerId', producerId);
    sessionStorage.setItem('dateOfCreation', dateOfCreation);
    sessionStorage.setItem('producerName', producerName)
    window.location="updateCar.html";
}

function goHome() {
    window.location="index.html";
}

function deleteCar(carId) {
    if (confirm("Вы уверены, что хотите удалить автомобиль с id=" + carId + "?"))
    {
    console.log('deleteCar' + carId);
    var url = PREFIX_URL + CAR_URL + "?id=" + carId;
    $.ajax({
        type: 'DELETE',
        url: url,
        success: function (data, textStatus, jqXHR) {
                    alert('Автомобиль успешно удален!');
                    findAll();
                },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Ошибка удаления автомобиля!');
        }
    });
    }
}

function findAll() {
    console.log('findAll');
    var url = PREFIX_URL + CARDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json", // data type of response
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('Произошла ошибка получения данных из БД!');
        }
    });
}

function getProducerDto(){
    console.log('getProducerDto');
    var url = PREFIX_URL + PRODUCERDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json", // data type of response
        success: function(data){
            producerdto= data.producers == null ? [] : (data.producers instanceof Array ? data.producers : [data.producers]);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        }
    });
}

function getProducerNameById(producerid){
    var retval="---";
    $.each(producerdto, function (index, producer) {
       if(producer.producerId==producerid) {
           retval=producer.producerName;
           return;
       }
    });
    return retval;
}

function drawRow(car) {
    var row = $("<tr />")
    $("#carList").append(row);
    row.append($("<td>" + car.carId + "</td>"));
    row.append($("<td>" + car.carName+"</td>"));
    var prodName=getProducerNameById(car.producerId);
    row.append($("<td>" + prodName.toString()+"</td>"));
    row.append($("<td>" + car.producerId + "</td>"));
    console.log('data='+car.dateOfCreation);
    row.append($("<td>" + car.dateOfCreation.toString() +"</td>"));
    row.append($("<td>" + '<button id="delete'+car.carId+'" class="mybutton"><span class="glyphicon glyphicon-trash"></span></button>' + "</td>"));
    $("#delete"+car.carId).click(function(){
        deleteCar(car.carId);
    });
    row.append($("<td>"+'<button id="update'+car.carId+'"updateButton" class="mybutton" type="button"><span class="glyphicon glyphicon-pencil"></span></button>' + "</td>"));
    $("#update"+car.carId).click(function(){
        gotoUpdateCar(car.carId, car.carName, car.producerId, car.dateOfCreation, prodName.toString());
    });
}

function renderList(data) {
    cleanOldData();
    var dto = data.cars == null ? [] : (data.cars instanceof Array ? data.cars : [data.cars]);
    var total = data.total;
    $.each(dto, function (index, car) {
        drawRow(car);
    });
    $('#carTotal').append($("<p>Всего автомобилей: " + total + "</p>"));
}

function cleanOldData(){
    $("#carList tr").remove();
    $('#carTotal p').remove();
}

function formToJSON() {
    return JSON.stringify({
        "carId": $('#carId').val(),
        "carName": $('#carName').val(),
        "producerId": $('#producerId').val(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}

    function lookupCarsByDate(){
        var dateBefore=$("#dateBefore").val();
        var dateAfter=$("#dateAfter").val();
        var url=PREFIX_URL+CARDTOBYDATE_URL+"?dateBefore="+dateBefore.toString()+"&dateAfter="+dateAfter.toString();
        $.ajax({
            type: 'GET',
            url: url,
            dataType: "json", // data type of response
            success: renderList,
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
                alert('Ошибка поиска по дате!\nПроверьте введенные даты.');
            }
        });
    }
function undoLookUp(){
    findAll();
    $("#dateBefore").val("");
    $("#dateAfter").val("");
}