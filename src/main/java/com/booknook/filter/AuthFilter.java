package com.booknook.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * Restricts admin pages to admins and customer pages to logged-in users.
 * Every servlet and JSP must be listed in one of the sets below (enforced by AuthFilterTest).
 */
@WebFilter("/*")
public class AuthFilter extends HttpFilter {
    private static final long serialVersionUID = 1L;

    public enum Access {
        ALLOW,
        LOGIN_REQUIRED,
        FORBIDDEN
    }

    static final Set<String> PUBLIC_PATHS = Set.of(
            "/",
            "/home.jsp",
            "/login.jsp",
            "/register.jsp",
            "/search.jsp",
            "/LoginServlet",
            "/LogoutServlet",
            "/RegisterServlet",
            "/SearchServlet");

    static final Set<String> USER_PATHS = Set.of(
            "/user-dashboard.jsp",
            "/cart.jsp",
            "/order-confirmation.jsp",
            "/order-history.jsp",
            "/AddToCartServlet",
            "/RemoveFromCartServlet",
            "/CheckoutServlet",
            "/OrderHistoryServlet");

    static final Set<String> ADMIN_PATHS = Set.of(
            "/admin-dashboard.jsp",
            "/manage-products.jsp",
            "/add-product.jsp",
            "/edit-product.jsp",
            "/manage-categories.jsp",
            "/add-category.jsp",
            "/edit-category.jsp",
            "/manage-users.jsp",
            "/edit-user.jsp",
            "/ManageProductsServlet",
            "/AddProductServlet",
            "/EditProductServlet",
            "/DeleteProductServlet",
            "/ManageCategoriesServlet",
            "/AddCategoryServlet",
            "/EditCategoryServlet",
            "/DeleteCategoryServlet",
            "/ManageUsersServlet",
            "/EditUserServlet",
            "/DeleteUserServlet");

    // decide access for a path given the role in the session (null when not logged in)
    public static Access check(String path, String role) {
        if (ADMIN_PATHS.contains(path)) {
            if (role == null) {
                return Access.LOGIN_REQUIRED;
            }
            return "admin".equals(role) ? Access.ALLOW : Access.FORBIDDEN;
        }
        if (USER_PATHS.contains(path)) {
            return role == null ? Access.LOGIN_REQUIRED : Access.ALLOW;
        }
        return Access.ALLOW;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String role = session == null ? null : (String) session.getAttribute("role");

        switch (check(request.getServletPath(), role)) {
            case LOGIN_REQUIRED:
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;
            case FORBIDDEN:
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                break;
            default:
                chain.doFilter(request, response);
        }
    }
}
