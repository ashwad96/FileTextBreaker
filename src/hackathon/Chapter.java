/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aswadhwa
 */
public class Chapter {
    private ArrayList<Paragraph> paragraphs;

    public Chapter() {
        paragraphs = new ArrayList<Paragraph>();
    }

    public void addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }
}
