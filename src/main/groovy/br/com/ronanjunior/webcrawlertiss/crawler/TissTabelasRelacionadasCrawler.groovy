package br.com.ronanjunior.webcrawlertiss.crawler

import br.com.ronanjunior.webcrawlertiss.utils.FileUtils
import br.com.ronanjunior.webcrawlertiss.utils.HttpUtils
import br.com.ronanjunior.webcrawlertiss.utils.JsoupUtils
import org.jsoup.nodes.Document

class TissTabelasRelacionadasCrawler {
    String baseUrl
    HttpUtils httpUtils

    FileUtils fileUtils = new FileUtils()

    TissTabelasRelacionadasCrawler(String baseUrl, String RequestContentType) {
        this.baseUrl = baseUrl
        this.httpUtils = new HttpUtils(baseUrl, RequestContentType)
    }

    void iniciar() {
        Document paginaTissTabelasRelacionadas = httpUtils.get("/")

        if (paginaTissTabelasRelacionadas){
            JsoupUtils paginaTissTabelasRelacionadasParser = new JsoupUtils(paginaTissTabelasRelacionadas)

            String tituloPagina = paginaTissTabelasRelacionadasParser
                    .buscarElemento("#content > h1")
                    .trim()
                    .replaceAll(/[\\\/:*?"<>|\s]/, "_")

            String nomePastaArquivo = paginaTissTabelasRelacionadasParser
                    .buscarElemento("#parent-fieldname-text > h2:nth-child(1)")
                    .trim()
                    .replaceAll(/[\\\/:*?"<>|\s]/, "_")

            String arquivoPadraoTiss = paginaTissTabelasRelacionadasParser
                    .buscarElemento(
                            "#parent-fieldname-text > p:nth-child(2) > a",
                            "href"
                    )

            String nomeArquivo = arquivoPadraoTiss.split("/").last()
            String caminho = "./downloads/${tituloPagina}/${nomePastaArquivo}/${nomeArquivo}"

            FileOutputStream outputStream = fileUtils.getOutputStreamForFile(caminho)

            boolean downloadBemSucedido = httpUtils
                    .downloadFile(arquivoPadraoTiss, outputStream)

            if (downloadBemSucedido) {
                println "Download do arquivo (${nomeArquivo}) foi bem-sucedido do arquivo!"
            } else {
                println "Falha no download do arquivo (${nomeArquivo})."
            }

        } else {
            println "Falha ao obter o documento HTML."
        }
    }
}
