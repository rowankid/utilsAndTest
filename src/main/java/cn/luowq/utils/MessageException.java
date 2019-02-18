package cn.luowq.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author: rowan
 * @Date: 2019/2/18 17:15
 * @Description:
 */
public class MessageException extends RuntimeException {
    private final String l10nKey;
    private final Collection<Object> l10nParams;

    protected MessageException(String s) {
        this(s, (String)null, (Object[])null);
    }

    private MessageException(String message, String l10nKey, Object[] l10nParams) {
        super(message);
        this.l10nKey = l10nKey;
        this.l10nParams = l10nParams == null ? Collections.emptyList() : Arrays.asList(l10nParams);
    }

    private MessageException(String message, Throwable cause) {
        super(message, cause);
        this.l10nKey = null;
        this.l10nParams = Collections.emptyList();
    }

    public static MessageException of(String message, Throwable cause) {
        return new MessageException(message, cause);
    }

    public static MessageException of(String message) {
        return new MessageException(message);
    }

    public static MessageException ofL10n(String l10nKey, Object... l10nParams) {
        return new MessageException((String)null, l10nKey, l10nParams);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

    public String l10nKey() {
        return this.l10nKey;
    }

    public Collection<Object> l10nParams() {
        return this.l10nParams;
    }
}
