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
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "tbr_id")
    private UserEntity m_user;


    @Builder
    public FriendEntity(String name,
                         String email,
                        UserEntity user){
        this.name = name;
        this.email = email;
        this.m_user = user;
    }

    public static FriendEntity addFriend(String name,
                                         String email,
                                         UserEntity user) {
        return FriendEntity.builder()
                .name(name)
                .email(email)
                .user(user)
                .build();
    }
}
