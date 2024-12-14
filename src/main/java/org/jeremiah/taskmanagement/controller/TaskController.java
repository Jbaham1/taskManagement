package org.jeremiah.taskmanagement.controller;

import org.jeremiah.taskmanagement.model.Task;
import org.jeremiah.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "add-task";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        model.addAttribute("task", task);
        return "edit-task";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDeadline(taskDetails.getDeadline());
        task.setPriority(taskDetails.getPriority());
        task.setStatus(taskDetails.getStatus());

        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
        return "redirect:/tasks";
    }
}
