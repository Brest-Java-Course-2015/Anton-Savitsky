/**
 * Created by antonsavitsky on 08.12.15.
 */
// The root URL for the RESTful services
$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var PRODUCER_URL = "/producer";

function goHome() {
    window.location="producers.html";
}

function validateAndSubmit(form){
    if(validate(form)) addProducer();
}

function addProducer() {
    if(confirm("Вы уверены, что хотите добавить производителя?")) {
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
}

function formToJSON() {
    console.log($('#producerName').val());
    return JSON.stringify({
        "producerName": $('#producerName').val(),
        "country": $('#country').val()
    });
}