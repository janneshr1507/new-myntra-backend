package com.jannesh.controller;

import com.jannesh.dto.item.ItemDTO;
import com.jannesh.dto.item.SaveItemDTO;
import com.jannesh.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/get/{itemId}")
    public ResponseEntity<?> getItemDetails(@PathVariable("itemId") UUID itemId) {
        return new ResponseEntity<>(itemService.fetchItemDetails(itemId), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveItem(@RequestBody SaveItemDTO requestDTO) {
        return new ResponseEntity<>(itemService.createItem(requestDTO), HttpStatus.OK);
    }

    @PutMapping("/updateDiscount/{itemId}/{discount}")
    public ResponseEntity<?> updateDiscount(@PathVariable("itemId") UUID itemId, @PathVariable("discount")BigDecimal discount) {
        return new ResponseEntity<>(itemService.updateDiscountDetails(itemId, discount), HttpStatus.OK);
    }

}
