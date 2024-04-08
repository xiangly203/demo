package com.example.demo.controller;

import com.example.demo.entity.Book;
//import com.example.demo.service.BookProducerService;
import com.example.demo.service.BookProducerService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    String myTopic = "MyTopic";
    private final AtomicLong atomicLong = new AtomicLong();
    @Resource
    private BookProducerService producer;

    @PostMapping
    public String sendMessageToKafkaTopic(@RequestParam("name") String name) {
        log.debug("Controller='{}' to topic='{}'", name, myTopic);
        producer.sendMessage(myTopic, new Book(atomicLong.addAndGet(1), name));
        return "123";
    }
}