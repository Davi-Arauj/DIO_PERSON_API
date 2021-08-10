package digital.innovation.one.personapi.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import digital.innovation.one.personapi.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}