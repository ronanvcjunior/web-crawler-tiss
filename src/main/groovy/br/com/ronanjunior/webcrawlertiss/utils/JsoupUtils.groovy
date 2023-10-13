package br.com.ronanjunior.webcrawlertiss.utils

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class JsoupUtils {
    Document document

    JsoupUtils(Document document) {
        this.document = document
    }


    List<String> findElements(String selector, String attribute) {
        List<String> results = []
        Elements elements = document.select(selector)

        if (elements) {
            elements.each { element ->
                results.add(element.attr(attribute))
            }
        }

        return results
    }


    List<String> findElements(String selector) {
        List<String> results = []
        Elements elements = document.select(selector)

        if (elements) {
            elements.each { element ->
                results.add(element.text())
            }
        }

        return results
    }
}