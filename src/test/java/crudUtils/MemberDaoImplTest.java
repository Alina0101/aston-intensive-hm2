package crudUtils;

import entities.Member;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberDaoImplTest {

    private static MemberDao memberDao;

    @BeforeAll
    public static void setup() {
        memberDao = new MemberDaoImpl();
    }

    @Test
    @Order(1)
    public void testSave() {
        Member member = new Member(
                "Иван Иванов",
                "ivan@example.com",
                LocalDate.of(2023, 1, 10)
        );
        memberDao.save(member);

        assertNotNull(member.getId(), "ID пользователя должен быть присвоен");
    }

    @Test
    @Order(2)
    public void testFindById() {
        Member member = new Member(
                "Мария Петрова",
                "maria@example.com",
                LocalDate.of(2022, 5, 20)
        );
        memberDao.save(member);

        Member found = memberDao.findById(member.getId());

        assertNotNull(found, "Пользователь должен быть найден");
        assertEquals(member.getEmail(), found.getEmail(), "Email должен совпадать");
    }

    @Test
    @Order(3)
    public void testFindAll() {
        List<Member> members = memberDao.findAll();

        assertFalse(members.isEmpty(), "Список пользователей не должен быть пустым");
    }

    @Test
    @Order(4)
    public void testUpdate() {
        Member member = new Member(
                "Старое имя",
                "update@example.com",
                LocalDate.of(2021, 3, 5)
        );
        memberDao.save(member);

        member.setName("Новое имя");
        memberDao.update(member);

        Member updated = memberDao.findById(member.getId());

        assertEquals("Новое имя", updated.getName(), "Имя должно быть обновлено");
    }

    @Test
    @Order(5)
    public void testDelete() {
        Member member = new Member(
                "Удаляемый пользователь",
                "delete@example.com",
                LocalDate.of(2020, 7, 15)
        );
        memberDao.save(member);
        Long id = member.getId();

        memberDao.delete(id);

        assertNull(memberDao.findById(id), "Пользователь должен быть удалён");
    }
}
