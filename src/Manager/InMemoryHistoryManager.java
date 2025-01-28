package Manager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> historyStorage = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        if(Objects.isNull(task)) return;

        historyStorage.add(task.getSnapshot());

        if(historyStorage.size() > 10) {
            historyStorage.removeFirst();
        }

    }

    @Override
    public List<Task> getHistory() {
        return historyStorage;
    }
}
