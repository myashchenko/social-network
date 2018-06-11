package ua.social.network.queue.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Object payload;
    private String className;

    public Message(final Object payload) {
        this.payload = payload;
        this.className = payload.getClass().getName();
    }
}
