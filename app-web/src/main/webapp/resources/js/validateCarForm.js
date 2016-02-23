/**
 * Created by antonsavitsky on 23.02.16.
 */
$("#carForm").submit(function(event) {
    console.log("validation...");
    if(validate(document.getElementById("carForm"))) {
        if (confirm("Вы действительно хотите изменить\nданные об автомобиле?")) {
            console.log("submitted");
            return true;
        } else event.preventDefault();
    } else {
        event.preventDefault();
    }
});

function showError(container, errorMessage) {
    container.className = 'error';
    var msgElem = document.createElement('span');
    msgElem.className = "error-message";
    msgElem.innerHTML = errorMessage;
    container.appendChild(msgElem);
}

function resetError(container) {
    container.className = '';
    if (container.lastChild.className == "error-message") {
        container.removeChild(container.lastChild);
    }
}

function validate(form) {
    console.log("8898"+form);
    var isValid=true;
    var elems = form.elements;
    console.log("sdf");

    resetError(elems.carName.parentNode);
    console.log("______"+elems.carName);
    if (!elems.carName.value) {
        showError(elems.carName.parentNode, 'Укажите название модели автомобиля!');
        isValid=false;
    }

    resetError(elems.producerId.parentNode);
    console.log("______"+elems.producerId);
    if (!elems.producerId.value) {
        showError(elems.producerId.parentNode, 'Укажите имя производителя!');
        isValid=false;
    }

    resetError(elems.dateOfCreation.parentNode);
    console.log("______"+elems.dateOfCreation);
    if (!elems.dateOfCreation.value) {
        showError(elems.dateOfCreation.parentNode, 'Укажите дату выпуска автомобиля!');
        isValid=false;
    }
    return isValid;
}
