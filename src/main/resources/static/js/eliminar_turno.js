function eliminarTurno() {
    /* --------------------------------------------- */
    /*             FETCH ELIMINAR TURNO              */ 
    /* --------------------------------------------- */
    const botones = document.querySelectorAll('button');

    botones.forEach(boton => {

        if (boton.name == "eliminar") {

            boton.addEventListener("click", (e) => {

                let eliminar = confirm("¿Estas seguro que quieres eliminar el Turno?")

                if (eliminar) {
                    // Capturar ID del turno seleccionado
                    const turnoId_btn = e.target.getAttribute("id")
                    // Eliminar turno de la vista 
                    let celda = document.getElementById(`turno/${turnoId_btn}`);
                    celda.innerHTML = ""

                    const url = `/turnos/${turnoId_btn}`;
                    
                    const settings = {
                        method: 'DELETE'
                    }

                    fetch(url,settings)
                    .then(response => response.json())
                    .catch(error => console.log(error));

                }
            })
        }
    })
}
    
