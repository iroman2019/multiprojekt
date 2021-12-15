package eu.pontsystems.fe.controller;

import eu.pontsystems.fe.dao.Messages;
import eu.pontsystems.fe.service.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KafkaSenderController implements WebMvcConfigurer {
    Messages messages;


    private final Producer producer;


    @PostMapping("/admessage")
    public String addMessage(@Valid Messages messages, BindingResult bindingResult, Model model, RedirectAttributes redirAttrs){


        if (bindingResult.hasErrors()) {
            log.info("hibás töltés");
            redirAttrs.addFlashAttribute("error", "Sorry, but you must fill  both fields!");
            return "redirect:/";
        }
        producer.send(messages);
        redirAttrs.addFlashAttribute("success", "Everything went just fine.");
        log.info("Producer sended message.");
        return "redirect:/";
    }



}
