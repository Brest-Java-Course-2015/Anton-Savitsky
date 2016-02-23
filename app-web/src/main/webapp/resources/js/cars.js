/**
 * Created by antonsavitsky on 22.02.16.
 */
function deleteCar(carId) {
        console.log('deleteCar' + carId);
        var url = "car/delete/" + carId;
    // Confirmation and Alert windows don't work in Chromium
    if(confirm('Вы действительно хотите удалить этот автомобиль?'))
        $.ajax({
            type: 'POST',
            url: url,
            success: function (data, textStatus, jqXHR) {
                alert('Автомобиль успешно удален!');
                console.log("success");
                location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
               alert('Ошибка удаления автомобиля!');
            }
        });
}
function gotoUpdateCar(carId) {
    window.location='car/update/' + carId;
}

$("#addCar").click(function(){
    window.location='car/add';
});