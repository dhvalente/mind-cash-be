# Makefile para facilitar execução de testes e exibir link do relatório JaCoCo

# Módulo principal
MODULE=financial-account
# Caminhos absolutos (Windows)
MVNW=$(CURDIR)\$(MODULE)\mvnw.cmd
POM=$(CURDIR)\$(MODULE)\pom.xml
# Caminho do relatório JaCoCo
JACOCO_REPORT=$(MODULE)\target\site\jacoco\index.html
JACOCO_REPORT_ABS=$(CURDIR)\$(JACOCO_REPORT)

.PHONY: test verify clean report report-folders

## test: roda os testes e mostra onde abrir o relatório de cobertura
test:
	"$(MVNW)" -f "$(POM)" -DskipTests=false test
	@echo =============================
	@echo Relatorio de cobertura JaCoCo:
	@echo $(JACOCO_REPORT)
	@echo $(JACOCO_REPORT_ABS)
	@echo =============================

## verify: roda a verificação (inclui regra minima de cobertura)
verify:
	"$(MVNW)" -f "$(POM)" verify
	@echo =============================
	@echo Relatorio de cobertura JaCoCo:
	@echo $(JACOCO_REPORT)
	@echo $(JACOCO_REPORT_ABS)
	@echo =============================

## report: gera testes (se necessário) e abre o relatório no navegador padrão (Windows)
report: test
	cmd /c start "" "$(JACOCO_REPORT_ABS)"

## clean: limpa artefatos de build
clean:
	"$(MVNW)" -f "$(POM)" clean

## report-folders: gera HTML customizado por hierarquia de pastas e abre no navegador
report-folders: test
	javac -cp "$(CURDIR)\$(MODULE)\target\test-classes;$(CURDIR)\$(MODULE)\target\classes" "$(CURDIR)\$(MODULE)\src\test\java\br\com\mindcash\financial\tools\JacocoFolderReport.java"
	java -cp "$(CURDIR)\$(MODULE)\target\test-classes;$(CURDIR)\$(MODULE)\target\classes" br.com.mindcash.financial.tools.JacocoFolderReport
	cmd /c start "" "$(CURDIR)\$(MODULE)\target\site\jacoco\by-folders.html"
