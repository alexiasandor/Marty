package com.example.martybackend.controller;

import com.example.martybackend.config.RabbitSender;
import com.example.martybackend.email.NotificationRequestDto;
import com.example.martybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.example.martybackend.constants.EmailConstants.*;

@Controller
public class HomeController {
    private final RabbitSender rabbitSender;

    @Autowired
    private UserService userService;


    @Autowired
    public HomeController(RabbitSender rabbitSender, UserService userService) {
        this.rabbitSender = rabbitSender;
        this.userService = userService;


    }


    @GetMapping("/")
    public ModelAndView home(){
        return new ModelAndView("connectAs");
    }
    // for connect
    @RequestMapping("/redirect")
    public String redirect(@RequestParam("username") String email,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role, Model model)  {


        if (userService.findUserByEmailAndPassword(email, password) == true) {

            String roleUser = userService.getUserRole(email);


            if (roleUser.equals(role)) {

                String redirectPath = "";

                if (role != null) {
                    if (role.equals("admin")) {
                        redirectPath = "redirect:/adminPage";
                    } else if (role.equals("chelner")) {
                        redirectPath = "redirect:/chelner";
                    } else if (role.equals("ajutor bucatarie")) {
                        redirectPath = "redirect:/kitchenHelper";
                    } else if (role.equals("client")) {
                        redirectPath = "redirect:/client";
                    }
                }


                NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
                notificationRequestDto.setRecipient(email);
                notificationRequestDto.setSubject(CONECTARE_REUSITA);
                notificationRequestDto.setBody(HELLO + email + CONNECTED_MESSAGE + role);

                rabbitSender.sendMessage(notificationRequestDto);


                return redirectPath;
            } else {
                model.addAttribute("error", ROL_INCORRECT);
            }
        } else {
            model.addAttribute("error", AUTENTIFICARE_ESUATA);
        }


        return "connectAs";
    }


    // for admin
    @RequestMapping("/adminPage")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("adminPage");
        return modelAndView;
    }

    @RequestMapping("/adminPageForUsers")
    public ModelAndView adminPageForUsers() {
        ModelAndView modelAndView = new ModelAndView("adminPageForUsers");
        return modelAndView;
    }

    @RequestMapping("/sendEmail")
    public ModelAndView sendEmail() {
        ModelAndView modelAndView = new ModelAndView("sendEmail");
        return modelAndView;
    }
    @RequestMapping("/generateCsv")
    public ModelAndView generateCsv() {
        ModelAndView modelAndView = new ModelAndView("generateCsv");
        return modelAndView;
    }

    @RequestMapping("/adminPageForProducts")
    public ModelAndView adminPageForProducts() {
        ModelAndView modelAndView = new ModelAndView("adminPageForProducts");
        return modelAndView;
    }

    @RequestMapping("/adminPageForOrders")
    public ModelAndView adminPageForOrders() {
        ModelAndView modelAndView = new ModelAndView("adminPageForOrders");
        return modelAndView;
    }

    @RequestMapping("/adminPageForSales")
    public ModelAndView adminPageForSales() {
        ModelAndView modelAndView = new ModelAndView("adminPageForSales");
        return modelAndView;
    }


    @RequestMapping("/chelner")
    public ModelAndView chelnerPage() {
        ModelAndView modelAndView = new ModelAndView("chelnerPage");
        return modelAndView;
    }
    @RequestMapping("/generateFile")
    public ModelAndView generateFile() {
        ModelAndView modelAndView = new ModelAndView("generateFile");
        return modelAndView;
    }

    @RequestMapping("/kitchenHelper")
    public ModelAndView ajutorBucatariePage() {
        ModelAndView modelAndView = new ModelAndView("kitchenHelper");
        return modelAndView;
    }

    @RequestMapping("/client")
    public ModelAndView clientPage() {
        ModelAndView modelAndView = new ModelAndView("clientPage");
        return modelAndView;
    }
    // pentru admin
    @RequestMapping("/addUser")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView("addUser");
        return modelAndView;
    }

    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser() {
        ModelAndView modelAndView = new ModelAndView("deleteUser");
        return modelAndView;
    }

    @RequestMapping("/getAllUsers")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("getAllUsers");
        return modelAndView;
    }

    @RequestMapping("/updateUser")
    public ModelAndView updateUser() {
        ModelAndView modelAndView = new ModelAndView("updateUser");
        return modelAndView;
    }

    @RequestMapping("/viewUserActivity")
    public ModelAndView viewUserActivity() {
        ModelAndView modelAndView = new ModelAndView("viewUserActivity");
        return modelAndView;
    }

    @RequestMapping("/messagesForWaiter")
    public ModelAndView messagesForWaiter() {
        ModelAndView modelAndView = new ModelAndView("messagesForWaiter");
        return modelAndView;
    }
    @RequestMapping("/happyDay")
    public ModelAndView happyDay() {
        ModelAndView modelAndView = new ModelAndView("happyDay");
        return modelAndView;
    }
    // for orders


    @RequestMapping("/deleteOrder")
    public ModelAndView deleteOrd() {
        ModelAndView modelAndView = new ModelAndView("deleteOrder");
        return modelAndView;
    }
    @RequestMapping("/getAllOrds")
    public ModelAndView getAllOrds() {
        ModelAndView modelAndView = new ModelAndView("getAllOrds");
        return modelAndView;
    }

    @RequestMapping("/updateOrder")
    public ModelAndView updateOrd() {
        ModelAndView modelAndView = new ModelAndView("updateOrder");
        return modelAndView;
    }
    // for products
    @RequestMapping("/addProduct")
    public ModelAndView addProduct() {
        ModelAndView modelAndView = new ModelAndView("addProduct");
        return modelAndView;
    }


    @RequestMapping("/deleteProduct")
    public ModelAndView deleteProduct() {
        ModelAndView modelAndView = new ModelAndView("deleteProduct");
        return modelAndView;
    }
    @RequestMapping("/getAllProducts")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView("getAllProducts");
        return modelAndView;
    }

    @RequestMapping("/updateProduct")
    public ModelAndView updateProduct() {
        ModelAndView modelAndView = new ModelAndView("updateProduct");
        return modelAndView;
    }
    @RequestMapping("/managePayment")
    public ModelAndView managePayment() {
        ModelAndView modelAndView = new ModelAndView("managePayment");
        return modelAndView;
    }
    // for sales
    @RequestMapping("/addSale")
    public ModelAndView addSale() {
        ModelAndView modelAndView = new ModelAndView("addSale");
        return modelAndView;
    }


    @RequestMapping("/deleteSale")
    public ModelAndView deleteSale() {
        ModelAndView modelAndView = new ModelAndView("deleteSale");
        return modelAndView;
    }
    @RequestMapping("/getAllSales")
    public ModelAndView getAllSales() {
        ModelAndView modelAndView = new ModelAndView("getAllSales");
        return modelAndView;
    }

    @RequestMapping("/updateSale")
    public ModelAndView updateSale() {
        ModelAndView modelAndView = new ModelAndView("updateSale");
        return modelAndView;
    }

    @RequestMapping("/addProductsToOrder")
    public ModelAndView addProductsToOrder() {
        ModelAndView modelAndView = new ModelAndView("addProductsToOrder");
        return modelAndView;
    }

    @RequestMapping("/orderPrice")
    public ModelAndView orderPrice() {
        ModelAndView modelAndView = new ModelAndView("orderPrice");
        return modelAndView;
    }
    //pentru ajutor bucatarie

    @RequestMapping("/sendNotification")
    public ModelAndView sendNotification() {
        ModelAndView modelAndView = new ModelAndView("sendNotification");
        return modelAndView;
    }
}