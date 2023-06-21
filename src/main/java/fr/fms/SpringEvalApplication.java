package fr.fms;

import fr.fms.dao.CategoryRepository;
import fr.fms.dao.TaskRepository;
import fr.fms.dao.TaskStatusRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Task;
import fr.fms.entities.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class SpringEvalApplication implements CommandLineRunner {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	TaskStatusRepository taskStatusRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringEvalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//generateDatas();
	}
	private void generateDatas() {

		Category premierePhase = categoryRepository.save(new Category(null,"Première phase",null));
		Category bdd = categoryRepository.save(new Category(null,"BDD",null));
		Category coucheDeService = categoryRepository.save(new Category(null,"couche de service",null));
		Category vue = categoryRepository.save(new Category(null,"vue",null));
		Category securite = categoryRepository.save(new Category(null,"sécurité ",null));
		Category tests = categoryRepository.save(new Category(null,"tests",null));
		Category deploiement = categoryRepository.save(new Category(null,"Déploiement",null));

		TaskStatus toDo = taskStatusRepository.save(new TaskStatus(null,"ToDo",null));
		TaskStatus inProgress = taskStatusRepository.save(new TaskStatus(null," In Progress",null));
		TaskStatus done = taskStatusRepository.save(new TaskStatus(null,"Done",null));

		taskRepository.save(new Task(null,"Installation","Installation de Spring Boot",new Date(),premierePhase,done,null));
		taskRepository.save(new Task(null,"Configuration","Configuration du projet Maven ou Gradle",new Date(),premierePhase,done,null));
		taskRepository.save(new Task(null,"Structure","Création de la structure de base du projet",new Date(),premierePhase,done,null));
		taskRepository.save(new Task(null,"Dependances","Definition des dependances necessaires dans le fichier de configuration du projet",new Date(),premierePhase,done,null));

		taskRepository.save(new Task(null,"Entites","Creation des entites",new Date(),bdd,done,null));
		taskRepository.save(new Task(null,"Repositories","Creation des repositories pour l'acces aux donnees",new Date(),bdd,done,null));
		taskRepository.save(new Task(null,"Entites","Configuration de la source de la base de donnees",new Date(),bdd,inProgress,null));

		taskRepository.save(new Task(null,"Controller","Creation des controller",new Date(),coucheDeService,inProgress,null));
		taskRepository.save(new Task(null,"Business","Creation couche business",new Date(),coucheDeService,inProgress,null));

		taskRepository.save(new Task(null,"HTML","Creation des page html",new Date(),vue,inProgress,null));
		taskRepository.save(new Task(null,"Bootstrap","bootstrap",new Date(),vue,inProgress,null));

		taskRepository.save(new Task(null,"WebSecurityConfigurerAdapter","Installation Security Config avec Role",new Date(),securite,toDo,null));
		taskRepository.save(new Task(null,"403","Creation page 403",new Date(),securite,toDo,null));
		taskRepository.save(new Task(null,"Login","Gestion Login/Logout",new Date(),securite,toDo,null));

		taskRepository.save(new Task(null,"ASSERTJ","ASSERTJ",new Date(), tests,toDo,null));
		taskRepository.save(new Task(null,"ControllerTest","Tester un Controller(MockMvc Mockito)",new Date(), tests,toDo,null));

		taskRepository.save(new Task(null,"Deploiement","Déploiement avec maven",new Date(), deploiement,toDo,null));

		categoryRepository.findAll().forEach(c -> System.out.println(c));
		taskStatusRepository.findAll().forEach(s -> System.out.println(s));
		taskRepository.findAll().forEach(t -> System.out.println(t));
	}
}
