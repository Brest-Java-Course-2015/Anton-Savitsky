/**
 * Created by antonsavitsky on 13.01.16.
 */

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
    var isValid=1;
    var elems = form.elements;

    resetError(elems.carName.parentNode);
    if (!elems.carName.value) {
        showError(elems.carName.parentNode, 'Укажите название модели автомобиля!');
        isValid=0;
    }

    resetError(elems.producerName.parentNode);
    console.log("______"+elems.producerName);
    if (!elems.producerName.value) {
        showError(elems.producerName.parentNode, 'Укажите имя производителя!');
        isValid=0;
    }

    resetError(elems.dateOfCreation.parentNode);
    if (!elems.dateOfCreation.value) {
        showError(elems.dateOfCreation.parentNode, 'Укажите дату выпуска автомобиля!');
        isValid=0;
    }
    return isValid;
}