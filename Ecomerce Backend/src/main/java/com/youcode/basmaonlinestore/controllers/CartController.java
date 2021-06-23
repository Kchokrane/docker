package com.youcode.basmaonlinestore.controllers;

import com.youcode.basmaonlinestore.entity.ProductInOrder;
import com.youcode.basmaonlinestore.entity.User;
import com.youcode.basmaonlinestore.form.ItemForm;
import com.youcode.basmaonlinestore.entity.Cart;
import com.youcode.basmaonlinestore.repository.ProductInOrderRepository;
import com.youcode.basmaonlinestore.service.CartService;
import com.youcode.basmaonlinestore.service.ProductInOrderService;
import com.youcode.basmaonlinestore.service.ProductService;
import com.youcode.basmaonlinestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductInOrderService productInOrderService;
    private final ProductInOrderRepository productInOrderRepository;

    @PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Collection<ProductInOrder> productInOrders, Principal principal) {
        User user = userService.findOne(principal.getName());

        try {
            cartService.mergeLocalCart(productInOrders, user);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }

        return ResponseEntity.ok(cartService.getCart(user));
    }

    @GetMapping("")
    public Cart getCart(Principal principal) {
        User user = userService.findOne(principal.getName());
        return cartService.getCart(user);
    }

    @PostMapping("/add")
    public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
        var productInfo = productService.findOne(form.getProductId());
        try {
            mergeCart(Collections.singleton(new ProductInOrder(productInfo, form.getQuantity())), principal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PutMapping("/{itemId}")
    public ProductInOrder modifyItem(@PathVariable("itemId") String itemId, @RequestBody Integer quantity, Principal principal) {
        User user = userService.findOne(principal.getName());
         productInOrderService.update(itemId, quantity, user);
        return productInOrderService.findOne(itemId, user);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") String itemId, Principal principal) {
        User user = userService.findOne(principal.getName());
         cartService.delete(itemId, user);
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(Principal principal) {
        User user = userService.findOne(principal.getName());// Email as username
        cartService.checkout(user);
        return ResponseEntity.ok(null);
    }

}
