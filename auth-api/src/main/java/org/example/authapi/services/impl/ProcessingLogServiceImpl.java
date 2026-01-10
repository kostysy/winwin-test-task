package org.example.authapi.services.impl;

import org.example.authapi.entities.ProcessingLog;
import org.example.authapi.repositories.ProcessingLogRepository;
import org.example.authapi.services.ProcessingLogService;

public class ProcessingLogServiceImpl implements ProcessingLogService {
    private final ProcessingLogRepository logRepository;

    public ProcessingLogServiceImpl(ProcessingLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public ProcessingLog create(ProcessingLog log) {
        return logRepository.save(log);
    }
}
