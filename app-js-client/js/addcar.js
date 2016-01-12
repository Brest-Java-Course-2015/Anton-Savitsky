// The root URL for the RESTful services
var PREFIX_URL = "http://"+ location.hostname+ ":"+location.port +"/app-rest-1.0.0-SNAPSHOT";
var CAR_URL = "/car";

$('#addCar').click(function () {
        addCar();
        //goHome();
});

function goHome() {
    window.location="index.html";
}

function addCar() {
    console.log('addCar');
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
            console.log('addCar error: ' + textStatus + "\n" +errorThrown);
        }
    });
}

function formToJSON() {
    console.log($('#carName').val());
    if($('#carName').val()=="") $('#carName').val()==null;
    return JSON.stringify({
        "carName": $('#carName').val(),
        "producerId": $('#producerId').val(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}