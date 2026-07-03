package com.example.ainaming.service.impl;

import com.example.ainaming.dto.HistoryItem;
import com.example.ainaming.entity.NameRecord;
import com.example.ainaming.repository.NameRecordRepository;
import com.example.ainaming.service.HistoryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 历史记录 Service 实现类
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final NameRecordRepository nameRecordRepository;

    public HistoryServiceImpl(NameRecordRepository nameRecordRepository) {
        this.nameRecordRepository = nameRecordRepository;
    }

    @Override
    public List<HistoryItem> listHistory() {
        List<NameRecord> records = nameRecordRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
        return records.stream()
                .map(record -> new HistoryItem(
                        record.getId(),
                        record.getSurname(),
                        record.getGender(),
                        record.getGeneratedNames(),
                        record.getCreateTime() == null ? "" : record.getCreateTime().format(FORMATTER)
                ))
                .collect(Collectors.toList());
    }
}
