# Gerenciador de Aulas - Docker Setup Guide

## 📋 Arquivos Criados

1. **Dockerfile** - Build de dois estágios para a aplicação Spring Boot
2. **docker-compose.yml** - Desenvolvimento com PostgreSQL, App e pgAdmin
3. **docker-compose.prod.yml** - Produção com configurações otimizadas
4. **.dockerignore** - Otimização do build
5. **.env.example** - Template de variáveis de ambiente
6. **application-docker.properties** - Config específica para Docker
7. **DOCKER-README.md** - Este arquivo

## 🚀 Quick Start - Desenvolvimento

### Pré-requisitos
- Docker >= 20.10
- Docker Compose >= 2.0

### 1. Preparar variáveis de ambiente
```bash
# Copiar o arquivo example
cp .env .env

# (Opcional) Editar .env com suas configurações
```

### 2. Iniciar os serviços
```bash
# Build e start (desenvolvimento com pgAdmin incluído)
docker-compose up -d

# Ou com rebuild
docker-compose up --build -d
```

### 3. Acessar a aplicação
- **API**: http://localhost:8080
- **pgAdmin**: http://localhost:5050 (admin@admin.com / admin)
- **PostgreSQL**: localhost:5432

### 4. Verificar logs
```bash
# Logs da app
docker-compose logs -f app

# Logs do banco de dados
docker-compose logs -f postgres

# Todos os logs
docker-compose logs -f
```

### 5. Parar os serviços
```bash
docker-compose down

# Com limpeza de volumes (para resetar banco de dados)
docker-compose down -v
```

## 🏭 Production Setup

### 1. Criar arquivo .env com credenciais seguras
```bash
cp .env .env
# Editar .env com valores seguros
```

### 2. Iniciar em produção
```bash
docker-compose -f docker-compose.prod.yml up -d
```

### 3. Verificar saúde
```bash
curl http://localhost:8080/actuator/health
```

## 📁 Estrutura de Volumes

### Desenvolvimento
- `postgres_data` - Dados do PostgreSQL
- `pgadmin_data` - Configurações pgAdmin
- `maven_cache` - Cache Maven (reutilizado nos builds)

### Produção
- `postgres_prod_data` - Dados do PostgreSQL (persistente)

## 🔍 Endpoints de Health Check

```bash
# Health status
curl http://localhost:8080/actuator/health

# Info
curl http://localhost:8080/actuator/info

# Metrics
curl http://localhost:8080/actuator/metrics
```

## 🐛 Troubleshooting

### Erro: "postgres connection refused"
```bash
# Verificar se postgres está rodando
docker-compose ps

# Reiniciar postgres
docker-compose restart postgres

# Verificar logs
docker-compose logs postgres
```

### Erro: "Port already in use"
```bash
# Alterar portas no .env
APP_PORT=9080
DB_PORT=5433
PGADMIN_PORT=5051

# Ou descobrir o processo usando a porta
# Linux/Mac: lsof -i :8080
# Windows: netstat -ano | findstr :8080
```

### Aplicação não conecta ao banco
1. Verificar se postgres está saudável: `docker-compose ps`
2. Verificar logs: `docker-compose logs postgres`
3. Verificar valores em .env correspondem a docker-compose.yml
4. Resetar volumes: `docker-compose down -v && docker-compose up -d`

## 🔧 Build Manual

### Build da imagem
```bash
docker build -t gerenciadordeaulas:latest .
```

### Run manual (sem compose)
```bash
# Com PostgreSQL externo
docker run -d \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/gerenciadordeaulas \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres123 \
  --name gerenciadordeaulas \
  gerenciadordeaulas:latest
```

## 📊 Monitoramento

### Container stats
```bash
docker stats gerenciadordeaulas-app
```

### Logs estruturados
```bash
# Últimas 100 linhas
docker-compose logs --tail=100 app

# Follow logs (tempo real)
docker-compose logs -f app
```

## 🔐 Segurança (Production)

1. **Alterar credenciais padrão** no `.env`
2. **Usar secrets** em Kubernetes/Swarm
3. **Implementar HTTPS** com reverse proxy (Nginx/Caddy)
4. **Limitar acesso ao pgAdmin** (não expor em produção)
5. **Usar variáveis de ambiente** para configurações sensíveis
6. **Implementar backup** do volume `postgres_prod_data`

## 📦 Deploy Options

### Docker Compose (Single Server)
```bash
docker-compose -f docker-compose.prod.yml up -d
```

### Kubernetes
```bash
# Gerar manifests a partir do compose
kompose convert -f docker-compose.prod.yml

# Deploy
kubectl apply -f .
```

### Docker Swarm
```bash
docker swarm init
docker stack deploy -c docker-compose.prod.yml gerenciadordeaulas
```

## 📝 Configurações Importantes

### Java Memory
- Desenvolvimento: 256m-512m
- Produção: 512m-1024m (ajustar conforme necessário)

### Connection Pool (Hikari)
- Min idle: 5
- Max pool size: 20
- Timeout: 3s
- Idle timeout: 10min
- Max lifetime: 30min

### Database
- Driver: PostgreSQL 16 (Alpine)
- DDL Mode: validate (não criar/alterar schema automaticamente)

## 🚨 Checklist Pre-Deploy

- [ ] `.env` configurado com credenciais seguras
- [ ] `docker-compose.prod.yml` revisado
- [ ] Volumes persistentes criados/mapeados
- [ ] Porta 8080 (ou APP_PORT) disponível
- [ ] PostgreSQL com backup configurado
- [ ] Limites de recurso definidos (CPU/Memory)
- [ ] Logging centralizado configurado
- [ ] Health checks testados
- [ ] Secrets management implementado

## 📚 Referências

- [Docker Docs](https://docs.docker.com)
- [Docker Compose Docs](https://docs.docker.com/compose)
- [Spring Boot Docker](https://spring.io/guides/gs/spring-boot-docker/)
- [PostgreSQL Docker](https://hub.docker.com/_/postgres)
