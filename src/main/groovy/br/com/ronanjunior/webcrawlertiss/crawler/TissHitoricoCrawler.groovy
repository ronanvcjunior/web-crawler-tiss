package br.com.ronanjunior.webcrawlertiss.crawler

import br.com.ronanjunior.webcrawlertiss.utils.FileUtils
import br.com.ronanjunior.webcrawlertiss.utils.HttpUtils
import br.com.ronanjunior.webcrawlertiss.utils.JsoupUtils
import org.jsoup.nodes.Document

class TissHitoricoCrawler {
    String baseUrl
    HttpUtils httpUtils

    FileUtils fileUtils = new FileUtils()

    TissHitoricoCrawler(String baseUrl, String RequestContentType) {
        this.baseUrl = baseUrl
        this.httpUtils = new HttpUtils(baseUrl, RequestContentType)
    }

    void iniciar() {
        Document paginaTissTissHitorico = httpUtils.get("/")

        if (paginaTissTissHitorico){
            JsoupUtils paginaTissTissHitoricoParser = new JsoupUtils(paginaTissTissHitorico)

            String tituloPagina = paginaTissTissHitoricoParser
                    .buscarElemento("#content > h1")
                    .trim()
                    .replaceAll(/[\\\/:*?"<>|\s]/, "_")

            List<String> cabecalho = paginaTissTissHitoricoParser
                    .buscarElementos("#parent-fieldname-text > table > thead > tr > th")

            String nomeArquivo = tituloPagina.split("/").last()
            String caminho = "./downloads/${tituloPagina}/${nomeArquivo}.csv"

            fileUtils.escreverNoArquivoCsv(caminho, cabecalho, false)

            Integer numLinha = 1;
            do {
                List<String> linha = paginaTissTissHitoricoParser
                        .buscarElementos("#parent-fieldname-text > table > tbody > tr:nth-child(${numLinha++}) > td")
                if (!linha || linha[0].toUpperCase() == "Jan/2016".toUpperCase())
                    break

                fileUtils.escreverNoArquivoCsv(caminho, linha, true)
            } while (true)
        } else {
            println "Falha ao obter o documento HTML."
        }
    }
}
