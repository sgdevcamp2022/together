package com.example.userservice.repository;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;


    @Builder
    public FriendEntity(String name,
                         String email){
        this.name = name;
        this.email = email;
    }

    public static FriendEntity addFriend(String name,
                                         String email) {
        return FriendEntity.builder()
                .name(name)
                .email(email)
                .build();
    }
}
