# LendlyApp 📱

Aplicación Android desarrollada como Parcial Domiciliario para la materia **TP3** — Universidad ORT Argentina, Año Académico 2026.

---

## 👥 Integrantes

| Nombre | Apellido |
|--------|----------|
| Gina | Sammarone |
| Gregorio | Iribarren |
| Joaquín | Darquier |
| Martín | Carrera |
| Alen | Carbajal |

**Curso:** BE-TP3A  
**Profesor:** Martín Rivas

---

## 📋 Descripción

LendlyApp es una aplicación móvil Android orientada a la gestión de préstamos, pagos y servicios financieros. Permite a los usuarios:

- Iniciar sesión y registrarse con verificación SMS (Firebase Auth)
- Navegar un onboarding introductorio de múltiples pasos
- Solicitar y gestionar préstamos activos
- Realizar cash-in por canal OTC (efectivo) u online
- Comprar productos en el Shop integrado con búsqueda y filtros
- Ver el historial de transacciones con detalle por operación
- Administrar el perfil de usuario y consultar el puntaje crediticio

---

## ⚙️ Stack Técnico

| Categoría | Tecnología |
|-----------|-----------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Arquitectura | MVVM + StateFlow / LiveData |
| Networking | Retrofit + OkHttp + Gson |
| Autenticación | Firebase Auth (Anonymous / SMS) |
| Inyección de dependencias | Hilt (KSP) |
| Persistencia local | Room + DataStore Preferences |
| Imágenes | Coil |
| Navegación | Navigation Compose |
| Gestión del proyecto | Jira (sprints, tareas, seguimiento) |

---

## 🤖 Uso de IA Generativa

Durante el desarrollo se utilizaron las siguientes herramientas de IA generativa:

- **ChatGPT:** consultas puntuales sobre conceptos de Android, resolución de dudas de arquitectura MVVM y revisión de lógica en ViewModels.
- **Claude (Anthropic):** asistencia en la escritura y refactorización de código Kotlin, implementación de módulos Hilt, estructura de repositorios y documentación del proyecto.
- **OpenAI Codex:** generación de código repetitivo y boilerplate, como data classes, DAOs de Room y funciones de mapeo entre entidades y modelos.

> *Declaración obligatoria según condiciones de entrega del parcial.*

---

## 🌐 API

**URL base:** `https://6d710e79-f4ca-4651-909f-7dd13bd29968.mock.pstmn.io`

Todas las llamadas incluyen el header: `x-api-key: 123456789`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/login` | Inicio de sesión, retorna token |
| POST | `/auth/create` | Registro de nuevo usuario |
| GET | `/users/{id}` | Perfil del usuario autenticado |
| GET | `/loans` | Préstamos activos e historial |
| POST | `/loans/apply` | Solicitud de nuevo préstamo |
| GET | `/transactions` | Historial de transacciones |
| GET | `/products` | Catálogo de productos del Shop |

> La `BASE_URL` puede sobreescribirse en `local.properties` con la clave `BASE_URL`.

---

## 🗂️ Estructura del proyecto

```
app/src/main/java/com/lendly/fintech/
│
├── LendlyApp.kt                    # Application + Hilt
├── MainActivity.kt
│
├── core/
│   ├── SessionEventBus.kt          # Bus de eventos de sesión expirada (401)
│   └── SnackbarController.kt       # Mensajes globales de feedback
│
├── data/
│   ├── api/                        # Interfaces Retrofit
│   │   ├── AuthApi.kt
│   │   ├── LoansApi.kt
│   │   ├── ProductsApi.kt
│   │   ├── TransactionsApi.kt
│   │   └── UserApi.kt
│   ├── auth/
│   │   └── FirebaseAuthManager.kt  # Sesión anónima Firebase
│   ├── common/
│   │   ├── Resource.kt             # Sealed class Loading / Success / Error
│   │   ├── NetworkErrorType.kt
│   │   └── SafeApiCall.kt
│   ├── local/
│   │   ├── SessionManager.kt       # Token en DataStore
│   │   └── db/
│   │       ├── LendlyDatabase.kt
│   │       ├── DbMappers.kt
│   │       ├── dao/                # LoanDao, TransactionDao, UserDao
│   │       └── entity/             # LoanEntity, TransactionEntity, UserEntity
│   ├── model/                      # Data classes de la API
│   ├── remote/
│   │   ├── NetworkModule.kt        # OkHttp + Retrofit (Hilt)
│   │   ├── AuthInterceptor.kt      # Bearer token + manejo 401
│   │   └── Qualifiers.kt
│   └── repository/                 # Interfaces + implementaciones
│       ├── AuthRepository(Impl).kt
│       ├── LoanRepository(Impl).kt
│       ├── ProductRepository(Impl).kt
│       ├── TransactionRepository(Impl).kt
│       └── UserRepository(Impl).kt
│
├── di/
│   ├── AppModule.kt                # Repositorios
│   ├── DataStoreModule.kt          # SessionManager
│   ├── DatabaseModule.kt           # Room
│   └── FirebaseModule.kt           # FirebaseAuth
│
└── ui/
    ├── AppViewModel.kt             # ViewModel raíz (snackbar + sesión)
    ├── LendlyAppRoot.kt
    ├── components/                 # Componentes reutilizables globales
    ├── navigation/
    │   ├── Routes.kt
    │   ├── LendlyNavHost.kt        # Flujo pre-login (splash → onboarding → auth)
    │   └── MainNavHost.kt          # Flujo post-login (BottomNav)
    ├── screens/
    │   ├── auth/                   # Login, registro, verificación SMS, etc.
    │   ├── home/                   # Pantalla principal
    │   ├── loan/                   # Préstamos, cash-in OTC y online
    │   ├── shop/                   # Productos, búsqueda, filtros
    │   ├── history/                # Historial y detalle de transacciones
    │   ├── manage/                 # Perfil, credit score
    │   ├── onboarding/
    │   ├── splash/
    │   └── debug/                  # Pantallas de prueba (solo desarrollo)
    └── theme/                      # Color, Type, Shape, Spacing, Theme
```

---

## 🚀 Cómo ejecutar

1. Clonar el repositorio
2. Abrir en **Android Studio Hedgehog** o superior
3. Sincronizar dependencias con **Sync Project with Gradle Files**
4. Ejecutar en emulador o dispositivo físico (**API 26 / Android 8.0 mínimo**)

> La app debe compilar y ejecutarse sin errores.

---

## 📅 Entrega

**Fecha límite:** Miércoles 3 de Junio de 2026 — 18:30 hs.  
**Formato:** Repositorio GitHub entregado por Aula Virtual.
