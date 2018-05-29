package ua.social.network.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * @author Mykola Yashchenko
 */
@GroupSequence({Default.class, AdditionalGroup.class})
public interface ValidationOrder {
}
