import api.HttpTaskServer;
import manager.Managers;
import manager.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefaultTaskManager();
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.start();
    }
}