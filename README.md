# MartiniChef — Grupo 14

Proyecto académico de la asignatura Desarrollo de Aplicaciones Móviles
(DSY1105), Duoc UC.

## Integrantes

- Matías Prieto.
- Tomás Gutiérrez.

## Descripción

MartiniChef es una aplicación Android para consultar recetas, aprender
conceptos básicos de cocina y administrar un perfil personal.

El proyecto es una simulación académica desarrollada con Kotlin,
Jetpack Compose y Material Design 3. Utiliza almacenamiento local;
no depende de un servidor ni de un servicio de autenticación externo.

## Funcionalidades

### Presentación e ingreso

- Presentación del logo con una transición de desvanecimiento.
- Registro de una cuenta local por dispositivo.
- Ingreso con el correo y la contraseña registrados.
- Validación de campos y mensajes de error.
- Cierre de sesión con regreso a la pantalla de ingreso.

Para comenzar, selecciona «Crear mi cuenta», completa el registro
y luego ingresa con esas mismas credenciales.

### Inicio personalizado

- Saludo con el nombre del perfil guardado.
- Fotografía de perfil y acceso para cambiarla.
- Receta destacada con acceso a su preparación.
- Accesos al catálogo, aprendizaje, datos personales y ajustes.
- Consejos de cocina con animación y preferencia persistente.

### Datos personales

- Consulta de nombre, correo y dirección guardados.
- Edición con validación de campos.
- Opciones para guardar o cancelar los cambios.
- Actualización del saludo después de guardar el nombre.
- Conservación de la contraseña al editar los datos.

Si se modifica el correo, el siguiente ingreso debe realizarse
con el nuevo correo y la misma contraseña.

### Fotografía de perfil

- Selección de una imagen desde la galería.
- Captura mediante la aplicación de cámara.
- Solicitud de permiso de cámara cuando corresponde.
- Uso de FileProvider para la captura.
- Copia de la fotografía al almacenamiento interno.
- Visualización de la misma fotografía en el perfil y en el inicio.
- Conservación de la fotografía después de reabrir la aplicación.

### Recetas

Catálogo con fotografías ilustrativas, tiempos, ingredientes y pasos:

- Pasta al tomate.
- Ensalada fresca.
- Crema de zapallo y zanahoria.
- Panqueques de avena y plátano.

Incluye navegación al detalle y regreso al inicio.

### Aprendizaje

- Filtros para mostrar todos los contenidos, videos o cursos.
- Dos guías de videos simulados.
- Dos cursos simulados con lecciones.
- Pantallas de detalle con información para consultar.

Los videos son guías escritas de demostración; no incluyen reproducción
audiovisual. Los cursos no ofrecen certificaciones ni servicios reales.

### Apariencia

- Paleta cálida inspirada en la cocina.
- Modo día y modo noche.
- Tamaños de texto pequeño, normal y grande.
- Persistencia de las preferencias.
- Diseños según el ancho disponible.
- Desplazamiento en pantallas con contenido extenso.

## Arquitectura

El proyecto separa las responsabilidades en:

- `model`: estructuras de datos.
- `data`: almacenamiento local mediante DataStore.
- `repository`: catálogos de recetas y aprendizaje.
- `viewmodel`: validaciones, estado y operaciones de guardado.
- `ui/screens`: pantallas de Jetpack Compose.
- `ui/components`: componentes visuales reutilizables.
- `ui/theme`: colores, tipografía y tema.
- `navigation`: rutas y conexiones entre pantallas.

La interfaz observa estados mediante StateFlow y se actualiza
cuando cambian los datos correspondientes.

## Almacenamiento local

La aplicación utiliza DataStore Preferences:

| Componente | Información |
| --- | --- |
| UsuarioDataStore | Nombre, correo, dirección y datos derivados para comprobar la contraseña |
| PerfilDataStore | Nombre del archivo de la fotografía |
| AjustesDataStore | Modo de apariencia y escala del texto |
| EstadoDataStore | Preferencia para mostrar consejos |

La contraseña no se guarda como texto original. Se deriva mediante
PBKDF2 con una sal aleatoria.

La fotografía se almacena como archivo en el almacenamiento interno.
Las recetas y los contenidos educativos son listas locales definidas
en los repositorios; no son tablas de una base de datos relacional.

## Tecnologías

- Kotlin.
- Jetpack Compose.
- Material Design 3.
- Navigation Compose.
- ViewModel y AndroidViewModel.
- StateFlow y corrutinas.
- DataStore Preferences.
- Activity Result API.
- FileProvider.
- Coil.
- Git, GitHub y Trello.

## Ejecución

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/MatiasPrietoc/MartiniChef_grupo14.git
   ```

2. Abrir la carpeta del proyecto en Android Studio.
3. Instalar el SDK Android requerido por el proyecto: API 37.
4. Esperar la sincronización de Gradle y la descarga de dependencias.
5. Seleccionar un emulador o dispositivo con Android API 24 o superior.
6. Ejecutar `app`.
7. Crear la cuenta local desde la pantalla de ingreso.

La primera sincronización requiere conexión a internet.

## Comprobaciones manuales

- Registro e ingreso con la cuenta local.
- Rechazo de credenciales incorrectas.
- Consulta y edición de datos personales.
- Cancelación de cambios sin modificar el perfil guardado.
- Actualización del nombre mostrado en el inicio.
- Selección, captura y conservación de la foto.
- Navegación entre inicio, recetas, aprendizaje y ajustes.
- Cierre de sesión y nuevo ingreso.
- Conservación de datos después de reabrir la aplicación.
- Cambio de apariencia y tamaño de texto.
- Activación de consejos y mensaje de guardado.

La comprobación del diseño en tablet está pendiente.

## Alcance de la simulación

- Se mantiene una cuenta local por dispositivo.
- No existe autenticación con un servidor.
- No se incluyen recuperación de contraseña ni sincronización entre dispositivos.
- Los datos locales se eliminan al borrar los datos de la aplicación.
- Los contenidos educativos son demostraciones académicas.
- Las fotografías de recetas son ilustrativas.

## Referencias visuales

Se utilizó https://gourmet.cl como referencia de ideas visuales
relacionadas con cocina.

El logo y las imágenes ilustrativas de las recetas se generaron
con asistencia de inteligencia artificial.

## Enlaces

- Repositorio: https://github.com/MatiasPrietoc/MartiniChef_grupo14
- Trello: https://trello.com/b/f2L94EoY/master-martini-grupo-14