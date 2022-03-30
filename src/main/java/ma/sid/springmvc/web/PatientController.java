package ma.sid.springmvc.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ma.sid.springmvc.dao.PatientRepository;
import ma.sid.springmvc.entities.Patient;

@Controller
public class PatientController {
	@Autowired
	private PatientRepository patientRepository;
	@GetMapping(path = "/index")
	public String index() {
		return "index";
	}
	@GetMapping(path = "/patients")
	public String listPatients(Model model,
			@RequestParam(name = "page",defaultValue = "0") int page ,
			@RequestParam(name = "size",defaultValue = "5") int size,
			@RequestParam(name = "keyword",defaultValue = "") String kw) {
		Page<Patient> patients=patientRepository.findByNomContains(kw,PageRequest.of(page, size));
		model.addAttribute("patients",patients.getContent());
		model.addAttribute("pages",new int[patients.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", kw);
		return "patients";
	}
	
	@GetMapping(path = "/delete")
	public String delete(Long id) {
		patientRepository.deleteById(id);
		return "redirect:/patients";
	}
	
	@GetMapping(path ="/formPatient")
	public String formPatient(Model model) {
		model.addAttribute("patient",new Patient());
		return "formPatient";
	}
	
	@PostMapping(path = "/savePatient")
	public String savePatient(@Valid Patient patient,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) return "formPatient";
		patientRepository.save(patient);
		return "redirect:/patients";
	}
	
	@GetMapping(path = "/edit")
	public String editPatient(Model model,Long id) {
		Patient patient=patientRepository.getById(id);
		model.addAttribute("patient",patient);
		return "formPatient";
	}
}
