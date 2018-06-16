package ua.social.network.userservice.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * @author Mykola Yashchenko
 */
@GroupSequence({Default.class, AdditionalGroup.class})
public interface ValidationOrder {
}
