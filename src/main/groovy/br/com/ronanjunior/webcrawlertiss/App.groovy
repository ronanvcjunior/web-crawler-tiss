package br.com.ronanjunior.webcrawlertiss

import br.com.ronanjunior.webcrawlertiss.crawler.GovCrawler
import br.com.ronanjunior.webcrawlertiss.utils.FileUtils
import br.com.ronanjunior.webcrawlertiss.utils.HttpUtils


class App {
    static final baseUrl = "https://www.gov.br/ans/pt-br"
    static void main(String[] args) {
        GovCrawler govCrawler = new GovCrawler(baseUrl, "text/html")

        govCrawler.iniciar()

        HttpUtils httpUtils = new HttpUtils("", "text/html")
        def filePath = './downloads/PadroTISSComunicao202301.zip'
        def outputStream = FileUtils.getOutputStreamForFile(filePath)
        boolean success = httpUtils.downloadFile('https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/PadroTISSComunicao202301.zip', outputStream)

        if (success) {
            println "Download bem-sucedido!"
        } else {
            println "Falha no download."
        }

    }
}