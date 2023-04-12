function eliminarPaciente() {
    /* --------------------------------------------- */
    /*          FETCH ELIMINAR PACIENTE              */ 
    /* --------------------------------------------- */
    const botones = document.querySelectorAll('button');
    
    botones.forEach(boton => {

        if (boton.name == "eliminar") {

            boton.addEventListener("click", (e) => {

                let eliminar = confirm("¿Estas seguro que quieres eliminar el Paciente?")

                if (eliminar) {
                    // Capturar ID del paciente seleccionado
                    const pacienteId_btn = e.target.getAttribute("id")
                    // Eliminar paciente de la vista
                    let celda = document.getElementById(`paciente/${pacienteId_btn}`);
                    celda.innerHTML = ""
    
                    const url = `/pacientes/${pacienteId_btn}`;
    
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
    
