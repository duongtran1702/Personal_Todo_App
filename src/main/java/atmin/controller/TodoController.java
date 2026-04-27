package atmin.controller;

import atmin.model.Todo;
import atmin.service.ITodoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TodoController {
    private final ITodoService todoService;

    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";
    }

    @PostMapping("/welcome")
    public String handleWelcome(@RequestParam("ownerName") String ownerName, HttpSession session, RedirectAttributes redirectAttributes) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng nhập tên!");
            return "redirect:/welcome";
        }
        session.setAttribute("ownerName", ownerName);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(@ModelAttribute("todo") Todo todo, Model model, HttpSession session) {
        if (session.getAttribute("ownerName") == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("todos", todoService.findAll());
        return "todo-page";
    }

    @PostMapping("/add")
    public String addTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("ownerName") == null) return "redirect:/welcome";
        if (result.hasErrors()) {
            model.addAttribute("todos", todoService.findAll());
            model.addAttribute("todo", todo);
            return "todo-page";
        }
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("message", "msg.success");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editTodo(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("ownerName") == null) return "redirect:/welcome";
        Optional<Todo> todo = todoService.findById(id);
        if (todo.isPresent()) {
            model.addAttribute("todo", todo.get());
            model.addAttribute("todos", todoService.findAll());
            return "todo-page";
        }
        redirectAttributes.addFlashAttribute("message", "msg.error.notFound");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession session) {
        if (session.getAttribute("ownerName") == null) return "redirect:/welcome";
        try {
            todoService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "msg.success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "msg.error.delete");
        }
        return "redirect:/";
    }
}
