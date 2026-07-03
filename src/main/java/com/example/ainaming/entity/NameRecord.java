package com.example.ainaming.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * 取名记录实体
 * 映射数据库表 name_record，用于持久化用户取名历史。
 * 启动时若 spring.jpa.hibernate.ddl-auto=update，Hibernate 会自动创建/更新该表结构。
 */
@Entity
@Table(name = "name_record")
public class NameRecord {

    /** 主键 ID，自增 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 姓氏 */
    @Column(length = 10)
    private String surname;

    /** 性别 */
    @Column(length = 10)
    private String gender;

    /** 生成的名字（多个名字以逗号分隔存储，或拆分为子表） */
    @Column(name = "generated_names", length = 1000)
    private String generatedNames;

    /** 创建时间 */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    public NameRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGeneratedNames() {
        return generatedNames;
    }

    public void setGeneratedNames(String generatedNames) {
        this.generatedNames = generatedNames;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
