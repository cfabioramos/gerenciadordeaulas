#!/bin/bash
# Script para gerenciar secrets de forma segura

set -e

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Função para imprimir com cores
print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

# Função para ler input de forma segura (sem echo)
read_secret() {
    local prompt="$1"
    local secret
    read -sp "$(echo -e ${BLUE}$prompt${NC}): " secret
    echo ""
    echo "$secret"
}

# Menu principal
show_menu() {
    echo ""
    echo -e "${BLUE}=== Gerenciador de Secrets - Gerenciador de Aulas ===${NC}"
    echo "1. Criar secrets para Docker Swarm"
    echo "2. Listar secrets existentes"
    echo "3. Remover secrets"
    echo "4. Gerar arquivo .env para desenvolvimento"
    echo "5. Validar conexão com banco"
    echo "6. Sair"
    echo ""
}

# Criar secrets para Docker Swarm
create_swarm_secrets() {
    print_warning "Esta operação é para Docker Swarm (docker swarm init requerido)"
    echo ""
    
    # Verificar se Docker Swarm está inicializado
    if ! docker info | grep -q "Swarm: active"; then
        print_error "Docker Swarm não está ativo. Inicialize com: docker swarm init"
        return 1
    fi
    
    print_info "Criando secrets para Docker Swarm..."
    echo ""
    
    # Nome do banco
    read -p "Nome do banco de dados [gerenciadordeaulas]: " db_name
    db_name=${db_name:-gerenciadordeaulas}
    
    # Usuário
    read -p "Usuário do banco [postgres]: " db_user
    db_user=${db_user:-postgres}
    
    # Senha
    db_password=$(read_secret "Senha do banco de dados")
    
    if [ -z "$db_password" ]; then
        print_error "Senha não pode estar vazia"
        return 1
    fi
    
    # Remover secrets antigos se existirem
    for secret in gerenciadordeaulas_db_name gerenciadordeaulas_db_user gerenciadordeaulas_db_password; do
        if docker secret ls | grep -q "$secret"; then
            print_warning "Removendo secret antigo: $secret"
            docker secret rm "$secret" 2>/dev/null || true
        fi
    done
    
    # Criar novos secrets
    echo "$db_name" | docker secret create gerenciadordeaulas_db_name -
    print_success "Secret 'gerenciadordeaulas_db_name' criado"
    
    echo "$db_user" | docker secret create gerenciadordeaulas_db_user -
    print_success "Secret 'gerenciadordeaulas_db_user' criado"
    
    echo "$db_password" | docker secret create gerenciadordeaulas_db_password -
    print_success "Secret 'gerenciadordeaulas_db_password' criado"
    
    echo ""
    print_success "Secrets criados com sucesso!"
    echo ""
    print_info "Próximos passos:"
    echo "  1. Fazer deploy com: docker stack deploy -c docker-compose.swarm.yml gerenciadordeaulas"
    echo "  2. Verificar com: docker secret ls"
}

# Listar secrets
list_secrets() {
    print_info "Secrets existentes:"
    echo ""
    docker secret ls
}

# Remover secrets
remove_secrets() {
    print_warning "Isso removerá todos os secrets. Continuar? (s/n)"
    read -r confirm
    
    if [ "$confirm" != "s" ]; then
        print_info "Operação cancelada"
        return 0
    fi
    
    for secret in gerenciadordeaulas_db_name gerenciadordeaulas_db_user gerenciadordeaulas_db_password; do
        if docker secret ls | grep -q "$secret"; then
            docker secret rm "$secret"
            print_success "Secret removido: $secret"
        fi
    done
    
    print_success "Todos os secrets foram removidos"
}

# Gerar .env para desenvolvimento
generate_env() {
    print_info "Gerando arquivo .env para desenvolvimento..."
    echo ""
    
    read -p "Nome do banco [gerenciadordeaulas]: " db_name
    db_name=${db_name:-gerenciadordeaulas}
    
    read -p "Usuário do banco [postgres]: " db_user
    db_user=${db_user:-postgres}
    
    db_password=$(read_secret "Senha do banco de dados")
    
    read -p "Porta da aplicação [8080]: " app_port
    app_port=${app_port:-8080}
    
    read -p "Porta do banco [5432]: " db_port
    db_port=${db_port:-5432}
    
    # Gerar .env
    cat > .env << EOF
# Gerenciador de Aulas - Variáveis de Ambiente

# Servidor
APP_PORT=$app_port

# Banco de Dados
DB_NAME=$db_name
DB_USER=$db_user
DB_PASSWORD=$db_password
DB_PORT=$db_port

# JPA / Hibernate
JPA_DDL_AUTO=validate
JPA_SHOW_SQL=false
JPA_FORMAT_SQL=false

# pgAdmin
PGADMIN_EMAIL=admin@admin.com
PGADMIN_PASSWORD=admin
PGADMIN_PORT=5050
EOF
    
    print_success ".env gerado com sucesso!"
    print_warning "IMPORTANTE: Este arquivo contém credenciais. NÃO faça commit no git!"
    print_info "O arquivo está configurado para ser ignorado pelo git (.gitignore)"
}

# Validar conexão
validate_connection() {
    print_info "Validando conexão com banco de dados..."
    echo ""
    
    read -p "Host do banco [localhost]: " db_host
    db_host=${db_host:-localhost}
    
    read -p "Porta do banco [5432]: " db_port
    db_port=${db_port:-5432}
    
    read -p "Usuário [postgres]: " db_user
    db_user=${db_user:-postgres}
    
    db_password=$(read_secret "Senha")
    
    read -p "Banco de dados [gerenciadordeaulas]: " db_name
    db_name=${db_name:-gerenciadordeaulas}
    
    print_info "Testando conexão..."
    
    # Usar Docker para testar (se PostgreSQL não estiver instalado localmente)
    if command -v psql &> /dev/null; then
        PGPASSWORD="$db_password" psql -h "$db_host" -U "$db_user" -d "$db_name" -p "$db_port" -c "SELECT 1" && \
            print_success "Conexão bem-sucedida!" || \
            print_error "Falha na conexão"
    else
        print_warning "psql não encontrado. Usando docker..."
        docker run --rm \
            -e PGPASSWORD="$db_password" \
            postgres:16-alpine \
            psql -h "$db_host" -U "$db_user" -d "$db_name" -p "$db_port" -c "SELECT 1" && \
            print_success "Conexão bem-sucedida!" || \
            print_error "Falha na conexão"
    fi
}

# Main loop
while true; do
    show_menu
    read -p "Escolha uma opção [1-6]: " choice
    
    case $choice in
        1) create_swarm_secrets ;;
        2) list_secrets ;;
        3) remove_secrets ;;
        4) generate_env ;;
        5) validate_connection ;;
        6) 
            print_info "Saindo..."
            exit 0
            ;;
        *) print_error "Opção inválida" ;;
    esac
done
