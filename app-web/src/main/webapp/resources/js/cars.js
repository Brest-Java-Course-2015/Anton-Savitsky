/**
 * Created by antonsavitsky on 22.02.16.
 */
function deleteCar(carId) {
        console.log('deleteCar' + carId);
        var url = "car/delete/" + carId;
        $.ajax({
            type: 'GET',
            url: url,
            success: function (data, textStatus, jqXHR) {
                //alert('Автомобиль успешно удален!');
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