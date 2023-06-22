package fr.fms.web;

import fr.fms.business.IBusinessImpl;
import fr.fms.dao.CategoryRepository;
import fr.fms.dao.TaskRepository;
import fr.fms.dao.TaskStatusRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Task;
import fr.fms.entities.TaskStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TaskStatusRepository taskStatusRepository;

    @Autowired
    IBusinessImpl businessImpl;

    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    /**
     * Méthode en GET correspondant à l'url .../index ou page d'accueil de notre application
     *
     * @param model         le modèle de la vue
     * @param page          le numéro de page
     * @param kw            le mot-clé de recherche
     * @param categoryId    l'identifiant de la catégorie (optionnel)
     * @param idCat         l'identifiant de la catégorie (par défaut : 0)
     * @param error         le message d'erreur à afficher (facultatif)
     * @return la vue "tasks" pour afficher la page principale des tâches
     */
    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "keyword", defaultValue = "") String kw,
                        @RequestParam(name = "categoryId", required = false) Long categoryId,
                        @RequestParam(name = "idCat", defaultValue = "0") Long idCat,
                        @ModelAttribute(name = "error") String error) {
        try {
            Page<Task> tasks;
            model.addAttribute("error", model.getAttribute("error"));

            if (categoryId != null) {
                tasks = taskRepository.findByCategoryIdAndDescriptionContains(categoryId, kw, PageRequest.of(page, 6));
            } else {
                tasks = taskRepository.findByDescriptionContains(kw);
            }

            model.addAttribute("listTask", tasks.getContent());
            model.addAttribute("pages", new int[tasks.getTotalPages()]);
            model.addAttribute("currentPage", page);
            model.addAttribute("keyword", kw);

            List<TaskStatus> taskStatus = taskStatusRepository.findAll();
            model.addAttribute("taskStatus", taskStatus);

            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("selectedCategoryId", categoryId);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            logger.error("[TACHES CONTROLLER : INDEX]: {}", e.getMessage());
        }

        return "tasks";
    }

    /**
     * Méthode en GET correspondant à l'url .../edit permettant d'afficher une tâche en vue de sa mise à jour
     *
     * @param model le modèle de la vue
     * @param id    l'identifiant de la tâche
     * @return la vue "task" pour afficher le formulaire d'édition d'une tâche
     */
    @GetMapping("/edit")
    public String editTask(Model model, @RequestParam("id") Long id) {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<TaskStatus> taskStatus = taskStatusRepository.findAll();
            model.addAttribute("categories", categories);
            model.addAttribute("taskStatus", taskStatus);
            Optional<Task> task = taskRepository.findById(id);
            if (task.isPresent()) {
                model.addAttribute("task", task.get());
            } else {
                model.addAttribute("error", "La tâche spécifiée n'existe pas");
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            logger.error("[TASK CONTROLLER : EDIT] : {} ", e.getMessage());
        }
        return "task";
    }

    /**
     * Méthode en GET correspondant à l'url .../delete consistant à supprimer une tâches à partir de son id
     *
     * @param id             l'identifiant de la tâche à supprimer
     * @param page           le numéro de page
     * @param keyword        le mot-clé de recherche
     * @param redirectAttrs  les attributs de redirection
     * @return une redirection vers la page principale des tâches
     */
    @GetMapping("/delete")
    public String delete(Long id, int page, String keyword , RedirectAttributes redirectAttrs) {
        try {
            businessImpl.deleteTask(id);
        } catch (Exception e) {
            redirectAttrs.addAttribute("error",e.getMessage());
            logger.error("[ARTICLE CONTROLLER : DELETE] : {} " , e.getMessage());
        }
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    /**
     * Méthode en POST correspondant à l'url .../save visant à sauvegarder ou mettre à jour une tâche
     *
     * @param task           la tâche à sauvegarder
     * @param bindingResult  les résultats de la validation
     * @param model          le modèle de la vue
     * @param redirectAttrs  les attributs de redirection
     * @return une redirection vers la page principale des tâches en cas de succès, sinon la vue "task"
     */
    @PostMapping("/save")
    public String save(@Valid Task task, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
        try {
            if (bindingResult.hasErrors()) {
                List<Category> categories = categoryRepository.findAll();
                List<TaskStatus> taskStatus = taskStatusRepository.findAll();
                model.addAttribute("categories", categories);
                model.addAttribute("taskStatus", taskStatus);
                return "task";
            } else {
                task.setDueDate(new Date());
                businessImpl.saveTask(task); // Appel à la méthode saveTask de votre interface IBusiness
                return "redirect:/index";
            }
        } catch (Exception e) {
            redirectAttrs.addAttribute("error", e.getMessage());
            logger.error("[TASK CONTROLLER : SAVE TASK] : {} ", e.getMessage());
            return "redirect:/index";
        }
    }

    /**
     * 	 * Méthode en GET correspondant à l'url .../task permettant d'ajouter une nouvelle tâche
     *
     * @param model le modèle de la vue
     * @return la vue "task" pour afficher le formulaire de création d'une tâche
     */
    @GetMapping("/task")
    public String task(Model model) {
        List<Category> categories = categoryRepository.findAll();
        List<TaskStatus> taskStatus = taskStatusRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("taskStatus", taskStatus);
        model.addAttribute("task", new Task());
        return "task";
    }

}
