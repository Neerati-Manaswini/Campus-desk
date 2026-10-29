package com.manaswini.students;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String list(@RequestParam(defaultValue = "") String q,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {
        PageRequest pageable = PageRequest.of(page, 5, Sort.by("name"));
        Page<Student> result = q.isBlank()
                ? repository.findAll(pageable)
                : repository.findByNameContainingIgnoreCaseOrCourseContainingIgnoreCase(q, q, pageable);
        model.addAttribute("students", result);
        model.addAttribute("q", q);
        return "students";
    }

    @GetMapping("/students/new")
    public String newForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("title", "Add student");
        return "form";
    }

    @GetMapping("/students/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No student with id " + id));
        model.addAttribute("student", student);
        model.addAttribute("title", "Edit student");
        return "form";
    }

    @PostMapping("/students")
    public String save(@Valid @ModelAttribute Student student, BindingResult result,
                       Model model, RedirectAttributes flash) {
        boolean duplicate = student.getId() == null
                ? repository.existsByEmail(student.getEmail())
                : repository.existsByEmailAndIdNot(student.getEmail(), student.getId());
        if (duplicate) {
            result.rejectValue("email", "duplicate", "This email is already registered");
        }
        if (result.hasErrors()) {
            model.addAttribute("title", student.getId() == null ? "Add student" : "Edit student");
            return "form";
        }
        repository.save(student);
        flash.addFlashAttribute("message", "Saved " + student.getName());
        return "redirect:/students";
    }

    @PostMapping("/students/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        repository.deleteById(id);
        flash.addFlashAttribute("message", "Student deleted");
        return "redirect:/students";
    }
}
