# 🤔 Render Deploy - FAQ & Troubleshooting

## ❓ Perguntas Frequentes

### P: Render é gratuito?
**R:** Sim! Plano Free inclui:
- ✓ 100 MB RAM
- ✓ Compartilhado CPU
- ✓ PostgreSQL free (250 MB)
- ✓ SSL grátis
- ✗ "Spin down" após 15 min inativo (wake up automático)

Para produção: Starter ($7/mês web + $15/mês database)

---

### P: Quanto tempo leva para fazer deploy?
**R:** ~5-10 minutos:
- Clone: 30s
- Build Docker: 3-5 min (depende de Maven)
- Push registry: 1 min
- Deploy: 1-2 min
- Health check: 30s

---

### P: Meu aplicativo fica inativo no Render free?
**R:** Sim! Depois de 15 minutos sem requisições:
- Container "dorme" (para economia)
- Próxima requisição tira do sleep (+3-5 seg)

**Solução:** Upgrade para Starter (remove spin down)

---

### P: Como fazer backup do banco de dados?
**R:** Render faz automático:
- Free: 7 dias de retenção
- Starter: 30 dias
- Pro: 90 dias

Acessar em: Banco de dados → Backups

---

### P: Posso usar meu domínio próprio?
**R:** Sim! Em Settings → Custom Domain:
1. Adicione: `api.seudominio.com.br`
2. Copie registros DNS
3. Configure no seu registrador
4. SSL automático via Let's Encrypt

---

### P: Quanto custa escalar?
**R:**
```
Web Service:
├─ Free: $0/mês
├─ Starter: $7/mês
├─ Standard: $25/mês
└─ Pro: $115/mês

PostgreSQL:
├─ Free: $0/mês (250 MB)
├─ Starter: $15/mês (1 GB)
├─ Standard: $50/mês (10 GB)
└─ Pro: $150+/mês (conforme usar)
```

---

## 🐛 Troubleshooting

### ❌ "Application failed to start"

**1. Verificar logs:**
```
Dashboard → Logs
```

**2. Causas comuns:**

**a) Porta incorreta**
```
❌ server.port=8080
✓ server.port=10000  (padrão Render)
```

**Solução:**
```
Render usa variável PORT automaticamente
Spring Boot lê ${SERVER_PORT} que setamos como 10000
```

**b) Variáveis de ambiente ausentes**
```
Dashboard → Environment
├─ Verificar SPRING_DATASOURCE_URL
├─ Verificar SPRING_DATASOURCE_USERNAME
└─ Verificar SPRING_DATASOURCE_PASSWORD
```

**c) Banco de dados não pronto**
```
Logs procurar por: "Connection refused"
├─ Banco ainda está iniciando
└─ Aguarde 30 segundos, Render fará retry automático
```

**Ação:** Aguarde health check passar (max 60s)

---

### ❌ "Build failed"

**Causas:**

**a) Dockerfile inválido**
```bash
# Testar localmente
docker build -t test .

# Se falhar, corrigir Dockerfile
```

**b) Repositório privado**
```
Settings → Source Control
├─ Verificar se token GitHub é válido
└─ Reconectar repository
```

**c) Tamanho de build muito grande**
```
Maven baixa ~500MB em primeira execução
├─ Primeiro deploy = ~10 min
├─ Próximos deploys = ~5 min (cache)

Limit Render: ~600 MB build
├─ Geralmente OK
└─ Se passar, considerar .dockerignore
```

**Solução:**
```dockerfile
# Usar cache Maven
COPY .mvn .mvn
COPY mvnw mvnw
```

---

### ❌ "Cannot connect to database"

**1. Verificar credenciais**
```
PostgreSQL → Info
└─ Copiar URL COMPLETA
    └─ Incluir: username:password@host:port/database
```

**2. Atualizar no Web Service**
```
Web Service → Environment
├─ SPRING_DATASOURCE_URL (URL completa)
├─ SPRING_DATASOURCE_USERNAME
└─ SPRING_DATASOURCE_PASSWORD
```

**3. Redeploy**
```
Web Service → Redeploy
```

**Teste:**
```bash
# Testar conexão localmente
PGPASSWORD="senha" psql -h host -U postgres -d gerenciadordeaulas -c "SELECT 1"
```

---

### ❌ "Health check timeout"

**Causa:** Java startup é lento

**Verificar logs:**
```
Procurar por "Started Application"
```

**Aumentar timeout:**
```
Settings → Health Check
├─ Timeout: 60 segundos (ou mais)
└─ Save
```

**Aumentar start_period:**
```
Se tiver container ainda iniciando
├─ SPRING_JPA_HIBERNATE_DDL_AUTO=validate
│  └─ Não tenta criar schema (mais rápido)
└─ Redeploy
```

---

### ⚠️ "Out of memory"

**Free tier tem apenas 100 MB**

**Verificar:**
```
Dashboard → Metrics → Memory
```

**Soluções:**

1. **Reduzir heap Java:**
```bash
JAVA_OPTS="-Xmx256m -Xms128m"
```

2. **Upgrade para Starter:**
```
512 MB RAM disponível
```

3. **Otimizar aplicação:**
```bash
# Desabilitar recursos não usados
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
```

---

### 🐌 "Deploy muito lento"

**Primeira vez:** 10 minutos (normal)
- Maven baixa dependências
- Build Docker completo

**Próximas vezes:** 5 minutos
- Maven cache reutilizado
- Docker layers em cache

**Se continuar lento:**
- Verificar internet
- Verificar size do repositório (git clone lento)
- Considerar pré-clonar dependências

---

### 🔁 "Loops infinitos de rebuild"

**Causa:** Health check falhando → rebuild → health check falha → loop

**Verificar:**
```
Logs procurar por padrão repetitivo
```

**Quebrar loop:**
```
Dashboard → Web Service
├─ Pause
└─ Aguarde que pare rebuilding
```

**Corrigir:**
```
Settings → Health Check
├─ Aumentar timeout
└─ Salvar e Redeploy
```

---

## 🎯 Checklist de Deploy

### Antes de Deploy

- [ ] Dockerfile testado localmente: `docker build -t test . && docker run -it test`
- [ ] Repositório public no GitHub
- [ ] `.env` NÃO está commitado (verificar `.gitignore`)
- [ ] `pom.xml` atualizado
- [ ] Java 21 compatível (temos!)

### Setup Render

- [ ] Conta Render criada
- [ ] GitHub conectado
- [ ] PostgreSQL criado (anotar URL/senha)
- [ ] Web Service criado
- [ ] Todas 12+ variáveis de ambiente configuradas
- [ ] Health check setado para `/actuator/health`

### Pós-Deploy

- [ ] Logs mostram "Started Application"
- [ ] Health check retorna HTTP 200 UP
- [ ] URL pública acessível
- [ ] Banco de dados conectado
- [ ] Backups configurados

---

## 🚀 Deploy Rápido (Checklist)

```bash
# 1. Git push
git commit -m "Deploy changes"
git push origin main

# 2. Acompanhar
# Abra: https://dashboard.render.com
# Clique em seu Web Service
# Abra aba "Logs"
# Aguarde terminar

# 3. Testar
curl https://seu-app.onrender.com/actuator/health

# 4. Pronto! 🎉
```

---

## 📊 Performance por Plano

| Métrica | Free | Starter | Standard | Pro |
|---------|------|---------|----------|-----|
| RAM | 100 MB | 512 MB | 2 GB | 4+ GB |
| CPU | Shared | Shared | 1 Core | 2+ Cores |
| Spin down | Sim | Não | Não | Não |
| Uptime SLA | - | 99.9% | 99.95% | 99.99% |
| Preço Web | $0 | $7 | $25 | $115 |
| Preço DB | $0 | $15 | $50 | $150+ |

**Recomendação:**
- Desenvolvimento: Free
- Produção: Starter ($22/mês)
- Crítico: Standard ($75+/mês)

---

## 💡 Dicas & Tricks

### ⚡ Deploy mais rápido
```
- Usar .dockerignore agressivo
- Cache Maven: copiar pom.xml antes de src/
- Reduzir layers do Dockerfile
```

### 🔒 Segurança
```
- Usar Environment variables para secrets
- Nunca commit .env
- Render criptografa variáveis
- Use ddl-auto=validate (não auto-update schema)
```

### 📈 Escalabilidade
```
- Free: OK para teste/demo
- Starter: OK para ~100 usuários
- Standard: OK para ~1k usuários
- Pro: OK para alta carga
```

### 🛠️ Debugging
```
# Tail logs em tempo real
# No dashboard, aba "Logs"
# Ver "live" logs enquanto acontecem

# SSH no container (se ativado)
# render ssh -s gerenciadordeaulas
```

### 🔄 Redeploy rápido
```
Dashboard → Web Service → Redeploy
├─ Sem rebuild
├─ Usa imagem existente
└─ 2-3 minutos
```

---

## 📞 Support Render

| Canal | Link |
|-------|------|
| Docs | https://render.com/docs |
| Status | https://status.render.com |
| Community | https://render.com/community |
| Email | support@render.com |

---

## 🎓 Próximas Melhorias

Depois que estiver rodando:

1. **Adicionar monitoring**
   - Sentry para erro tracking
   - Datadog para métricas

2. **Implementar backup strategy**
   - Cronjobs para backup a S3
   - Point-in-time recovery

3. **Setup CI/CD**
   - Testes automatizados antes de deploy
   - Staging environment

4. **Custom domain**
   - api.seudominio.com.br
   - SSL automático

5. **Escalabilidade**
   - Upgrade para Starter
   - Depois Standard se necessário

---

## 📝 Logs Exemplo (Sucesso)

```
Building docker image...
Sending build context to Docker daemon
Step 1/15 : FROM maven:3.9.6-eclipse-temurin-21 AS builder
...
Step 15/15 : ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"]
Successfully built abc123def456

Pushing to container registry...
Successfully pushed to render

Starting deploy...
Container starting on port 10000
2024-08-03 22:45:00 INFO: Started EscolaGerenciamentoDeAulas
2024-08-03 22:45:02 INFO: Database connection pool: GerenciadorDeAulasPool
2024-08-03 22:45:05 INFO: Application ready, listening on 10000
Health check passed ✓
Deploy succeeded ✓
Live URL: https://gerenciadordeaulas-xyz123.onrender.com ✓
```

---

**Pronto! Seu app agora está no ar! 🚀**
