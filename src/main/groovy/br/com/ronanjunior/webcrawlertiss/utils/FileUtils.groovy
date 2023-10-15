package br.com.ronanjunior.webcrawlertiss.utils

class FileUtils {
    FileOutputStream getOutputStreamForFile(String filePath, Boolean modoAnexacao = false) {
        try {
            File file = new File(filePath)
            File parentDirectory = file.parentFile
            if (!parentDirectory.exists()) {
                if (!parentDirectory.mkdirs()) {
                    throw new IOException("Falha ao criar o diretório: ${parentDirectory}")
                }
            }
            return new FileOutputStream(file, modoAnexacao)
        } catch (IOException e) {
            e.printStackTrace()
            return null
        }
    }

    boolean escreverNoArquivoCsv(String filePath, List<String> linha, Boolean modoAnexacao = true) {
        FileOutputStream outputStream = getOutputStreamForFile(filePath, modoAnexacao)
        if (outputStream) {
            PrintWriter writer = new PrintWriter(outputStream)

            try {
                String linhaCsv = linha.join(',')
                writer.println(linhaCsv)
                writer.close()
                return true
            } catch (IOException e) {
                e.printStackTrace()
            }
        }
        return false
    }


}
