# 🔧 Render Build Command - Referência Rápida

## ✅ Comando Correto

Para o campo obrigatório **"Build Command"** do Render:

```
docker build -t app .
```

## 📝 Campos do Render

| Campo | Valor | Notas |
|-------|-------|-------|
| **Environment** | `Docker` | Tipo de ambiente |
| **Build Command** | `docker build -t app .` | ⚠️ OBRIGATÓRIO |
| **Start Command** | (deixar vazio) | Usa ENTRYPOINT do Dockerfile |
| **Root Directory** | (deixar vazio) | Usa raiz do repo |
| **Dockerfile Path** | (deixar vazio) | Procura por Dockerfile na raiz |

## 🔍 Explicação

```
docker build -t app .
│       │      │   │
│       │      │   └─ Usa Dockerfile da pasta atual
│       │      └───── Nome da imagem: "app"
│       └──────────── Comando de build
└───────────────────── Docker CLI
```

O Render vai:
1. Executar este comando na raiz do repositório
2. Procurar pelo `Dockerfile`
3. Seguir os passos do Dockerfile (nossa config multi-stage)
4. Gerar imagem chamada `app`
5. Usar `ENTRYPOINT` da imagem para iniciar a aplicação

## ⚠️ Importante

```
❌ ERRADO:
├─ Deixar Build Command vazio
├─ Usar "mvn clean package"
├─ Usar "npm run build"
└─ Usar "./mvnw clean package"

✅ CORRETO:
└─ docker build -t app .
```

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
│ Start Command:          (vazio)                     │
│ Root Directory:         (vazio)                     │
│ Dockerfile Path:        (vazio - procura na raiz)  │
│ Region:                 São Paulo (sa)              │
│ Plan:                   Free                        │
│ Auto-deploy:            ✓ ON                        │
└─────────────────────────────────────────────────────┘
```

## 🚀 O que acontece depois

1. **Você faz:** `git push origin main`
2. **GitHub avisa:** Render via webhook
3. **Render clona:** Seu repositório
4. **Render executa:** `docker build -t app .`
   - Baixa base image (maven + Java 21)
   - Copia pom.xml e .mvn
   - Executa Maven (compile + package)
   - Copia JAR para nova imagem (JRE only)
   - Build completo
5. **Render inicia:** Container com a imagem
   - Expõe porta 10000
   - Injeta variáveis de ambiente
   - Executa ENTRYPOINT (java -jar)
6. **Health check:** Testa /actuator/health
7. **Status:** Available ✓

## 💡 Por que `docker build -t app .`?

```
Porque:
✓ Render detecta Dockerfile automaticamente
✓ Multi-stage build otimiza tamanho final
✓ Sem credenciais expostas
✓ Reproduzível (mesmo resultado sempre)
✓ Compatível com CI/CD
✓ Escalável para Kubernetes depois
```

## 🆘 Se receber erro

### "Build failed"
```
Verificar:
1. Dockerfile existe no repositório?
   └─ git ls-files | grep Dockerfile

2. Dockerfile é válido?
   └─ docker build -t test . (testar localmente)

3. .gitignore não ignora Dockerfile?
   └─ cat .gitignore | grep -v Dockerfile
```

### "Unknown docker flag"
```
Pode ser que Render prefira:
├─ Deixar Build Command vazio
└─ E usar dockerfile path padrão

Ou tentar:
├─ ./mvnw clean package -DskipTests
└─ (para Maven direto)
```

### "Image build timeout"
```
Primeira build pode levar 10 minutos:
├─ Maven baixa dependências (~500MB)
├─ Docker constrói imagem
└─ Paciência! Próximas builds são rápidas

Dica: Usar .dockerignore para reduzir
```

## 📊 Comparação: Diferentes abordagens

| Abordagem | Build Command | Pro | Contra |
|-----------|---------------|-----|--------|
| **Docker** | `docker build -t app .` | Multi-stage, otimizado | 10 min primeira build |
| **Maven direto** | `./mvnw clean package` | Mais rápido | JDK no container final |
| **Buildpack** | (vazio) | Renderiza automaticamente | Menos controle |

**Recomendação:** Use Docker (primeira opção)

## ✅ Checklist

- [ ] Build Command = `docker build -t app .`
- [ ] Start Command = (vazio)
- [ ] Dockerfile existe e é válido
- [ ] .dockerignore otimiza o build
- [ ] Dockerfile testado localmente
- [ ] Git push feito para main
- [ ] Todas variáveis de ambiente setadas

---

**Pronto! Pode fazer deploy agora!** 🚀
