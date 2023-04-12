window.addEventListener('load', function () {
    /* --------------------------------------------- */
    /*          FETCH REGISTRAR PACIENTE              */ 
    /* --------------------------------------------- */
    const formulario = document.getElementById('registrar_paciente');
    
    formulario.addEventListener('submit', function (event) {
        event.preventDefault()

        // Datos del paciente
        const apellido_data = document.getElementById('apellido').value
        const nombre_data = document.getElementById('nombre').value
        const dni_data = document.getElementById('dni').value
        const ingreso_data = document.getElementById('ingreso').value
        const email_data = document.getElementById('email').value
        // Datos del domicilio
        const calle_data = document.getElementById('calle').value
        const numero_data = document.getElementById('numero').value
        const localidad_data = document.getElementById('localidad').value
        const provincia_data = document.getElementById('provincia').value
    
        const payload = {
            apellido: normalizarTexto(apellido_data),
            nombre: normalizarTexto(nombre_data),
            dni: normalizarSeparacion(dni_data),
            fechaIngreso: ingreso_data,
            email: normalizarEmail(email_data),
            domicilio: {
                calle: normalizarSeparacion(calle_data),
                numero: normalizarTexto(numero_data),
                localidad: normalizarMayusc(localidad_data),
                provincia: normalizarTexto(provincia_data),
            }
        };

        const url = '/pacientes';
        const settings = {
            method: 'POST',
            headers: {
                "Content-type": "application/json"
            }, 
            body: JSON.stringify(payload)
        }
    
        fetch(url, settings)
        .then(response => response.json())
        .then(data => {
            let successAlert = '<div class="alert alert-success alert-dismissible">'+
                            '<button type="button" class="close" data-dismiss="alert">&times;</button>'+
                            '<strong></strong> Paciente registrado </div>'

            document.getElementById('response').innerHTML = successAlert;
            document.getElementById('response').style.display = "block";
            resetForm();
        })
        .catch(error => {
            console.log(error.message);
            let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                '<strong> Error, intente nuevamente</strong> </div>'

            document.getElementById('response').innerHTML = errorAlert;
            document.getElementById('response').style.display = "block";
            resetForm();
        })
    
        function resetForm(){
            document.getElementById('apellido').value = "";
            document.getElementById('nombre').value = "";
            document.getElementById('dni').value = "";
            document.getElementById('ingreso').value = "";
            document.getElementById('email').value = "";

            document.getElementById('calle').value = "";
            document.getElementById('numero').value = "";
            document.getElementById('localidad').value = "";
            document.getElementById('provincia').value = "";

            setTimeout(() => { document.getElementById('response').innerHTML = "" }, 5000)
        }
    })
        
})
