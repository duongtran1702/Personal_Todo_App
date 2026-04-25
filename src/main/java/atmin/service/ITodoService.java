package atmin.service;

import atmin.model.Todo;

import java.util.List;

public interface ITodoService {
    void save(Todo todo);

    List<Todo> findAll();

}
