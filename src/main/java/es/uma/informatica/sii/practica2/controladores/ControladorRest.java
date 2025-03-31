package es.uma.informatica.sii.practica2.controladores;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import es.uma.informatica.sii.practica2.entidades.Contacto;
import es.uma.informatica.sii.practica2.servicios.LogicaContactos;
import es.uma.informatica.sii.practica2.servicios.excepciones.ContactoNoEncontrado;

@RestController
@RequestMapping("/api/agenda/contactos")
public class ControladorRest {
	private LogicaContactos servicio;

	public ControladorRest(LogicaContactos servicioContactos) {
		servicio = servicioContactos;
	}

	// GET: Returns the list of contacts
	@GetMapping
	public ResponseEntity<List<Contacto>> listaDeContactos() {
		return ResponseEntity.ok(servicio.getTodosContactos());
	}

	// POST: Creates a new contact
	@PostMapping
	public ResponseEntity<?> addContact(@RequestBody Contacto contact, UriComponentsBuilder builder) {
		servicio.createContact(contact);
		URI uri = builder
				.path("/api")
				.path("/agenda")
				.path("/contactos")
				.path(String.format("/%d",contact.getId()))
				.build()
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	// GET: Returns information about a contact
	@GetMapping("{id}")
	public ResponseEntity<Contacto> getContact(@PathVariable(name = "id") Long id){
		Optional<Contacto> contact = servicio.findById(id);
		return ResponseEntity.of(contact);
	}

	// PUT: Modifies the information of a contact
	@PutMapping("{id}")
	public ResponseEntity<Contacto> modifyContact(@PathVariable(name = "id") Long id, @RequestBody Contacto contactMod){
		Contacto updatedContact = servicio.updateContact(id, contactMod);
		return updatedContact != null ? ResponseEntity.ok(updatedContact) : ResponseEntity.notFound().build();
	}

	// DELETE: Removes a contact
	@DeleteMapping("{id}")
	public ResponseEntity<Void> removeContactById(@PathVariable(name = "id") Long id){
		if(!servicio.existsContact(id)){
			return ResponseEntity.notFound().build();
		}
		servicio.removeById(id);
		return ResponseEntity.ok().build();
	}
}
