package org.kodigo.bookings.service.booking.codegen;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public final class CodeGenerator implements IBookingCodeGenerator {

    private final int randomChars;
    private final String prefix;
    private final String separator;
    private final Predicate<String> existsPredicate; // returns true if code already exists
    private final int maxAttempts;

    public CodeGenerator(int randomChars,
                                 String prefix,
                                 String separator,
                                 Predicate<String> existsPredicate,
                                 int maxAttempts) {
        if (randomChars < 2) throw new IllegalArgumentException("randomChars >= 2");
        if (maxAttempts < 1) throw new IllegalArgumentException("maxAttempts >= 1");
        this.randomChars = randomChars;
        this.prefix = prefix == null ? "" : prefix;
        this.separator = separator == null ? "" : separator;
        this.existsPredicate = Objects.requireNonNull(existsPredicate, "existsPredicate");
        this.maxAttempts = maxAttempts;
    }

    /** Factory sin verificación de colisiones (solo para demos/tests). */
    public static CodeGenerator simple(int randomChars, String prefix, String separator) {
        return new CodeGenerator(
                randomChars,
                prefix,
                separator,
                code -> false, // no collision check
                1
        );
    }

    @Override
    public String nextCode() {
        for (int i = 0; i < maxAttempts; i++) {
            String body = base36Timestamp() + base36Random(randomChars);
            String code = prefix.isEmpty() ? body : prefix + (separator.isEmpty() ? "" : separator) + body;
            code = code.toUpperCase();
            if (!existsPredicate.test(code)) return code;
        }
        throw new IllegalStateException("Unable to generate unique code after " + maxAttempts + " attempts");
    }

    private String base36Timestamp() {
        long now = System.currentTimeMillis();
        return Long.toString(now, 36).toUpperCase();
    }

    private String base36Random(int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            int n = ThreadLocalRandom.current().nextInt(36);
            sb.append(Character.forDigit(n, 36));
        }
        return sb.toString().toUpperCase();
    }
}
