# dog-api-tests-ntconsult

Automação de testes de API usando **Java 21**, **REST Assured**, **JUnit 5** e **Allure**.

---

## 1. Pré-requisitos

- **Java 21+** instalado e configurado no `PATH`  
- **Maven CLI ≥ 3.9** instalado  
- Internet para acessar a API pública [Dog CEO](https://dog.ceo)  

---

## 2. Como executar

1. **Clone o repositório**  
   ```bash
   git clone https://github.com/NTConsult/dog-api-tests-ntconsult.git
   cd dog-api-tests-ntconsult
   ```

2. **Rode os testes** e gere os arquivos de resultado  
   ```bash
   mvn clean test
   ```

3. **Abra o relatório interativo**  
   ```bash
   mvn allure:serve
   ```
   O dashboard Allure ficará disponível em `http://localhost:…` até você interromper com `Ctrl+C`.

---

## 3. Estrutura de pastas

```
dog-api-tests-ntconsult/
├─ pom.xml
├─ README.md
└─ src
   ├─ main/java           ← vazio (projeto só de testes)
   └─ test
      ├─ java
      │   └─ br/com/ntconsult/api/tests
      │       ├─ core         ← BaseTest
      │       └─ scenarios    ← BreedsListTest, BreedImagesTest, RandomImageTest
      └─ resources
          └─ allure-results   ←
              ├─ environment.properties
              ├─ categories.json
```

---

## 4. Personalização do relatório Allure

### Environment  
Definido em `src/test/resources/allure-results/environment.properties`:

```properties
API Base URI=https://dog.ceo/api
Tested By=Davi Rodrigues
Environment=QA
Build Version=1.0-SNAPSHOT
```

### Categories  
Definido em `src/test/resources/allure-results/categories.json`:

```jsonc
[
  {
    "name": "Client Errors",
    "match": [
      { "status": "failed", "message": ".*404.*" },
      { "status": "failed", "message": ".*400.*" }
    ]
  },
  {
    "name": "Server Errors",
    "match": [
      { "status": "failed", "message": ".*500.*" }
    ]
  }
]
```

### Executors  
Definido em `src/test/resources/allure-results/executor.json` *ou* gerado pelo plugin:

```json
{
  "name": "NTConsult CI",
  "type": "maven",
  "url": "https://github.com/NTConsult/dog-api-tests-ntconsult/actions",
  "buildName": "1.0-SNAPSHOT",
  "reportUrl": "http://allure-reports.ntconsult.local/dog-api-tests"
}
```

### Trend (histórico)  
Para habilitar a aba **Trend**, configure no POM:
```xml
<historyDirectory>${project.build.directory}/allure-history</historyDirectory>
```
e mantenha `allure-history` entre execuções (por exemplo, como artefato no CI).

---

## 5. Integração Contínua (GitHub Actions)

Crie o arquivo `.github/workflows/ci.yml` com:

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
          restore-keys: |
            ${{ runner.os }}-m2-

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

Em cada push ou pull request seus testes serão executados, o relatório será gerado e armazenado como artefato.

---

> **Dica:** mantenha sempre seus arquivos em UTF-8 e utilize _Unicode escapes_ em `.properties` para caracteres especiais, se necessário.
