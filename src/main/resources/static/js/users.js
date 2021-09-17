const nomInput = document.getElementById("nom_input");
const usernameInput = document.getElementById("username_input");
const emailInput = document.getElementById("email_input");
const passwordInput = document.getElementById("password_input");
const photouserInput = document.getElementById("photouser_input");
const progress = document.getElementById("progress");
const addUserForm = document.getElementById("addUserform");
const alertPlaceholder = document.getElementById('liveAlertPlaceholder')
var progressStatut = 0;
var nomInputNotProgressed = true;
var emailInputNotProgressed = true;
var passwordInputNotProgressed = true;
var photouserInputNotProgressed = true;

function generatePassword() {
    passwordInput.value = charAlea();
}


function nomInputOnBlur() {
    usernameInput.value = nomInput.value + "." + aleaBetween(100, 1000);
    if (nomInput.value != "" & nomInputNotProgressed) {
        progressStatut += 25;
        progress.setAttribute("style", "width:" + progressStatut + "%;");
        progress.setAttribute("aria-valuenow", progressStatut);
        nomInputNotProgressed = false;
    }
}

function emailInputOnBlur() {

    if (emailInput.value != "" & emailInputNotProgressed) {
        progressStatut += 25;
        progress.setAttribute("style", "width:" + progressStatut + "%;");
        progress.setAttribute("aria-valuenow", progressStatut);
        emailInputNotProgressed = false;
    }
}


function passwordInputOnBlur() {
    if (passwordInput.value != "" & passwordInputNotProgressed) {
        progressStatut += 25;
        progress.setAttribute("style", "width:" + progressStatut + "%;");
        progress.setAttribute("aria-valuenow", progressStatut);
        passwordInputNotProgressed = false;
    }
}

function photouserOnBlur() {
    if (photouserInput.value != "" & photouserInputNotProgressed) {
        progressStatut += 25;
        progress.setAttribute("style", "width:" + progressStatut + "%;");
        progress.setAttribute("aria-valuenow", progressStatut);
        photouserInputNotProgressed = false;
    }
}

function verifyForm() {

    let chiffres = /[0-9]/g;
    let lettres = /[A-Z]/g;
    if (passwordInput.value.match(chiffres) & passwordInput.value.match(chiffres)) {
        return true;
    } else {
        alert('Votre mot de passe doit contenir au moins une majuscule et un chiffre.');
        return false;
    }
}

function alert(message) {
    let wrapper = document.createElement('div')
    wrapper.innerHTML = '<div class="alert alert-danger" role="alert">' + message + '</div>'
    alertPlaceholder.append(wrapper)
}



nomInput.onblur = nomInputOnBlur;
emailInput.onblur = emailInputOnBlur;
passwordInput.onblur = passwordInputOnBlur;
photouserInput.onblur = photouserOnBlur;


addUserForm.onsubmit = verifyForm;