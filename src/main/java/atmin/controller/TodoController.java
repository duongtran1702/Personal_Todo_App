package atmin.controller;


import atmin.model.Todo;

import atmin.service.ITodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TodoController {
    private final ITodoService todoService;

    @GetMapping("/")
    public String list(@ModelAttribute("todo") Todo todo, Model model) {
        model.addAttribute("todos", todoService.findAll());
        return "todo-page";
    }

    @PostMapping("/add")
    public String addTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("todos", todoService.findAll());
            model.addAttribute("todo", todo);
            return "todo-page";
        }
        todoService.save(todo);
        return "redirect:/";
    }
}
