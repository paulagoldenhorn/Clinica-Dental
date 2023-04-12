function obtenerTurno(id) {
    const url = `/turnos/${id}`;
    return fetch(url)
    .then(response => response.json())
    .then(data =>{ return data} )
    .catch(error => console.log(error))
}
function obtenerOdontologos(dondeRenderizar) {
    const url = "/odontologos";
    fetch(url)
    .then((response) => response.json())
    .then((data) => {
      for (odontologo of data) {
        let { id, nombre, apellido } = odontologo;
        dondeRenderizar.innerHTML += `<option value=${id}>${apellido}, ${nombre}</option>`;
      }
    });
}
function obtenerPacientes(dondeRenderizar){
    const url = "/pacientes";
    fetch(url)
    .then((response) => response.json())
    .then((data) => {
      for (paciente of data) {
        let { id, nombre, apellido } = paciente;
        dondeRenderizar.innerHTML += `<option value=${id}>${apellido}, ${nombre}</option>`;
      }
    });
}

function actualizarTurno() {

    // Capturar espacio donde se renderizará el formulario
    const formulario = document.getElementById('form_actualizar_turno');

    const botones = document.querySelectorAll('button');
    
    botones.forEach(boton => {

        if (boton.name == "actualizar") {

          boton.addEventListener("click", (e) => {

            // Capturar ID del turno a actualizar
            const turnoId_btn = e.target.getAttribute("id")
            // Obtener datos del turno
            obtenerTurno(turnoId_btn)
            .then(turno => {  
                // Renderizar formulario  
                formulario.innerHTML = `
                <h3>Actualizar Turno</h3>
                <br>
                <div class="mb-3">
                      <label for="new_fecha" class="form-label">Fecha</label>
                      <input type="date" class="form-control" id="new_fecha" name="new_fecha" value=${turno.fecha} required>
                    
                      <label for="new_hora" class="form-label">Hora</label>
                      <input type="time" class="form-control" id="new_hora" name="new_hora" value=${turno.hora} required>
                    
                      <div class="form-group">
                        <label for="formControlSelect">Odontólogo</label>
                        <select class="form-control" id="formControlSelect_odontologo">
                          
                        </select>
                        <label for="formControlSelect">Paciente</label>
                        <select class="form-control" id="formControlSelect_paciente">
                          
                        </select>
                      </div>
                  </div>
                  <button class="btn btn-success" type="submit" id="btn-actualizar">Actualizar</button>
                  <button class="btn btn-secondary" type="button" id="btn-cancelar">Cancelar</button
                  <br><br>
                `
                // Capturar espacion donde se renderizarán los odontólogos y pacientes
                const opciones_odontologos = document.getElementById('formControlSelect_odontologo')
                const opciones_pacientes = document.getElementById('formControlSelect_paciente')

                obtenerOdontologos(opciones_odontologos)
                obtenerPacientes(opciones_pacientes)
                
                // Capturar ID del odontologo al que se le haga click
                let id_odontologo_seleccionado = turno.odontologo.id;
                opciones_odontologos.addEventListener("change", function (event) { id_odontologo_seleccionado = event.target.value });
                // Capturar ID del paciente al que se le haga click
                let id_paciente_seleccionado = turno.paciente.id;
                opciones_pacientes.addEventListener("change", function (event) {  id_paciente_seleccionado = event.target.value });

                // Programar botón "Cancelar" para que elimine el formulario
                const btn_cancelar = document.getElementById('btn-cancelar')
                btn_cancelar.addEventListener('click', function(event) { formulario.innerHTML = "" })
              
                /* --------------------------------------------- */
                /*           FETCH ACTUALIZAR TURNO              */ 
                /* --------------------------------------------- */
                formulario.addEventListener('submit', function (event) {
                    event.preventDefault()

                    const fecha_data = document.getElementById('new_fecha').value
                    const hora_data = document.getElementById('new_hora').value

                    const payload = {
                        id: turno.id,
                        fecha: fecha_data,
                        hora: hora_data,
                        paciente: {id: id_paciente_seleccionado},
                        odontologo: {id: id_odontologo_seleccionado}
                    };

                    const url = '/turnos';
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
