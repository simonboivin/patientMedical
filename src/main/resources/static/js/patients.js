let emailInput = document.getElementById("email");
let alertNewPatient = document.getElementById("alert_new_patient");

function displayAlert(couleur, message) {
    alertNewPatient.innerHTML = '<div class=\"alert alert-' + couleur + '\" role=\"alert\">'+ message +'</div>';
}

function doAjax() {

    console.log("doAjax");

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        // instructions of anonymous function
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
            if(xhr.responseText=="true") {
                console.log("mail du patient connu dans la base");
                displayAlert("danger","Cette adresse email est déjà enregistrée.");
            }
        }
    };

    let emailValue = document.getElementById('email').value;
    xhr.open("GET", "/patients/check?email="+emailValue, true);
    xhr.send();
}


emailInput.onchange=doAjax;


