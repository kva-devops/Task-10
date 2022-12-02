package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@Controller
@AllArgsConstructor
public class IndexController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping(value = {"/user"})
    public String loadPageUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        model.addAttribute("authenticatedUser", authenticatedUser);
        return "user";
    }

    @GetMapping(value = "/admin")
    public String loadPageAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        model.addAttribute("authenticatedUser", authenticatedUser);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("/admin/create")
    public String createUser(@Valid @ModelAttribute User user,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "error";
        } else {
            String[] authoritiesIds = request.getParameterValues("authoritiesIds");
            Set<Role> roleSet = roleService.findRolesByIds(authoritiesIds);
            user.setRoleSet(roleSet);
            user.setEnabled(true);
            userService.createOrUpdateUser(user);
            return "redirect:/admin";
        }
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam(name = "id") Integer id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/update")
    public String updateUser(@RequestParam(name = "id") Integer id,
                             @ModelAttribute User user,
                             HttpServletRequest request) {
            String[] authoritiesIds = request.getParameterValues("authoritiesIds");
            if (authoritiesIds != null) {
                Set<Role> roleSet = roleService.findRolesByIds(authoritiesIds);
                user.setRoleSet(roleSet);
            }
            user.setId(id);
            user.setEnabled(true);
            userService.createOrUpdateUser(user);
            return "redirect:/admin";
    }
}
