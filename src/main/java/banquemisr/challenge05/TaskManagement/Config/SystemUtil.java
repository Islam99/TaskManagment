package banquemisr.challenge05.TaskManagement.Config;

import banquemisr.challenge05.TaskManagement.Task.Task;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Optional;

public class SystemUtil {

    public static Object unwrapEntity(Optional<?> entity,Class type) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException( type.getSimpleName() + " Not found");
    }

    public static Object setNonNullValues(Object objectDTO,Object object) throws Exception{
        Field[] fields = objectDTO.getClass().getDeclaredFields();
        for(Field field : fields)
        {
            field.setAccessible(true);
            Object value = field.get(objectDTO);
            if(value != null)
                field.set(object, value);
            field.setAccessible(false);
        }
        return object;
    }

    public static Pageable createPageable(String sort, String sortDirection, int page, int limit) {
        Sort.Direction direction = sortDirection == null ? Sort.Direction.DESC : (sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC);
        return PageRequest.of(page, limit, Sort.by(direction, sort));
    }
}
