/**
 * Created by antonsavitsky on 01.12.15.
 */
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT";
var CAR_URL = "/car";
var PRODUCERDTO_URL = "/producersdto";

function getProducers() {
    console.log('getProducers');
    var url = PREFIX_URL + PRODUCERDTO_URL;
    $.ajax({
        type: 'GET',
        url: url,
        dataType: "json", // data type of response
        success: function(data){
            var dto = data.producers == null ? [] : (data.producers instanceof Array ? data.producers : [data.producers]);
            $.each(dto, function (index, producer) {
                $(".prodidselect").append("<option value="+producer.producerName+">"+producer.producerId+"</option>");
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        }
    });
}
$('#updateCar').click(function () {
    updateCar();
});

function fillSelectList(){
    getProducers();
}

fillSelectList();
function insertValues(){
    $('#carId').val(sessionStorage.getItem('carId'));
    $('#carName').val(sessionStorage.getItem('carName'));
    $('#producerId').val(sessionStorage.getItem('producerId'));
    $('#dateOfCreation').val(sessionStorage.getItem('dateOfCreation'));
    $('#producerName').val(sessionStorage.getItem('producerName'));
}
insertValues();

$(".prodidselect").click(function() {
    $('#producerName').val($(".prodidselect").val());
});

function goHome() {
    window.location="index.html";
}

function updateCar() {
    if(confirm("Хотите обновить автомобиль?")){
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
    }else{goHome();}
}

function formToJSON() {
    return JSON.stringify({
        "carId": $('#carId').val(),
        "carName": $('#carName').val(),
        "producerId": $(".prodidselect option:selected").text(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}

