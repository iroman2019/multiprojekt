package eu.pontsystems.fe.controller;

import eu.pontsystems.be.services.Consumer;
import eu.pontsystems.fe.dao.Messages;
import eu.pontsystems.fe.service.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
public class KafkaSenderController implements WebMvcConfigurer {
    Messages messages;

    @Autowired
    Producer producer;

    private final Consumer consumerService;

    public KafkaSenderController(Consumer consumerService) {
        this.consumerService = consumerService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    // URL - http://localhost:9000/api/kafka/send
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @PostMapping(value = "/send")
    public String send(@RequestBody final Messages messages, BindingResult bindingResult, Model model, RedirectAttributes redirAttrs) {
        System.out.println("Kontroller észleli");
        if (bindingResult.hasErrors()) {
            System.out.println("hibás töltés");
            redirAttrs.addFlashAttribute("error", "Sorry, but you must fill  both fields!");
            return "index";
        }
        System.out.println(messages);
        log.info("Sending message to kafka topic");
       // producer.send(messages);
        redirAttrs.addFlashAttribute("success", "Everything went just fine.");
        return "redirect:/";

    }
    @PostMapping("/admessage")
    public String addMessage(@Valid Messages messages, BindingResult bindingResult, Model model, RedirectAttributes redirAttrs){


        if (bindingResult.hasErrors()) {
            System.out.println("hibás töltés");
            redirAttrs.addFlashAttribute("error", "Sorry, but you must fill  both fields!");
            return "redirect:/";
        }
        System.out.println(messages);
        producer.send(messages);
        redirAttrs.addFlashAttribute("success", "Everything went just fine.");
        return "redirect:/";
    }



}
