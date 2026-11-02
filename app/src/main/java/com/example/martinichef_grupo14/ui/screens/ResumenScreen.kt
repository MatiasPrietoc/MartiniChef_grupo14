package com.example.martinichef_grupo14.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.martinichef_grupo14.viewmodel.MisDatosViewModel
import com.example.martinichef_grupo14.viewmodel.UsuarioViewModel

@Suppress("UNUSED_PARAMETER")
@Composable
fun ResumenScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: UsuarioViewModel,
    misDatosViewModel: MisDatosViewModel = viewModel()
) {
    // El parámetro UsuarioViewModel se conserva para que
    // AppNavigation siga siendo compatible.
    val estado by misDatosViewModel.uiState.collectAsState()

    if (estado.cargando) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (estado.editando) {
                "Editar mis datos"
            } else {
                "Mis datos"
            },
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = if (estado.editando) {
                "Actualiza tu información y guarda los cambios."
            } else {
                "Este es tu perfil de MartiniChef."
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (estado.perfilGuardado != null) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = estado.nombre,
                        onValueChange = {
                            misDatosViewModel.onNombreChanged(it)
                        },
                        label = {
                            Text("Nombre completo")
                        },
                        readOnly = !estado.editando,
                        enabled = !estado.guardando,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = estado.email,
                        onValueChange = {
                            misDatosViewModel.onEmailChanged(it)
                        },
                        label = {
                            Text("Correo electrónico")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        readOnly = !estado.editando,
                        enabled = !estado.guardando,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = estado.direccion,
                        onValueChange = {
                            misDatosViewModel.onDireccionChanged(it)
                        },
                        label = {
                            Text("Dirección")
                        },
                        readOnly = !estado.editando,
                        enabled = !estado.guardando,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (estado.editando) {
                Text(
                    text = "Si cambias tu correo, úsalo para ingresar " +
                            "la próxima vez. Tu contraseña seguirá siendo la misma.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            estado.error?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            estado.mensaje?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (estado.editando) {
                Button(
                    onClick = {
                        misDatosViewModel.guardarCambios()
                    },
                    enabled = !estado.guardando,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (estado.guardando) {
                            "Guardando..."
                        } else {
                            "Guardar cambios"
                        }
                    )
                }

                OutlinedButton(
                    onClick = {
                        misDatosViewModel.cancelarEdicion()
                    },
                    enabled = !estado.guardando,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar")
                }
            } else {
                Button(
                    onClick = {
                        misDatosViewModel.comenzarEdicion()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Editar mis datos")
                }

                Text(
                    text = "Tu perfil está guardado en este dispositivo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Text(
                text = estado.error ?: "Todavía no hay un perfil guardado.",
                color = MaterialTheme.colorScheme.error
            )
        }

        if (!estado.editando) {
            OutlinedButton(
                onClick = {
                    navController?.let { controller ->
                        val volvioAlInicio = controller.popBackStack(
                            route = "pantalla_principal",
                            inclusive = false
                        )

                        if (!volvioAlInicio) {
                            controller.popBackStack(
                                route = "login",
                                inclusive = false
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }
    }
}