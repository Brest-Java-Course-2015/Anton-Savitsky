/**
 * Created by antonsavitsky on 01.12.15.
 */
// The root URL for the RESTful services
$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var PREFIX_URL = "http://localhost:8080/rest-service-provider-1.0-SNAPSHOT";

var CAR_URL = "/car";
var PRODUCERDTO_URL = "/dto";
var PRODUCER_URL="/producer";
var idproducerselected=null;
var carid=null;
//set id of selected producer onclick
$(".prodnameselect").click(function() { idproducerselected=$(".prodnameselect").val(); } );


function validateAndSubmit(form){
    if(validate(form)) updateCar();
}

function insertValues(){
    console.log("insertValues");
    carid=sessionStorage.getItem('carId');
    $('#carName').val(sessionStorage.getItem('carName'));
    $('#producerName').val(sessionStorage.getItem('producerName'));
    idproducerselected=sessionStorage.getItem('producerId');
    $('#dateOfCreation').val(sessionStorage.getItem('dateOfCreation'));
}
insertValues();

function getProducersDto() {
    console.log('getProducers');
    var url = PREFIX_URL +PRODUCER_URL+ PRODUCERDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        success: function(data){
            var producerdto = data.producers == null ? [] : (data.producers instanceof Array ? data.producers : [data.producers]);
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
    getProducersDto();
}

fillSelectList();


function goHome() {
    window.location="index.html";
}

function updateCar() {
    if(confirm("Вы уверены, что хотите обновить автомобиль?")){
        var url = PREFIX_URL + CAR_URL;
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: url,
            data: formToJSON(),
            success: function (data, textStatus, jqXHR) {
                alert('Автомобиль успешно обновлен!');
                goHome();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Ошибка обновления! Проверьте поля ввода!');
            }
        });
    }
}

function formToJSON() {
    return JSON.stringify({
        "carId": carid.toString(),
        "producer": {"producerId": idproducerselected},
        "carName": $('#carName').val(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}

