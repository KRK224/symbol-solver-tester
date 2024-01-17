package org.kryun.symbol.pkg.symbolsolver.interfaces.generateresolvedtype;

import com.github.javaparser.resolution.types.ResolvedType;

public interface GenerateClassResolvedType<T> extends GenerateResolvedType {
    public abstract ResolvedType generateResolvedType(T resolvableNode) throws Exception;
}
