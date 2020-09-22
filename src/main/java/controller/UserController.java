package controller;

import model.Category;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import service.CategoryService;
import service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/user")
    public ModelAndView showListUser(@SortDefault(sort = {"username"}) @PageableDefault(value = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/list");
        Page<User> users = userService.findAll(pageable);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/create-user")
    public ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView("/create");
        User user = new User();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/create-user")
    public ModelAndView saveUser(@ModelAttribute("user") User user, @SortDefault (sort = {"username"}) @PageableDefault(value = 5) Pageable pageable){
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/list");
        Page<User> users = userService.findAll(pageable);
        modelAndView.addObject("users", users);
        modelAndView.addObject("message", "New user created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-user/{id}")
    public ModelAndView editUser(@PathVariable long id){
        User user = userService.findById(id);
        if(user != null) {
            return new ModelAndView("/edit", "user", user);
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/edit-user/{id}")
    public ModelAndView updateUser(@ModelAttribute("user") User user, @SortDefault (sort = {"username"}) @PageableDefault(value = 10) Pageable pageable){
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/list");
        Page<User> users = userService.findAll(pageable);
        modelAndView.addObject("users", users);
        modelAndView.addObject("message", "User edited successfully");
        return modelAndView;
    }

    @GetMapping("/delete-user/{id}")
    public ModelAndView deleteUser(@PathVariable long id){
        User user = userService.findById(id);
        if(user != null) {
            return new ModelAndView("/delete", "user", user);
        }else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/delete-user/{id}")
    public ModelAndView removeUser(@ModelAttribute("user") User user, @SortDefault (sort = {"username"}) @PageableDefault(value = 10) Pageable pageable ){
        userService.remove(user.getId());
        ModelAndView modelAndView = new ModelAndView("/list");
        Page<User> users = userService.findAll(pageable);
        modelAndView.addObject("users", users);
        modelAndView.addObject("message", "User deleted successfully");
        return modelAndView;
    }

    @GetMapping("/view-user/{id}")
    public ModelAndView showUser(@PathVariable long id){
        User user = userService.findById(id);
        if(user != null) {
            return new ModelAndView("/view", "user", user);
        }else {
            return new ModelAndView("/error.404");
        }
    }
}
