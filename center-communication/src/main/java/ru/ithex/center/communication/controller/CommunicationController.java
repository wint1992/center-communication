package ru.ithex.center.communication.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.ithex.center.communication.emailsender.controller.EmailSenderController;
import ru.ithex.center.communication.emailsender.model.dto.EmailDTO;
import ru.ithex.center.communication.model.CommunicationDTO;
import ru.ithex.center.communication.model.mapper.EmailMapper;

@Service
@RestController
@CrossOrigin("*")
@RequestMapping("/api/communicate")
public class CommunicationController {
	private final EmailSenderController emailSenderController;

	public CommunicationController(EmailSenderController emailSenderController) {
		this.emailSenderController = emailSenderController;
	}

	@PostMapping()
	public void communicate(
			@RequestBody CommunicationDTO requestDTO) {
		switch (requestDTO.getCode()){
			case 200:
				sendEmail(EmailMapper.map(requestDTO.getParams()));
		}
	}

	@PostMapping("/email")
	public void sendEmail(
			@RequestBody EmailDTO requestDTO) {
		emailSenderController.sender(requestDTO);
	}
}
