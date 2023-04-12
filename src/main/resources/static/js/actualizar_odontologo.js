function obtenerOdontologo(id) {
    const url = `/odontologos/${id}`;
    return fetch(url)
    .then(response => response.json())
    .then(data =>{ return data} )
    .catch(error => console.log(error))
}

function actualizarOdontologo() {

    // Capturar espacio donde se renderizará el formulario
    const formulario = document.getElementById('form_actualizar_odontologo');

    const botones = document.querySelectorAll('button');
    
    botones.forEach(boton => {

        if (boton.name == "actualizar") {

          boton.addEventListener("click", (e) => {

            // Capturar ID del odontologo a actualizar
            const odontologoId_btn = e.target.getAttribute("id")
            // Obtener datos del odontologo
            obtenerOdontologo(odontologoId_btn)
            .then(data => { 
                // Renderizar formulario 
                formulario.innerHTML = `
                    <h3>Actualizar Odontólogo</h3>
                    <br>
                    <div class="mb-3">
                        <label for="new_nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="new_nombre" name="new_nombre" value="${data.nombre}" required>
                        <br>
                        <label for="new_apellido" class="form-label">Apellido</label>
                        <input type="text" class="form-control" id="new_apellido" name="new_apellido" value="${data.apellido}" required>
                        <br>
                        <label for="new_matricula" class="form-label">Matrícula</label>
                        <input type="text" class="form-control" id="new_matricula" name="new_matricula" value="${data.matricula}" required>
                    </div>
                    <button class="btn btn-success" type="submit" id="btn-actualizar">Actualizar</button>
                    <button class="btn btn-secondary" type="button" id="btn-cancelar">Cancelar</button>
                    <br><br>
                `
                // Programar botón "Cancelar" para que elimine el formulario
                const btn_cancelar = document.getElementById('btn-cancelar')
                btn_cancelar.addEventListener('click', function(event) { formulario.innerHTML = "" })
              
                /* --------------------------------------------- */
                /*         FETCH ACTUALIZAR ODONTOLOGO           */ 
                /* --------------------------------------------- */
                formulario.addEventListener('submit', function (event) {
                    event.preventDefault()

                    const matricula_data = document.getElementById('new_matricula').value
                    const apellido_data = document.getElementById('new_apellido').value
                    const nombre_data = document.getElementById('new_nombre').value

                    const payload = {
                        id: data.id,
                        matricula: normalizarMayusc(matricula_data),
                        apellido: normalizarTexto(apellido_data),
                        nombre: normalizarTexto(nombre_data)
                    };

                    const url = '/odontologos';
                    const settings = {
                        method: 'PUT',
                        headers: {
                            "Content-type": "application/json"
                        }, 
                        body: JSON.stringify(payload)
                    }
                
                    fetch(url, settings)
                    .then(response => response.json())
                    .catch(error => console.log(error))

                    location.reload()
                })
            }).catch(error => console.log(error))
         })
        }
    })
}