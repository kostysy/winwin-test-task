package org.example.dataapi.services.impl;

import org.example.dataapi.services.TransformService;
import org.springframework.stereotype.Service;

@Service
public class TransformServiceImpl implements TransformService {
    private final String SUFIX = "_WinWinTravel";

    @Override
    public String transform(String text) {
         return new StringBuilder(text.toUpperCase()).reverse()
                 .append(SUFIX).toString();
    }
}
