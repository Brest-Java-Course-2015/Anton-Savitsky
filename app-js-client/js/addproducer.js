/**
 * Created by antonsavitsky on 08.12.15.
 */
var PREFIX_URL = "http://"+ location.hostname+ ":"+location.port +"/app-rest-1.0.0-SNAPSHOT";
var PRODUCER_URL = "/producer";

$('#addProducer').click(function () {
    addProducer();
});

function goHome() {
    window.location="producers.html";
}

function addProducer() {
    console.log('addProducer');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: PREFIX_URL + PRODUCER_URL,
        dataType: "json",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Производитель успешно добавлен!');
            if (confirm("Хотите добавить ещё одного производителя?")) {
            } else {
                goHome();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('Данные введены некорректно!\nПроверьте правильность введенных данных!');
        }
    });
}

function formToJSON() {
    console.log($('#producerName').val());
    return JSON.stringify({
        "producerName": $('#producerName').val(),
        "country": $('#country').val()
    });
}