function obtenerPaciente(id) {
  const url = `/pacientes/${id}`;
  return fetch(url)
  .then(response => response.json())
  .then(data => { return data})
  .catch(error => console.log(error))
}

function actualizarPaciente() {

    // Capturar espacio donde se renderizará el formulario
    const formulario = document.getElementById('form_actualizar_paciente');
    
    const botones = document.querySelectorAll('button');
    
    botones.forEach(boton => {

        if (boton.name == "actualizar") {

          boton.addEventListener("click", (e) => {

            // Capturar ID del paciente a actualizar
            const pacienteId_btn = e.target.getAttribute("id")
            // Obtener datos del paciente
            obtenerPaciente(pacienteId_btn)
            .then(data => {           
              // Renderizar formulario  
              formulario.innerHTML = `
                <h3>Actualizar paciente</h3>
                <br>
                <div class="mb-3">
                  <div class="row">
                    <div class="col">
                      <label for="new_nombre" class="form-label">Nombre</label>
                      <input type="text" class="form-control" id="new_nombre" name="new_nombre" value="${data.nombre}" required>
                    </div>
                    <div class="col">
                      <label for="new_apellido" class="form-label">Apellido</label>
                      <input type="text" class="form-control" id="new_apellido" name="new_apellido" value="${data.apellido}" required>
                    </div>
                    <div class="col">
                      <label for="new_email" class="form-label">Correo</label>
                      <input type="email" class="form-control" id="new_email" name="new_email" value="${data.email}" required>
                    </div>
                  </div>
                  
                  <br>
            
                  <div class="row">
                    <div class="col">
                      <label for="new_dni" class="form-label">DNI</label>
                      <input type="text" class="form-control" id="new_dni" name="new_dni" value="${data.dni}" required>
                    </div>
                    <div class="col">
                      <label for="new_ingreso" class="form-label">Fecha de ingreso</label>
                      <input type="date" class="form-control" id="new_ingreso" name="new_ingreso" value="${data.fechaIngreso}" required>
                    </div>
                  </div> 
                  
                  <br>
                  <br>
            
                  <h4>Domicilio</h4>
                  <br>
                  <div class="row">
                    <div class="col">
                      <label for="new_calle" class="form-label">Calle</label>
                      <input type="text" class="form-control" id="new_calle" name="new_calle" value="${data.domicilio.calle}" required>
                    </div>
                    <div class="col">
                      <label for="new_numero" class="form-label">Número</label>
                      <input type="text" class="form-control" id="new_numero" name="new_numero" value="${data.domicilio.numero}" required>
                    </div>
                  </div>
            
                  <br>
            
                  <div class="row">
                    <div class="col">
                      <label for="new_localidad" class="form-label">Localidad</label>
                      <input type="text" class="form-control" id="new_localidad" name="new_localidad" value="${data.domicilio.localidad}" required>
                    </div>
                    <div class="col">
                      <label for="new_provincia" class="form-label">Provincia</label>
                      <input type="text" class="form-control" id="new_provincia" name="new_provincia" value="${data.domicilio.provincia}" required>
                    </div>
                  </div>
           
                  <button class="btn btn-success" type="submit" id="btn-actualizar">Actualizar</button>
                  <button class="btn btn-secondary" type="button" id="btn-cancelar">Cancelar</button>
                  <br><br>
                `
                // Programar botón "Cancelar" para que elimine el formulario
                const btn_cancelar = document.getElementById('btn-cancelar')
                btn_cancelar.addEventListener('click', function(event) { formulario.innerHTML = "" })
                
                  /* --------------------------------------------- */
                  /*         FETCH ACTUALIZAR PACIENTE             */ 
                  /* --------------------------------------------- */
                formulario.addEventListener('submit', function (event) {
                    event.preventDefault()

                    const apellido_data = document.getElementById('new_apellido').value
                    const nombre_data = document.getElementById('new_nombre').value
                    const dni_data = document.getElementById('new_dni').value
                    const ingreso_data = document.getElementById('new_ingreso').value
                    const email_data = document.getElementById('new_email').value

                    const calle_data = document.getElementById('new_calle').value
                    const numero_data = document.getElementById('new_numero').value
                    const localidad_data = document.getElementById('new_localidad').value
                    const provincia_data = document.getElementById('new_provincia').value
            
                    const payload = {
                        id: data.id,
                        apellido: normalizarTexto(apellido_data),
                        nombre: normalizarTexto(nombre_data),
                        dni: normalizarSeparacion(dni_data),
                        fechaIngreso: ingreso_data,
                        email: normalizarEmail(email_data),
                        domicilio: {
                            id: data.domicilio.id,
                            calle: normalizarSeparacion(calle_data),
                            numero: normalizarTexto(numero_data),
                            localidad: normalizarMayusc(localidad_data),
                            provincia: normalizarTexto(provincia_data),
                        }
                    };
            
                    const url = '/pacientes';
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