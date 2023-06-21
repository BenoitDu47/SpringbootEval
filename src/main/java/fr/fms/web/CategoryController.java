package fr.fms.web;

import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Category;
import fr.fms.exceptions.ManageErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepository;
	
	private final Logger logger = LoggerFactory.getLogger(CategoryController.class);


	/**
	 * Méthode qui contrôle si l'id de la catégorie existe avant de renvoyer vers l'affichage des tâches par catégorie
	 *
	 * @param id    l'identifiant de la catégorie
	 * @param model le modèle de la vue
	 * @return une redirection vers la page principale des tâches filtrée par la catégorie spécifiée
	 */
	@GetMapping("/category")
	public String categories(Long id, Model model) {
		Long idCat = (long)-1;
		try {
			Optional<Category> cat = categoryRepository.findById(id+1);
			if(cat.isPresent()) {
				Category category = cat.get();
				idCat = category.getId();
				model.addAttribute("idCat" , idCat);
			}
			else return "redirect:/index?error=" + ManageErrors.STR_ERROR;
		}
		catch(Exception e) {
			logger.error("[CATEGORY CONTROLLER : CATEGORY] : {} " , e.getMessage());
			return "redirect:/index?error=" + e.getMessage();
		}		
		return "redirect:/index?idCat=" + idCat;
	}
}
