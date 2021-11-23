package eu.pontsystems.fe.controller;


import eu.pontsystems.fe.dao.Messages;
import eu.pontsystems.fe.service.DataService;
import eu.pontsystems.fe.service.NumberTheoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class IndexController implements WebMvcConfigurer {

    private DataService dataService;

    @Autowired
    private NumberTheoryService numberTheoryService;

    @Autowired
    public IndexController(DataService dataService){
        this.dataService=dataService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @RequestMapping("/")
    public String showHomePage(Model model, RedirectAttributes retireAttrs)
    {
        retireAttrs.addFlashAttribute("success", "Everything went just fine.");
        model.addAttribute("year", dataService.getNextYear()-1);
        model.addAttribute("is_happy_year", numberTheoryService.isHappyNumber(LocalDate.now().getYear()));
        Messages messages = new Messages();
        model.addAttribute("messages", messages);
        return "index";
    }



}
