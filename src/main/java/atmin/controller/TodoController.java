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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
    public String addTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("todos", todoService.findAll());
            model.addAttribute("todo", todo);
            return "todo-page";
        }
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("message", "Thao tác thành công!");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editTodo(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Todo> todo = todoService.findById(id);
        if (todo.isPresent()) {
            model.addAttribute("todo", todo.get());
            model.addAttribute("todos", todoService.findAll());
            return "todo-page";
        }
        redirectAttributes.addFlashAttribute("message", "Không tìm thấy công việc!");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            todoService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Thao tác thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa công việc!");
        }
        return "redirect:/";
    }
}
