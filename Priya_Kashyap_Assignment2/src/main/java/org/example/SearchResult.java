package org.example;

public class SearchResult {
    private String keyword; // The word we were looking for
    private int row; // The row number where we found the word
    private int column; // The column number where we found the word

    // This is a constructor
    public SearchResult(String keyword, int row, int column) {
        this.keyword = keyword; // Store the keyword in the keyword variable
        this.row = row; // Store the row number in the row variable
        this.column = column; // Store the column number in the column variable
    }

    // This method is used to get the keyword that was found.
    public String getKeyword() {
        return keyword;
    }

    // This method is used to get the row number where the keyword was found.
    public int getRow() {
        return row;
    }

    // This method is used to get the column number where the keyword was found.
    public int getColumn() {
        return column;
    }
}

