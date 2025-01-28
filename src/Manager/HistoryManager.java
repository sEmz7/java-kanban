package Manager;

import Tasks.Task;

import java.util.List;

public interface HistoryManager<T> {

    void addTask(Task task);

    List<Task> getHistory();
}
