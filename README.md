# Task Tracker CLI

Uma aplicação de linha de comando (CLI) simples para rastrear e gerenciar tarefas, construída em **Java puro (sem bibliotecas externas)** para praticar conceitos de Backend, manipulação do sistema de arquivos (`java.nio.file`), streams e arquitetura em camadas.

Projeto baseado no desafio: [Task Tracker - roadmap.sh](https://roadmap.sh/projects/task-tracker).

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+** (usando OpenJDK)
- **API `java.nio.file`** para leitura/escrita no sistema de arquivos
- **API `java.time`** (`LocalDateTime`, `DateTimeFormatter`) para rastreamento de datas
- **Streams API** para filtragem e buscas
- **Git** para controle de versão com commits semânticos

---

## 📂 Arquitetura do Projeto

O projeto segue a separação de responsabilidades em camadas:

```
task-tracker/
├── src/
│   ├── Main.java             # Ponto de entrada (CLI controller e tratamento de argumentos)
│   ├── TaskService.java      # Camada de regras de negócio (lógica da aplicação)
│   ├── TaskRepository.java   # Camada de persistência (leitura e gravação no tasks.json)
│   ├── Task.java             # Modelo de dados e conversão JSON manual
│   └── Status.java           # Enum com os status possíveis (todo, in-progress, done)
├── task-cli                  # Script executável para rodar a CLI diretamente
└── README.md
```

---

## 🚀 Como Executar

### 1. Compilar o projeto
No diretório raiz do projeto (`task-tracker`):
```bash
mkdir -p bin
javac -d bin src/*.java
chmod +x task-cli
```

### 2. Comandos disponíveis

Adicionar uma nova tarefa:
```bash
./task-cli add "Comprar mantimentos"
# Saída: Task added successfully (ID: 1)
```

Atualizar a descrição de uma tarefa:
```bash
./task-cli update 1 "Comprar mantimentos e preparar jantar"
# Saída: Task updated successfully (ID: 1)
```

Marcar status como em andamento ou concluída:
```bash
./task-cli mark-in-progress 1
./task-cli mark-done 1
```

Excluir uma tarefa:
```bash
./task-cli delete 1
# Saída: Task deleted successfully (ID: 1)
```

Listar tarefas:
```bash
./task-cli list
./task-cli list todo
./task-cli list in-progress
./task-cli list done
```
