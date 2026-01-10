package org.example.authapi.services;

import org.example.authapi.entities.ProcessingLog;
import org.springframework.stereotype.Service;

@Service
public interface ProcessingLogService {
    ProcessingLog create(ProcessingLog log);
}
