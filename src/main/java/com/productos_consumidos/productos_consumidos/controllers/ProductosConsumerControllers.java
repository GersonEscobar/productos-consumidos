package com.productos_consumidos.productos_consumidos.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.productos_consumidos.productos_consumidos.DTO.ProductoDTO;

@RestController
public class ProductosConsumerControllers {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumir-productos")
    public String consumirProductos() {
        String serviceUrl = discoveryClient.getInstances("Productos")
                                            .stream()
                                            .map(instance -> instance.getUri().toString().trim())
                                            .findFirst()
                                            .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return restTemplate.getForObject("http://Productos/api/v1/productos", String.class);
    }


    @PostMapping("/crear-producto")
public ResponseEntity<String> crearProducto(@RequestBody ProductoDTO producto) {
    String serviceUrl = discoveryClient.getInstances("Productos")
        .stream()
        .map(instance -> instance.getUri().toString().replaceAll("/$", ""))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Servicio 'Productos' no encontrado"));

    if (producto.getFechaRegistro() == null) {
        producto.setFechaRegistro(LocalDateTime.now());
    }

    return restTemplate.postForEntity("http://Productos/api/v1/productos", producto, String.class);
}


@PutMapping("/actualizar-producto")
public ResponseEntity<String> actualizarProducto(@RequestBody ProductoDTO producto) {
    String serviceUrl = discoveryClient.getInstances("Productos")
        .stream()
        .map(instance -> instance.getUri().toString().replaceAll("/$", ""))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Servicio 'Productos' no encontrado"));

    restTemplate.put("http://Productos/api/v1/productos", producto);
    return ResponseEntity.ok("Producto actualizado correctamente");
}


@DeleteMapping("/eliminar-producto/{id}")
public ResponseEntity<String> eliminarProducto(@PathVariable("id") Long idProducto) {
    String serviceUrl = discoveryClient.getInstances("Productos")
        .stream()
        .map(instance -> instance.getUri().toString().replaceAll("/$", ""))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Servicio 'Productos' no encontrado"));

    restTemplate.delete("http://Productos/api/v1/productos/" + idProducto);
    return ResponseEntity.ok("Producto eliminado correctamente");
}
}
