package eu.pontsystems.fe.controller;


import eu.pontsystems.be.model.StoredMessages;
import eu.pontsystems.be.services.StoredMessagesService;
import eu.pontsystems.fe.dao.Messages;
import eu.pontsystems.fe.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class IndexController implements WebMvcConfigurer {

    private DataService dataService;

    private StoredMessagesService storedMessagesService;

    @Autowired
    public IndexController(DataService dataService, StoredMessagesService storedMessagesService){
        this.dataService=dataService;
        this.storedMessagesService = storedMessagesService;
    }

    @RequestMapping("/")
    public String showHomePage(Model model, RedirectAttributes retireAttrs)
    {
        retireAttrs.addFlashAttribute("success", "Everything went just fine.");
        model.addAttribute("year", dataService.getNextYear());
        Messages messages = new Messages();
        model.addAttribute("messages", messages);

        List<Messages> allMessages= new ArrayList<Messages>();
        List<StoredMessages> allStoredMessages = storedMessagesService.getAllStoredMessages(null);
        if (allStoredMessages!=null && !allStoredMessages.isEmpty()) {
            for (StoredMessages storedMessage : allStoredMessages) {
                Messages sMessage = new Messages();
                sMessage.setName(storedMessage.getName());
                sMessage.setMessage(storedMessage.getMessage());
                allMessages.add(sMessage);
            }
        }
        model.addAttribute("allmessages", allMessages);
        return "index";
    }

}
