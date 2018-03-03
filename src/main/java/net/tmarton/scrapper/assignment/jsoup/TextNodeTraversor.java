package net.tmarton.scrapper.assignment.jsoup;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

/**
 * Created by tmarton on 03/03/2018.
 */
public class TextNodeTraversor {

    public List<String> traverse(Element elem) {
        final List<String> wordChunks = new ArrayList<>();
        (new NodeTraversor(new NodeVisitor() {
            public void head(Node node, int depth) {
                if (node instanceof TextNode) {
                    TextNode textNode = (TextNode) node;
                    wordChunks.add(textNode.getWholeText().trim());
                }
            }

            public void tail(Node node, int depth) {
            }
        })).traverse(elem);
        return wordChunks;
    }
}
