package br.com.ronanjunior.webcrawlertiss.utils

import groovyx.net.http.HttpBuilder
import org.jsoup.nodes.Document

class HttpUtils {
    String baseUrl
    String requestContentType

    HttpUtils(String baseUrl, String requestContentType) {
        this.baseUrl = baseUrl
        this.requestContentType = requestContentType
    }

    Document get(String path) {
        Document response = null
        try {
            response = HttpBuilder.configure {
                request.uri = baseUrl + path
            }.get {
                request.contentType = requestContentType
            }

            return response
        } catch (Exception e) {
            println "Erro ao acessar a página: ${e.message}"

            return response
        }
    }
}
