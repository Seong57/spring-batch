package com.example.demo.domain;

import lombok.Getter;

@Getter
public enum ServicePolicy {

    A(1L, "/seong57/service/a", 10),
    B(2L, "/seong57/service/b", 11),
    C(3L, "/seong57/service/c", 12),
    D(4L, "/seong57/service/d", 13),
    E(5L, "/seong57/service/e", 14),
    F(6L, "/seong57/service/f", 15),
    G(7L, "/seong57/service/g", 16),
    H(8L, "/seong57/service/h", 17),
    I(9L, "/seong57/service/i", 18),
    J(10L, "/seong57/service/j", 19),
    K(11L, "/seong57/service/k", 20),
    L(12L, "/seong57/service/l", 21),
    M(13L, "/seong57/service/m", 22),
    N(14L, "/seong57/service/n", 23),
    O(15L, "/seong57/service/o", 24),
    P(16L, "/seong57/service/p", 25),
    Q(17L, "/seong57/service/q", 26),
    R(18L, "/seong57/service/r", 27),
    S(19L, "/seong57/service/s", 28),
    T(20L, "/seong57/service/t", 29),
    U(21L, "/seong57/service/u", 30),
    V(22L, "/seong57/service/v", 31),
    W(23L, "/seong57/service/w", 32),
    X(24L, "/seong57/service/x", 33),
    Y(25L, "/seong57/service/y", 34),
    Z(26L, "/seong57/service/z", 35)
    ;

    private final Long id;
    private final String url;
    private final Integer fee;

    ServicePolicy(Long id, String url, Integer fee) {
        this.id = id;
        this.url = url;
        this.fee = fee;
    }
}
