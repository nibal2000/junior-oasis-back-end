package com.example.junioroasisbackend.utils.converters;

import com.example.junioroasisbackend.utils.enums.Mediable;
import org.springframework.core.convert.converter.Converter;


public class MediableEnumConverter implements Converter<String , Mediable> {
    @Override
    public Mediable convert(String source) {
        try {
            return Mediable.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }

    }
}
