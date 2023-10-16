package br.com.ronanjunior.webcrawlertiss.crawler

import br.com.ronanjunior.webcrawlertiss.utils.FileUtils
import br.com.ronanjunior.webcrawlertiss.utils.HttpUtils
import br.com.ronanjunior.webcrawlertiss.utils.JsoupUtils
import org.jsoup.nodes.Document

class TissHistoricoCrawler {
    String baseUrl
    HttpUtils httpUtils

    FileUtils fileUtils = new FileUtils()

    TissHistoricoCrawler(String baseUrl, String RequestContentType) {
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
                    .buscarElementos("#parent-fieldname-text > table > thead > tr > th:lt(3)")

            String nomeArquivo = tituloPagina.split("/").last()
            String caminho = "./downloads/${tituloPagina}/${nomeArquivo}.csv"

            Boolean arquivoCriadoComSucesso = fileUtils.escreverNoArquivoCsv(caminho, cabecalho, false)

            if (arquivoCriadoComSucesso) {
                Boolean arquivoTodoGravadoComSucesso = true
                Integer numLinha = 1;
                do {
                    List<String> linha = paginaTissTissHitoricoParser
                            .buscarElementos("#parent-fieldname-text > table > tbody > tr:nth-child(${numLinha++}) > td:lt(3)")
                    if (!linha || linha[0].toUpperCase() == "Jan/2016".toUpperCase())
                        break

                    Boolean arquivoGravadoComSucesso = fileUtils.escreverNoArquivoCsv(caminho, linha, true)
                    if (!arquivoGravadoComSucesso) {
                        arquivoTodoGravadoComSucesso = false
                    }
                } while (true)

                if (arquivoTodoGravadoComSucesso) {
                    println "Geração do Arquivo (${nomeArquivo}.csv) foi bem-sucedido!"
                } else {
                    println "Falha ao criar o arquivo ${nomeArquivo}.csv"
                }
            } else {
                println "Falha ao criar o arquivo ${nomeArquivo}.csv"
            }
        } else {
            println "Falha ao obter o documento HTML."
        }
    }
}
