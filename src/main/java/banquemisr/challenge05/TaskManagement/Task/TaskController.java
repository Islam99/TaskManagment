package banquemisr.challenge05.TaskManagement.Task;

import banquemisr.challenge05.TaskManagement.Config.APIResponse;
import banquemisr.challenge05.TaskManagement.History.History;
import banquemisr.challenge05.TaskManagement.Security.LoggedInUser;
import banquemisr.challenge05.TaskManagement.User.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static banquemisr.challenge05.TaskManagement.Config.SystemUtil.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    TaskService taskService;



    @PostMapping("")
    public ResponseEntity<APIResponse> create(@Valid @RequestBody CreateTaskDTO createTaskDTO,
                                       @Schema(hidden = true)@LoggedInUser User user) throws Exception
    {
        APIResponse response = new APIResponse(null , taskService.create(createTaskDTO,user), HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<APIResponse> update(@Valid @RequestBody UpdateTaskDTO updateTaskDTO, @PathVariable Long id,
                                       @Schema(hidden = true)@LoggedInUser User user) throws Exception
    {
        APIResponse response = new APIResponse(null , taskService.update(updateTaskDTO,id,user), HttpStatus.ACCEPTED);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> find(@PathVariable Long id) throws Exception
    {
        APIResponse response = new APIResponse(null , taskService.findById(id), HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<APIResponse> findTaskHistory(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "timestamp") String sort,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "4") Integer limit) throws Exception
    {
        Pageable pageable = createPageable(sort,sortDirection,page,limit);
        APIResponse response = new APIResponse(null , taskService.findTaskHistory(id,pageable) , HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("")
    public ResponseEntity<APIResponse> findAll(
            @RequestParam(required = false, defaultValue = "dueDate") String sort,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "4") Integer limit
    ) throws Exception
    {
        Pageable pageable = createPageable(sort,sortDirection,page,limit);
        APIResponse response = new APIResponse(null , taskService.findAll(pageable) , HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) throws Exception
    {
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/filter")
    public ResponseEntity<APIResponse> findAllFiltered(
            @RequestParam(required = false, defaultValue = "dueDate") String sort,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "4") Integer limit,
            @RequestBody TaskFilter taskFilter
    )
    {
        Pageable pageable = createPageable(sort,sortDirection,page,limit);
        APIResponse response = new APIResponse(null , taskService.findAllFiltered(taskFilter,pageable) , HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse> findAllSearchable(
            @RequestParam(required = false, defaultValue = "dueDate") String sort,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "4") Integer limit,
            @RequestParam(required = true) String searchString
    ) throws Exception
    {
        Pageable pageable = createPageable(sort,sortDirection,page,limit);
        APIResponse response = new APIResponse(null , taskService.findAllSearchable(searchString,pageable) , HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
