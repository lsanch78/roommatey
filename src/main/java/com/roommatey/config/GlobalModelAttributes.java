package com.roommatey.config;

import com.roommatey.model.Household;
import com.roommatey.model.RoommateyUserDetails;
import com.roommatey.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {


    // So we could grab the current user logged in globally
    @ModelAttribute
    public void addLoggedInUserAttributes(Model model, Authentication auth){

        // Grab names globally
        if (auth != null && auth.getPrincipal() instanceof RoommateyUserDetails details){
            User user = details.getUser();


            model.addAttribute("userName", user.getName());


            model.addAttribute("household", user.getHousehold());
        }



    }
}
