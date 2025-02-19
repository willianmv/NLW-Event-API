package br.com.nlw.events.dto;

import java.util.List;

public record ErrorMessage(Integer statusCode, String message, List<ErroCampo>errorsList) {
}
