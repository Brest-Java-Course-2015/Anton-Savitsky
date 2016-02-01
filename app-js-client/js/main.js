// The root URL for the RESTful services
$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var CAR_URL = "/car";
var CARDTO_URL = "/carsdto";
var CARDTOBYDATE_URL="/carsdtobydate";
var PRODUCERDTO_URL="/producersdto";
var producerdto=null;

initData();

function gotoAddCar() {
    window.location = "addCar.html";
}

function gotoUpdateCar(carId,carName,producerId,dateOfCreation, producerName)
{
    sessionStorage.setItem('carId', carId);
    sessionStorage.setItem('carName', carName);
    sessionStorage.setItem('producerId', producerId);
    sessionStorage.setItem('dateOfCreation', dateOfCreation);
    sessionStorage.setItem('producerName', producerName);
    window.location="updateCar.html";
}

function deleteCar(carId, carName) {
    if (confirm("Вы уверены, что хотите удалить автомобиль "+carName+"?"))
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
        dataType: "json",
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('Произошла ошибка получения данных из БД!');
        }
    });
}

function initData(){
    console.log('getProducerDto');
    var url = PREFIX_URL + PRODUCERDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json",
        success: function(data){
            producerdto= data.producers == null ? [] : (data.producers instanceof Array ? data.producers : [data.producers]);
            findAll();
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
       }
    });
    return retval;
}

function drawRow(car) {
    var row = $("<tr />")
    $("#carList").append(row);
    row.append($("<td>" + car.carName+"</td>"));
    var prodName=getProducerNameById(car.producerId);
    row.append($("<td>" + prodName.toString()+"</td>"));
    row.append($("<td>" + car.dateOfCreation +"</td>"));
    row.append($("<td>" + '<button id="delete'+car.carId+'" class="mybutton"><span class="glyphicon glyphicon-trash"></span></button>' + "</td>"));
    $("#delete"+car.carId).click(function(){
        deleteCar(car.carId, car.carName);
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

function lookupCarsByDate(){
        var dateBefore=$("#dateBefore").val();
        var dateAfter=$("#dateAfter").val();
        var url=PREFIX_URL+CARDTOBYDATE_URL+"?dateBefore="+dateBefore.toString()+"&dateAfter="+dateAfter.toString();
        $.ajax({
            type: 'GET',
            url: url,
            dataType: "json",
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