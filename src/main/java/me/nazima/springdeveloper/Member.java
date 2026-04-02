package me.nazima.springdeveloper;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED) //아무 인자 없는 생성자
@AllArgsConstructor //id, name 있는 생성자
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;

    public void changeName(String name){
        this.name=name;
    }

    public Member(String name){
        this.name=name;
    }
}