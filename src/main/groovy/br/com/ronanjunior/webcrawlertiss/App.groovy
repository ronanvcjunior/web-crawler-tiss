package br.com.ronanjunior.webcrawlertiss

import br.com.ronanjunior.webcrawlertiss.utils.HttpUtils
import br.com.ronanjunior.webcrawlertiss.utils.JsoupUtils
import org.jsoup.nodes.Document


class App {
    static void main(String[] args) {

        String baseUrl = 'https://www.gov.br/ans/pt-br'

        HttpUtils httpUtils = new HttpUtils(baseUrl, 'text/html')

        Document document = httpUtils.get("/")

        if (document) {
            JsoupUtils jsoupUtils = new JsoupUtils(document)
            List<String> responseList = jsoupUtils.findElements(".nome-orgao")
            if (!responseList.isEmpty()) {
                String response = responseList.first()
                println "Título da página: ${response}"
            } else {
                println "Falha ao obter e analisar o documento HTML."
            }
        } else {
            println "Falha ao obter o documento HTML."
        }

    }
}