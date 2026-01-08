package io.github.gabitxt.gerenciamentousuario.mapper;

/**
 * Interface para conversão genérica entre objetos.
 */
public interface Mapper<S, T> {

    T toTarget(S source);

    S toSource(T target);
}

