package com.booknook.filter;

import static org.junit.jupiter.api.Assertions.*;

import com.booknook.filter.AuthFilter.Access;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class AuthFilterTest {

    @Test
    public void adminPagesRequireAdminRole() {
        assertEquals(Access.LOGIN_REQUIRED, AuthFilter.check("/DeleteUserServlet", null));
        assertEquals(Access.FORBIDDEN, AuthFilter.check("/DeleteUserServlet", "user"));
        assertEquals(Access.ALLOW, AuthFilter.check("/DeleteUserServlet", "admin"));
        assertEquals(Access.FORBIDDEN, AuthFilter.check("/manage-users.jsp", "user"));
    }

    @Test
    public void customerPagesRequireLogin() {
        assertEquals(Access.LOGIN_REQUIRED, AuthFilter.check("/CheckoutServlet", null));
        assertEquals(Access.ALLOW, AuthFilter.check("/CheckoutServlet", "user"));
        assertEquals(Access.LOGIN_REQUIRED, AuthFilter.check("/cart.jsp", null));
    }

    @Test
    public void publicPagesAreOpen() {
        assertEquals(Access.ALLOW, AuthFilter.check("/SearchServlet", null));
        assertEquals(Access.ALLOW, AuthFilter.check("/login.jsp", null));
    }

    // fails when a new servlet or JSP is added without deciding who may access it
    @Test
    public void everyServletAndPageIsClassified() throws IOException, URISyntaxException, ClassNotFoundException {
        List<String> routes = new ArrayList<>();

        Path servletDir =
                Paths.get(AuthFilter.class.getResource("/com/booknook/servlet").toURI());
        try (Stream<Path> files = Files.list(servletDir)) {
            for (Path file : files.collect(Collectors.toList())) {
                String name = file.getFileName().toString();
                if (name.endsWith(".class") && !name.contains("$")) {
                    Class<?> type = Class.forName("com.booknook.servlet." + name.replace(".class", ""));
                    WebServlet mapping = type.getAnnotation(WebServlet.class);
                    if (mapping != null) {
                        routes.addAll(List.of(mapping.value()));
                    }
                }
            }
        }
        try (Stream<Path> files = Files.list(Paths.get("src/main/webapp"))) {
            files.map(f -> "/" + f.getFileName())
                    .filter(f -> f.endsWith(".jsp"))
                    .forEach(routes::add);
        }

        assertTrue(routes.size() > 30, "expected to discover all servlets and JSPs");
        for (String route : routes) {
            boolean classified = AuthFilter.PUBLIC_PATHS.contains(route)
                    || AuthFilter.USER_PATHS.contains(route)
                    || AuthFilter.ADMIN_PATHS.contains(route);
            assertTrue(classified, route + " is not listed in AuthFilter");
        }
    }
}
