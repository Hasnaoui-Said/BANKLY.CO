package co.bankly.wallet.exception.constraint;

import javax.validation.Payload;

public @interface ValidUsername {
    String message() default "Username must not contain spaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
