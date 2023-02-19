package com.naturalprogrammer;

import io.jbock.util.Either;

import java.util.Optional;
import java.util.function.Supplier;

public class EitherUtils {

    private EitherUtils() {}

    public static <L, R> Either<L, R> of(Optional<R> possibleValue, Supplier<L> errorSupplier) {
        return possibleValue.map(Either::<L, R>right).orElseGet(() -> Either.<L, R>left(errorSupplier.get()));
    }
}
