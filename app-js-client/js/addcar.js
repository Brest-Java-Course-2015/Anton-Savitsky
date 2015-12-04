// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT"
var CAR_URL = "/car";

// Register listeners
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
            alert('Car added successfully');
            if (confirm("Хотите добавить ещё один автомобиль?")) {
            } else {
                goHome();
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('addCar error: ' + textStatus);
        }
    });
}

function formToJSON() {
    return JSON.stringify({
        "carName": $('#carName').val(),
        "producerId": $('#producerId').val(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}