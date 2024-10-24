package com.project.plogger.entity;

import com.project.plogger.entity.pk.PostTagPk;

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
@Table(name = "post_tag")
@Entity(name = "post_tag")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PostTagPk.class)
public class PostTagEntity {
    
    @Id
    private String userId;
    @Id
    private Integer recruitId;
    private String createdAt;

}
