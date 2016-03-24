/**
 * Created by antonsavitsky on 13.01.16.
 */
$("#producerForm").submit(function(event) {
    console.log("validation...");
    if(validate(document.getElementById("producerForm"))) {
        if (confirm("Вы действительно хотите сохранить изменения?")) {
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
    var isValid=true;
    var elems = form.elements;

    resetError(elems.producerName.parentNode);
    if (!elems.producerName.value) {
        showError(elems.producerName.parentNode, 'Укажите название производителя!');
        isValid=false;
    }

    resetError(elems.country.parentNode);
    if (!elems.country.value) {
        showError(elems.country.parentNode, 'Укажите страну!');
        isValid=false;
    }
    return isValid;
}