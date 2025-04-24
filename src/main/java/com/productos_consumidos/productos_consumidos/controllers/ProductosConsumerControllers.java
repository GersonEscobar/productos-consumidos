package com.productos_consumidos.productos_consumidos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductosConsumerControllers {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumir-productos")
    public String consumirProductos() {
        // Usando DiscoveryClient para encontrar el servicio 'productos'
        String serviceUrl = discoveryClient.getInstances("Productos")
                                            .stream()
                                            .map(instance -> instance.getUri().toString().trim())
                                            .findFirst()
                                            .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // Consumir el servicio usando RestTemplate
        return restTemplate.getForObject("http://Productos/api/v1/productos", String.class);
    }
}
