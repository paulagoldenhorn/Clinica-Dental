window.addEventListener("load", function () {
  // Capturar espacion donde se renderizarán los odontólogos y pacientes
  const opciones_odontologos = document.getElementById("formControlSelect_odontologo")
  const opciones_pacientes = document.getElementById("formControlSelect_paciente");
  // Función para renderizar odontólogos
  function obtenerOdontologos() {
      const url_odontologos = "/odontologos";
      fetch(url_odontologos)
      .then((response) => response.json())
      .then((data) => {
        for (odontologo of data) {
          let { id, nombre, apellido } = odontologo;
          opciones_odontologos.innerHTML += `<option value=${id}>${apellido}, ${nombre}</option>`;
        }
      });
  }
  // Función para renderizar pacientes
  function obtenerPacientes(){
      const url_pacientes = "/pacientes";
      fetch(url_pacientes)
      .then((response) => response.json())
      .then((data) => {
        for (pacientes of data) {
          let { id, nombre, apellido } = pacientes;
          opciones_pacientes.innerHTML += `<option value=${id}>${apellido}, ${nombre}</option>`;
        }
      });
  }
  
  obtenerOdontologos()
  obtenerPacientes()

  // Capturar ID del odontologo al que se le haga click
  let id_odontologo_seleccionado;
  opciones_odontologos.addEventListener("change", function (event) { id_odontologo_seleccionado = event.target.value });
  // Capturar ID del paciente al que se le haga click
  let id_paciente_seleccionado;
  opciones_pacientes.addEventListener("change", function (event) { id_paciente_seleccionado = event.target.value });

  /* --------------------------------------------- */
  /*           FETCH REGISTRAR TURNO               */ 
  /* --------------------------------------------- */
  const formulario = document.getElementById("registrar_turno");

  formulario.addEventListener("submit", function (event) {
    event.preventDefault();

    const fecha_data = document.getElementById("fecha").value;
    const hora_data = document.getElementById("hora").value;

    const payload = {
      fecha: fecha_data,
      hora: hora_data,
      paciente: {id: id_paciente_seleccionado},
      odontologo: {id: id_odontologo_seleccionado}
    };

    const url = "/turnos";
    const settings = {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(payload),
    };

    fetch(url, settings)
      .then((response) => response.json())
      .then((data) => {
          let successAlert =
            '<div class="alert alert-success alert-dismissible">' +
            '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
            "<strong></strong> Turno registrado </div>";

          document.getElementById("response").innerHTML = successAlert;
          document.getElementById("response").style.display = "block";
          resetForm();
      })
      .catch((error) => {
        console.log(error.message);
        let errorAlert =
          '<div class="alert alert-danger alert-dismissible">' +
          '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
          "<strong> Error, intente nuevamente</strong> </div>";

        document.getElementById("response").innerHTML = errorAlert;
        document.getElementById("response").style.display = "block";
        resetForm();
      });

    function resetForm() {
      document.getElementById("fecha").value = "";
      document.getElementById("hora").value = "";
      opciones_odontologos.value = "";
      opciones_pacientes.value = "";

      setTimeout(() => { document.getElementById("response").innerHTML = "" }, 5000);
    }
  });
});

