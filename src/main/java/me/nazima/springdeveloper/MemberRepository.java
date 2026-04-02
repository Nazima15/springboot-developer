package me.nazima.springdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // save(), findById(id), findAll(), deleteById(id) 등 사용 가능
    public Optional<Member> findByName(String name);
}