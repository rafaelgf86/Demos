/**
 * mx.com.intx.service.validations
 */
package mx.com.intx.service.validations;

import java.util.ArrayList;
import java.util.List;

import mx.com.intx.entities.EmailContactUsEntity;
import mx.com.intx.exceptions.FormException;
import mx.com.intx.faults.FormFault;
import mx.com.intx.service.mail.MailManager;
import mx.com.intx.utils.Utilities;

/**
 * @author INTX
 *
 */
public class ContactUsValidations extends MailManager {

	public void applyAddValidations(EmailContactUsEntity data) throws FormException {
		List<FormFault> formFaults = new ArrayList<FormFault>();

		if (data.getEmail() == null || data.getEmail().isEmpty()) {
			formFaults.add(new FormFault("V_CTU_000", "email", "Email is mandatory"));
		} else if (!Utilities.validatePattern(data.getEmail(), "^([\\w+-.%]+@[\\w-.]+\\.[A-Za-z]{2,}(\\s*,?\\s*)*)+$",
				false)) {
			formFaults.add(new FormFault("V_CTU_001", "email", "It is not a valid email"));
		}

		if (data.getComment() == null || data.getComment().isEmpty()) {
			formFaults.add(new FormFault("V_CTU_002", "comment", "Comment is mandatory"));
		}

		// En caso de que haya errores de formulario lanzar en la excepción
		if (formFaults.size() > 0) {
			throw new FormException("Form Exception", formFaults);
		}
	}
}
