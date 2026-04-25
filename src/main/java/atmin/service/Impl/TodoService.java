package atmin.service.Impl;

import atmin.model.Todo;
import atmin.repository.ITodoRepository;
import atmin.service.ITodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService implements ITodoService {
    private final ITodoRepository todoRepository;


    @Override
    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
}
