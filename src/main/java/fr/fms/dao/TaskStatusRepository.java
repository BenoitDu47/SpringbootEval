package fr.fms.dao;

import fr.fms.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepository  extends JpaRepository<TaskStatus,Long> {
}
