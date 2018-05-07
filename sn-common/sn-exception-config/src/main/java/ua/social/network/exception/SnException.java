package ua.social.network.exception;

import org.springframework.context.MessageSourceResolvable;

/**
 * @author Mykola Yashchenko
 */
public class SnException extends RuntimeException implements MessageSourceResolvable {

    private final SnExceptionDetails exceptionDetails;
    private final String[] arguments;

    public SnException(final SnExceptionDetails exceptionDetails, final String... arguments) {
        super(exceptionDetails.code());

        this.exceptionDetails = exceptionDetails;
        this.arguments = arguments;
    }

    public SnExceptionDetails exceptionDetails() {
        return exceptionDetails;
    }

    @Override
    public String[] getCodes() {
        return new String[] {exceptionDetails.code()};
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public String getDefaultMessage() {
        return "";
    }
}
