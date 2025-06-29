# dog-api-tests-ntconsult

Automação de testes de API usando Java 21, REST Assured, JUnit 5 e Allure.

---

## 1. Pré-requisitos

- **Java 21+** instalado e no `PATH`  
- **Maven 3.9+** instalado  
- Acesso à internet (API pública [Dog CEO](https://dog.ceo))

---

## 2. Executar os testes

```bash
mvn clean test
```

- Os resultados brutos ficam em `target/surefire-reports`  
- Os arquivos Allure (JSON, attachments etc.) em `src/test/resources/allure-results`

---

## 3. Gerar e visualizar o relatório Allure

```bash
mvn allure:serve
```

- Inicia um servidor local  
- Abra o link mostrado no console  
- Pare com `Ctrl+C`

---

## 4. Estrutura do projeto

```
dog-api-tests-ntconsult/
├─ .github/workflows/ci.yml
├─ src/
│  └─ test/
│     ├─ java/
│     │  └─ br/
│     │     └─ com/
│     │        └─ ntconsult/
│     │           ├─ core/
│     │           │   └─ BaseTest.java
│     │           └─ api/
│     │              └─ tests/
│     │                 └─ scenarios/
│     │                    ├─ BreedsListTest.java
│     │                    ├─ BreedImagesTest.java
│     │                    └─ RandomImageTest.java
│     └─ resources/
│        └─ allure-results/
│           ├─ environment.properties
│           ├─ categories.json
│           └─ logback-test.xml
├─ .gitignore
├─ pom.xml
└─ README.md
```

---

## 5. Configuração do relatório Allure

### 5.1 Environment

`src/test/resources/allure-results/environment.properties`
```properties
API Base URI=https://dog.ceo/api
Tested By=Davi Rodrigues
Environment=QA
Build Version=1.0-SNAPSHOT
```

### 5.2 Categories

`src/test/resources/allure-results/categories.json`
```json
[
  { "name": "❌ Failed Tests",   "matchedStatuses": ["failed"] },
  { "name": "⚠️ Broken Tests",   "matchedStatuses": ["broken"] },
  { "name": "✅ Passed Tests",   "matchedStatuses": ["passed"] },
  { "name": "⏭️ Skipped Tests",  "matchedStatuses": ["skipped"] },
  { "name": "❔ Unknown Tests",  "matchedStatuses": ["unknown"] }
]
```

### 5.3 Executor

No `pom.xml`, sob o plugin **allure-maven**, há:
```xml
<configuration>
  <executor>
    <name>Davi Rodrigues</name>
    <type>maven</type>
  </executor>
</configuration>
```

---

## 6. Integração Contínua (GitHub Actions)

Arquivo `.github/workflows/ci.yml`:

```yaml
name: CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Setup Java 21
        uses: actions/setup-java@v3
        with:
          distribution: temurin
          java-version: '21'

      - name: Cache Maven
        uses: actions/cache@v3
        with:
          path: ~/.m2/repository
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}

      - name: Run tests
        run: mvn clean test

      - name: Generate Allure report
        run: mvn verify

      - name: Upload Allure report
        uses: actions/upload-artifact@v3
        with:
          name: allure-report
          path: target/site/allure-maven-plugin
```
