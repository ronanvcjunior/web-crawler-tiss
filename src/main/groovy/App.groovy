import static groovyx.net.http.HttpBuilder.configure

class App {
    static void main(String[] args) {

        def http = configure {
            request.uri = 'https://www.gov.br/ans/pt-br'
        }

        println http
    }
}
