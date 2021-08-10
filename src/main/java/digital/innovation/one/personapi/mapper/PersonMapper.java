package digital.innovation.one.personapi.mapper;

import org.springframework.web.bind.annotation.Mapping;

import digital.innovation.one.personapi.dto.request.PersonDTO;
import digital.innovation.one.personapi.entity.Person;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toModel(PersonDTO personDTO);

    PersonDTO toDTO(Person person);
}