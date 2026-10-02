package com.booknook.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HtmlTest {

    @Test
    public void escapesMarkupCharacters() {
        assertEquals("&lt;script&gt;alert(&#39;x&#39;)&lt;/script&gt;", Html.escape("<script>alert('x')</script>"));
        assertEquals("a &amp; &quot;b&quot;", Html.escape("a & \"b\""));
    }

    @Test
    public void handlesNullAndNumbers() {
        assertEquals("", Html.escape(null));
        assertEquals("8.75", Html.escape(8.75));
    }
}
