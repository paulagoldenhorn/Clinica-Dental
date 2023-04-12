window.addEventListener('load', function () {
    /* --------------------------------------------- */
    /*         FETCH REGISTRAR ODONTOLOGO            */ 
    /* --------------------------------------------- */
    const formulario = document.getElementById('registrar_odontologo');
    
    formulario.addEventListener('submit', function (event) {
        event.preventDefault()

        const matricula_data = document.getElementById('matricula').value
        const apellido_data = document.getElementById('apellido').value
        const nombre_data = document.getElementById('nombre').value
    
        const payload = {
            matricula: normalizarMayusc(matricula_data),
            apellido: normalizarTexto(apellido_data),
            nombre: normalizarTexto(nombre_data)
        };
    
        const url = '/odontologos';
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
                            '<strong></strong> Odontólogo registrado </div>'

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
            document.getElementById('nombre').value = "";
            document.getElementById('apellido').value = "";
            document.getElementById('matricula').value = "";

            setTimeout(() => { document.getElementById('response').innerHTML = "" }, 5000)
        }
    })
        
})
