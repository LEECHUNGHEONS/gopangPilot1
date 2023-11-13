package com.gopang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authority")
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "authority_name",length = 50)
    private String authorityName;

    @OneToMany(mappedBy = "authority")
    private List<CustumUserDetails> custumUserDetails = new ArrayList<>();
}
