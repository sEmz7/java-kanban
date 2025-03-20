package manager;

import manager.file.FileBackedTaskManager;
import manager.memory.InMemoryHistoryManager;
import manager.memory.InMemoryTaskManager;

public final class Managers {

    private Managers() {

    }

    public static InMemoryTaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getDefaultFileBackedTaskManager() {
        return new FileBackedTaskManager();
    }
}
