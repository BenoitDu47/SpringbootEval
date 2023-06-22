package fr.fms;

import fr.fms.dao.CategoryRepository;
import fr.fms.dao.TaskRepository;
import fr.fms.dao.TaskStatusRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Task;
import fr.fms.entities.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringEvalApplicationTests {
	@Autowired
	TaskRepository taskRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	TaskStatusRepository taskStatusRepository;

	/**
	 * Teste l'ajout d'une tâche et la recherche par titre.
	 */
	@Test
	void test_add_task() {
		// GIVEN
		// Crée une catégorie "anonymous" et la sauvegarde dans la base de données
		Category anonymous = categoryRepository.save(new Category(null, "anonymous", null));
		// Crée un statut "done" pour la tâche et le sauvegarde dans la base de données
		TaskStatus done = taskStatusRepository.save(new TaskStatus(null, "done", null));
		// Crée une nouvelle tâche avec les détails fournis et la sauvegarde dans la base de données
		Date toDay = new Date();
		taskRepository.save(new Task(null, "incognito", "incognito007", toDay, anonymous, done));

		// WHEN
		// Recherche la tâche qui contient le titre "incognito007"
		Task task = taskRepository.findByTitleContains("incognito007").get(0);

		// THEN
		// Vérifie que la description de la tâche correspond à "incognito007"
		assertEquals("incognito007", task.getDescription());
	}

	/**
	 * Teste la recherche de toutes les tâches.
	 */
	@Test
	void should_find_one_article() {
		// GIVEN
		// Crée une catégorie "toto" et la sauvegarde dans la base de données
		Category toto = categoryRepository.save(new Category(null, "toto", null));
		// Crée un statut "tutu" pour la tâche et le sauvegarde dans la base de données
		TaskStatus done = taskStatusRepository.save(new TaskStatus(null, "tutu", null));
		// Crée une nouvelle tâche avec les détails fournis et la sauvegarde dans la base de données
		Date toDay = new Date();
		taskRepository.save(new Task(null, "totoBello", "toto Bello", toDay, toto, done));

		// WHEN
		// Récupère toutes les tâches de la base de données
		Iterable<Task> tasks = taskRepository.findAll();

		// THEN
		// Vérifie que la liste de tâches n'est pas vide
		assertThat(tasks).isNotEmpty();
	}
}
