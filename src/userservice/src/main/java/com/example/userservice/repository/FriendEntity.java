package com.example.userservice.repository;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "friend")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tbr_id")
    @JsonIgnore
    private UserEntity user;

    @Builder
    public FriendEntity(String name,
                         String email,
                        UserEntity user){
        this.name = name;
        this.email = email;
        this.user = user;
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
