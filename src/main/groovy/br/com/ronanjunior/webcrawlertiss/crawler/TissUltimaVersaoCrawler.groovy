package br.com.ronanjunior.webcrawlertiss.crawler

import br.com.ronanjunior.webcrawlertiss.utils.FileUtils
import br.com.ronanjunior.webcrawlertiss.utils.HttpUtils
import br.com.ronanjunior.webcrawlertiss.utils.JsoupUtils
import org.jsoup.nodes.Document

class TissUltimaVersaoCrawler {
    String baseUrl
    HttpUtils httpUtils

    FileUtils fileUtils = new FileUtils()

    TissUltimaVersaoCrawler(String baseUrl, String RequestContentType) {
        this.baseUrl = baseUrl
        this.httpUtils = new HttpUtils(baseUrl, RequestContentType)
    }

    void iniciar() {
        Document paginaTissUltimaVersao = httpUtils.get("/")

        if (paginaTissUltimaVersao){
            JsoupUtils paginaTissUltimaVersaoParser = new JsoupUtils(paginaTissUltimaVersao)

            String tituloPagina = paginaTissUltimaVersaoParser
                    .buscarElemento("#content > h1")
                    .trim()
                    .replaceAll(/[\\\/:*?"<>|\s]/, "_")

            List<String> nomesArquivosPadraoTiss = paginaTissUltimaVersaoParser
                    .buscarElementos(
                            "#parent-fieldname-text > div > table > tbody > tr > td:nth-child(1)"
                    )

            List<String> arquivosPadraoTiss = paginaTissUltimaVersaoParser
                    .buscarElementos(
                            "#parent-fieldname-text > div > table > tbody > tr > td:nth-child(3) > a",
                            "href"
                    )

            arquivosPadraoTiss.eachWithIndex{ String arquivo, int i ->
                String nomeArquivo = arquivo.split("/").last()
                String nomePastaArquivo = nomesArquivosPadraoTiss[i]
                        .trim()
                        .replaceAll(/[\\\/:*?"<>|\s]/, "_")
                String caminho = "./downloads/${tituloPagina}/${nomePastaArquivo}/${nomeArquivo}"

                FileOutputStream outputStream = fileUtils.getOutputStreamForFile(caminho)

                boolean downloadBemSucedido = httpUtils
                        .downloadFile(arquivo, outputStream)

                if (downloadBemSucedido) {
                    println "Download do arquivo (${nomeArquivo}) foi bem-sucedido!"
                } else {
                    println "Falha no download do arquivo (${nomeArquivo})."
                }
            }

        } else {
            println "Falha ao obter o documento HTML."
        }
    }
}
