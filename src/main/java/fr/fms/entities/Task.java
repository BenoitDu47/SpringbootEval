package fr.fms.entities;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Date dueDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private TaskStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
