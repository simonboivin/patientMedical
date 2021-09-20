const emailInput = document.getElementById("email");
const alertNewPatient = document.getElementById("alert_new_patient");
const validerButton = document.getElementById("valider_button");
const form = document.getElementById('patientAddForm');


function displayAlert(couleur, message) {
    alertNewPatient.innerHTML = '<div class=\"alert alert-' + couleur + '\" role=\"alert\">' + message + '</div>';
}

function closeAlert() {
    alertNewPatient.innerHTML = "";
}

function resetAlertsAndMessages() {
    closeAlert();
    if (emailInput.classList.contains("is-valid")) {
        emailInput.classList.remove("is-valid");
    }
    validerButton.disabled = false;
}

function logReset(event) {
    console.log("reset");
    resetAlertsAndMessages();
}

function doAjax() {
    console.log("doAjax");
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        // instructions of anonymous function
        resetAlertsAndMessages();
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
            if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailValue)) {
                console.log("Erreur de formattage de l'email");
                validerButton.disabled = true;
                displayAlert("danger", "Email invalide");

            } else if (xhr.responseText == "true") {
                console.log("mail du patient connu dans la base");
                displayAlert("warning", "Cette adresse email est déjà enregistrée.");
                validerButton.disabled = true;
            } else {
                emailInput.classList.add("is-valid");
                validerButton.disabled = false;
            }
        }
    };

    let emailValue = document.getElementById('email').value;
    xhr.open("GET", "/patients/check?email=" + emailValue, true);
    xhr.send();
}


emailInput.onchange = doAjax;
form.addEventListener('reset', logReset);

