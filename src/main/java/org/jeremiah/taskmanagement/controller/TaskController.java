package org.jeremiah.taskmanagement.controller;

import org.jeremiah.taskmanagement.model.Task;
import org.jeremiah.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Display All Tasks
    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks";
    }

    // Serve Add Task Page
    @GetMapping("/add")
    public String showAddTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "add-task";
    }

    // Add a New Task
    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    // Serve Edit Task Page
    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        model.addAttribute("task", task);
        return "edit-task";
    }

    // Update Task
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDeadline(taskDetails.getDeadline());
        task.setPriority(taskDetails.getPriority());
        task.setStatus(taskDetails.getStatus());

        taskRepository.save(task);
        return "redirect:/tasks";
    }

    // Delete Task
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }
}
