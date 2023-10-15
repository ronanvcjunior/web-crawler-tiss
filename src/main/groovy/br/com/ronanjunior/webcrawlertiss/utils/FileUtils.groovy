package br.com.ronanjunior.webcrawlertiss.utils

class FileUtils {
    static FileOutputStream getOutputStreamForFile(String filePath) {
        try {
            def file = new File(filePath)
            def parentDirectory = file.parentFile
            if (!parentDirectory.exists()) {
                if (!parentDirectory.mkdirs()) {
                    throw new IOException("Falha ao criar o diretório: ${parentDirectory}")
                }
            }
            return new FileOutputStream(file)
        } catch (IOException e) {
            e.printStackTrace()
            return null
        }
    }
}
