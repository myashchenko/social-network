package ua.social.network.notification.api;

/**
 * @author Mykola Yashchenko
 */
public enum EmailTemplate {
    WELCOME("welcome");

    private final String template;

    EmailTemplate(final String template) {
        this.template = template;
    }

    public String template() {
        return template;
    }
}
