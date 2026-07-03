package com.example.ainaming.repository;

import com.example.ainaming.entity.NameRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * NameRecord 数据访问接口
 * 继承 JpaRepository 后，自动获得增删改查等基础方法（save、findById、findAll 等），
 * 不需要手写 SQL。当前项目暂未在 Service 层调用它，作为后续扩展预留。
 */
public interface NameRecordRepository extends JpaRepository<NameRecord, Long> {
}
