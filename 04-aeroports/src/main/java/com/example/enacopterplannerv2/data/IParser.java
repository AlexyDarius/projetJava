package com.example.enacopterplannerv2.data;

import java.util.List;

/**
 * Cette interface définit l'implémentation de la méthode de parsing pour les parser
 * @author alexyroman
 */
public interface IParser {

    default List<?> parse(String data) {
        return null;
    }

}
