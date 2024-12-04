package banquemisr.challenge05.TaskManagement.Task;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class TaskSpec {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";
    public static final String PRIORITY = "priority";
    public static final String DUEDATE = "dueDate";

    private TaskSpec() {
    }

    public static Specification<Task> filterBy(TaskFilter taskFilter) {
        return Specification
                .where(hasTitle(taskFilter.getTitle()))
                .and(hasDescription(taskFilter.getDescription()))
                .and(hasStatus(taskFilter.getStatus()))
                .and(hasPriority(taskFilter.getPriority()))
                .and(dueDateGreaterThan(taskFilter.getStartDate()))
                .and(dueDateLessThan(taskFilter.getEndDate()));
    }

    private static Specification<Task> hasTitle(String title) {
        return ((root, query, cb) -> title == null || title.isEmpty() ? cb.conjunction() : cb.like(root.get(TITLE), "%"+title+"%"));
    }

    private static Specification<Task> hasDescription(String description) {
        return (root, query, cb) -> description == null ? cb.conjunction() : cb.like(root.get(DESCRIPTION), "%"+description+"%");
    }

    private static Specification<Task> hasStatus(List<String> status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : root.get(STATUS).in(status);
    }

    private static Specification<Task> hasPriority(List<Short> priority) {
        return (root, query, cb) -> priority == null ? cb.conjunction() : root.get(PRIORITY).in(priority);
    }

    private static Specification<Task> dueDateGreaterThan(LocalDate startDate) {
        return (root, query, cb) -> startDate == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get(DUEDATE), startDate);
    }

    private static Specification<Task> dueDateLessThan(LocalDate endDate) {
        return (root, query, cb) -> endDate == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get(DUEDATE), endDate);
    }
}