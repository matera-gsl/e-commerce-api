package com.roadmap.ecommerce.util;

public class MoneyParser {
    public static Integer toCents(Object value) {
        if (value instanceof Double d)
            return (int) Math.round(d * 100);
        if (value instanceof String s) {
            String clean = s.replaceAll("[^0-9,.]", "").replace(",", ".");
            return (int) Math.round(Double.parseDouble(clean) * 100);
        }
        return 0;
    }

    public static String format(Integer cents) {
        return String.format("R$ %.2f", cents / 100.0);
    }
}
