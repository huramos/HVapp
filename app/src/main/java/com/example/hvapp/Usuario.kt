package com.example.hvapp

import java.time.LocalDate
import java.time.ZoneId

data class Usuario(val nombre: String, val email: String, val contraseña: String, val fechaCreacion: LocalDate)

val usuarios: MutableList<Usuario> = mutableListOf()

fun registrarUsuario(nombre: String, email: String, contraseña: String): String {
    if (nombre.isBlank() || email.isBlank() || contraseña.isBlank()) {
        return "Los campos nombre, email y contraseña no pueden estar vacíos."
    }
    return if (usuarios.size < 5) {
        val nuevoUsuario = Usuario(nombre, email, contraseña, LocalDate.now(ZoneId.of("America/Santiago")))
        usuarios.add(nuevoUsuario)
        "Usuario $nombre registrado correctamente."
    } else {
        "No se pueden registrar más de 5 usuarios."
    }
}
