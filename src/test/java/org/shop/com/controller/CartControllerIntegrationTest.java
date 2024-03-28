package org.shop.com.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.shop.com.dto.CartCreateDto;
import org.shop.com.dto.CartDto;
import org.shop.com.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateOrUpdateCart() throws Exception {
        CartCreateDto cartCreateDto = new CartCreateDto(1L, Collections.emptyList());
        CartDto expectedCartDto = new CartDto(1L, 1L);

        given(cartService.createOrUpdateCart(any(CartCreateDto.class))).willReturn(expectedCartDto);

        mockMvc.perform(post("/v1/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L));

        Mockito.verify(cartService, Mockito.times(1)).createOrUpdateCart(any(CartCreateDto.class));
    }

    @Test
    public void testGetCartByUserId() throws Exception {
        Long userId = 1L;
        CartDto expectedCartDto = new CartDto(1L, userId);

        given(cartService.getCartByUserId(userId)).willReturn(expectedCartDto);

        mockMvc.perform(get("/v1/carts/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(userId));

        Mockito.verify(cartService, Mockito.times(1)).getCartByUserId(userId);
    }

    @Test
    public void testDeleteCart() throws Exception {
        Long cartId = 1L;

        mockMvc.perform(delete("/v1/carts/{cartId}", cartId))
                .andExpect(status().isOk());

        Mockito.verify(cartService, Mockito.times(1)).deleteCart(cartId);
    }

    @Test
    public void testGetAllCarts() throws Exception {
        CartDto cartDto1 = new CartDto(1L, 1L);
        CartDto cartDto2 = new CartDto(2L, 2L);

        given(cartService.getAllCarts()).willReturn(Arrays.asList(cartDto1, cartDto2));

        mockMvc.perform(get("/v1/carts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        Mockito.verify(cartService, Mockito.times(1)).getAllCarts();
    }
}