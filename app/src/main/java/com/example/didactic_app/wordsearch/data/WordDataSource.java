package com.example.didactic_app.wordsearch.data;

import com.example.didactic_app.wordsearch.model.Word;

import java.util.List;

/**
 * Created by abdularis on 18/07/17.
 */

public interface WordDataSource {

    List<Word> getWords(String palabras);

}
