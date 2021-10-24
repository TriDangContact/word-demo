package com.tridang.assignment.service;/*
 * @author Tri Dang
 */

import com.tridang.assignment.model.RestStatus;
import java.util.Set;

public interface WordService {
    boolean isValidInput(String input);
    RestStatus validateWord(String input);
    String add(String input);
    String delete(String input);
    Set<String> listAll();
    Set<String> findSubWords(String input);
}
