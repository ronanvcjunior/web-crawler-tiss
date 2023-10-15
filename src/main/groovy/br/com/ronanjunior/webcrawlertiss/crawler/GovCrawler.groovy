package br.com.ronanjunior.webcrawlertiss.crawler

import br.com.ronanjunior.webcrawlertiss.utils.HttpUtils
import br.com.ronanjunior.webcrawlertiss.utils.JsoupUtils
import org.jsoup.nodes.Document

class GovCrawler {
    String baseUrl
    HttpUtils httpUtils

    GovCrawler(String baseUrl, String RequestContentType) {
        this.baseUrl = baseUrl
        this.httpUtils = new HttpUtils(baseUrl, RequestContentType)
    }

    void iniciar() {
        Document paginaInicialGov = httpUtils.get("/")

        if (paginaInicialGov) {
            JsoupUtils paginaInicalGovParser = new JsoupUtils(paginaInicialGov)

            String linkAssuntosPrestadores = paginaInicalGovParser
                    .buscarElemento("#ce89116a-3e62-47ac-9325-ebec8ea95473 > div > a", "href")

            String pathAssuntosPrestadores =
                    linkAssuntosPrestadores.replace(this.baseUrl, "")

            Document paginaAssuntosPrestadores = httpUtils.get(pathAssuntosPrestadores)

            if (paginaAssuntosPrestadores) {
                JsoupUtils paginaAssuntosPrestadoresParser = new JsoupUtils(paginaAssuntosPrestadores)

                String linkTiss = paginaAssuntosPrestadoresParser
                        .buscarElemento("#content-core > div > div > div:nth-child(1) > a", "href")

                String pathTiss =
                        linkTiss.replace(this.baseUrl, "")

                Document paginaTiss = httpUtils.get(pathTiss)

                if (paginaTiss) {
                    JsoupUtils paginaTissParser = new JsoupUtils(paginaTiss)

                    String linkTissUltimaVersao = paginaTissParser
                            .buscarElemento("#parent-fieldname-text > p:nth-child(4) > a", "href")

                    String linkTissHitorico = paginaTissParser
                            .buscarElemento("#parent-fieldname-text > p:nth-child(6) > a", "href")

                    String linkTissTabelasRelacionadas = paginaTissParser
                            .buscarElemento("#parent-fieldname-text > p:nth-child(8) > a", "href")

                    TissUltimaVersaoCrawler tissUltimaVersaoCrawler = new TissUltimaVersaoCrawler(linkTissUltimaVersao, "text/html")
                    tissUltimaVersaoCrawler.iniciar()
                } else {
                    println "Falha ao obter o documento HTML."
                }

            } else {
                println "Falha ao obter o documento HTML."
            }

        } else {
            println "Falha ao obter o documento HTML."
        }
    }
}
