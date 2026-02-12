# Makefile para projeto MindCash com Gradle

.PHONY: build test clean report run all help

# Diretório do projeto
PROJECT_DIR = $(CURDIR)
GRADLEW = $(PROJECT_DIR)/gradlew.bat

# Diretório do relatório JaCoCo
JACOCO_REPORT = $(PROJECT_DIR)/financial-account/build/reports/jacoco/html/index.html

help:
	@echo "Comandos disponiveis:"
	@echo "  make build   - Compila o projeto"
	@echo "  make test    - Roda os testes e gera relatorio de cobertura"
	@echo "  make clean   - Limpa os arquivos de build"
	@echo "  make report  - Abre o relatorio de cobertura no navegador"
	@echo "  make run     - Executa a aplicacao"
	@echo "  make all     - Limpa, compila e testa"

build:
	@echo "Compilando o projeto..."
	$(GRADLEW) build -x test

test:
	@echo "Executando testes e gerando relatorio de cobertura..."
	$(GRADLEW) :financial-account:test :financial-account:jacocoTestReport
	@echo ""
	@echo "============================================="
	@echo "Relatorio de cobertura gerado em:"
	@echo "$(JACOCO_REPORT)"
	@echo "============================================="

clean:
	@echo "Limpando projeto..."
	$(GRADLEW) clean

report:
	@echo "Abrindo relatorio de cobertura..."
	@start "" "$(JACOCO_REPORT)"

run:
	@echo "Iniciando aplicacao..."
	$(GRADLEW) :financial-account:bootRun

all: clean build test
	@echo "Build completo!"

