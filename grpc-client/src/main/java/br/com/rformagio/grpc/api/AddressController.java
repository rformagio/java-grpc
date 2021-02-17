package br.com.rformagio.grpc.api;

import br.com.rformagio.grpc.service.AddressClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

    private AddressClientService addressClientService;

    @Autowired
    public AddressController(AddressClientService addressClientService) {
        this.addressClientService = addressClientService;
    }

    @GetMapping("/{cep}")
    public String getAddress(@PathVariable("cep") String cep) {
        try {
            return addressClientService.getAddress(cep);
        } catch (Exception e) {
            System.out.println("##########################################################################");
            System.out.println("ERRO: " + e.getMessage());
        }

        return "ERRO $$$$$$$$$$$$$$$$$$$$$$$$$$$";
    }

    @GetMapping("/")
    public String hello() {
        return "HELLO !!!!!";
    }
}
