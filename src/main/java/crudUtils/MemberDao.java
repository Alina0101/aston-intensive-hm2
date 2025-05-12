package crudUtils;

import entities.Member;

import java.util.List;

public interface MemberDao {
    void save(Member book);

    Member findById(Long id);

    List<Member> findAll();

    void update(Member book);

    void delete(Long id);
}