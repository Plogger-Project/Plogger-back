package com.project.plogger.entity;

import com.project.plogger.entity.pk.RecruitJoinPk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recruit_join")
@Entity(name = "recruit_join")
@IdClass(RecruitJoinPk.class)
public class RecruitJoinEntity {
    
    @Id
    private String userId;
    @Id
    private Integer recruitId;

}
