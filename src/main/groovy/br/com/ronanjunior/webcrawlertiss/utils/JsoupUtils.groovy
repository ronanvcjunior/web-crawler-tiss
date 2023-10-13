package br.com.ronanjunior.webcrawlertiss.utils

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class JsoupUtils {
    Document document

    JsoupUtils(Document document) {
        this.document = document
    }

    String buscarElemento(String selector, String attribute) {
        String resultado = null
        Elements element = document.select(selector)

        if (element && element.hasAttr(attribute))
            resultado = element.attr(attribute)

        return resultado
    }

    String buscarElemento(String selector) {
        String resultado = null
        Elements element = document.select(selector)

        if (element)
            resultado = element.text()

        return resultado
    }

    List<String> buscarElementos(String selector, String attribute) {
        List<String> resultados = []
        Elements elements = document.select(selector)

        if (elements) {
            elements.each { element ->
                resultados.add(element.attr(attribute))
            }
        }

        return resultados
    }

    List<String> buscarElementos(String selector) {
        List<String> resultados = []
        Elements elements = document.select(selector)

        if (elements) {
            elements.each { element ->
                resultados.add(element.text())
            }
        }

        return resultados
    }
}