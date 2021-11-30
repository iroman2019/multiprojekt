package eu.pontsystems.fe.controller;

import eu.pontsystems.fe.dao.Messages;
import eu.pontsystems.fe.dao.paging.Page;
import eu.pontsystems.fe.dao.paging.PageArray;
import eu.pontsystems.fe.dao.paging.PagingRequest;
import eu.pontsystems.fe.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages")
public class MessagesRestController {

    private final MessagesService messagesService;

    @Autowired
    public MessagesRestController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping
    public Page<Messages> list(@RequestBody PagingRequest pagingRequest) {
        return messagesService.getMessages(pagingRequest);
    }

    @PostMapping("/array")
    public PageArray array(@RequestBody PagingRequest pagingRequest) {
        return messagesService.getMessagesArray(pagingRequest);
    }
}
