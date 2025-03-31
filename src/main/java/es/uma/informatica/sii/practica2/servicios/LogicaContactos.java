package es.uma.informatica.sii.practica2.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uma.informatica.sii.practica2.entidades.Contacto;
import es.uma.informatica.sii.practica2.repositorios.ContactoRepo;
import es.uma.informatica.sii.practica2.servicios.excepciones.ContactoNoEncontrado;

@Service
@Transactional
public class LogicaContactos {
	
	private ContactoRepo repo;
	
	@Autowired
	public LogicaContactos(ContactoRepo repo) {
		this.repo=repo;
	}
	
	public List<Contacto> getTodosContactos() {
		return repo.findAll();
	}
	
	public Contacto createContact(Contacto contact){
		contact.setId(null);
		return repo.save(contact);
	}

	public Optional<Contacto> findById(Long id){
		return repo.findById(id);
	}

	public Contacto updateContact(Long id, Contacto contact){
		if(repo.existsById(id)){
			contact.setId(id);
			return repo.save(contact);

		} else{
			return null;
		}
	}

	public void removeById(Long id){
		repo.deleteById(id);
	}

	public boolean existsContact(Long id){
		return repo.existsById(id);
	}
}
