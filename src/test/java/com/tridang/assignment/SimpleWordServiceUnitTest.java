package com.tridang.assignment;/*
 * @author Tri Dang
 */
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.tridang.assignment.constant.MerriamValidationStatus;
import com.tridang.assignment.model.RestStatus;
import com.tridang.assignment.rest.MerriamRestService;
import com.tridang.assignment.service.SimpleWordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class SimpleWordServiceUnitTest {

    @Mock
    MerriamRestService merriamRestServiceMock;
    @Mock
    Set<String> storageMock;

    @InjectMocks
    SimpleWordService wordService = new SimpleWordService();

    @Test
    void testIsValid_WhenInputNull_ExpectFalse() {
        boolean actual = wordService.isValidInput(null);
        Assertions.assertFalse(actual);
    }

    @Test
    void testIsValid_WhenInputEmpty_ExpectFalse() {
        String input = "";
        boolean actual = wordService.isValidInput(input);
        Assertions.assertFalse(actual);
    }

    @Test
    void testIsValid_WhenInputDeliminator_ExpectTrue() {
        String input = "lady bug";
        boolean actual = wordService.isValidInput(input);
        Assertions.assertFalse(actual);
    }

    @Test
    void testIsValid_WhenInputInvalidLength_ExpectFalse() {
        String input = "abcdefghijk";
        boolean actual = wordService.isValidInput(input);
        Assertions.assertFalse(actual);
    }

    @Test
    void testIsValid_WhenInputInvalidCharacters_ExpectFalse() {
        String input = "123";
        boolean actual = wordService.isValidInput(input);
        Assertions.assertFalse(actual);
    }

    @Test
    void testIsValid_WhenInputValid_ExpectTrue() {
        String input = "bug";
        boolean actual = wordService.isValidInput(input);
        Assertions.assertTrue(actual);
    }

    @Test
    void testAdd_WhenIsWordAndNotExisted_ExpectAdd() {
        when(merriamRestServiceMock.validate(anyString())).thenReturn(new RestStatus(MerriamValidationStatus.VALIDATED.getStatus(), ""));
        when(storageMock.contains(anyString())).thenReturn(false);

        String word = "bug";
        String expected = String.format("Added to storage: [%s]", word);
        String actual = wordService.add(word);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testAdd_WhenIsWordAndExisted_ExpectNotAdd() {
        when(merriamRestServiceMock.validate(anyString())).thenReturn(new RestStatus(MerriamValidationStatus.VALIDATED.getStatus(), ""));
        when(storageMock.contains(anyString())).thenReturn(true);

        String word = "bug";
        String expected = String.format("Word already exists in storage: [%s]", word);
        String actual = wordService.add(word);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testAdd_WhenInvalidWord_ExpectNotAdd() {
        when(merriamRestServiceMock.validate(anyString())).thenReturn(new RestStatus(MerriamValidationStatus.NOT_VALIDATED.getStatus(), "list of suggestions"));

        String word = "awdawdawd";
        String expected = "Unable to add to storage: list of suggestions";
        String actual = wordService.add(word);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testAdd_WhenInvalidInput_ExpectNotAdd() {
        String word = "lady bug";
        String expected = String.format("Unable to add. Input is not valid: [%s]", word);
        String actual = wordService.add(word);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testDelete_WhenExists_ExpectDelete() {
        when(storageMock.contains(anyString())).thenReturn(true);
        when(storageMock.remove(anyString())).thenReturn(true);

        String word = "bug";
        String expected = String.format("Deleted word: [%s]", word);
        String actual = wordService.delete(word);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testDelete_WhenNotExists_ExpectNotDelete() {
        when(storageMock.contains(anyString())).thenReturn(false);

        String word = "bug";
        String expected = String.format("Word currently not in storage: [%s]", word);
        String actual = wordService.delete(word);

        Assertions.assertEquals(expected, actual);
    }
}
