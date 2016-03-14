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

    resetError(elems.producerName.parentNode);
    if (!elems.producerName.value) {
        showError(elems.producerName.parentNode, 'Укажите название производителя!');
        isValid=0;
    }

    resetError(elems.country.parentNode);
    if (!elems.country.value) {
        showError(elems.country.parentNode, 'Укажите страну!');
        isValid=0;
    }
    return isValid;
}