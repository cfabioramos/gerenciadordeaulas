# 🎯 Render Deploy - Passo a Passo Visual

## 📍 PASSO 1: Preparar GitHub

```
┌─────────────────────────────────────────────────────┐
│ GITHUB - Preparar Repositório                       │
└─────────────────────────────────────────────────────┘

TERMINAL:
┌─────────────────────────────────────────────────────┐
│ $ cd C:\desenvolvimento\gerenciadordeaulas          │
│ $ git add .                                         │
│ $ git commit -m "Docker: Pronto para produção"      │
│ $ git push origin main                              │
│                                                     │
│ ✓ Dockerfile → enviado                              │
│ ✓ .dockerignore → enviado                           │
│ ✓ src/ → enviado                                    │
└─────────────────────────────────────────────────────┘

GITHUB:
https://github.com/cfabioramos/gerenciadordeaulas
├─ Verificar que está PUBLIC
├─ Verificar que todos arquivos estão lá
└─ ✓ Pronto para conectar no Render
```

---

## 📍 PASSO 2: Criar Conta Render

```
┌─────────────────────────────────────────────────────┐
│ RENDER.COM - Criar Conta                            │
└─────────────────────────────────────────────────────┘

1. Acesse: https://render.com
2. Clique "Sign Up"
3. Escolha "Continue with GitHub"
4. Autorize acesso
5. Preencha nome de usuário
6. ✓ Conta criada!
```

---

## 📍 PASSO 3: Criar Banco de Dados PostgreSQL

```
┌─────────────────────────────────────────────────────┐
│ RENDER - PostgreSQL Database                        │
└─────────────────────────────────────────────────────┘

Dashboard:
  New + ──→ PostgreSQL

Formulário:
┌───────────────────────────────────┐
│ Name:              gerenciadordeaulas-db       │
│ Database:          gerenciadordeaulas          │
│ User:              postgres                     │
│ Region:            São Paulo (sa)              │
│ Version:           16                           │
│ Plan:              Free                         │
└───────────────────────────────────┘

Clique: Create Database
⏱️  Aguarde 2-3 minutos

SALVAR INFORMAÇÕES:
┌───────────────────────────────────────────────┐
│ External Database URL:                        │
│ postgresql://postgres:SENHA@host:5432/db      │
│                                               │
│ ⚠️ COPIE COMPLETO! Vamos usar no próximo passo│
└───────────────────────────────────────────────┘

Status: Available
├─ Database: gerenciadordeaulas ✓
├─ User: postgres ✓
├─ URL: (sua URL única) ✓
└─ Backups: Enabled ✓
```

---

## 📍 PASSO 4: Criar Web Service (Aplicação)

```
┌─────────────────────────────────────────────────────┐
│ RENDER - Web Service                                │
└─────────────────────────────────────────────────────┘

Dashboard:
  New + ──→ Web Service

CONECTAR GITHUB:
┌─────────────────────────────────────────────────────┐
│ "Connect repository"                                │
│ └─ Selecione: cfabioramos/gerenciadordeaulas        │
│    └─ Clique "Connect"                              │
└─────────────────────────────────────────────────────┘

CONFIGURAR:
┌─────────────────────────────────────────────────────┐
│ Name:               gerenciadordeaulas              │
│                                                     │
│ Environment:        Docker                          │
│ Build Command:      (deixar vazio)                  │
│ Start Command:      (deixar vazio)                  │
│                                                     │
│ Region:             São Paulo (sa)                  │
│ Plan:               Free                            │
│                                                     │
│ Branch:             main                            │
│ Auto-Deploy:        ✓ ON                            │
└─────────────────────────────────────────────────────┘

AVANÇADO - ADICIONAR VARIÁVEIS DE AMBIENTE:
┌─────────────────────────────────────────────────────┐
│ Advanced → Add Environment Variable                 │
└─────────────────────────────────────────────────────┘

ADICIONE TODAS ESTAS VARIÁVEIS:

1. SERVER_PORT
   ├─ Value: 10000

2. SPRING_APPLICATION_NAME
   ├─ Value: EscolaGerenciamentoDeAulas

3. SPRING_DATASOURCE_URL
   ├─ Value: postgresql://postgres:SENHA@host:5432/gerenciadordeaulas
   └─ ⚠️ SUBSTITUIR "SENHA" e "host" pela URL do banco!

4. SPRING_DATASOURCE_USERNAME
   ├─ Value: postgres

5. SPRING_DATASOURCE_PASSWORD
   ├─ Value: [SENHA DO BANCO]

6. SPRING_DATASOURCE_DRIVER_CLASS_NAME
   ├─ Value: org.postgresql.Driver

7. SPRING_JPA_HIBERNATE_DDL_AUTO
   ├─ Value: validate

8. SPRING_JPA_SHOW_SQL
   ├─ Value: false

9. SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL
   ├─ Value: false

10. SPRING_JPA_DATABASE_PLATFORM
    ├─ Value: org.hibernate.dialect.PostgreSQLDialect

11. SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE
    ├─ Value: 5

12. SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE
    ├─ Value: 20

13. JAVA_OPTS
    ├─ Value: -Xmx512m -Xms256m -XX:+UseG1GC

✓ TODAS CONFIGURADAS
```

---

## 📍 PASSO 5: Deploy Inicial

```
┌─────────────────────────────────────────────────────┐
│ RENDER - Fazendo Deploy                             │
└─────────────────────────────────────────────────────┘

DASHBOARD:
  gerenciadordeaulas (seu web service)
  └─ Clique "Deploy"

STATUS DE BUILD:
┌─────────────────────────────────────────────────────┐
│ Building...                                         │
│ ├─ Cloning repo                                    │
│ ├─ Building Docker image                           │
│ ├─ Pushing to registry                             │
│ └─ Starting service                                │
│                                                     │
│ ⏱️  Tempo: 5-10 minutos (primeira vez)              │
│ 💡 Não feche esta aba!                             │
└─────────────────────────────────────────────────────┘

ABA LOGS (observar em tempo real):
├─ Building docker image...
├─ Step 1/15 FROM maven:3.9.6...
├─ Step 2/15 WORKDIR /app
├─ ... mais passos ...
├─ Successfully built
├─ Pushing to container registry...
├─ Container starting on port 10000
├─ Started EscolaGerenciamentoDeAulas
├─ Database connection pool: GerenciadorDeAulasPool
├─ Health check passed ✓
└─ Deploy succeeded ✓
```

---

## 📍 PASSO 6: Verificar URL Pública

```
┌─────────────────────────────────────────────────────┐
│ RENDER - URL Pública                                │
└─────────────────────────────────────────────────────┘

Dashboard → gerenciadordeaulas
├─ Procurar no topo da página
│
├─ URL: https://gerenciadordeaulas-xxxxx.onrender.com
│
└─ Copiar URL!

TESTAR HEALTH CHECK:
┌─────────────────────────────────────────────────────┐
│ Browser:                                            │
│ https://gerenciadordeaulas-xxxxx.onrender.com/      │
│     actuator/health                                 │
│                                                     │
│ Esperado:                                           │
│ {                                                   │
│   "status": "UP"                                    │
│ }                                                   │
│                                                     │
│ ✓ Sucesso!                                          │
└─────────────────────────────────────────────────────┘
```

---

## 📍 PASSO 7: Compartilhar & Monitorar

```
┌─────────────────────────────────────────────────────┐
│ RENDER - Após Deploy                                │
└─────────────────────────────────────────────────────┘

COMPARTILHE:
┌─────────────────────────────────────────────────────┐
│ Copie a URL e compartilhe:                          │
│ https://gerenciadordeaulas-xxxxx.onrender.com       │
│                                                     │
│ ✓ Todos podem acessar                              │
│ ✓ Com SSL/HTTPS automático                         │
│ ✓ Sem custo no free tier                           │
└─────────────────────────────────────────────────────┘

MONITORAR:
┌─────────────────────────────────────────────────────┐
│ Dashboard → Logs                                    │
│ ├─ Acompanhe requisições em tempo real              │
│ │                                                   │
│ Dashboard → Metrics                                │
│ ├─ CPU usage                                        │
│ ├─ Memory usage                                     │
│ └─ Network I/O                                      │
│                                                     │
│ Dashboard → Events                                 │
│ └─ Histórico de deploys                            │
└─────────────────────────────────────────────────────┘

PRÓXIMOS DEPLOYS:
┌─────────────────────────────────────────────────────┐
│ Automático ao fazer push:                           │
│ $ git push origin main                              │
│                                                     │
│ Render detecta, faz build e deploy                 │
│ Tempo: ~5 minutos (com cache)                       │
│                                                     │
│ Você pode acompanhar em:                           │
│ Dashboard → gerenciadordeaulas → Logs              │
└─────────────────────────────────────────────────────┘
```

---

## 🎯 Checklist Completo

```
ANTES DE COMEÇAR:
└─ ✓ Git push feito
   └─ ✓ Repositório está public no GitHub

PASSO 1: GitHub ✓
└─ ✓ Arquivo Dockerfile presente
   └─ ✓ Arquivo .dockerignore presente
      └─ ✓ arquivo .env ignorado

PASSO 2: Conta Render ✓
└─ ✓ Login com GitHub
   └─ ✓ Email verificado

PASSO 3: Banco de Dados ✓
└─ ✓ PostgreSQL criado em Render
   └─ ✓ URL/senha guardada
      └─ ✓ Status: Available

PASSO 4: Web Service ✓
└─ ✓ GitHub conectado
   └─ ✓ 13 variáveis de ambiente configuradas
      └─ ✓ SPRING_DATASOURCE_URL com valores corretos

PASSO 5: Deploy ✓
└─ ✓ Build concluído
   └─ ✓ Container iniciado
      └─ ✓ Health check passou

PASSO 6: URL Pública ✓
└─ ✓ /actuator/health responde
   └─ ✓ Status: UP
      └─ ✓ HTTPS funcionando

PASSO 7: Produção ✓
└─ ✓ Auto-deploy ativado
   └─ ✓ Logs sendo monitorados
      └─ ✓ Pronto para uso!
```

---

## 🆘 Algo deu errado?

```
┌──────────────────────────────────────┐
│ TROUBLESHOOTING RÁPIDO               │
└──────────────────────────────────────┘

❌ Build failed?
└─ Consulte: RENDER-FAQ.md → "Build failed"

❌ Cannot connect to database?
└─ Consulte: RENDER-FAQ.md → "Cannot connect"

❌ Health check timeout?
└─ Consulte: RENDER-FAQ.md → "Health check timeout"

❌ Aplicação muito lenta?
└─ Consulte: RENDER-FAQ.md → "Out of memory"

❌ Algo que não está aqui?
└─ Consulte: https://render.com/docs
```

---

## 🎉 Pronto!

Seu aplicativo está live em:
```
https://gerenciadordeaulas-xxxxx.onrender.com
```

✓ Acessível publicamente  
✓ Com SSL/HTTPS  
✓ Banco de dados seguro  
✓ Auto-deploy habilitado  
✓ Backups automáticos  

**Próximos passos:**
1. Testar API endpoints
2. Adicionar domínio customizado (opcional)
3. Configurar monitoring/alertas (opcional)
4. Fazer backup do código (Github)

---

**Dúvidas?** Abra: RENDER-FAQ.md ou RENDER-DEPLOY.md
