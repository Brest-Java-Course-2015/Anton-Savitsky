// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT";
var CAR_URL = "/car";
var CARDTO_URL = "/carsdto";
var CARDTOBYDATE_URL="/carsdtobydate";

findAll();

function gotoAddCar() {
    window.location = "addCar.html";
}

function gotoUpdateCar(carId)
{
    sessionStorage.setItem('carId', carId);
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
                    alert('Car deleted successfully!');
                    findAll();
                },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('deleteCar error: ' + textStatus + " for carId="+carId);
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
            alert('findAll: ' + textStatus);
        }
    });
}

function drawRow(car) {
    var row = $("<tr />")
    $("#carList").append(row);
    row.append($("<td>" + car.carId + "</td>"));
    row.append($("<td>" + car.carName+"</td>"));
    row.append($("<td>" + car.producerId + "</td>"));
    row.append($("<td>" + car.dateOfCreation +"</td>"));
    row.append($("<td>" + '<button class="mybutton" onclick="deleteCar('+ car.carId +')"><span class="glyphicon glyphicon-trash"></span></button>' + "</td>"));
    row.append($("<td>"+'<button id="updateButton" class="mybutton" type="button" onclick="gotoUpdateCar('+ car.carId +')"><span class="glyphicon glyphicon-pencil"></span></button>' + "</td>"));

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
                alert('lookupCarsByDate: ' + textStatus);
            }
        });
    }
function undoLookUp(){
    findAll();
    $("#dateBefore").val("");
    $("#dateAfter").val("");
}