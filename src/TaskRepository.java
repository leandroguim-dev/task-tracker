import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private static final Path FILE_PATH = Path.of("tasks.json");

    // Lê todas as tarefas salvas no arquivo tasks.json
    public List<Task> findAll() {
        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }

        try {
            String content = Files.readString(FILE_PATH).trim();
            if (content.isEmpty() || content.equals("[]")) {
                return new ArrayList<>();
            }

            List<Task> tasks = new ArrayList<>();
            // Percorre o texto do JSON procurando blocos delimitados por { e }
            int start = -1;
            int braceCount = 0;

            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                if (c == '{') {
                    if (braceCount == 0) {
                        start = i;
                    }
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0 && start != -1) {
                        String taskJson = content.substring(start, i + 1);
                        tasks.add(Task.fromJson(taskJson));
                        start = -1;
                    }
                }
            }

            return tasks;
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de tarefas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Salva a lista inteira de tarefas dentro do arquivo tasks.json
    public void saveAll(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).toJson());
            if (i < tasks.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]\n");

        try {
            Files.writeString(FILE_PATH, sb.toString());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo de tarefas: " + e.getMessage());
        }
    }
}