window.addEventListener('load', function () {
    /* --------------------------------------------- */
    /*            FETCH CARGAR PACIENTES             */ 
    /* --------------------------------------------- */
    const url = '/pacientes';
    const settings = {
        method: 'GET'
    }

    fetch(url,settings)
    .then(response => response.json())
    .then(data => {
        for(paciente of data) {
            
            let {id, dni, nombre, apellido, email, fechaIngreso, domicilio} = paciente
            
            let dashboard = document.getElementById("container-dashboard");

            // Renderizar pacientes
            dashboard.innerHTML += `
            <tr id="paciente/${id}">
                <th scope="row" id="row-dni">${dni}</th>
                <td id="row-nombre">${nombre}</td>
                <td id="row-apellido">${apellido}</td>
                <td id="row-email">${email}</td>
                <td id="row-ingreso">${fechaIngreso}</td>
                <td id="row-calle">${domicilio.calle}</td>
                <td id="row-numero">${domicilio.numero}</td>
                <td id="row-localidad">${domicilio.localidad}</td>
                <td id="row-provincia">${domicilio.provincia}</td>
                <td><button type="button" class="btn btn-success" name="actualizar" id="${id}">Editar</button></td>
                <td><button type="button" class="btn btn-danger" name="eliminar" id="${id}">Eliminar</button></td>
            </tr>`
        }
        // Ejecutar metodos de acciones a realizar
        actualizarPaciente()
        eliminarPaciente()
    })

})
