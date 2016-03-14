/**
 * Created by antonsavitsky on 07.12.15.
 */
// The root URL for the RESTful services
$("head").append($('<script type="text/javascript" src="js/properties.js"></script>'));
var PRODUCER_URL = "/producer";

function validateAndSubmit(form){
    if(validate(form)) updateProducer();
}

function insertVals() {
    $('#producerId').val(sessionStorage.getItem('producerId'));
    console.log(sessionStorage.getItem('producerId'));
    $('#producerName').val(sessionStorage.getItem('name'));
    console.log(sessionStorage.getItem('name'));
    $('#country').val(sessionStorage.getItem('country'));
    console.log(sessionStorage.getItem('country'));
}

insertVals();

function goHome() {
    window.location="producers.html";
}

function updateProducer() {
    if(confirm("Вы уверены, что хотите обновить производителя?")){
        var url = PREFIX_URL + PRODUCER_URL;
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: url,
            data: formToJSON(),
            success: function (data, textStatus, jqXHR) {
                alert('Производитель успешно обновлен!');
                goHome();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('Возникла ошибка обновления производителя!\nПроверьте введенные данные и попробуйте снова');
            }
        });
    }
}

function formToJSON() {
    return JSON.stringify({
        "producerId": sessionStorage.getItem("producerId"),
        "producerName": $('#producerName').val(),
        "country": $('#country').val()
    });
}