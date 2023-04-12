function eliminarOdontologo() {
    /* --------------------------------------------- */
    /*          FETCH ELIMINAR ODONTOLOGO            */ 
    /* --------------------------------------------- */
    const botones = document.querySelectorAll('button');

    botones.forEach(boton => {

        if (boton.name == "eliminar") {

            boton.addEventListener("click", (e) => {

                let eliminar = confirm("¿Estas seguro que quieres eliminar el Odontologo?")

                if (eliminar) {
                    // Capturar ID del odontologo seleccionado
                    const odontologoId_btn = e.target.getAttribute("id")
                    // Eliminar odontologo de la vista
                    let celda = document.getElementById(`odontologo/${odontologoId_btn}`);
                    celda.innerHTML = ""

                    const url = `/odontologos/${odontologoId_btn}`;
                    
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
    
