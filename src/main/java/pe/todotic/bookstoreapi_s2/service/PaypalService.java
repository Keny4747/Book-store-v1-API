package pe.todotic.bookstoreapi_s2.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pe.todotic.bookstoreapi_s2.model.SalesOrder;
import pe.todotic.bookstoreapi_s2.web.paypal.Token;

@Service
public class PaypalService {

    private final static String PAYPAL_API_BASE = "https://api-m.sandbox.paypal.com";

    private final static String PAYPAL_CLIENT_ID="AcrkpAtpV6wA5FPPWWtosUercaqNR0jXwjAozrdRUCmefS1VCRQcXLdBdd1vlffrHwvqzbZ3lFUD-vXa";

    private final static String PAYPAL_CLIENT_SECRET="EDRi5tculBMJRmNb03kFOs_g_C2LUxs4nwihMTLXOs2A93nlxe69HchhaR5x8Q7kOz_yRLEwDnf77UFy";


    private String getAccesToken(){
        String url = String.format("%s/v1/oauth2/token",PAYPAL_API_BASE);
        RestTemplate restTemplate = new RestTemplate();//permite hacer el intercambio entre servidores

        HttpHeaders headers = new HttpHeaders();//encabezados que necesitamos enviar
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(PAYPAL_CLIENT_ID,PAYPAL_CLIENT_SECRET);

        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
        form.add("grant_type","client_credentials");

        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(form,headers);
        ResponseEntity<Token> response= restTemplate.postForEntity(url,entity, Token.class);

        return response.getBody().getAccesToken();
    }

    public void createOrder(SalesOrder salesOrder, String returnUrl, String cancel){

    }
}
