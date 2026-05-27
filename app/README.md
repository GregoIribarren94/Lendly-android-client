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

- Iniciar sesión y registrarse con verificación SMS
- Solicitar y gestionar préstamos activos
- Realizar compras en el Shop integrado
- Ver el historial de transacciones
- Administrar el perfil y puntaje crediticio (gauge/barra)

---

## ⚙️ Stack Técnico

| Categoría | Tecnología |
|-----------|-----------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Arquitectura | MVVM + LiveData / StateFlow |
| Networking | Retrofit (Gson) |
| Navegación | Navigation Component |
| Inyección de dependencias | Hilt / Koin |
| Imágenes | Glide / Coil |
| Persistencia local | Room |

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

---

## 🗂️ Estructura del proyecto

(A modificar)
```
LendlyApp/
├── app/
│   ├── data/
│   │   ├── api/          # Retrofit services & endpoints
│   │   ├── model/        # Data classes
│   │   └── repository/   # Repositorios
│   ├── ui/
│   │   ├── auth/         # Login y Registro
│   │   ├── home/         # Pantalla principal
│   │   ├── loans/        # Préstamos
│   │   ├── shop/         # Tienda
│   │   ├── history/      # Historial
│   │   └── manage/       # Perfil y configuración
│   └── di/               # Módulos de inyección de dependencias
└── README.md
```

---
(A modificar)
## 🚀 Cómo ejecutar

1. Clonar el repositorio
2. Abrir en Android Studio
3. Sincronizar dependencias (Gradle)
4. Ejecutar en emulador o dispositivo físico (API 26+)

> La app debe compilar y ejecutarse sin errores.

---

## 🤖 Uso de IA Generativa

<!-- Completar con las herramientas de IA utilizadas y en qué partes -->

Durante el desarrollo se utilizaron las siguientes herramientas de IA generativa:

- **[Herramienta]:** utilizada para [descripción del uso]

> *Declaración obligatoria según condiciones de entrega del parcial.*

---

## 📅 Entrega

**Fecha límite:** Miércoles 3 de Junio de 2026 — 18:30 hs.  
**Formato:** Repositorio GitHub entregado por Aula Virtual.
