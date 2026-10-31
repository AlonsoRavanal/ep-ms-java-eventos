# EventPass — Microservicio de Eventos

Microservicio REST desarrollado con **Java 21**, **Spring Boot 4.1.1**, **Spring Data JPA** y **H2** para administrar los eventos de EventPass y su ubicación geográfica mediante regiones y comunas.

Este repositorio forma parte del proyecto académico **EventPass**, desarrollado para la asignatura *Java: Diseño y Construcción de Soluciones Nativas en Nube*.

## 1. Funcionalidades

- Crear, consultar, actualizar y eliminar **regiones**.
- Crear, consultar, actualizar y eliminar **comunas**, asociadas a una región existente.
- Crear, consultar, actualizar y eliminar **eventos**, asociados a una comuna existente.
- Guardar los datos en una base de datos relacional **H2 persistente en archivo**.
- Validar los valores numéricos de los eventos y comprobar que el aforo disponible no supere el máximo.
- Responder solicitudes HTTP desde Postman u otro cliente REST.

## 2. Tecnologías y dependencias

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje de programación |
| Spring Boot 4.1.1 | Desarrollo y ejecución de la aplicación |
| Spring Web MVC | Controladores y endpoints REST |
| Spring Data JPA / Hibernate | Persistencia y mapeo de entidades |
| H2 Database | Base de datos relacional local |
| Jakarta Bean Validation | Validaciones de campos |
| Apache Maven / Maven Wrapper | Compilación, pruebas y empaquetado |

Las versiones y dependencias corresponden al `pom.xml` incluido en el proyecto.

## 3. Arquitectura

El proyecto está organizado en capas:

```text
src/
├── main/
│   ├── java/com/alonsoravanalrubenvelazquez/ep_ms_java_eventos/
│   │   ├── controllers/    # Entrada HTTP y respuestas REST
│   │   ├── services/       # Interfaces y lógica de negocio
│   │   ├── repositories/   # Acceso a datos mediante Spring Data JPA
│   │   ├── models/         # Entidades JPA: Region, Comuna y Evento
│   │   └── EpMsJavaEventosApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/              # Pruebas del proyecto
```

Las relaciones JPA implementadas son:

- Una **región** puede tener varias **comunas** (`@OneToMany` / `@ManyToOne`).
- Una **comuna** puede tener varios **eventos** (`@OneToMany` / `@ManyToOne`).
- Cada **evento** pertenece a una comuna y cada comuna pertenece a una región.

## 4. Requisitos previos

- **JDK 21** instalado y configurado en `JAVA_HOME`.
- **Git** para clonar el repositorio.
- Conexión a Internet para descargar dependencias Maven durante la primera ejecución.
- **Postman** o una herramienta equivalente para probar los endpoints.

El proyecto incluye **Maven Wrapper** (`mvnw` y `mvnw.cmd`), por lo que no es obligatorio instalar Maven globalmente.

Comprueba la versión de Java:

```cmd
java -version
```

## 5. Clonar y ejecutar localmente

### 5.1. Clonar el repositorio

Abre una terminal y ejecuta:

```cmd
git clone https://github.com/AlonsoRavanal/ep-ms-java-eventos.git
cd ep-ms-java-eventos
```

### 5.2. Preparar la carpeta de datos

En **Windows (CMD)**, desde la raíz del proyecto:

```cmd
if not exist data mkdir data
```

La carpeta `data/` almacenará el archivo de base de datos H2 local. No se necesita instalar un servidor de base de datos externo.

### 5.3. Ejecutar la aplicación

En **Windows (CMD)**:

```cmd
mvnw.cmd spring-boot:run
```

En **Linux/macOS**:

```bash
mkdir -p data
./mvnw spring-boot:run
```

Cuando Spring Boot haya iniciado correctamente, la API estará disponible en:

**`http://localhost:8082`**

Para detenerla, presiona `Ctrl + C` en la terminal.

## 6. Configuración y persistencia de datos

La configuración se encuentra en `src/main/resources/application.properties`:

```properties
spring.application.name=ep-ms-java-eventos
server.port=8082

spring.datasource.url=jdbc:h2:file:./data/bdeventos
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

- **Puerto de la API:** `8082`.
- **Motor de base de datos:** H2 en modo archivo.
- **URL JDBC:** `jdbc:h2:file:./data/bdeventos`.
- **Persistencia:** los datos se almacenan en `data/bdeventos.mv.db` y pueden conservarse entre reinicios.
- **JPA/Hibernate:** la opción `ddl-auto=update` actualiza el esquema según las entidades.

### Consola H2

Con la aplicación ejecutándose, abre:

`http://localhost:8082/h2-console`

Utiliza:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:file:./data/bdeventos` |
| User Name | `sa` |
| Password | *(vacía)* |

La URL utiliza una ruta relativa: ejecuta la aplicación desde la raíz del proyecto para que la base de datos se cree y se encuentre en el lugar esperado. La consola H2 está destinada al entorno de desarrollo local.

## 7. Endpoints disponibles

**URL base:** `http://localhost:8082`

| Recurso | Método | Endpoint | Operación |
|---|---|---|---|
| Regiones | GET | `/api/regiones` | Listar regiones |
| Regiones | GET | `/api/regiones/{id}` | Consultar región por ID |
| Regiones | POST | `/api/regiones` | Crear región |
| Regiones | PUT | `/api/regiones/{id}` | Actualizar región |
| Regiones | DELETE | `/api/regiones/{id}` | Eliminar región |
| Comunas | GET | `/api/comunas` | Listar comunas |
| Comunas | GET | `/api/comunas/{id}` | Consultar comuna por ID |
| Comunas | POST | `/api/comunas` | Crear comuna |
| Comunas | PUT | `/api/comunas/{id}` | Actualizar comuna |
| Comunas | DELETE | `/api/comunas/{id}` | Eliminar comuna |
| Eventos | GET | `/api/eventos` | Listar eventos |
| Eventos | GET | `/api/eventos/{id}` | Consultar evento por ID |
| Eventos | POST | `/api/eventos` | Crear evento |
| Eventos | PUT | `/api/eventos/{id}` | Actualizar evento |
| Eventos | DELETE | `/api/eventos/{id}` | Eliminar evento |

Las operaciones de creación responden con **201 Created** cuando son correctas; las consultas y actualizaciones correctas responden con **200 OK**; las eliminaciones correctas responden con **204 No Content**. Las búsquedas de IDs inexistentes están programadas para responder con **404 Not Found**. Las validaciones de eventos pueden responder con **400 Bad Request**.

## 8. Pruebas con Postman: flujo completo

En Postman, selecciona **Body → raw → JSON** para las solicitudes `POST` y `PUT`, y establece `Content-Type: application/json`.

Las entidades se crean en este orden: **región → comuna → evento**, porque las comunas necesitan una región existente y los eventos una comuna existente.

### Paso 1. Crear una región

**POST** `http://localhost:8082/api/regiones`

```json
{
  "nombre": "Región Metropolitana de Santiago"
}
```

**Resultado esperado:** `201 Created`. Anota el `id` devuelto por el servidor.

### Paso 2. Crear una comuna

**POST** `http://localhost:8082/api/comunas`

```json
{
  "nombre": "Maipú",
  "region": {
    "id": 1
  }
}
```

Sustituye `1` por el ID real de la región creada en el paso anterior.

**Resultado esperado:** `201 Created`. Anota el ID real de la comuna.

### Paso 3. Crear un evento

**POST** `http://localhost:8082/api/eventos`

```json
{
  "nombre": "Concierto de prueba EventPass",
  "descripcion": "Evento creado para probar el microservicio",
  "fecha": "2026-12-15T20:00:00",
  "recinto": "Recinto de prueba",
  "precio": 15000,
  "aforoMaximo": 500,
  "aforoDisponible": 500,
  "comuna": {
    "id": 1
  }
}
```

Sustituye `comuna.id` por el ID que realmente devolvió la API en el paso 2. La fecha utiliza el formato `AAAA-MM-DDTHH:mm:ss`.

**Resultado esperado:** `201 Created`, con los datos del evento y su ID.

### Paso 4. Consultar los registros

Realiza estas peticiones:

```text
GET http://localhost:8082/api/regiones
GET http://localhost:8082/api/comunas
GET http://localhost:8082/api/eventos
GET http://localhost:8082/api/eventos/1
```

Reemplaza `1` en la última solicitud por el ID real del evento.

**Resultado esperado:** `200 OK`, con los datos solicitados.

### Paso 5. Actualizar un evento

**PUT** `http://localhost:8082/api/eventos/1`

Reemplaza `1` en la URL y en `comuna.id` por los IDs correspondientes. El controlador recibe un evento completo, por lo que envía todos los campos necesarios:

```json
{
  "nombre": "Concierto de prueba EventPass",
  "descripcion": "Evento actualizado desde Postman",
  "fecha": "2026-12-15T20:00:00",
  "recinto": "Recinto de prueba",
  "precio": 20000,
  "aforoMaximo": 500,
  "aforoDisponible": 450,
  "comuna": {
    "id": 1
  }
}
```

**Resultado esperado:** `200 OK`. Ejecuta después un `GET /api/eventos/{id}` para comprobar que el cambio quedó almacenado.

### Paso 6. Eliminar un evento

**DELETE** `http://localhost:8082/api/eventos/1`

Reemplaza `1` por el ID real.

**Resultado esperado:** `204 No Content`. Comprueba después con `GET /api/eventos/{id}` que el recurso ya no existe (`404 Not Found`).

> Si necesitas eliminar una comuna o región que tenga registros asociados, primero elimina los registros dependientes para respetar las restricciones de integridad referencial.

## 9. Reglas de validación y pruebas de errores

El código de `Evento` y `EventoServiceImpl` incorpora las siguientes reglas:

| Campo | Regla |
|---|---|
| `precio` | Obligatorio y mayor o igual a `0` |
| `aforoMaximo` | Obligatorio y mayor que `0` |
| `aforoDisponible` | Obligatorio y mayor o igual a `0` |
| `aforoDisponible` frente a `aforoMaximo` | El aforo disponible no puede superar el máximo |
| `comuna.id` | Debe referirse a una comuna existente |

Para demostrar el manejo de errores, utiliza el JSON válido del paso 3 y cambia **un campo por vez**:

| Prueba | Cambio | Respuesta esperada |
|---|---|---|
| Precio negativo | `"precio": -10000` | `400 Bad Request` |
| Precio nulo | `"precio": null` | `400 Bad Request` |
| Aforo máximo inválido | `"aforoMaximo": 0` | `400 Bad Request` |
| Aforo disponible negativo | `"aforoDisponible": -1` | `400 Bad Request` |
| Aforo superior al máximo | `"aforoMaximo": 500`, `"aforoDisponible": 700` | `400 Bad Request` |
| Comuna inexistente | `"comuna": {"id": 999999}` | `404 Not Found` si no existe esa comuna |
| Evento inexistente | `GET /api/eventos/999999` | `404 Not Found` si no existe ese evento |

Los ejemplos de respuesta son resultados **esperados por el código**; deben verificarse efectivamente en Postman durante la demostración. Para las pruebas con ID `999999`, comprueba que no exista un recurso con ese ID.

## 10. Compilación, pruebas y empaquetado con Maven

Desde la carpeta raíz del proyecto, en **Windows (CMD)**:

```cmd
mvnw.cmd clean
mvnw.cmd test
mvnw.cmd package
```

O en un solo paso:

```cmd
mvnw.cmd clean package
```

Si la compilación y las pruebas terminan correctamente, Maven mostrará `BUILD SUCCESS` y generará un archivo JAR en `target/`, con un nombre basado en el `artifactId` y la versión del proyecto, por ejemplo:

```text
target/ep-ms-java-eventos-0.0.1-SNAPSHOT.jar
```

Para ejecutar el JAR después de compilar:

```cmd
java -jar target\ep-ms-java-eventos-0.0.1-SNAPSHOT.jar
```

El proyecto incluye una prueba de arranque de contexto Spring Boot. La cobertura de pruebas automatizadas puede ampliarse; las pruebas REST descritas antes se ejecutan manualmente en Postman.

## 11. Control de versiones

Se utiliza Git con las ramas:

- `main`: versión estable del proyecto.
- `develop`: integración de cambios.
- `feature/`: implementación de funcionalidades específicas.

Los cambios de las ramas de funcionalidad pueden integrarse mediante **Pull Requests** hacia `develop`; posteriormente, una versión comprobada puede integrarse a `main`.

Repositorio: [AlonsoRavanal/ep-ms-java-eventos](https://github.com/AlonsoRavanal/ep-ms-java-eventos)

---

**Proyecto:** EventPass · **Microservicio:** Usuarios · **Asignatura:** Java: Diseño y Construcción de Soluciones Nativas en Nube (JVY0101).
