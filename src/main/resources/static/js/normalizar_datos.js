function normalizarTexto(texto) {
    return texto.trim()
}
function normalizarSeparacion(dato){
    return dato.replaceAll(".", "").trim()
}
function normalizarEmail(email) {
    return email.toLowerCase().trim()
}
function normalizarMayusc(dato){
    return dato.toUpperCase().trim()
}