package fr.fms.web;

import fr.fms.dao.TaskStatusRepository;
import fr.fms.entities.TaskStatus;
import fr.fms.exceptions.ManageErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class TaskStatusController {
    @Autowired
    TaskStatusRepository taskStatusRepository;

    private final Logger logger = LoggerFactory.getLogger(TaskStatusController.class);

    /**
     * Méthode qui contrôle si l'id du statut existe avant de renvoyer vers l'affichage des tâches  par statuts
     *
     * @param id    l'identifiant du statut
     * @param model le modèle de la vue
     * @return une redirection vers la page principale des tâches filtrée par le statut spécifié
     */
    @GetMapping("/taskStatus")
    public String TasksStatus(Long id, Model model) {
        Long idStatu = (long)-1;
        try {
            Optional<TaskStatus> Statu = taskStatusRepository.findById(id+1);
            if(Statu.isPresent()) {
                TaskStatus taskStatus = Statu.get();
                idStatu = taskStatus.getId();
                model.addAttribute("idStatu" , idStatu);
            }
            else return "redirect:/index?error=" + ManageErrors.STR_ERROR;
        }
        catch(Exception e) {
            logger.error("[TaskStatus CONTROLLER : TaskStatus] : {} " , e.getMessage());
            return "redirect:/index?error=" + e.getMessage();
        }
        return "redirect:/index?idStatu=" + idStatu;
    }
}

