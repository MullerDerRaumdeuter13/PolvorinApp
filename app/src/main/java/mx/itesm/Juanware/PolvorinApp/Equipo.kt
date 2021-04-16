package mx.itesm.Juanware.PolvorinApp

class Equipo{
    var nombreEquipo = ""
    var integrantes = 1
    var numeroTelefono = ""

    var imageUrl = ""
    constructor(nombreEquipo: String, integrantes: Int, numeroTelefono: String, imageUrl: String) {
        this.nombreEquipo = nombreEquipo
        this.integrantes = integrantes
        this.numeroTelefono = numeroTelefono
        this.imageUrl = imageUrl
    }
}