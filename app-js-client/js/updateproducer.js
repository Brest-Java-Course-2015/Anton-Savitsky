/**
 * Created by antonsavitsky on 07.12.15.
 */
// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT";
var PRODUCER_URL = "/producer";

function insertVals() {
    $('#producerId').val(sessionStorage.getItem('producerId'));
    $('#producerName').val(sessionStorage.getItem('name'));
    $('#country').val(sessionStorage.getItem('country'));
}

insertVals();

// Register listeners
$('#updateProducer').click(function () {
    updateProducer(sessionStorage.getItem('producerId'));
    console.log(sessionStorage.getItem('producerId'));
    goHome();
});


function goHome() {
    window.location="producers.html";
}

function updateProducer(producerId) {
    if(confirm("Хотите обновить производителя?")){
        console.log('updateProducer id='+producerId);
        var url = PREFIX_URL + PRODUCER_URL;
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: url,
            data: formToJSON(),
            success: function (data, textStatus, jqXHR) {
                alert('Производитель успешно обновлен!');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Возникла ошибка обновления производителя!\nПроверьте введенные данные и попробуйте снова');
            }
        });
    }else{goHome();}
}

function formToJSON() {
    return JSON.stringify({
        "producerId": sessionStorage.getItem("producerId"),
        "producerName": $('#producerName').val(),
        "country": $('#country').val()
    });
}