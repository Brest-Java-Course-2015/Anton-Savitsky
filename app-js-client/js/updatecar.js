/**
 * Created by antonsavitsky on 01.12.15.
 */
// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT";
var CAR_URL = "/car";
var PRODUCERDTO_URL = "/producersdto";




// Register listeners
$('#updateCar').click(function () {
    updateCar(sessionStorage.getItem('carId'));
    console.log(sessionStorage.getItem('carId'));
});


fillSelectList();

function fillSelectList(){
    getProducers();
}

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
            alert('getProducers: ' + textStatus);
        }
    });
}

$(".prodidselect").click(function() {
    console.log('$(".prodidselect").change');
    $('#producerName').val($(".prodidselect").val());
});

/*
insertValues();

function insertValues(){
    $('#carId').val(sessionStorage.getItem('carId'));
    $('#carName').val(sessionStorage.getItem('carName'));
    $('#producerId').val(sessionStorage.getItem('producerId'));
    $('#dateOfCreation').val(sessionStorage.getItem('dateOfCreation'));
}
*/

function goHome() {
    window.location="index.html";
}

function updateCar(carId) {
    if(confirm("Хотите обновить автомобиль?")){
        console.log('updateCar');
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
        "carId": sessionStorage.getItem("carId"),
        "carName": $('#carName').val(),
        "producerId": $(".prodidselect option:selected").text(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}

