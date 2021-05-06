package mx.itesm.Juanware.PolvorinApp

data class Evento (val nombreEvento: String = "",val descripcionEvento: String = "",
                   val tipoEvento: String = "", val  idCreadorEvento: String = "",
                   val latEvento: Double = 0.0, val longEvento: Double = 0.0,
                   val maxParticipantes: Int, val participantes: ArrayList<String>)
