package ua.com.alevel.internetclothingstore.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.internetclothingstore.dto.UserDTO;
import ua.com.alevel.internetclothingstore.service.UserService;

import java.security.Principal;

//TODO DONE
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "reg";
    }

    //TODO LOGGING
    @PostMapping("/new")
    public String addNewUser(UserDTO user, Model model) {
        String page = "reg";
        if (user.getFirstName().isBlank()) {
            return incorrectInputData(user, model, "First name is blank!!!", page);
        }
        if (user.getLastName().isBlank()) {
            return incorrectInputData(user, model, "Last name is blank!!!", page);
        }
        if (user.getNickName().isBlank()) {
            return incorrectInputData(user, model, "Nickname is blank!!!", page);
        }
        if (user.getEmail().isBlank()) {
            return incorrectInputData(user, model, "Email is blank!!!", page);
        }
        if (user.getPassword().isBlank() || user.getMatchingPassword().isBlank()) {
            return incorrectInputData(user, model, "Password is blank!!!", page);
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            return incorrectInputData(user, model, "Passwords are not matching!!!", page);
        }
        if (userService.findFirstByNickName(user.getNickName()) != null) {
            return incorrectInputData(user, model, "User with login '" + user.getNickName() + "' is already exist", page);
        }

        userService.save(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return "redirect:/users";
            }
        }
        return "redirect:/login";
    }

    private String incorrectInputData(UserDTO userDTO, Model model, String errorMessage, String page) {
        model.addAttribute("user", userDTO);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorFlag", true);
        return page;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String ListOfUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "userList";
    }

    //TODO LOGGING
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String visitProfile(Model model, Principal principal) {
        final UserDTO userDTO = userService.getByNickName(principal.getName());
        model.addAttribute("user", userDTO);
        return "profile";
    }

    //TODO LOGGING
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public String updatePassword(@ModelAttribute("user") UserDTO userDTO, Principal principal, Model model) {
        String page = "profile";
        if (userDTO.getPassword().isBlank() || userDTO.getMatchingPassword().isBlank()) {
            return incorrectInputData(userDTO, model, "Password is blank!!!", page);
        }
        if (!userDTO.getPassword().equals(userDTO.getMatchingPassword())) {
            return incorrectInputData(userDTO, model, "Passwords are not matching!!!", page);
        }
        userService.updatePassword(userDTO);
        return "redirect:/users/profile";
    }
}