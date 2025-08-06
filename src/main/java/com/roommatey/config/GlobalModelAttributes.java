package com.roommatey.config;

import com.roommatey.model.RoommateyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {


    // So we could grab the current user logged in globally
    @ModelAttribute
    public void addLoggedInUserName(Model model, Authentication auth){



        if (auth != null && auth.getPrincipal() instanceof RoommateyUserDetails details){
            String name = details.getUser().getName();
            model.addAttribute("userName", name);
        }

    }
}
