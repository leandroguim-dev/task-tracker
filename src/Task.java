import java.time.LocalDateTime;

public class Task {
    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor 1: Usado ao criar uma NOVA tarefa pelo comando "add"
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // Construtor 2: Usado ao carregar uma tarefa que já estava salva no arquivo JSON
    public Task(int id, String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        // TODO 1: Atribua os 5 parâmetros aos atributos da classe (ex: this.id = id;)
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // TODO 2: Gere os 5 métodos Getters aqui (no IntelliJ: Alt + Insert -> Getter -> selecione todos)


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters que atualizam também a data de modificação (updatedAt):
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(Status status) {
        // TODO 3: Atribua o novo status e atualize o this.updatedAt com LocalDateTime.now()
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}

