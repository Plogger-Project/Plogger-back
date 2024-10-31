package com.project.plogger.entity;

import com.project.plogger.dto.request.follow.PostFollowRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "follow")
@Table(name = "follow")
public class FollowEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer followId;
    private String followerId;
    private String followeeId;

    public FollowEntity(PostFollowRequestDto dto) {
        this.followeeId = dto.getFolloweeId();
    }

}
