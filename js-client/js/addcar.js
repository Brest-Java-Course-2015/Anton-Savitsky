// The root URL for the RESTful services
//$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var PREFIX_URL = "http://localhost:8080/rest-service-provider-1.0-SNAPSHOT";


var CAR_URL = "/car";
var PRODUCER_URL = "/producer";
var PRODUCER_DTO="/dto";
var idproducerselected;

function validateAndSubmit(form){
    if(validate(form)) addCar();
}

function getProducers() {
    console.log('getProducers');
    var url = PREFIX_URL+PRODUCER_URL + PRODUCER_DTO;
    $.ajax({
        type: 'GET',
        url: url,
        success: function(data){
            var producerdto = data.producers == null ? [] : (data.producers instanceof Array ? data.producers : [data.producers]);
            idproducerselected=producerdto[0].producerId;
            $.each(producerdto, function(index, producer){
                $(".prodnameselect").append("<option value="+producer.producerId+">"+producer.producerName+"</option>");
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        }
    });
}

function fillSelectList(){
    getProducers();
}

fillSelectList();

$(".prodnameselect").click(function() { idproducerselected=$(".prodnameselect").val(); } );

function goHome() {
    window.location="index.html";
}

function addCar() {
    if(confirm("Вы уверены, что хотите добавить автомобиль?")) {
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: PREFIX_URL + CAR_URL,
            dataType: "json",
            data: formToJSON(),
            success: function (data, textStatus, jqXHR) {
                alert('Автомобиль успешно добавлен!');
                if (confirm("Хотите добавить ещё один автомобиль?")) {
                } else {
                    goHome();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Данные введены некорректно!\nПроверьте правильность введенных данных!');
                console.log('addCar error: ' + textStatus + "\n" + errorThrown);
            }
        });
    }
}

function formToJSON() {
    console.log($('#dateOfCreation').val());
    return JSON.stringify({
        "carName": $('#carName').val(),
        //"producerId": idproducerselected.toString(),
        "producer": {"producerId": idproducerselected.toString()},
        "dateOfCreation": $('#dateOfCreation').val()
    });
}