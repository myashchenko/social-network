package ua.social.network.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.social.network.action.Action;
import ua.social.network.common.Message;
import ua.social.network.exception.ServiceException;

import java.lang.reflect.Field;

/**
 * @author Mykola Yashchenko
 */
public abstract class AbstractService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);
    private static final String STATUS_FIELD = "status";

    protected <RES> RES process(Action<RES> action, Class<RES> resClass) {
        try {
            return action.execute();
        } catch (ServiceException se) {
            return process(resClass, se.getStatusMessage(), se.getStatusArgs());
        } catch (Exception e) {
            return process(resClass, Message.SYSTEM_ERROR);
        }
    }

    protected <RES> RES processSuccess(Class<RES> resClass) {
        try {
            RES res = resClass.newInstance();
            Status status = new Status();
            status.setCode(Code.SUCCESS);
            Field statusField = resClass.getField(STATUS_FIELD);
            statusField.set(res, status);
            return res;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException();
        }
    }

    private <RES> RES process(Class<RES> resClass, Message message, String... args) {
        try {
            Status status = new Status();
            status.setCode(message.getCode());
            status.setMessage(message.getMessage(args));
            RES instance = resClass.newInstance();
            Field statusField = resClass.getField(STATUS_FIELD);
            statusField.set(instance, status);
            return instance;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException();
        }
    }
}
