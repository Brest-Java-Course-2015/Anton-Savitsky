/**
 * Created by antonsavitsky on 07.12.15.
 */
// The root URL for the RESTful services
var PREFIX_URL = "http://localhost:8080/app-rest-1.0.0-SNAPSHOT";
var PRODUCER_URL = "/producer";

/*
insertValues();

function insertValues(){

}
*/
// Register listeners
$('#updateProducer').click(function () {
    updateProducer(sessionStorage.getItem('producerId'));
    console.log(sessionStorage.getItem('producerId'));
    //goHome();
});


function goHome() {
    window.location="index.html";
}

function updateProducer(producerId) {
    if(confirm("Хотите обновить производителя?")){
        console.log('updateProducer');
        var url = PREFIX_URL + PRODUCER_URL;
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: url,
            data: formToJSON(),
            success: function (data, textStatus, jqXHR) {
                alert('Producer updated successfully');
                goHome();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('updateProducer error: ' + textStatus);
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