package ua.social.network.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Mykola Yashchenko
 */
@Component
public class TransactionHelper {

    @Transactional
    public <T> T doInTransaction(Func<T> function) {
        return function.execute();
    }

    @FunctionalInterface
    public interface Func<T> {
        T execute();
    }
}
