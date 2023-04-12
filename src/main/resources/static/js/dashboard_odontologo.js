window.addEventListener('load', function () {
    /* --------------------------------------------- */
    /*          FETCH CARGAR ODONTOLOGOS             */ 
    /* --------------------------------------------- */
    const url = '/odontologos';
    const settings = {
        method: 'GET'
    }

    fetch(url,settings)
    .then(response => response.json())
    .then(data => {
        for(odontologo of data) {
            
            let {id, matricula, nombre, apellido} = odontologo
            
            let dashboard = document.getElementById("container-dashboard");

            // Renderizar odontologos
            dashboard.innerHTML += `
            <tr id="odontologo/${id}">
                <th scope="row" id="row-matricula">${matricula}</th>
                <td id="row-nombre">${nombre}</td>
                <td id="row-apellido">${apellido}</td>
                <td><button type="button" class="btn btn-success" name="actualizar" id="${id}">Editar</button></td>
                <td><button type="button" class="btn btn-danger" name="eliminar" id="${id}">Eliminar</button></td>
            </tr>`
            
        }
        // Ejecutar metodos de acciones a realizar
        actualizarOdontologo()
        eliminarOdontologo()
    })

})
