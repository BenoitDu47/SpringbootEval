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
