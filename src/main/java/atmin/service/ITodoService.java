package atmin.service;

import atmin.model.Todo;

import java.util.List;
import java.util.Optional;

public interface ITodoService {
    void save(Todo todo);

    List<Todo> findAll();

    Optional<Todo> findById(Long id);

    void deleteById(Long id);
}
