const editRdvButtons = document.getElementsByClassName("edit_rdv_button");
const deleteRdvButtons = document.getElementsByClassName("delete_rdv_button");



function editRdv(id) {
    console.log("edit RDV" + id);
    let editRdvModalForm = document.getElementById('editRdvModalForm');
    let modalFormLink = '/rdv/edit/' + id;
    editRdvModalForm.setAttribute("action", modalFormLink);
    $.get("/rdv/edit/" + id)
        .done(function (data) {
            $("#editRdvModalBody").html(data);
        });
}


for (let i = 0; i < editRdvButtons.length; i++) {
    editRdvButtons[i].addEventListener("click", () => {
        editRdv(editRdvButtons[i].getAttribute('dataid'));
    });
}


function deleteRdv(id) {
    if (confirm("Le rendez-vous sera supprimé, êtes-vous sur?")) {
        let url = "/rdv/delete/" + id;
        let parametres = { method: 'POST' };
        fetch(url, parametres).then(function (response) {
            if (response.ok) {
                window.location = "/rdv/list?success";
            } else {
                alert("Problème");
            }
        })
    }
    ;

}

for (let i = 0; i < deleteRdvButtons.length; i++) {
    deleteRdvButtons[i].addEventListener("click", () => {
        deleteRdv(deleteRdvButtons[i].getAttribute('dataid'));
    });
}
