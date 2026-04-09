# Guía de Exposición de Servicios con ngrok

## Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                    TU MÁQUINA LOCAL                         │
│  ┌──────────────┐   ┌──────────────┐   ┌──────────────┐    │
│  │    MySQL     │   │    MinIO     │   │  SonarQube   │    │
│  │   :3306      │   │ :9000 :9001  │   │    :9100     │    │
│  └──────┬───────┘   └──────┬───────┘   └──────┬───────┘    │
│         │                  │                   │             │
│         └──────────────────┼───────────────────┘             │
│                            │                                 │
│                    ┌───────▼───────┐                         │
│                    │  docker network │                        │
│                    │  red_interna   │                        │
│                    └───────┬───────┘                         │
│                            │                                 │
│                    ┌───────▼───────┐                         │
│                    │    ngrok       │                        │
│                    │   container    │                         │
│                    └───────┬───────┘                         │
└────────────────────────────┼─────────────────────────────────┘
                             │
                    ══════════════════════
                    INTERNET (túneles salientes)
                    ══════════════════════
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
   ┌────▼─────┐        ┌─────▼─────┐       ┌─────▼─────┐
   │ MySQL    │        │  MinIO    │       │ SonarQube  │
   │ Client   │        │  Browser  │       │  Browser   │
   │ (DBeaver)│        │           │       │            │
   └──────────┘        └───────────┘       └────────────┘
```

## Requisitos Previos

### 1. Obtener Token de ngrok

1. Regístrate en [ngrok.com](https://ngrok.com)
2. Ve a https://dashboard.ngrok.com/get-started/your-authtoken
3. Copia tu auth token

### 2. Configurar Variables de Entorno

```bash
# Copia el archivo de ejemplo
cp .env.example .env

# Edita con tu token
nano .env
# Cambia: NGROK_AUTHTOKEN=your_ngrok_auth_token_here
```

## Levantando los Servicios

```bash
# Levantar todos los servicios
docker compose up -d

# Ver logs de ngrok (aquí aparecerán las URLs públicas)
docker compose logs -f ngrok

# Ver estado de todos los servicios
docker compose ps
```

## Obteniendo las URLs Públicas

### Método 1: Ver Logs de ngrok

```bash
docker compose logs ngrok
```

Busca líneas como:
```
tunnel session started                       session_addr=...
url=tcp://2.tcp.ngrok.io:12345 -> tcp://localhost:3306
url=https://abc123.ngrok-free.app -> http://localhost:9000
url=https://xyz789.ngrok-free.app -> http://localhost:9001
```

### Método 2: API de ngrok (puerto 4040)

Abre en tu navegador: http://localhost:4040

Aquí verás todos los túneles activos con sus URLs.

### Método 3: Dashboard Web de ngrok

```bash
# Ver la interfaz web de ngrok localmente
open http://localhost:4040
```

## Conectándose a los Servicios

### MySQL (TCP)

**Desde DBeaver, TablePlus, o cualquier cliente SQL:**

1. Busca la URL TCP en los logs:
   ```
   url=tcp://2.tcp.ngrok.io:12345 -> tcp://localhost:3306
   ```

2. Configura tu cliente:
   ```
   Host: 2.tcp.ngrok.io
   Port: 12345
   User: sppp (o root)
   Password: sppp123 (o rootpass)
   Database: datasppp
   ```

**Desde aplicación Spring Boot:**
```properties
spring.datasource.url=jdbc:mysql://2.tcp.ngrok.io:12345/datasppp
spring.datasource.username=sppp
spring.datasource.password=sppp123
```

### MinIO Console (Web)

1. Busca la URL en los logs:
   ```
   url=https://xyz789.ngrok-free.app -> http://localhost:9001
   ```

2. Abre en navegador
3. Credenciales:
   - User: minioadmin
   - Password: minioadmin

### MinIO API (S3)

**URL del API:** `https://abc123.ngrok-free.app`

**Configuración SDK (Java):**
```java
import io.minio.MinioClient;

MinioClient minioClient = MinioClient.builder()
    .endpoint("https://abc123.ngrok-free.app")
    .credentials("minioadmin", "minioadmin")
    .build();
```

**Configuración Spring Boot:**
```properties
minio.url=https://abc123.ngrok-free.app
minio.access-key=minioadmin
minio.secret-key=minioadmin
```

### SonarQube (Web)

1. Busca la URL en los logs:
   ```
   url=https://sonarqube.ngrok-free.app -> http://localhost:9000
   ```

2. Abre en navegador
3. Credenciales por defecto:
   - User: admin
   - Password: admin

**Análisis Maven:**
```bash
./mvnw sonar:sonar \
  -Dsonar.host.url=https://sonarqube.ngrok-free.app \
  -Dsonar.token=tu-token-de-sonarqube
```

## Seguridad ⚠️

### ADVERTENCIAS IMPORTANTES

#### 1. MySQL Expuesto a Internet

```danger
⚠️  ¡PELIGRO! MySQL con contraseña débil es vulnerable.
```

**Riesgos:**
- Ataques de fuerza bruta
- Exposición de datos sensibles
- Acceso no autorizado a la base de datos

**Recomendaciones:**
- [ ] Usar contraseña STRONG para MySQL (mínimo 16 caracteres)
- [ ] Considerar usar SSL/TLS en la conexión
- [ ] Monitorear accesos sospechosos
- [ ] Desactivar cuando no se use
- [ ] No exponer en producción

#### 2. MinIO Expuesto a Internet

```danger
⚠️  Los archivos almacenados serán accesibles públicamente.
```

**Riesgos:**
- Acceso no autorizado a archivos
- Filtración de datos sensibles
- Costos de almacenamiento inesperados

**Recomendaciones:**
- [ ] Cambiar credenciales por defecto
- [ ] Crear buckets con políticas restrictivas
- [ ] No subir archivos sensibles sin cifrar
- [ ] Revisar políticas de acceso regularmente

#### 3. SonarQube Expuesto

```warning
⚠️  El código fuente puede ser analizado desde cualquier lugar.
```

**Riesgos:**
- Exposición de código propietario
- Tokens de análisis robados

**Recomendaciones:**
- [ ] Cambiar contraseña de admin inmediatamente
- [ ] Crear usuarios con permisos limitados
- [ ] No ejecutar en redes no confiables

### Buenas Prácticas

1. **Desactivar cuando no se use:**
   ```bash
   docker compose stop ngrok
   ```

2. **Usar VPN como alternativa** para mayor seguridad

3. **Rotar credenciales** periódicamente

4. **Monitorizar logs** de accesos sospechosos:
   ```bash
   docker compose logs --tail=100 | grep -i "error\|unauthorized\|failed"
   ```

## Notas sobre Subdominios

### Plan Gratuito
- Las URLs son aleatorias: `https://abc123.ngrok-free.app`
- Cada reinicio genera nuevas URLs

### Plan Pro ($10/mes)
Puedes especificar subdominios fijos:
```yaml
minio-api:
  hostname: mi-minio.ngrok-free.app
```

## Solución de Problemas

### ngrok no inicia

```bash
# Ver errores específicos
docker compose logs ngrok

# Verificar token
cat .env | grep NGROK_AUTHTOKEN
```

### Tunnels caídos

```bash
# Reiniciar solo ngrok
docker compose restart ngrok

# Ver estado
docker compose ps
```

### MySQL timeout

```bash
# Verificar que MySQL esté corriendo
docker compose logs data_base_ppp

# Probar conexión local
mysql -h localhost -u sppp -pspp123 datasppp
```

### MinIO no responde

```bash
# Ver logs de MinIO
docker compose logs archivos_ppp

# Verificar que el bucket existe
# Conectar al contenedor:
docker exec -it archivos_ppp mc alias set local http://localhost:9000 minioadmin minioadmin
docker exec -it archivos_ppp mc ls local/
```

## Comandos Rápidos

```bash
# Iniciar todo
docker compose up -d

# Ver todas las URLs
docker compose logs ngrok 2>&1 | grep -E "url=|tcp://"

# Dashboard ngrok local
open http://localhost:4040

# Detener todo
docker compose down

# Detener solo túneles (mantener DB)
docker compose stop ngrok

# Ver todos los logs
docker compose logs -f
```
