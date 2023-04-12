window.addEventListener('load', function () {
    /* --------------------------------------------- */
    /*            FETCH CARGAR TURNOS                */ 
    /* --------------------------------------------- */
    const url = '/turnos';
    const settings = {
        method: 'GET'
    }

    fetch(url, settings)
    .then(response => response.json())
    .then(data => {
        for(turno of data) {
            
            let {id, fecha, hora, odontologo, paciente} = turno
            
            let dashboard = document.getElementById("container-dashboard");

            // Renderizar turnos
            dashboard.innerHTML += `
            <tr id="turno/${id}">
                <th scope="row" id="row-fecha">${fecha}</th>
                <td id="row-hora">${hora}</td>
                <td id="row-paciente">${paciente.apellido}, ${paciente.nombre}</td>
                <td id="row-odontologo">${odontologo.apellido}, ${odontologo.nombre}</td>
                <td><button type="button" class="btn btn-success" name="actualizar" id="${id}">Editar</button></td>
                <td><button type="button" class="btn btn-danger" name="eliminar" id="${id}">Eliminar</button></td>
            </tr>`
            
        }
        // Ejecutar metodos de acciones a realizar
        actualizarTurno()
        eliminarTurno()
    })

})
