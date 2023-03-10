package com.example.userservice.repository;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tbr_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = true)
    private String encryptedPwd;


    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<FriendEntity> friendList = new ArrayList<>();

    public void deleteFriend(String email){
        this.friendList.stream()
                .filter(o->o.getEmail().equals(email))
                .collect(Collectors.toList())
                .forEach(li->{this.friendList.remove(li);}
                );
    }

    public void addFriend(FriendEntity friend){
        this.friendList.add(friend);
    }
}
