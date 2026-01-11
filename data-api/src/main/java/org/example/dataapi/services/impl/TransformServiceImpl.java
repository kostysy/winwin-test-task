package org.example.dataapi.services.impl;

import org.example.dataapi.services.TransformService;
import org.springframework.stereotype.Service;

@Service
public class TransformServiceImpl implements TransformService {
    private final String SUFFIX = "_WinWinSufix";

    @Override
    public String transform(String text) {
         return new StringBuilder(text.toUpperCase()).reverse()
                 .append(SUFFIX).toString();
    }
}
