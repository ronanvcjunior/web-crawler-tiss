package br.com.ronanjunior.webcrawlertiss

import br.com.ronanjunior.webcrawlertiss.crawler.GovCrawler


class App {
    static final baseUrl = "https://www.gov.br/ans/pt-br"
    static void main(String[] args) {
        GovCrawler govCrawler = new GovCrawler(baseUrl, "text/html")

        govCrawler.iniciar()

    }
}