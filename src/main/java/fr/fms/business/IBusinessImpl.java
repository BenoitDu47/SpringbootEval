package fr.fms.business;

import fr.fms.dao.CategoryRepository;
import fr.fms.dao.TaskRepository;
import fr.fms.dao.TaskStatusRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Task;
import fr.fms.entities.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IBusinessImpl implements IBusiness {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TaskStatusRepository taskStatusRepository;


    @Override
    public List<Task> getTasks() throws Exception {
        try {
            // Utilisez le repository approprié pour récupérer les tâches depuis la base de données
            List<Task> tasks = taskRepository.findAll();
            return tasks;
        } catch (Exception e) {
            // Gérez les exceptions ou propagez-les si nécessaire
            throw new Exception("Une erreur s'est produite lors de la récupération des tâches.", e);
        }
    }

    @Override
    public Page<Task> getTasksPages(String kw, int page) throws Exception {
        return null;
    }

    @Override
    public Page<Task> getTasksByCategoriePage(Long idCat, int page) throws Exception {
        return null;
    }

    @Override
    public void saveTask(Task task) throws Exception {

        taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return null;
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Category> getCatogries() throws Exception {
        return null;
    }

    @Override
    public List<TaskStatus> getTaskStatus() throws Exception {
        return null;
    }

    @Override
    public void saveTask(Category category, TaskStatus taskStatus) throws Exception {

    }


}
