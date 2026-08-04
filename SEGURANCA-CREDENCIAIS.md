# 🔐 Segurança de Credenciais - Guia Rápido

## ✅ O que foi feito

1. **application-docker.properties** - LIMPO ❌ sem credenciais
   ```properties
   spring.datasource.url=${SPRING_DATASOURCE_URL}
   spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
   spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
   ```

2. **.env** - IGNORADO pelo git ✓
   ```
   .env está no .gitignore → nunca será commitado
   ```

3. **.env.example** - Template SEGURO ✓
   ```
   Contém apenas placeholders, não valores reais
   ```

## 🚀 Como usar em Desenvolvimento

### 1️⃣ Primeira vez: Gerar .env

```bash
# Copie o template
cp .env.example .env

# Edite com suas credenciais (este arquivo é local e ignorado)
# Nunca faça commit!
```

**Conteúdo do .env:**
```
APP_PORT=8080
DB_NAME=gerenciadordeaulas
DB_USER=postgres
DB_PASSWORD=sua_senha_bem_segura_123
DB_PORT=5432
```

### 2️⃣ Iniciar containers

```bash
# Docker Compose lê .env automaticamente
docker-compose up -d

# Pronto! Seus secrets não ficam expostos no código
```

## 🏭 Produção com Docker Swarm

### 1️⃣ Inicializar Swarm

```bash
docker swarm init
```

### 2️⃣ Criar secrets (use o script)

```bash
# Opção 1: Script interativo
bash scripts/manage-secrets.sh
# Escolha: 1. Criar secrets para Docker Swarm

# Opção 2: Linha de comando
echo "gerenciadordeaulas" | docker secret create gerenciadordeaulas_db_name -
echo "postgres" | docker secret create gerenciadordeaulas_db_user -
echo "senha_super_segura_123" | docker secret create gerenciadordeaulas_db_password -
```

### 3️⃣ Deploy

```bash
docker stack deploy -c docker-compose.swarm.yml gerenciadordeaulas
```

### 4️⃣ Verificar

```bash
docker secret ls
docker service ls
docker logs <container-id>
```

## 🔒 Segurança - Antes vs Depois

### ❌ ANTES (PERIGOSO)
```properties
spring.datasource.password=senha123
```
✓ Exposto no código-fonte  
✓ Exposto em diffs do git  
✓ Exposto em backups  
✓ Exposto em histórico de commits  

### ✅ DEPOIS (SEGURO)
```properties
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```
✓ Variável de ambiente  
✓ Nunca no git  
✓ Injetada em tempo de execução  
✓ Segura em produção  

## 📊 Fluxo de Credenciais

```
┌────────────────────────────────────────────────────────────┐
│ DESENVOLVIMENTO (Máquina Local)                            │
│ ✓ .env (local, ignorado pelo git)                          │
│ ✓ Docker Compose lê .env                                   │
│ ✓ Credenciais nunca no repositório                         │
└────────────────────────────────────────────────────────────┘
                          ↓
┌────────────────────────────────────────────────────────────┐
│ GIT REPOSITORY                                             │
│ ✓ .env.example (apenas template)                           │
│ ✓ docker-compose.yml (referencia ${VAR})                   │
│ ✓ Nenhuma credencial real                                  │
└────────────────────────────────────────────────────────────┘
                          ↓
┌────────────────────────────────────────────────────────────┐
│ PRODUÇÃO (Docker Swarm/Kubernetes/Cloud)                  │
│ ✓ Docker Secrets / Kubernetes Secrets / AWS Secrets Mgr   │
│ ✓ Variáveis de ambiente injetadas                          │
│ ✓ Máxima segurança                                         │
└────────────────────────────────────────────────────────────┘
```

## 📁 Arquivo modificados

| Arquivo | Mudança | Razão |
|---------|---------|-------|
| `application-docker.properties` | Usa `${VAR}` | Sem credenciais hardcoded |
| `.gitignore` | Adicionado `.env` | Proteger credenciais locais |
| `docker-compose.yml` | Referencia `.env` | Lê variáveis do arquivo |
| `docker-compose.prod.yml` | Sem `.env` | Produção usa env vars |
| `docker-compose.swarm.yml` | Usa Docker Secrets | Máxima segurança Swarm |
| `.env.example` | Template criado | Guia para desenvolvedores |

## 🛠️ Troubleshooting

### "Conexão recusada ao banco"
```bash
# Verificar se .env existe
ls -la .env

# Verificar se variáveis foram carregadas
docker-compose config | grep -A5 postgres

# Verificar logs
docker-compose logs postgres
```

### "Variable is not set" error
```bash
# Copiar .env.example
cp .env.example .env

# Preencher com valores
# Reiniciar
docker-compose down && docker-compose up -d
```

### Valores aparecem expostos em logs
```bash
# Não mostre variáveis sensíveis em logs
SPRING_JPA_SHOW_SQL=false  # no .env
```

## 🎯 Checklist de Segurança

- [x] `.env` está no `.gitignore`
- [x] `application-docker.properties` usa `${VAR}`
- [x] `.env.example` tem apenas placeholders
- [x] Nunca commit arquivo `.env`
- [x] Docker Compose injeta variáveis
- [x] Produção usa Docker Secrets / Kubernetes Secrets
- [x] Nenhuma credencial em logs
- [x] Nenhuma credencial no docker history

## 📚 Arquivos de Referência

- `SECRETS-MANAGEMENT.md` - Guia completo
- `DOCKER-README.md` - Setup Docker
- `scripts/manage-secrets.sh` - Script automatizado
- `docker-compose.swarm.yml` - Produção com Secrets
- `.env.example` - Template de variáveis

## ✨ Benefícios

✓ **Segurança** - Credenciais nunca no repositório  
✓ **Flexibilidade** - Mesma imagem Docker para dev/prod  
✓ **Conformidade** - Atende OWASP/PCI-DSS  
✓ **Rastreabilidade** - Mudanças em secrets são auditadas  
✓ **Automação** - CI/CD injeta secrets automaticamente  

---

**Dúvidas?** Consulte `SECRETS-MANAGEMENT.md` para mais opções!
