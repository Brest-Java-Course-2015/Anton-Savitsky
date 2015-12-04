/**
 * Created by antonsavitsky on 01.12.15.
 */
// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT";
var CAR_URL = "/car";

// Register listeners
$('#updateCar').click(function () {
    updateCar(sessionStorage.getItem('carId'));
    console.log(sessionStorage.getItem('carId'));
    //goHome();
});


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
                alert('Car updated successfully');
                goHome();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('updateUser error: ' + textStatus);
            }
        });
    }else{goHome();}
}

function formToJSON() {
    return JSON.stringify({
        "carId": sessionStorage.getItem("carId"),
        "carName": $('#carName').val(),
        "producerId": $('#producerId').val(),
        "dateOfCreation": $('#dateOfCreation').val()
    });
}