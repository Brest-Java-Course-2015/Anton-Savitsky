// The root URL for the RESTful services
$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var CAR_URL = "/car";
var PRODUCERDTO_URL = "/producersdto";
var idproducerselected;

function validateAndSubmit(form){
    if(validate(form)) addCar();
}

function getProducers() {
    console.log('getProducers');
    var url = PREFIX_URL + PRODUCERDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json",
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
    return JSON.stringify({
        "carName": $('#carName').val(),
        "producerId": idproducerselected.toString(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}