# 🚀 Render Start Command - Referência Rápida

## ✅ Comando Correto

Para o campo obrigatório **"Start Command"** do Render:

```
sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"
```

## 📝 Campos do Render

| Campo | Valor | Notas |
|-------|-------|-------|
| **Build Command** | `docker build -t app .` | Cria a imagem |
| **Start Command** | `sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"` | ⚠️ OBRIGATÓRIO |
| **Root Directory** | (deixar vazio) | Usa raiz do repo |
| **Dockerfile Path** | (deixar vazio) | Procura na raiz |

## 🔍 Explicação

```
sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"
│  │   │     │          │   │
│  │   │     │          │   └─ Arquivo JAR (gerado pelo Maven)
│  │   │     │          └────── Flag do Java
│  │   │     └─────────────── Variável de ambiente (Render injeta)
│  │   └──────────────────── Java CLI
│  └──────────────────────── Executa shell
└───────────────────────────── Shell command
```

O Render vai:
1. Usar imagem criada por `docker build`
2. Injetar variáveis de ambiente (JAVA_OPTS, etc)
3. Executar este comando
4. Iniciar a aplicação Spring Boot
5. Aplicação escuta na porta 10000

## 📊 Opções (Ordem de Preferência)

### ✅ OPÇÃO 1 - Recomendada (com JAVA_OPTS)
```
sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"
```
**Pro:**
- Respeita configuração de memória (JAVA_OPTS)
- Compatível com Render
- Flexível para mudanças futuras

**Contra:**
- Depende de JAVA_OPTS estar configurado

---

### ✅ OPÇÃO 2 - Simples (sem JAVA_OPTS)
```
java -jar /app/gerenciadoraulas.jar
```
**Pro:**
- Mais simples
- Direto

**Contra:**
- Ignora JAVA_OPTS
- Usa defaults JVM (pode ser lento)
- Menos controle

---

### ✅ OPÇÃO 3 - Com valores explícitos
```
sh -c "java -Xmx512m -Xms256m -XX:+UseG1GC -jar /app/gerenciadoraulas.jar"
```
**Pro:**
- Não depende de variáveis

**Contra:**
- Hardcoded (difícil mudar depois)
- Menos flexível

---

## 🎯 Configuração Completa no Render

```
┌─────────────────────────────────────────────────────┐
│ Web Service Setup                                   │
├─────────────────────────────────────────────────────┤
│ Name:                   gerenciadordeaulas          │
│ Repository:             cfabioramos/gerenciadordeaulas
│ Branch:                 main                        │
│ Environment:            Docker                      │
│ Build Command:          docker build -t app .       │
│ Start Command:          sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"
│ Root Directory:         (vazio)                     │
│ Dockerfile Path:        (vazio - procura na raiz)  │
│ Region:                 São Paulo (sa)              │
│ Plan:                   Free                        │
│ Auto-deploy:            ✓ ON                        │
└─────────────────────────────────────────────────────┘
```

## ⚠️ Importante

```
❌ ERRADO:
├─ Deixar Start Command vazio
├─ Usar "npm start"
├─ Usar "python app.py"
├─ Usar "./mvnw spring-boot:run"
└─ Usar apenas "java" (sem -jar)

✅ CORRETO:
├─ sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"
└─ Ou simplesmente: java -jar /app/gerenciadoraulas.jar
```

## 🔄 Como Funciona no Render

```
1. Git Push
   ↓
2. Render clona repositório
   ↓
3. Executa Build Command:
   └─ docker build -t app .
      ├─ Baixa maven + JDK 21
      ├─ Maven compila código
      ├─ Maven faz package (JAR)
      ├─ Copia JAR para nova imagem (JRE only)
      └─ Imagem pronta (~200MB)
   ↓
4. Inicia container:
   └─ Injeta variáveis de ambiente:
      ├─ SERVER_PORT=10000
      ├─ SPRING_DATASOURCE_URL=...
      ├─ JAVA_OPTS=-Xmx512m -Xms256m...
      └─ ... mais 10 variáveis
   ↓
5. Executa Start Command:
   └─ sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"
      ├─ Shell expande ${JAVA_OPTS}
      ├─ Java inicia aplicação
      ├─ Spring Boot carrega
      ├─ Conecta ao banco de dados
      └─ Escuta porta 10000
   ↓
6. Render testa Health Check:
   └─ GET /actuator/health
      ├─ Aplicação responde HTTP 200
      ├─ Status: UP
      └─ Deploy bem-sucedido! ✓
```

## 💡 Por que não deixar vazio?

Porque:
```
❌ Dockerfile tem ENTRYPOINT, mas Render quer explicit
├─ O Start Command sobrescreve o ENTRYPOINT
└─ Render precisa saber o que rodar

✅ Ser explícito ajuda:
├─ CI/CD entender o que está acontecendo
├─ Fácil modificar start command
├─ Compatível com diferentes plataformas
└─ Debugging mais fácil
```

## 🆘 Se receber erro

### "Command not found: sh"
```
❌ ERRADO: sh -c "java..."
✅ CORRETO: java -jar /app/gerenciadoraulas.jar

Ou tentar: /bin/sh -c "java..."
```

### "jar file not found"
```
Verificar:
1. Dockerfile copia JAR para /app/?
   └─ COPY --from=builder /app/target/gerenciadoraulas-*.jar /app/gerenciadoraulas.jar

2. Nome do JAR é "gerenciadoraulas.jar"?
   └─ Verificar artifact name no pom.xml
```

### "Address already in use"
```
Significa que porta 10000 está em uso.
Mas isso é raro no Render.

Mais provável: Aplicação travou antes.
Solução:
1. Verificar logs para exceção
2. Aumentar START timeout
3. Redeploy
```

### "Health check failed"
```
Aplicação iniciou mas não respondeu a health check.
Solução:
1. Aumentar timeout em Settings (60s → 120s)
2. Verificar se /actuator/health está habilitado
3. Aumentar JAVA_OPTS memory
4. Redeploy
```

## ✅ Checklist

- [ ] Build Command = `docker build -t app .`
- [ ] Start Command = `sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"`
- [ ] JAR file é gerado pelo Maven (target/)
- [ ] Dockerfile copia JAR para /app/
- [ ] JAVA_OPTS variável setada (opcional, tem default)
- [ ] Porta está configurada como 10000 (ou via SERVER_PORT)
- [ ] Health check endpoint em /actuator/health

---

## 📚 Comparação Completa

| Aspecto | Build Command | Start Command |
|---------|---------------|---------------|
| **Campo** | Obrigatório | Obrigatório |
| **Renders** | Uma vez por deploy | Cada vez que container inicia |
| **Duração** | ~10 min (primeira) | ~30s |
| **Nosso valor** | `docker build -t app .` | `sh -c "java ${JAVA_OPTS} -jar /app/gerenciadoraulas.jar"` |
| **Função** | Criar imagem Docker | Iniciar aplicação Java |

---

**Pronto! Agora você tem ambos os comandos!** 🚀
