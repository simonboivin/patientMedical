function charAlea() {
    let chaine = "";
    for (let i = 0; i < aleaBetween(10, 20); i++) {
        let caractere = aleaBetween(48, 90);
        while (caractere > 57 & caractere < 65) { caractere = aleaBetween(48, 90); }
        chaine += String.fromCharCode(caractere);
    }
    return chaine;
}

function aleaBetween(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}


