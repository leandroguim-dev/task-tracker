import java.util.List;

public class Main {
    private static final TaskService service = new TaskService();

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        String command = args[0].toLowerCase();

        try {
            switch (command) {
                case "add":
                    handleAdd(args);
                    break;
                case "update":
                    handleUpdate(args);
                    break;
                case "delete":
                    handleDelete(args);
                    break;
                case "mark-in-progress":
                    handleMarkInProgress(args);
                    break;
                case "mark-done":
                    handleMarkDone(args);
                    break;
                case "list":
                    handleList(args);
                    break;
                default:
                    System.out.println("Comando desconhecido: " + command);
                    printHelp();
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void handleAdd(String[] args) {
        if (args.length < 2 || args[1].trim().isEmpty()) {
            System.out.println("Uso: task-cli add \"<descricao da tarefa>\"");
            return;
        }
        int id = service.addTask(args[1]);
        System.out.println("Task added successfully (ID: " + id + ")");
    }

    private static void handleUpdate(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: task-cli update <id> \"<nova descricao>\"");
            return;
        }
        int id = parseId(args[1]);
        service.updateTask(id, args[2]);
        System.out.println("Task updated successfully (ID: " + id + ")");
    }

    private static void handleDelete(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: task-cli delete <id>");
            return;
        }
        int id = parseId(args[1]);
        service.deleteTask(id);
        System.out.println("Task deleted successfully (ID: " + id + ")");
    }

    private static void handleMarkInProgress(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: task-cli mark-in-progress <id>");
            return;
        }
        int id = parseId(args[1]);
        service.updateStatus(id, Status.IN_PROGRESS);
        System.out.println("Task marked in-progress successfully (ID: " + id + ")");
    }

    private static void handleMarkDone(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: task-cli mark-done <id>");
            return;
        }
        int id = parseId(args[1]);
        service.updateStatus(id, Status.DONE);
        System.out.println("Task marked done successfully (ID: " + id + ")");
    }

    private static void handleList(String[] args) {
        Status statusFilter = null;
        if (args.length >= 2) {
            statusFilter = Status.fromValue(args[1]);
        }

        List<Task> tasks = service.listTasks(statusFilter);
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private static int parseId(String idArg) {
        try {
            return Integer.parseInt(idArg);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O ID deve ser um número inteiro válido: " + idArg);
        }
    }

    private static void printHelp() {
        System.out.println("Uso do Task Tracker CLI:");
        System.out.println("  task-cli add \"<descricao>\"");
        System.out.println("  task-cli update <id> \"<descricao>\"");
        System.out.println("  task-cli delete <id>");
        System.out.println("  task-cli mark-in-progress <id>");
        System.out.println("  task-cli mark-done <id>");
        System.out.println("  task-cli list");
        System.out.println("  task-cli list [todo|in-progress|done]");
    }
}