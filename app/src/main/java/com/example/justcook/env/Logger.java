package com.example.justcook.env;

import android.util.Log;
import java.util.HashSet;
import java.util.Set;

/** 편리한 메시지 prefix 지정 및 로그 비활성화를 허용하는 플랫폼 로그 기능의 Wrapper */
public class Logger {
    private static final String DEFAULT_TAG = "tensorflow";
    private static final int DEFAULT_MIN_LOG_LEVEL = Log.DEBUG;

    // 스택 추적을 검사할 때 무시할 클래스
    private static final Set<String> IGNORED_CLASS_NAMES;

    static {
        IGNORED_CLASS_NAMES = new HashSet<String>(3);
        IGNORED_CLASS_NAMES.add("dalvik.system.VMStack");
        IGNORED_CLASS_NAMES.add("java.lang.Thread");
        IGNORED_CLASS_NAMES.add(Logger.class.getCanonicalName());
    }

    private final String tag;
    private final String messagePrefix;
    private int minLogLevel = DEFAULT_MIN_LOG_LEVEL;

    /**
     * 클래스 이름을 메시지 prefix로 사용하여 Logger를 만듭니다.
     *
     * @param clazz the simple name of this class is used as the message prefix.
     */
    public Logger(final Class<?> clazz) {
        this(clazz.getSimpleName());
    }

    /**
     * 지정된 메시지 prefix로 사용하여 Logger를 만듭니다.
     *
     * @param messagePrefix is prepended to the text of every message.
     */
    public Logger(final String messagePrefix) {
        this(DEFAULT_TAG, messagePrefix);
    }

    /**
     * 사용자 지정 태그와 사용자 지정 메시지 prefix를 사용하여 Logger를 만듭니다.
     * 메시지 접두사가 <pre>null</pre>로 설정된 경우,
     * 호출자의 클래스 이름이 prefix로 사용됩니다.
     *
     * @param tag identifies the source of a log message.
     * @param messagePrefix prepended to every message if non-null. If null, the name of the caller is
     *     being used
     */
    public Logger(final String tag, final String messagePrefix) {
        this.tag = tag;
        final String prefix = messagePrefix == null ? getCallerSimpleName() : messagePrefix;
        this.messagePrefix = (prefix.length() > 0) ? prefix + ": " : prefix;
    }

    /** 발신자의 클래스 이름을 메시지 prefix로 사용하여 Logger를 만듭니다. */
    public Logger() {
        this(DEFAULT_TAG, null);
    }

    /** 발신자의 클래스 이름을 메시지 prefix로 사용하여 Logger를 만듭니다. */
    public Logger(final int minLogLevel) {
        this(DEFAULT_TAG, null);
        this.minLogLevel = minLogLevel;
    }

    /**
     * 발신자의 simple name을 리턴합니다.
     *
     * <p>Android getStackTrace() returns an array that looks like this: stackTrace[0]:
     * dalvik.system.VMStack stackTrace[1]: java.lang.Thread stackTrace[2]:
     * com.google.android.apps.unveil.env.UnveilLogger stackTrace[3]:
     * com.google.android.apps.unveil.BaseApplication
     *
     * <p>This function returns the simple version of the first non-filtered name.
     *
     * @return caller's simple name
     */
    private static String getCallerSimpleName() {
        // 현재 호출 스택을 가져와 호출자 클래스를 제거할 수 있습니다.
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (final StackTraceElement elem : stackTrace) {
            final String className = elem.getClassName();
            if (!IGNORED_CLASS_NAMES.contains(className)) {
                // 우리는 클래스의 simple name에만 관심이 있고, 완전한 패키지에는 관심이 없습니다.
                final String[] classParts = className.split("\\.");
                return classParts[classParts.length - 1];
            }
        }

        return Logger.class.getSimpleName();
    }

    public void setMinLogLevel(final int minLogLevel) {
        this.minLogLevel = minLogLevel;
    }

    public boolean isLoggable(final int logLevel) {
        return logLevel >= minLogLevel || Log.isLoggable(tag, logLevel);
    }

    private String toMessage(final String format, final Object... args) {
        return messagePrefix + (args.length > 0 ? String.format(format, args) : format);
    }

    public void v(final String format, final Object... args) {
        if (isLoggable(Log.VERBOSE)) {
            Log.v(tag, toMessage(format, args));
        }
    }

    public void v(final Throwable t, final String format, final Object... args) {
        if (isLoggable(Log.VERBOSE)) {
            Log.v(tag, toMessage(format, args), t);
        }
    }

    public void d(final String format, final Object... args) {
        if (isLoggable(Log.DEBUG)) {
            Log.d(tag, toMessage(format, args));
        }
    }

    public void d(final Throwable t, final String format, final Object... args) {
        if (isLoggable(Log.DEBUG)) {
            Log.d(tag, toMessage(format, args), t);
        }
    }

    public void i(final String format, final Object... args) {
        if (isLoggable(Log.INFO)) {
            Log.i(tag, toMessage(format, args));
        }
    }

    public void i(final Throwable t, final String format, final Object... args) {
        if (isLoggable(Log.INFO)) {
            Log.i(tag, toMessage(format, args), t);
        }
    }

    public void w(final String format, final Object... args) {
        if (isLoggable(Log.WARN)) {
            Log.w(tag, toMessage(format, args));
        }
    }

    public void w(final Throwable t, final String format, final Object... args) {
        if (isLoggable(Log.WARN)) {
            Log.w(tag, toMessage(format, args), t);
        }
    }

    public void e(final String format, final Object... args) {
        if (isLoggable(Log.ERROR)) {
            Log.e(tag, toMessage(format, args));
        }
    }

    public void e(final Throwable t, final String format, final Object... args) {
        if (isLoggable(Log.ERROR)) {
            Log.e(tag, toMessage(format, args), t);
        }
    }
}