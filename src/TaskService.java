import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository repository;

    public TaskService() {
        this.repository = new TaskRepository();
    }

    // Adiciona uma nova tarefa e retorna o ID gerado
    public int addTask(String description) {
        List<Task> tasks = repository.findAll();

        // Gera o próximo ID: pega o maior ID atual e soma 1 (se a lista estiver vazia, começa em 1)
        int nextId = tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;

        Task newTask = new Task(nextId, description);
        tasks.add(newTask);
        repository.saveAll(tasks);

        return nextId;
    }

    // Atualiza a descrição de uma tarefa existente
    public void updateTask(int id, String newDescription) {
        List<Task> tasks = repository.findAll();
        Task task = findById(tasks, id);

        task.setDescription(newDescription);
        repository.saveAll(tasks);
    }

    // Remove uma tarefa pelo ID
    public void deleteTask(int id) {
        List<Task> tasks = repository.findAll();
        Task task = findById(tasks, id);

        tasks.remove(task);
        repository.saveAll(tasks);
    }

    // Altera o status da tarefa para um novo status (in-progress ou done)
    public void updateStatus(int id, Status newStatus) {
        List<Task> tasks = repository.findAll();
        Task task = findById(tasks, id);

        task.setStatus(newStatus);
        repository.saveAll(tasks);
    }

    // Lista todas as tarefas ou filtra por status se um status for informado
    public List<Task> listTasks(Status statusFilter) {
        List<Task> tasks = repository.findAll();

        if (statusFilter == null) {
            return tasks;
        }

        return tasks.stream()
                .filter(task -> task.getStatus() == statusFilter)
                .collect(Collectors.toList());
    }

    // Método auxiliar para buscar uma tarefa na lista ou disparar erro caso não encontre
    private Task findById(List<Task> tasks, int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tarefa com ID " + id + " não encontrada."));
    }
}