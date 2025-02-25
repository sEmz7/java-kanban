package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class Managers {

    private Managers() {

    }

    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getDefaultFileBackedTaskManager() {
        return new FileBackedTaskManager();
    }
}
