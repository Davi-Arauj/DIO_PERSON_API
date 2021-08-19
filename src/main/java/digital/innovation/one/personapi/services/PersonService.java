package digital.innovation.one.personapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import digital.innovation.one.personapi.dto.request.PersonDTO;
import digital.innovation.one.personapi.dto.response.MessageResponseDTO;
import digital.innovation.one.personapi.entity.Person;
import digital.innovation.one.personapi.exception.PersonNotFoundException;
import digital.innovation.one.personapi.mapper.PersonMapper;
import digital.innovation.one.personapi.repositorys.PersonRepository;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {
	
    private PersonRepository personRepository;
	
    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    
    //Criando uma nova Pessoa.
    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with ID ");
    }
    //Buscando todas as pessoas e transformando em DTO.
    public List<PersonDTO> listAll() {
           List<Person> allPeople = personRepository.findAll();
    	   return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    //Buscando uma pessoa por o ID, mais antes verifica se ela existe.
    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);
        return personMapper.toDTO(person);
    }
    //Deletando uma pessoa por o ID, mais antes verifica se ela existe.
    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);
    }
    //Atualizando uma pessoa, primeiro verifica se existe e assim atualiza, se não existir ela será criada.
    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated person with ID ");
    }
    //Metodo verificar se pessoa existe para nos auxiliar no desenvolvimento.
    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
    //Metodo criar menssagem de resposta.
    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
        		.builder()
                .message(message + id)
                .build();
    }
}
