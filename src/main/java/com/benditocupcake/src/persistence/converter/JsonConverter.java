package com.benditocupcake.src.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Converter
@RequiredArgsConstructor
public class JsonConverter implements AttributeConverter<List<Map<String, Object>>, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Map<String, Object>> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao converter lista de ingredientes para JSON", e);
        }
    }

    @Override
    public List<Map<String, Object>> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Map<String, Object>>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Erro ao converter JSON para lista de ingredientes", e);
        }
    }
}