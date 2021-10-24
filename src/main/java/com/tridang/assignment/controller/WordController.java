package com.tridang.assignment.controller;/*
 * @author Tri Dang
 */
import static com.tridang.assignment.util.StringUtil.cleanInput;

import com.tridang.assignment.constant.RestEndpoints;
import com.tridang.assignment.service.SimpleWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping(value = RestEndpoints.DEMO_PATH)
@RestController
public class WordController {
    @Autowired
    SimpleWordService simpleWordService;

    @GetMapping("/")
    public String index() {
        return "Welcome to Tri Dang's demo!";
    }

    @PostMapping(value = RestEndpoints.ADD_WORD_PATH + "{word}")
    public ResponseEntity<String> addWord(@PathVariable final String word) {
        return new ResponseEntity<>(simpleWordService.add(cleanInput(word)), HttpStatus.OK);
    }

    @DeleteMapping(value = RestEndpoints.DELETE_WORD_PATH + "{word}")
    public ResponseEntity<String> deleteWord(@PathVariable final String word) {
        return new ResponseEntity<>(simpleWordService.delete(cleanInput(word)), HttpStatus.OK);
    }

    @GetMapping(RestEndpoints.LIST_ALL_WORDS_PATH)
    public ResponseEntity<Set<String>> listAllWords() {
        return new ResponseEntity<>(simpleWordService.listAll(), HttpStatus.OK);
    }

    @GetMapping(value = RestEndpoints.FIND_SUB_WORDS_PATH + "{word}")
    public ResponseEntity<Set<String>> findSubWords(@PathVariable final String word) {
        return new ResponseEntity<>(simpleWordService.findSubWords(cleanInput(word)), HttpStatus.OK);
    }
}

