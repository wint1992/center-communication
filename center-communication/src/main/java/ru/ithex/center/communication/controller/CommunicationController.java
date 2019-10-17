package ru.ithex.center.communication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ithex.baseweb.model.dto.response.ResponseWrapperDTO;
import ru.ithex.baseweb.model.dto.response.error.BadRequestError;
import ru.ithex.baseweb.model.dto.response.error.InternalServerError;
import ru.ithex.center.communication.emailsender.controller.EmailSenderController;
import ru.ithex.center.communication.exception.CommunicationDtoValidationException;
import ru.ithex.center.communication.model.CommunicationDTO;
import ru.ithex.center.communication.model.mapper.EmailMapper;

@Service
public class CommunicationController {
	private static final Logger log = LoggerFactory.getLogger(CommunicationController.class);

	private final EmailSenderController emailSenderController;

	public CommunicationController(EmailSenderController emailSenderController) {
		this.emailSenderController = emailSenderController;
	}

	public ResponseWrapperDTO communicate(CommunicationDTO requestDTO) {
		try {
			switch (requestDTO.getCode()){
				case 200:
					return emailSenderController.sender(EmailMapper.map(requestDTO.getParams()));
			}
			return ResponseWrapperDTO.ok(null);
		} catch (CommunicationDtoValidationException e) {
			log.error("CommunicationService sending error: ", e);
			return ResponseWrapperDTO.error(new BadRequestError(e.getMessage()));
		} catch (Exception e) {
			log.error("CommunicationService sending error: ", e);
			return ResponseWrapperDTO.error(new InternalServerError(e.getMessage()));
		}
	}
}
