/**
 * Created by antonsavitsky on 08.12.15.
 */
// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT"
var PRODUCER_URL = "/producer";

// Register listeners
$('#addProducer').click(function () {
    addProducer();
    //goHome();
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
            alert('addProducer error: ' + textStatus);
        }
    });
}

function formToJSON() {
    return JSON.stringify({
        "producerName": $('#producerName').val(),
        "country": $('#country').val()
    });
}