package com.tridang.assignment;/*
 * @author Tri Dang
 */
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tridang.assignment.constant.RestEndpoints;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class WordControllerUnitTest {
    @Autowired
    MockMvc mvc;

    String key = "key";
    String value = "value";

    @Test
    void getIndex() throws Exception {
        String expected = "Welcome to Tri Dang's demo!";
        mvc.perform(MockMvcRequestBuilders
                .get("/demo/")
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void postAddWord_WhenInvalidInput() throws Exception {
        String word = "123 ";
        String expected = String.format("Unable to add. Input is not valid: [%s]", word.trim());
        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", word)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void postAddWord_WhenInvalidWord() throws Exception {
        String word = "awdawdawd";
        String expected = "Unable to add to storage: Unable to validate word: " + word;
        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", word)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    void postAddWord_WhenWordAlreadyExists() throws Exception {
        String word = "bug";
        String expected = String.format("Word already exists in storage: [%s]", word);
        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", word)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value));

        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", word)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void getDeleteWord_WhenValidWord() throws Exception {
        String word = "bug";
        String expected = String.format("Deleted word: [%s]", word);
        mvc.perform(MockMvcRequestBuilders
                .delete("/demo" + RestEndpoints.DELETE_WORD_PATH + "{word}", word)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void getDeleteWord_WhenWordAlreadyExists() throws Exception {
        String word = "bug";
        String expected = String.format("Deleted word: [%s]", word);
        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", word)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value));

        mvc.perform(MockMvcRequestBuilders
                .delete("/demo" + RestEndpoints.DELETE_WORD_PATH + "{word}", word)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void getListAllWords_WhenNotEmptyStorage() throws Exception {
        String word = "bug";
        String expected = String.format("[\"%s\"]", word);

        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", word)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value));

        mvc.perform(MockMvcRequestBuilders
                .get("/demo" + RestEndpoints.LIST_ALL_WORDS_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void getFindSubWords_WhenWordExists() throws Exception {
        String fullWord = "bug";
        String subWord = "bug";
        String expected = String.format("[\"%s\"]", subWord);
        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", subWord)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value));

        mvc.perform(MockMvcRequestBuilders
                .get("/demo" + RestEndpoints.FIND_SUB_WORDS_PATH + "{word}", fullWord)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void getFindSubWords_WhenManyWordExists() throws Exception {
        String fullWord = "ladybug";
        String subWord1 = "bug";
        String subWord2 = "lady";
        String subWord3 = "third";
        String expected = String.format("[\"%s\",\"%s\"]", subWord1, subWord2);
        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", subWord1)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value));
        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", subWord2)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value));
        mvc.perform(MockMvcRequestBuilders
                .post("/demo" + RestEndpoints.ADD_WORD_PATH + "{word}", subWord3)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value));

        mvc.perform(MockMvcRequestBuilders
                .get("/demo" + RestEndpoints.FIND_SUB_WORDS_PATH + "{word}", fullWord)
                .accept(MediaType.APPLICATION_JSON)
                .header(key, value))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }
}

