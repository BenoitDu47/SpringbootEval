package fr.fms.business;

import fr.fms.entities.Category;
import fr.fms.entities.Task;
import fr.fms.entities.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBusiness {

    public List<Task> getTasks() throws Exception;

    public Page<Task> getTasksPages(String kw, int page) throws Exception;

    Page<Task> getTasksByCategoriePage(Long idCat, int page) throws Exception;

    public void saveTask(Task task) throws Exception;

    Task getTaskById(Long id) throws Exception;

    public void deleteTask(Long id) throws Exception;

    public List<Category> getCatogries() throws Exception;

    public List<TaskStatus> getTaskStatus() throws Exception;

    void saveTask(Category category, TaskStatus taskStatus) throws Exception;

}
