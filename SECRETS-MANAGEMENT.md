# Secrets Management Guide

Este documento mostra as melhores práticas para gerenciar credenciais e informações sensíveis.

## ⚠️ NUNCA faça isso:
```
❌ Não coloque credenciais no git
❌ Não hardcode senhas em application.properties
❌ Não exponha credenciais em logs
❌ Não use credenciais padrão em produção
```

## ✅ Abordagens Recomendadas

### 1️⃣ Desenvolvimento Local (Mais Simples)

**Use arquivo `.env`** (ignorado pelo git via .gitignore)

```bash
# Criar arquivo .env
cp .env .env

# Editar com suas credenciais
# .env
DB_NAME=gerenciadordeaulas
DB_USER=postgres
DB_PASSWORD=sua_senha_segura
APP_PORT=8080
```

**Iniciar com Docker Compose:**
```bash
docker-compose up -d
# Compose lê .env automaticamente
```

### 2️⃣ Variáveis de Ambiente do Sistema

**Linux/Mac:**
```bash
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=senha_segura
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/gerenciadordeaulas

docker-compose up -d
```

**Windows PowerShell:**
```powershell
$env:SPRING_DATASOURCE_USERNAME="postgres"
$env:SPRING_DATASOURCE_PASSWORD="senha_segura"
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/gerenciadordeaulas"

docker-compose up -d
```

### 3️⃣ Docker Secrets (Docker Swarm - Produção)

Para ambientes de produção com Docker Swarm:

```bash
# Inicializar Docker Swarm
docker swarm init

# Criar secrets
echo "postgres" | docker secret create db_user -
echo "senha_super_segura_123" | docker secret create db_password -
echo "gerenciadordeaulas" | docker secret create db_name -

# Deploy com docker-compose.swarm.yml
docker stack deploy -c docker-compose.swarm.yml gerenciadordeaulas

# Verificar secrets
docker secret ls
```

**docker-compose.swarm.yml:**
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB_FILE: /run/secrets/db_name
      POSTGRES_USER_FILE: /run/secrets/db_user
      POSTGRES_PASSWORD_FILE: /run/secrets/db_password
    secrets:
      - db_name
      - db_user
      - db_password
    volumes:
      - postgres_prod_data:/var/lib/postgresql/data

  app:
    image: gerenciadordeaulas:latest
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/gerenciadordeaulas
      SPRING_DATASOURCE_USERNAME_FILE: /run/secrets/db_user
      SPRING_DATASOURCE_PASSWORD_FILE: /run/secrets/db_password
    secrets:
      - db_user
      - db_password
    ports:
      - "8080:8080"
    depends_on:
      - postgres

secrets:
  db_name:
    external: true
  db_user:
    external: true
  db_password:
    external: true

volumes:
  postgres_prod_data:
```

### 4️⃣ Kubernetes Secrets (Produção - Recomendado)

**Criar secrets:**
```bash
# Encoding em base64
echo -n "postgres" | base64
echo -n "senha_super_segura" | base64

# Criar secrets.yaml
kubectl create secret generic gerenciador-db-secrets \
  --from-literal=username=postgres \
  --from-literal=password=senha_super_segura \
  --from-literal=database=gerenciadordeaulas
```

**kubernetes-deployment.yaml:**
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  SPRING_DATASOURCE_URL: "jdbc:postgresql://postgres:5432/gerenciadordeaulas"

---
apiVersion: v1
kind: Secret
metadata:
  name: db-credentials
type: Opaque
stringData:
  username: postgres
  password: "SUA_SENHA_SUPER_SEGURA"
  database: gerenciadordeaulas

---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: gerenciadordeaulas-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: gerenciadordeaulas
  template:
    metadata:
      labels:
        app: gerenciadordeaulas
    spec:
      containers:
      - name: app
        image: gerenciadordeaulas:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            configMapKeyRef:
              name: app-config
              key: SPRING_DATASOURCE_URL
        - name: SPRING_DATASOURCE_USERNAME
          valueFrom:
            secretKeyRef:
              name: db-credentials
              key: username
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: db-credentials
              key: password
```

### 5️⃣ Gerenciadores de Secrets Externos

#### AWS Secrets Manager
```java
// Java client para AWS Secrets Manager
@Configuration
public class SecretsConfig {
    
    @Bean
    public String dbPassword() {
        SecretsManagerClient client = SecretsManagerClient.builder()
            .region(Region.US_EAST_2)
            .build();
        
        GetSecretValueRequest request = GetSecretValueRequest.builder()
            .secretId("gerenciadordeaulas/db-password")
            .build();
        
        GetSecretValueResponse response = client.getSecretValue(request);
        return response.secretString();
    }
}
```

#### HashiCorp Vault
```bash
# Install Vault
brew install vault  # ou choco install vault

# Start Vault
vault server -dev

# Store secret
vault kv put secret/gerenciadordeaulas/db \
  username=postgres \
  password=senha_segura \
  url=jdbc:postgresql://postgres:5432/gerenciadordeaulas

# Aplicação lê via Spring Cloud Vault
```

**application-vault.yml:**
```yaml
spring:
  cloud:
    vault:
      host: localhost
      port: 8200
      scheme: http
      authentication: TOKEN
      token: s.your-token-here
      kv-version: 2
      
  datasource:
    username: ${vault.gerenciadordeaulas.db.username}
    password: ${vault.gerenciadordeaulas.db.password}
    url: ${vault.gerenciadordeaulas.db.url}
```

## 🔄 Workflow Seguro Recomendado

```
┌─────────────────────────────────────────────────────────┐
│ 1. DESENVOLVIMENTO LOCAL                                │
│ └─ Use .env (ignorado pelo git)                         │
│ └─ Credenciais locais/padrão seguras                    │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│ 2. STAGING/TESTE                                        │
│ └─ Use environment variables de CI/CD (GitHub Actions) │
│ └─ Secrets armazenados em Settings > Secrets            │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│ 3. PRODUÇÃO                                             │
│ └─ Use Kubernetes Secrets OU Docker Secrets            │
│ └─ OU use gerenciador externo (Vault, AWS, etc)        │
│ └─ NUNCA use .env em produção                          │
└─────────────────────────────────────────────────────────┘
```

## 📋 Checklist de Segurança

- [ ] `.env` está no `.gitignore`
- [ ] `.env` contém apenas placeholders
- [ ] `application-docker.properties` NÃO contém senhas hardcoded
- [ ] Credenciais são injetadas via variáveis de ambiente
- [ ] Secrets não aparecem em logs
- [ ] Senhas são alteradas regularmente em produção
- [ ] Acesso a credenciais é auditado/registrado
- [ ] Backup de dados é criptografado
- [ ] Conexões HTTPS/TLS estão ativas
- [ ] Princípio do menor privilégio aplicado

## 🚨 Emergência: Credencial Comprometida

```bash
# 1. Gerar nova senha
# 2. Atualizar em production (docker-compose.prod.yml)
# 3. Recriar container
docker-compose -f docker-compose.prod.yml down
docker-compose -f docker-compose.prod.yml up -d

# 4. Verificar logs de acesso ao banco
# 5. Se necessário, resetar banco de dados
```

## 📚 Referências

- [Spring Boot Externalized Configuration](https://spring.io/guides/gs/centralized-configuration/)
- [12-Factor App - Config](https://12factor.net/config)
- [Docker Secrets](https://docs.docker.com/engine/swarm/secrets/)
- [Kubernetes Secrets](https://kubernetes.io/docs/concepts/configuration/secret/)
- [OWASP Secrets Management](https://cheatsheetseries.owasp.org/cheatsheets/Secrets_Management_Cheat_Sheet.html)
