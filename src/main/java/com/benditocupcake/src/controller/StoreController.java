package com.benditocupcake.src.controller;

import com.benditocupcake.src.controller.dto.response.AddressResponse;
import com.benditocupcake.src.controller.dto.response.StoreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/store")
public class StoreController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StoreResponse getStoreInfo() {
        // Dados mockados da loja
        AddressResponse address = AddressResponse.builder()
                .street("Avenida Paulista")
                .number("1500")
                .complement("Loja 25")
                .neighborhood("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .zipCode("01310200")
                .build();

        // Horários de funcionamento em formato similar ao da imagem
        Map<String, String> businessHours = Map.of(
                "Segunda-feira:", "9:00 - 18:00",
                "Terça-feira:", "9:00 - 18:00",
                "Quarta-feira:", "9:00 - 18:00",
                "Quinta-feira:", "9:00 - 18:00",
                "Sexta-feira:", "9:00 - 18:00",
                "Sábado:", "10:00 - 16:00",
                "Domingo:", "Fechado"
        );

        return StoreResponse.builder()
                .id(1L)
                .name("Bendito Cupcake - Matriz")
                .cnpj("12.345.678/0001-90")
                .phone("(11) 3456-7890")
                .email("contato@benditocupcake.com")
                .businessHours(businessHours)
                .address(address)
                .build();
    }
}