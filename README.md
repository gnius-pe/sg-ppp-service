# Sistema de Gestión de Prácticas Pre-profesionales (SG-PPP)

[![SIstema-de-informacion-P-gina-4-1.png](https://i.postimg.cc/nz5LcRS5/SIstema-de-informacion-P-gina-4-1.png)](https://postimg.cc/mzY4mwsN)

## Descripción

Backend del sistema de gestión de prácticas pre-profesionales desarrollado con **Spring Boot** y **MySQL**. Expone APIs RESTful para el consumo del frontend y gestiona el almacenamiento de archivos mediante **MinIO**.

## Arquitectura

```
┌─────────────┐      ┌──────────────┐      ┌─────────────────┐
│   Frontend  │─────▶│  Spring Boot │─────▶│  MySQL 8.0      │
│   (React)   │      │  REST API    │      │  (Data Base)    │
└─────────────┘      └──────────────┘      └─────────────────┘
                            │
                            ▼
                     ┌─────────────────┐
                     │  MinIO          │
                     │  (Archivos)     │
                     └─────────────────┘
```

## Tecnologías

| Componente | Tecnología | Versión |
|-------------|------------|---------|
| Framework   | Spring Boot | 2.7.5 |
| Lenguaje    | Java        | 17     |
| Base de datos | MySQL     | 8.0    |
| Almacenamiento | MinIO    | Latest |
| Documentación | Swagger  | 3.0    |

## Modelo de Negocio

### Flujo del proceso PPP

1. **Alumno** → Crea **SolicitudPPP** (solicitud inicial con archivo)
2. **Alumno** → Sube **CartaAceptacion** (PDF - carta de aceptación empresarial)
3. **Alumno** → Sube **FormatoF1** (Word - documento formal)
4. **PreDocumentosPPP** → Agrupa solicitud, carta y formato para seguimiento
5. **Docente** (miembro comisión PPP) → Revisa mediante **RevisionPreDocumentoPPP** y aprueba o indica correcciones

### Entidades

| Entidad | Descripción |
|---------|-------------|
| Alumno | Estudiante que realiza la práctica pre-profesional |
| SolicitudPPP | Solicitud inicial con archivo adjunto |
| CartaAceptacion | Documento PDF de aceptación empresarial |
| FormatoF1 | Documento Word formal de la práctica |
| PreDocumentosPPP | Contenedor que agrupa todos los documentos para seguimiento |
| RevisionPreDocumentoPPP | Revisión realizada por el docente evaluador |
| Docente | Miembro de la comisión PPP encargado de revisar |

## Requisitos para ejecución

### Prerequisites

- **Java JDK 17** o superior
- **Maven 3.8+**
- **Docker Desktop** (para levantar MySQL y MinIO)
- **MySQL Client** (opcional, para verificación)

### Configuración rápida

1. **Levantar servicios Docker:**
   ```bash
   docker compose up -d
   ```

2. **Ejecutar la aplicación:**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Acceder a Swagger UI:**
   ```
   http://localhost:8080/swagger-ui/
   ```

### Variables de entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `spring.datasource.url` | URL de MySQL | `jdbc:mysql://localhost:3306/datasppp` |
| `spring.datasource.username` | Usuario MySQL | `sppp` |
| `spring.datasource.password` | Contraseña MySQL | `sppp123` |

## Servicios

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| Spring Boot | 8080 | API REST principal |
| MySQL | 3306 | Base de datos |
| MinIO API | 9000 | Almacenamiento de archivos |
| MinIO Console | 9001 | Interfaz administrativa MinIO |

## Licencia

Este proyecto está bajo la licencia **MIT License**.

Copyright (c) 2024 SG-PPP

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.