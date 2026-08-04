# 🚀 Deploy no Render - Guia Completo

## 📋 O que é Render?

Render é uma plataforma de deploy moderna que oferece:
- ✓ Deploy automático via Git
- ✓ Gerenciamento de secrets/variáveis de ambiente seguro
- ✓ PostgreSQL hospedado incluído
- ✓ SSL/HTTPS automático
- ✓ Escalabilidade automática
- ✓ Plano gratuito disponível

**Acesso:** https://render.com

---

## 🎯 Pré-requisitos

✓ Conta GitHub com seu repositório (https://github.com/cfabioramos/gerenciadordeaulas)  
✓ Conta Render (free tier ok)  
✓ Imagem Docker funcional (já temos!)  
✓ Este repositório com Dockerfile e docker-compose.yml  

---

## 📝 PASSO 1: Preparar o Repositório

### 1.1 Fazer commit de todos os arquivos Docker
```bash
cd C:\desenvolvimento\gerenciadordeaulas

# Adicionar todos os arquivos Docker
git add Dockerfile
git add docker-compose.yml
git add docker-compose.prod.yml
git add docker-compose.swarm.yml
git add .dockerignore
git add application-docker.properties
git add .env.example
git add DOCKER-README.md
git add SECRETS-MANAGEMENT.md
git add SEGURANCA-CREDENCIAIS.md
git add scripts/manage-secrets.sh
git add render.yaml  # Vamos criar este!

# Commit
git commit -m "Docker: Configuração completa para deploy em produção

- Dockerfile multi-stage otimizado
- docker-compose para desenvolvimento e produção
- Segurança de credenciais com variáveis de ambiente
- Pronto para deploy no Render

Co-authored-by: Copilot <223556219+Copilot@users.noreply.github.com>"

# Push para GitHub
git push origin main
```

### 1.2 Verificar que o repositório é público
- Acesse https://github.com/cfabioramos/gerenciadordeaulas
- Clique em "Settings"
- Certifique-se que está public (para free tier do Render)

---

## 🌐 PASSO 2: Criar conta e conectar GitHub no Render

### 2.1 Acesse Render
```
https://render.com
```

### 2.2 Sign Up
- Clique em "Sign Up"
- **Recomendado:** "Continue with GitHub"
- Autorize acesso ao GitHub
- Escolha username

### 2.3 Após login, clique em "New +"
```
Dashboard → New +
```

---

## 🗄️ PASSO 3: Criar Banco de Dados PostgreSQL

### 3.1 Criar PostgreSQL no Render

```
New + → PostgreSQL
```

**Preencha com:**
| Campo | Valor |
|-------|-------|
| Name | `gerenciadordeaulas-db` |
| Database | `gerenciadordeaulas` |
| User | `postgres` |
| Region | Your closest (ex: São Paulo) |
| PostgreSQL Version | 16 |
| Plan | Free |

### 3.2 Guardar as credenciais

Após criar, você verá:
```
External Database URL: postgresql://postgres:PASSWORD@gerenciadordeaulas-db-xxxx.c4v7fq5.postgres.render.com:5432/gerenciadordeaulas
```

⚠️ **SALVE ESTE URL! Vamos usar depois**

---

## 🚀 PASSO 4: Criar Web Service (Aplicação)

### 4.1 Criar novo Web Service

```
New + → Web Service
```

### 4.2 Conectar repositório GitHub

```
Connect repository
├─ Selecione: cfabioramos/gerenciadordeaulas
└─ Clique "Connect"
```

### 4.3 Configurar o Web Service

**Nome:**
```
Name: gerenciadordeaulas
```

**Build & Deploy:**
```
Environment: Docker
Build Command: (leave empty - usará Dockerfile)
Start Command: (leave empty - usará ENTRYPOINT do Dockerfile)
```

**Region:**
```
Your closest (ex: São Paulo)
```

**Plan:**
```
Free (adequado para teste)
OU
Starter ($7/mês) para mais recursos
```

### 4.4 CRÍTICO: Adicionar Environment Variables

Clique em "Advanced" → "Add Environment Variable"

```
Adicione as seguintes variáveis:
```

| Variável | Valor | Fonte |
|----------|-------|-------|
| `SERVER_PORT` | `10000` | (padrão Render) |
| `SPRING_DATASOURCE_URL` | `postgresql://postgres:SENHA@gerenciadordeaulas-db-xxxx.c4v7fq5.postgres.render.com:5432/gerenciadordeaulas` | Do banco de dados |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Do banco de dados |
| `SPRING_DATASOURCE_PASSWORD` | `SUA_SENHA_DO_BANCO` | Do banco de dados |
| `SPRING_APPLICATION_NAME` | `EscolaGerenciamentoDeAulas` | Padrão |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `validate` | Segurança |
| `JAVA_OPTS` | `-Xmx512m -Xms256m -XX:+UseG1GC` | Performance |

⚠️ **Para variáveis sensíveis:** Render as criptografa automaticamente

### 4.5 Configurar Auto-Deploy

```
✓ Auto-deploy: ON
├─ Deploy novo build quando:
│  └─ Push para "main" branch
└─ Tempo de deploy: ~5-10 minutos
```

### 4.6 Deploy

Clique em **"Deploy"**

```
Status: Building
└─ Aguarde 5-10 minutos
   ├─ Baixa repositório
   ├─ Build da imagem Docker
   ├─ Push para registry Render
   ├─ Inicia container
   └─ Verifica health check
```

---

## ✅ PASSO 5: Verificar Deploy

### 5.1 Acompanhar logs

```
No dashboard Render:
Gerenciadordeaulas → Logs
```

Você deve ver algo como:

```
Building Docker image...
Pulling base image...
[Step 1/X] FROM maven:3.9.6...
...
Started application
Port: 10000
Health check: OK
```

### 5.2 Obter URL pública

```
Dashboard → Gerenciadordeaulas
└─ URL no topo: https://gerenciadordeaulas-xxxx.onrender.com
```

### 5.3 Testar API

```bash
# Health check
curl https://gerenciadordeaulas-xxxx.onrender.com/actuator/health

# Resposta esperada:
{
  "status": "UP"
}
```

---

## 🔐 PASSO 6: Segurança em Produção

### 6.1 Credenciais seguras no Render

✓ Render **criptografa** variáveis de ambiente  
✓ Não aparecem em logs  
✓ Não aparecem em diffs  

### 6.2 Backup do banco de dados

```
Gerenciadordeaulas-db → Backups
├─ Render faz backup automático
└─ Mantém por 7 dias (free) ou 30 dias (pago)
```

### 6.3 Monitoramento

```
Gerenciadordeaulas → Metrics
├─ CPU
├─ Memory
└─ Network
```

---

## 📊 PASSO 7: Configurar Domínio Customizado (Opcional)

### 7.1 Se tiver domínio próprio

```
Gerenciadordeaulas → Settings
└─ Custom Domain
    ├─ Adicione: api.seudominio.com.br
    ├─ Copie os registros DNS
    └─ Adicione no seu registrador (GoDaddy, Namecheap, etc)
```

### 7.2 SSL automático

```
✓ Render fornece SSL gratuito via Let's Encrypt
✓ Renova automaticamente
```

---

## 🔄 PASSO 8: CI/CD - Deploy Automático

### 8.1 Como funciona

```
Você faz commit e push
    ↓
GitHub detecta push em "main"
    ↓
Render recebe webhook do GitHub
    ↓
Render clona repositório
    ↓
Render faz build (Dockerfile)
    ↓
Render faz push para seu registry
    ↓
Render inicia novo container
    ↓
Health check passa?
    ├─ SIM → Deploy concluído ✓
    └─ NÃO → Rollback automático
```

### 8.2 Atualizar aplicação

```bash
# Fazer alterações no código
# Commit
git add .
git commit -m "Feature: nova funcionalidade"
git push origin main

# Render detecta push automaticamente
# Deploy automático em ~5-10 minutos
# Você pode acompanhar em: Render → Logs
```

---

## 🚨 Troubleshooting

### ❌ "Build failed"

**Verificar:**
```
Logs → procurar por "ERROR"
```

**Causas comuns:**
1. Dockerfile incompleto
2. `.env` com credenciais (remova do git)
3. Dependências Maven não instaladas

**Solução:**
```bash
# Revalidar localmente
docker build -t test .
docker run -it test /bin/bash

# Se funcionar localmente, fazer novo push
git commit --allow-empty -m "Rebuild"
git push
```

### ❌ "Application crashed"

**Verificar:**
```
Logs → procurar por "Exception"
```

**Causas comuns:**
1. Variáveis de ambiente não configuradas
2. Banco de dados indisponível
3. Porta errada

**Solução:**
```
Render Dashboard → Environment
└─ Verificar se todas variáveis estão corretas
```

### ❌ "Cannot connect to database"

**Verificar:**
```
1. URL do banco está correto?
2. Username/password corretos?
3. Banco de dados criado?
```

**Solução:**
```
Render Dashboard → PostgreSQL
└─ Copiar URL corretamente
   └─ Atualizar em Web Service → Environment
      └─ Redeploy
```

### ⏱️ "Timeout" no health check

**Aumentar tempo:**
```
Render Dashboard → Web Service → Settings
└─ Health Check Timeout: 300 (segundos)
```

---

## 📈 Aumentar Performance (Quando necessário)

### Upgrade de plano

```
Render Dashboard → Gerenciadordeaulas → Settings
└─ Plan: Free → Starter ($7/mês)
   ├─ 512MB RAM (vs 100MB)
   ├─ CPU dedicada
   ├─ Sem "spin down" de inatividade
   └─ 20GB SSD
```

### Database upgrade

```
Render Dashboard → gerenciadordeaulas-db → Settings
└─ Plan: Free → Starter ($15/mês)
   ├─ 1GB RAM
   ├─ Storage ilimitado
   ├─ Replicação automática
```

---

## 🎯 Checklist Final

- [ ] Repositório com Dockerfile no GitHub
- [ ] Conta Render criada
- [ ] PostgreSQL criado no Render
- [ ] Web Service criado
- [ ] Todas variáveis de ambiente configuradas
- [ ] Deploy concluído com sucesso
- [ ] Health check respondendo
- [ ] URL acessível públicamente
- [ ] Domínio customizado (opcional)
- [ ] Backups configurados
- [ ] Monitoramento ativado

---

## 📚 Referências Rápidas

| Tarefa | Link |
|--------|------|
| Dashboard Render | https://dashboard.render.com |
| Documentação Render | https://render.com/docs |
| Health Check | `https://seu-app.onrender.com/actuator/health` |
| Logs | Dashboard → Web Service → Logs |
| Variáveis | Dashboard → Web Service → Environment |

---

## 💡 Dicas

✓ **Primeiros 5 minutos são críticos** - Monitor logs  
✓ **Free tier "spin down"** - Reinicia após 15 min inativo  
✓ **Upgrade quando pronto** - Passar para Starter quando estável  
✓ **Backup automático** - Render cuida  
✓ **SSL grátis** - Sempre ativado  
✓ **Deploy é rápido** - ~5 minutos rebuild + restart  

---

## 🎉 Pronto!

Seu aplicativo agora está:
- ✓ Acessível via HTTPS
- ✓ Auto-deploy via Git
- ✓ Banco de dados seguro
- ✓ Backups automáticos
- ✓ Escalável conforme necessidade

**Próximo passo:** Compartilhe a URL com os usuários!

```
https://gerenciadordeaulas-xxxx.onrender.com
```

---

**Perguntas?** Consulte RENDER-FAQ.md
