package ru.javabegin.backend.todo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javabegin.backend.todo.entity.Task;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE " +
            "(:title IS NULL OR :title='' OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND" +
            "(:completed IS NULL OR t.completed=:completed) AND " +
            "(:priorityId IS NULL or t.priority.id=:priorityId) AND " +
            "(:categoryId IS NULL OR t.category.id=:categoryId) AND " +
            "(" +
            "(CAST(:dateFrom AS timestamp) IS NULL OR t.taskDate>=:dateFrom) AND " +
            "(CAST(:dateTo AS timestamp) IS NULL OR t.taskDate<=:dateTo)" +
            ") AND " +
            "(t.user.email=:email)"
    )
    Page<Task> findByParams(@Param("title") String title,
                            @Param("completed") Boolean completed,
                            @Param("priorityId") Long priorityId,
                            @Param("categoryId") Long categoryId,
                            @Param("email") String email,
                            @Param("dateFrom") Date dateFrom,
                            @Param("dateTo") Date dateTo,
                            Pageable pageable
    );

    List<Task> findByUserEmailOrderByTitleAsc(String email);
}
