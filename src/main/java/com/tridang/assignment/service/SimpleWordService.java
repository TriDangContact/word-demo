package com.tridang.assignment.service;/*
 * @author Tri Dang
 */
import static com.tridang.assignment.constant.MerriamValidationStatus.VALIDATED;

import com.tridang.assignment.model.RestStatus;
import com.tridang.assignment.rest.MerriamRestService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Component("simpleWordService")
public class SimpleWordService implements WordService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MerriamRestService merriamRestService;

    private Set<String> wordStorage;

    public SimpleWordService() {
        this.wordStorage = new HashSet<>();
    }

    @Override
    public boolean isValidInput(String input) {
        logger.info("Validating input: [{}]", input);
        if (input != null
                && !input.isEmpty()
                && input.length() <= 10
                && input.matches("^[a-zA-Z]*$")) {
            return true;
        } else {
            logger.error("Input must adhere to following requirements: Cannot be null, empty string, or > 10 characters. Cannot contain non English alphabet characters (A-Za-z). Must contain single word.");
        }
        return false;
    }

    @Override
    public RestStatus validateWord(String input) {
        return merriamRestService.validate(input);
    }

    @Override
    public String add(String input) {
        if (isValidInput(input)) {
            RestStatus status = validateWord(input);
            if (VALIDATED.getStatus().equals(status.getCode())) {
                if (wordStorage.contains(input)) {
                    return "Word already exists in storage: [" + input + "]";
                } else {
                    wordStorage.add(input);
                    return "Added to storage: [" + input + "]";
                }
            } else {
                return "Unable to add to storage: " + status.getMessage();
            }
        } else {
            return "Unable to add. Input is not valid: [" + input + "]";
        }
    }

    @Override
    public String delete(String input) {
        if (wordStorage.contains(input)) {
            wordStorage.remove(input);
            return "Deleted word: [" + input + "]";
        } else {
            return "Word currently not in storage: [" + input + "]";
        }
    }

    @Override
    public Set<String> listAll() {
        return wordStorage;
    }

    @Override
    public Set<String> findSubWords(String input) {
        Set<String> resultSet = new HashSet<>();
        for (String word : wordStorage) {
            if (input.contains(word)) resultSet.add(word);
        }
        return resultSet;
    }
}
