package com.project.plogger.entity;

import jakarta.persistence.Entity;
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
@Table(name = "tel_auth")
@Entity(name = "tel_auth")
public class TelAuth {
    
    @Id
    private String telNumber;
    private String authNumber;

}
