package com.project.plogger.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitJoinPk implements Serializable {
    
    @Column(name = "user_id")
    private String userId;
    @Column(name = "recruit_id")
    private Integer recruitId;

}
