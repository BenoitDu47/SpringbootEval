package fr.fms.dao;

import fr.fms.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findAll();

    Page<Task> findByDescriptionContains(String description);

    Page<Task> findByCategoryIdAndDescriptionContains(Long categoryId, String kw, PageRequest of);

    void deleteById(Long id);

    public List<Task> findByTitleContains(String brand);
}
