/**
 * Created by antonsavitsky on 01.12.15.
 */
// The root URL for the RESTful services
$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var CAR_URL = "/car";
var PRODUCERDTO_URL = "/producersdto";
var idproducerselected=null;
var carid=null;

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

function getProducers() {
    console.log('getProducers');
    var url = PREFIX_URL + PRODUCERDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json",
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
    getProducers();
}

fillSelectList();

$(".prodnameselect").click(function() { idproducerselected=$(".prodnameselect").val(); } );

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
        "carName": $('#carName').val(),
        "producerId": idproducerselected.toString(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}

