// The root URL for the RESTful services
//$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
//var PREFIX_URL = "http://localhost:8081/rest";
var PREFIX_URL = "http://localhost:8080/rest-service-provider-1.0-SNAPSHOT";


var CAR_URL = "/car";
var CARDTO_URL = "/dto";
var CARDTOBYDATE_URL="/dto/date";

//----Websockets experments------------

var url='http://localhost:8080/rest-service-provider-1.0-SNAPSHOT/endpoint';

var sock=new SockJS(url);

var stomp=Stomp.over(sock);

var sendOnConnectCallback=function(){
    stomp.send("/app/message", {}, JSON.stringify({'message':'hey bro'}));
};

var onMessageReceivedCallback=function(message){
    console.log("Received: "+message.body+'\n________________________!!!');
    renderList(JSON.parse(message.body));
};

var subscribeOnConnectCallback=function(){
    stomp.subscribe("/topic/update", onMessageReceivedCallback);
};

stomp.connect("anton","mypass", subscribeOnConnectCallback);





//-----------------------------


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
    sessionStorage.setItem('producerName', producerName);
    window.location="updateCar.html";
}

function deleteCar(carId, carName) {
    if (confirm("Вы уверены, что хотите удалить автомобиль "+carName+"?"))
    {
        console.log('deleteCar' + carId);
        var url = PREFIX_URL + CAR_URL + "/" + carId;
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
    var url = PREFIX_URL + CAR_URL + CARDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json",
        success: function(data) {
            console.log(data.toString());
            renderList(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('Произошла ошибка получения данных из БД!');
        }
    });
}

function drawRow(car) {
    var row = $("<tr id='row"+car.carId+"'/>");
    $("#carList").append(row);
    row.append($("<td>" + car.carName+"</td>"));
    row.append($("<td>" + car.producer.producerName+"</td>"));
    row.append($("<td>" + car.dateOfCreation +"</td>"));
    row.append($("<td>" + '<button id="delete'+car.carId+'" class="mybutton"><span class="glyphicon glyphicon-trash"></span></button>' + "</td>"));
    $("#delete"+car.carId).click(function(){
        deleteCar(car.carId, car.carName);
    });
    row.append($("<td>"+'<button id="update'+car.carId+'"updateButton" class="mybutton" type="button"><span class="glyphicon glyphicon-pencil"></span></button>' + "</td>"));
    $("#update"+car.carId).click(function(){
        gotoUpdateCar(car.carId, car.carName, car.producer.producerId, car.dateOfCreation, car.producer.producerName);
    });
}

function renderList(data) {
    console.log('inside renderList');
    cleanOldData();
    var dto = data.cars == null ? [] : (data.cars instanceof Array ? data.cars : [data.cars]);

    var total = data.total;
    console.log('total:'+total);
    $.each(dto, function (index, car) {
        console.log(car.toString());
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
    var url=PREFIX_URL+CAR_URL+CARDTOBYDATE_URL+"?dateBefore="+dateBefore.toString()+"&dateAfter="+dateAfter.toString();
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


