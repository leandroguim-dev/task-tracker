import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static final java.time.format.DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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

    // --- MÉTODOS DE JSON E EXIBIÇÃO ---
    public String toJson() {
        String safeDescription = description
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");

        return String.format(
                "  {\n" +
                        "    \"id\": %d,\n" +
                        "    \"description\": \"%s\",\n" +
                        "    \"status\": \"%s\",\n" +
                        "    \"createdAt\": \"%s\",\n" +
                        "    \"updatedAt\": \"%s\"\n" +
                        "  }",
                id,
                safeDescription,
                status.getValue(),
                createdAt.format(FORMATTER),
                updatedAt.format(FORMATTER)
        );
    }

    public static Task fromJson(String json) {
        int id = Integer.parseInt(extractValue(json, "id"));
        String desc = extractValue(json, "description")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
        Status status = Status.fromValue(extractValue(json, "status"));
        LocalDateTime createdAt = LocalDateTime.parse(extractValue(json, "createdAt"), FORMATTER);
        LocalDateTime updatedAt = LocalDateTime.parse(extractValue(json, "updatedAt"), FORMATTER);

        return new Task(id, desc, status, createdAt, updatedAt);
    }

    private static String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex == -1) {
            throw new IllegalArgumentException("Campo ausente no JSON: " + key);
        }

        int valueStart = keyIndex + searchKey.length();
        while (valueStart < json.length() && Character.isWhitespace(json.charAt(valueStart))) {
            valueStart++;
        }

        if (json.charAt(valueStart) == '"') {
            valueStart++;
            StringBuilder sb = new StringBuilder();
            for (int i = valueStart; i < json.length(); i++) {
                char c = json.charAt(i);
                if (c == '\\' && i + 1 < json.length()) {
                    sb.append(c).append(json.charAt(i + 1));
                    i++;
                } else if (c == '"') {
                    break;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            int valueEnd = valueStart;
            while (valueEnd < json.length()
                    && json.charAt(valueEnd) != ','
                    && json.charAt(valueEnd) != '}'
                    && !Character.isWhitespace(json.charAt(valueEnd))) {
                valueEnd++;
            }
            return json.substring(valueStart, valueEnd).trim();
        }
    }

    @Override
    public String toString() {
        return String.format("[%d] %-12s | %s (Criada: %s)",
                id,
                "(" + status.getValue() + ")",
                description,
                createdAt.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
}

