package eu.pontsystems.fe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.pontsystems.be.model.StoredMessages;
import eu.pontsystems.be.services.StoredMessagesService;
import eu.pontsystems.fe.dao.Messages;
import eu.pontsystems.fe.dao.MessagesComparators;
import eu.pontsystems.fe.dao.paging.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessagesService {


    @Autowired
    private StoredMessagesService storedMessagesService;

    private static final Comparator<Messages> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public PageArray getMessagesArray(PagingRequest pagingRequest) {
        pagingRequest.setColumns(Stream.of("name", "message")
                .map(Column::new)
                .collect(Collectors.toList()));
        Page<Messages> messagesPage = getMessages(pagingRequest);

        PageArray pageArray = new PageArray();
        pageArray.setRecordsFiltered(messagesPage.getRecordsFiltered());
        pageArray.setRecordsTotal(messagesPage.getRecordsTotal());
        pageArray.setDraw(messagesPage.getDraw());
        pageArray.setData(messagesPage.getData()
                .stream()
                .map(this::toStringList)
                .collect(Collectors.toList()));
        return pageArray;
    }

    private List<String> toStringList(Messages messages) {
        return Arrays.asList(messages.getName(), messages.getMessage());
    }

    public Page<Messages> getMessages(PagingRequest pagingRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Messages> messages = new ArrayList<Messages>();

        List<StoredMessages> allStoredMessages = storedMessagesService.getAllStoredMessages(null);
        for (StoredMessages storedMessage : allStoredMessages) {
            Messages sMessage = new Messages();
            sMessage.setName(storedMessage.getName());
            sMessage.setMessage(storedMessage.getMessage());
            messages.add(sMessage);
        }
        return getPage(messages, pagingRequest);
    }

    private Page<Messages> getPage(List<Messages> messages, PagingRequest pagingRequest) {
        List<Messages> filtered = messages.stream()
                .sorted(sortMessages(pagingRequest))
                .filter(filterMessages(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        long count = messages.stream()
                .filter(filterMessages(pagingRequest))
                .count();

        Page<Messages> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<Messages> filterMessages(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return messages -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return messages -> messages.getName()
                .toLowerCase()
                .contains(value)
                || messages.getMessage()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<Messages> sortMessages(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<Messages> comparator = MessagesComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }
}

