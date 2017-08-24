package ua.social.network.transaction;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mykola Yashchenko
 */
@Component
public class TransactionHelper {

    @Transactional
    public <T> T doInTransaction(Callable<T> function) {
        try {
            return function.call();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
