//package com.dee.ecommerce.product_service.util;
//
//
//import org.springframework.security.core.AuthenticatedPrincipal;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SecurityUtils {
//
//    public static String getCurrentUserId() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth.getName();
//    }
//
//    public static String getCurrentUserRole() {
//        return SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getAuthorities()
//                .stream()
//                .findFirst()
//                .map(GrantedAuthority::getAuthority)
//                .orElse(null);
//    }
//}
